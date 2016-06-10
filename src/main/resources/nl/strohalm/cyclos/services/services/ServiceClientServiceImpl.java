/*
    This file is part of Cyclos (www.cyclos.org).
    A project of the Social Trade Organisation (www.socialtrade.org).

    Cyclos is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Cyclos is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cyclos; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */
package nl.strohalm.cyclos.services.services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.strohalm.cyclos.dao.services.ServiceClientDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.accounts.AccountType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferType;
import nl.strohalm.cyclos.entities.accounts.transactions.TransferTypeQuery;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.entities.services.ServiceClientQuery;
import nl.strohalm.cyclos.entities.services.ServiceOperation;
import nl.strohalm.cyclos.services.access.ChannelServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.services.transactions.TransactionContext;
import nl.strohalm.cyclos.services.transfertypes.TransferTypeServiceLocal;
import nl.strohalm.cyclos.utils.DataIteratorHelper;
import nl.strohalm.cyclos.utils.InternetAddressHelper;
import nl.strohalm.cyclos.utils.InternetAddressHelper.AddressType;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.query.QueryParameters.ResultType;
import nl.strohalm.cyclos.utils.validation.GeneralValidation;
import nl.strohalm.cyclos.utils.validation.InvalidError;
import nl.strohalm.cyclos.utils.validation.PropertyValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Implementation for ServiceClientService
 * @author luis
 */
public class ServiceClientServiceImpl implements ServiceClientServiceLocal {

    /**
     * Validates a hostname so that it may be a qualified host name, an IP address or a IP address range in form A.B.C.D-E
     * @author luis
     */
    private class HostnameValidation implements PropertyValidation {
        private static final long serialVersionUID = 8330071155849944811L;

        @Override
        public ValidationError validate(final Object object, final Object property, final Object value) {
            if (value == null || "".equals(value)) {
                return null;
            }
            final String address = value.toString();
            final AddressType addressType = InternetAddressHelper.resolveAddressType(address);
            if (addressType == null) {
                return new InvalidError();
            }
            if (!resolveAddress((ServiceClient) object)) {
                return new InvalidError();
            }
            return null;
        }
    };

    private static class TransferTypeNameComparator implements Comparator<TransferType> {
        @Override
        public int compare(final TransferType tt1, final TransferType tt2) {
            return tt1.getName().compareTo(tt2.getName());
        }
    }

    private static Comparator<TransferType> TT_COMPARATOR      = new TransferTypeNameComparator();

    private static List<String>             FORBIDDEN_CHANNELS = Arrays.asList(Channel.WEB, Channel.WAP1, Channel.WAP2, Channel.POSWEB, Channel.POS);
    private ServiceClientDAO                serviceClientDao;
    private TransferTypeServiceLocal        transferTypeService;
    private ChannelServiceLocal             channelService;
    private FetchServiceLocal               fetchService;

    @Override
    public int delete(final Long... ids) {
        return serviceClientDao.delete(ids);
    }

    @Override
    public ServiceClient findByAddressAndCredentials(final String address, final String username, final String password) {
        final ServiceClientQuery query = new ServiceClientQuery();
        query.fetch(RelationshipHelper.nested(ServiceClient.Relationships.MEMBER, Element.Relationships.GROUP));
        query.setUniqueResult();
        query.setAddress(address);
        query.setUsername(username);
        query.setPassword(password);
        final List<ServiceClient> list = search(query);
        if (list.isEmpty()) {
            throw new EntityNotFoundException(ServiceClient.class);
        }
        return list.iterator().next();
    }

    @Override
    public List<Channel> listPossibleChannels() {
        final List<Channel> channels = new ArrayList<Channel>(channelService.list());
        for (final Iterator<Channel> iterator = channels.iterator(); iterator.hasNext();) {
            final Channel channel = iterator.next();
            if (FORBIDDEN_CHANNELS.contains(channel.getInternalName())) {
                iterator.remove();
            }
        }
        return channels;
    }

