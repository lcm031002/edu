package com.edu.erp.invoice.service.impl;

import com.edu.erp.dao.TabInvoiceReceiveLogDao;
import com.edu.erp.invoice.service.InvoiceService;
import com.edu.erp.invoice.service.TabInvoiceReceiveLogService;
import com.edu.erp.model.TabDataInvoice;
import com.edu.erp.model.TabInvoiceReceiveLog;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("tabInvoiceReceiveLogService")
public class TabInvoiceReceiveLogServiceImpl implements TabInvoiceReceiveLogService {

    @Resource(name = "tabInvoiceReceiveLogDao")
    private TabInvoiceReceiveLogDao tabInvoiceReceiveLogDao;

    @Resource(name = "invoiceService")
    private InvoiceService invoiceService;

    @Override
    public List<TabInvoiceReceiveLog> queryByInvoiceId(Long invoiceId) throws Exception {
        return this.tabInvoiceReceiveLogDao.queryByInvoiceId(invoiceId);
    }

    @Override
    public void add(TabInvoiceReceiveLog tabInvoiceReceiveLog) throws Exception {
        Assert.notNull(tabInvoiceReceiveLog.getInvoiceId(), "发票ID不能为空！");
        this.tabInvoiceReceiveLogDao.add(tabInvoiceReceiveLog);

        TabDataInvoice invoice = new TabDataInvoice();
        invoice.setId(tabInvoiceReceiveLog.getInvoiceId());
        invoice.setReceiveStatus(tabInvoiceReceiveLog.getStatus());
        invoice.setUpdate_time(tabInvoiceReceiveLog.getCreate_time());
        invoice.setUpdate_user(tabInvoiceReceiveLog.getCreate_user());
        this.invoiceService.receiveInvoice(invoice);
    }

    @Override
    public void addByInvoice(TabInvoiceReceiveLog invoiceReceiveLog) throws Exception {
        Assert.notNull(invoiceReceiveLog.getInvoiceId(), "发票ID不能为空！");
        this.tabInvoiceReceiveLogDao.add(invoiceReceiveLog);
    }

    @Override
    public void deleteByInvoiceId(Long invoiceId) throws Exception {
        this.tabInvoiceReceiveLogDao.deleteByInvoiceId(invoiceId);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        this.tabInvoiceReceiveLogDao.deleteById(id);
    }
}
