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
package nl.strohalm.cyclos.services.sms;

import java.util.Collection;
import java.util.List;

import nl.strohalm.cyclos.dao.sms.SmsLogDAO;
import nl.strohalm.cyclos.dao.sms.SmsLogReportTotal;
import nl.strohalm.cyclos.dao.sms.SmsTypeDAO;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.sms.SmsLog;
import nl.strohalm.cyclos.entities.sms.SmsLogQuery;
import nl.strohalm.cyclos.entities.sms.SmsLogReportQuery;
import nl.strohalm.cyclos.entities.sms.SmsLogReportVO;
import nl.strohalm.cyclos.entities.sms.SmsType;
import nl.strohalm.cyclos.utils.StringHelper;

import org.apache.commons.lang.StringUtils;

/**
 * Implementation for SMS log service
 * @author Jefferson Magno
 */
public class SmsLogServiceImpl implements SmsLogServiceLocal {

    private SmsLogDAO  smsLogDao;
    private SmsTypeDAO smsTypeDao;

    @Override
    public SmsLogReportVO getSmsLogReport(final SmsLogReportQuery query) {

        final SmsLogReportVO report = new SmsLogReportVO();
        if (query.isReturnTotals()) {
            List<SmsLogReportTotal> totals = smsLogDao.getReportTotals(query);
            for (final SmsLogReportTotal total : totals) {
                report.setTotals(total.getType(), total.getStatus(), total.getTotal());
            }
        }
        report.setLogs(smsLogDao.search(query));
        return report;
    }

    @Override
    public Collection<SmsType> getSmsTypes() {
        return smsTypeDao.list();
    }

    @Override
    public SmsType loadSmsTypeByCode(final String code) {
        SmsType smsType = null;

        if (StringUtils.isNotEmpty(code)) {
            smsType = smsTypeDao.loadByCode(code);
        }

        if (smsType == null) {
            throw new EntityNotFoundException(SmsType.class, null, "No sms type defined with code: '" + code + "'");
        }
        return smsType;
    }

    @Override
    public Collection<SmsType> loadSmsTypes(final Collection<Long> ids) {
        return smsTypeDao.load(ids);
    }

    @Override
    public SmsLog save(final SmsLog smsLog) {
        smsLog.setArg0(StringHelper.replaceSupplementaryCharacters(smsLog.getArg0()));
        smsLog.setArg1(StringHelper.replaceSupplementaryCharacters(smsLog.getArg1()));
        smsLog.setArg2(StringHelper.replaceSupplementaryCharacters(smsLog.getArg2()));
        smsLog.setArg3(StringHelper.replaceSupplementaryCharacters(smsLog.getArg3()));
        smsLog.setArg4(StringHelper.replaceSupplementaryCharacters(smsLog.getArg4()));
        return smsLogDao.insert(smsLog);
    }

    @Override
    public List<SmsLog> search(final SmsLogQuery query) {
        return smsLogDao.search(query);
    }

    public void setSmsLogDao(final SmsLogDAO smsLogDao) {
        this.smsLogDao = smsLogDao;
    }

    public void setSmsTypeDao(final SmsTypeDAO smsTypeDao) {
        this.smsTypeDao = smsTypeDao;
    }
}