    @Override
    public List<TransferType> listPossibleDoPaymentTypes(final ServiceClient client) {
        final Channel channel = client.getChannel();
        if (channel == null) {
            return Collections.emptyList();
        }
        final TransferTypeQuery query = new TransferTypeQuery();
        query.setContext(TransactionContext.PAYMENT);
        query.setChannel(channel.getInternalName());
        Member member = client.getMember();
        if (member != null) {
            member = fetchService.fetch(member, Element.Relationships.GROUP);
            query.setFromOwner(member);
        }
        final List<TransferType> tTypes = transferTypeService.search(query);

        // the System to System payments are a kind of self payment transfers
        if (member == null) {
            query.setContext(TransactionContext.SELF_PAYMENT);
            tTypes.addAll(transferTypeService.search(query));
        }
        Collections.sort(tTypes, TT_COMPARATOR);
        return tTypes;

    }

    @Override
    public List<TransferType> listPossibleReceivePaymentTypes(final ServiceClient client) {
        Member member = client.getMember();
        final Channel channel = client.getChannel();
        if (member == null || channel == null) {
            return Collections.emptyList();
        }
        final TransferTypeQuery query = new TransferTypeQuery();
        query.setContext(TransactionContext.PAYMENT);
        query.setChannel(channel.getInternalName());
        member = fetchService.fetch(member, Element.Relationships.GROUP);
        query.setToOwner(member);
        query.setFromNature(AccountType.Nature.MEMBER);
        return transferTypeService.search(query);
    }

    @Override
    public ServiceClient load(final Long id, final Relationship... fetch) {
        return serviceClientDao.load(id, fetch);
    }

    @Override
    public void resolveAllIpAddresses() {
        final ServiceClientQuery query = new ServiceClientQuery();
        query.setResultType(ResultType.ITERATOR);
        final List<ServiceClient> clients = serviceClientDao.search(query);
        try {
            for (final ServiceClient client : clients) {
                try {
                    if (resolveAddress(client)) {
                        serviceClientDao.update(client);
                    }
                } catch (final Exception e) {
                    // Ignore
                }
            }
        } finally {
            DataIteratorHelper.close(clients);
        }
    }

    @Override
    public ServiceClient save(final ServiceClient client) {
        validate(client);

        final Channel channel = fetchService.fetch(client.getChannel());
        final boolean isWebShop = channel != null && Channel.WEBSHOP.equals(channel.getInternalName());

        // Enforce collections
        if (isWebShop || channel == null) {
            // Only clients with channel != WEBSHOP may do / receive payments
            client.setDoPaymentTypes(null);
            client.setReceivePaymentTypes(null);
            client.setChargebackPaymentTypes(null);
        } else if (client.getMember() == null) {
            // Clients without members cannot receive payments
            client.setReceivePaymentTypes(null);
        }

        // Enforce the permissions based on collections
        Set<ServiceOperation> permissions = client.getPermissions();
        if (permissions == null) {
            permissions = new HashSet<ServiceOperation>();
            client.setPermissions(permissions);
        } else {
            // For webshops, remove all operations which are not WEBSHOP
            if (isWebShop) {
                for (final Iterator<ServiceOperation> iterator = permissions.iterator(); iterator.hasNext();) {
                    final ServiceOperation operation = iterator.next();
                    if (operation != ServiceOperation.WEBSHOP) {
                        iterator.remove();
                    }
                }
            } else {
                // Channels which are not webshop cannot have webshop, search ads or search members permissions
                permissions.remove(ServiceOperation.WEBSHOP);
                // Empty channel cannot see access information
                if (channel == null) {
                    permissions.remove(ServiceOperation.ACCESS);
                }
            }
        }
        if (CollectionUtils.isEmpty(client.getDoPaymentTypes())) {
            permissions.remove(ServiceOperation.DO_PAYMENT);
        } else {
            permissions.add(ServiceOperation.DO_PAYMENT);
        }
        if (client.getMember() == null || CollectionUtils.isEmpty(client.getReceivePaymentTypes())) {
            permissions.remove(ServiceOperation.RECEIVE_PAYMENT);
        } else {
            permissions.add(ServiceOperation.RECEIVE_PAYMENT);
        }
        if (CollectionUtils.isEmpty(client.getChargebackPaymentTypes())) {
            permissions.remove(ServiceOperation.CHARGEBACK);
        } else {
            permissions.add(ServiceOperation.CHARGEBACK);
        }
        if (client.getMember() != null || client.getChannel() == null || CollectionUtils.isEmpty(client.getManageGroups())) {
            client.setManageGroups(null);
            permissions.remove(ServiceOperation.MANAGE_MEMBERS);
        } else {
            permissions.add(ServiceOperation.MANAGE_MEMBERS);
        }

        // Save
        if (client.isTransient()) {
            return serviceClientDao.insert(client);
        } else {
            return serviceClientDao.update(client);
        }
    }

