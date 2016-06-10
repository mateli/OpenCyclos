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
package nl.strohalm.cyclos.setup.migrations.version3_5;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import nl.strohalm.cyclos.entities.access.Channel;
import nl.strohalm.cyclos.entities.settings.LocalSettings.Language;
import nl.strohalm.cyclos.setup.CreateBasicData;
import nl.strohalm.cyclos.setup.Setup;
import nl.strohalm.cyclos.setup.UntraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;
import nl.strohalm.cyclos.utils.conversion.CoercionHelper;

/**
 * Creates the built-in channels
 * @author luis
 * @author Jefferson Magno
 */
@SuppressWarnings("deprecation")
public class ChannelsMigration implements UntraceableMigration {

    private Long           webId;
    private Long           wap1Id;
    private Long           wap2Id;
    private Long           webShopId;
    private Long           posWebId;
    private ResourceBundle resourceBundle;

    public void execute(final JDBCWrapper jdbc) throws SQLException {
        // Get resource bundle object
        final String languageStr = jdbc.readScalarAsString("select value from settings where name='language'");
        final Language language = CoercionHelper.coerce(Language.class, languageStr);
        resourceBundle = Setup.getResourceBundle(language.getLocale());

        // Insert builtin channels
        insertChannels(jdbc);

        // Retrieve the id of some builtin channels
        initChannelIds(jdbc);

        // Associate channels to groups
        associateChannelsToGroups(jdbc);

        // Associate channels to transfer types
        associateChannelsToTransferTypes(jdbc);

        // Associate channels to member
        associateChannelsToMembers(jdbc);

        // Clean databasejdbc.execute(enableTransferTypeSql, transferTypeId);
        cleanDB(jdbc);
    }

    /*
     * Keep the member groups with access to specific channels
     */
    private void associateChannelsToGroups(final JDBCWrapper jdbc) throws SQLException {
        final ResultSet groups = jdbc.query("select id, mobile_service from groups where subclass in ('M', 'B') and status = 'N'");
        final String relateGroupSql = "insert into groups_channels (group_id, channel_id) values (?, ?)";
        while (groups.next()) {
            final long groupId = groups.getLong("id");

            // All groups are related to web
            jdbc.execute(relateGroupSql, groupId, webId);

            // Relate to mobile service
            final String mobileService = groups.getString("mobile_service");
            if ("BOTH".equals(mobileService) || "WAP2_ONLY".equals(mobileService)) {
                // WAP 2
                jdbc.execute(relateGroupSql, groupId, wap2Id);
            }
            if ("BOTH".equals(mobileService)) {
                // WAP 1
                jdbc.execute(relateGroupSql, groupId, wap1Id);
            }
        }
        JDBCWrapper.closeQuietly(groups);

        // Copy current group channels to group default channels
        final String insertDefaultChannelsSql = "insert into groups_default_channels (group_id, channel_id) select group_id, channel_id from groups_channels";
        jdbc.execute(insertDefaultChannelsSql);
    }

    private void associateChannelsToMembers(final JDBCWrapper jdbc) throws SQLException {
        final StringBuilder associateChannelsToMembersSql = new StringBuilder();
        associateChannelsToMembersSql.append(" insert into members_channels (member_id, channel_id)");
        associateChannelsToMembersSql.append(" select m.id, c.id from members m ");
        associateChannelsToMembersSql.append(" inner join groups g on m.group_id = g.id ");
        associateChannelsToMembersSql.append(" inner join groups_channels gc on gc.group_id = g.id ");
        associateChannelsToMembersSql.append(" inner join channels c on gc.channel_id = c.id ");
        jdbc.execute(associateChannelsToMembersSql.toString());
    }

    private void associateChannelsToTransferTypes(final JDBCWrapper jdbc) throws SQLException {
        final String associateChannelToTTSql = "insert into transfer_types_channels (transfer_type_id, channel_id) values (?, ?)";
        final String enableTransferTypeSql = "update transfer_types set allowed_payment=true where id = ?";

        // Associate web channel to the "direct payment" transfer types
        final ResultSet directPaymentTTs = jdbc.query("select id from transfer_types where allowed_payment = true");
        while (directPaymentTTs.next()) {
            final long transferTypeId = directPaymentTTs.getLong("id");
            jdbc.execute(associateChannelToTTSql, transferTypeId, webId);
        }
        JDBCWrapper.closeQuietly(directPaymentTTs);

        // Associate wap1 and wap2 channels to the "mobile" transfer types
        final ResultSet mobileTTs = jdbc.query("select id from transfer_types where allowed_mobile = true");
        while (mobileTTs.next()) {
            final long transferTypeId = mobileTTs.getLong("id");
            jdbc.execute(associateChannelToTTSql, transferTypeId, wap1Id);
            jdbc.execute(associateChannelToTTSql, transferTypeId, wap2Id);
            jdbc.execute(enableTransferTypeSql, transferTypeId);

        }
        JDBCWrapper.closeQuietly(mobileTTs);

        // Associate channel webshop and posweb to the transfer type
        final ResultSet webShopTTs = jdbc.query("select id from transfer_types where allowed_external_payment = true");
        while (webShopTTs.next()) {
            final long transferTypeId = webShopTTs.getLong("id");
            jdbc.execute(associateChannelToTTSql, transferTypeId, webShopId);
            jdbc.execute(associateChannelToTTSql, transferTypeId, posWebId);
            jdbc.execute(enableTransferTypeSql, transferTypeId);
        }
        JDBCWrapper.closeQuietly(webShopTTs);
    }

    /*
     * Clean database
     */
    private void cleanDB(final JDBCWrapper jdbc) throws SQLException {
        jdbc.execute("alter table groups drop column mobile_service");
        jdbc.execute("alter table transfer_types drop column allowed_external_payment");
        jdbc.execute("alter table transfer_types drop column allowed_mobile");
    }

    /*
     * Initialize build-in channel ids
     */
    private void initChannelIds(final JDBCWrapper jdbc) throws SQLException {
        final String selectChannelId = "select id from channels where internal_name = ?";
        webId = jdbc.readScalarAsLong(selectChannelId, Channel.WEB);
        wap1Id = jdbc.readScalarAsLong(selectChannelId, Channel.WAP1);
        wap2Id = jdbc.readScalarAsLong(selectChannelId, Channel.WAP2);
        webShopId = jdbc.readScalarAsLong(selectChannelId, Channel.WEBSHOP);
        posWebId = jdbc.readScalarAsLong(selectChannelId, Channel.POSWEB);
    }

    /*
     * Insert built-in channels
     */
    private void insertChannels(final JDBCWrapper jdbc) throws SQLException {
        final String insert = "insert into channels (internal_name, display_name, use_pin) values (?, ?, ?)";
        final List<Channel> builtinChannels = CreateBasicData.getBuiltinChannels(resourceBundle);
        for (final Channel channel : builtinChannels) {
            jdbc.execute(insert, channel.getInternalName(), channel.getDisplayName(), Channel.POSWEB.equals(channel.getInternalName()));
        }
    }

}