    @Override
    public List<ServiceClient> search(final ServiceClientQuery query) {
        return serviceClientDao.search(query);
    }

    public void setChannelServiceLocal(final ChannelServiceLocal channelService) {
        this.channelService = channelService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setServiceClientDao(final ServiceClientDAO serviceClientDao) {
        this.serviceClientDao = serviceClientDao;
    }

    public void setTransferTypeServiceLocal(final TransferTypeServiceLocal transferTypeService) {
        this.transferTypeService = transferTypeService;
    }

    @Override
    public void validate(final ServiceClient client) {
        getValidator(client).validate(client);
    }

    private Validator getValidator(final ServiceClient client) {
        final Validator validator = new Validator("serviceClient");
        validator.property("name").required().maxLength(100);
        validator.property("hostname").key("serviceClient.address").required().add(new HostnameValidation()).maxLength(100);
        validator.property("username").maxLength(100);
        validator.property("password").maxLength(100);
        validator.property("channel").anyOf(listPossibleChannels().toArray());
        final Channel channel = fetchService.fetch(client.getChannel());
        if (channel != null && !Channel.WEBSHOP.equals(channel.getInternalName())) {
            final Object[] doPaymentTTs = listPossibleDoPaymentTypes(client).toArray();
            final Object[] receiveTTs = listPossibleReceivePaymentTypes(client).toArray();
            validator.property("doPaymentTypes").key("serviceOperation.DO_PAYMENT").anyOf(doPaymentTTs);
            validator.property("receivePaymentTypes").key("serviceOperation.RECEIVE_PAYMENT").anyOf(receiveTTs);
            validator.property("receivePaymentTypes").key("serviceOperation.CHARGEBACK").anyOf(client.getMember() == null ? doPaymentTTs : receiveTTs);
        }
        validator.general(new GeneralValidation() {

            private static final long serialVersionUID = -6137330080312890785L;

            @Override
            public ValidationError validate(final Object object) {
                final ServiceClient client = (ServiceClient) object;
                final String username = StringUtils.trimToNull(client.getUsername());
                final String password = StringUtils.trimToNull(client.getPassword());
                int nonEmpty = 0;
                if (username != null) {
                    nonEmpty++;
                }
                if (password != null) {
                    nonEmpty++;
                }
                if (nonEmpty == 1) {
                    return new ValidationError("serviceClient.error.empty.usernameOrPassword");
                }
                return null;
            }
        });
        return validator;
    }

    private boolean resolveAddress(final ServiceClient client) {
        final String hostname = client.getHostname();
        final AddressType addressType = InternetAddressHelper.resolveAddressType(hostname);
        if (addressType == null) {
            // Invalid ?!?
            return false;
        }
        final String addressBegin = client.getAddressBegin();
        final String addressEnd = client.getAddressBegin();
        switch (addressType) {
            case HOSTNAME:
                try {
                    // Resolve the ip address
                    final InetAddress[] addr = InetAddress.getAllByName(hostname);
                    final String ip = InternetAddressHelper.padAddress(addr[0].getHostAddress());
                    client.setAddressBegin(ip);
                    client.setAddressEnd(ip);
                } catch (final UnknownHostException e) {
                    // Error resolving ip address. Leave the previous one
                }
                break;
            case SIMPLE_IP:
                // It's a fixed ip
                final String paddedAddress = InternetAddressHelper.padAddress(hostname);
                client.setAddressBegin(paddedAddress);
                client.setAddressEnd(paddedAddress);
                break;
            case IP_RANGE:
                // An ip range
                final String[] range = InternetAddressHelper.getRangeBoundaries(hostname);
                client.setAddressBegin(InternetAddressHelper.padAddress(range[0]));
                client.setAddressEnd(InternetAddressHelper.padAddress(range[1]));
                break;
        }
        return !ObjectUtils.equals(client.getAddressBegin(), addressBegin) || !ObjectUtils.equals(client.getAddressEnd(), addressEnd);
    }
}
