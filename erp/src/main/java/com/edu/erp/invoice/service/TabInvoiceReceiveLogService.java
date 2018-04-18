package com.edu.erp.invoice.service;

import com.edu.erp.model.TabInvoiceReceiveLog;
import java.util.List;

public interface TabInvoiceReceiveLogService {

    List<TabInvoiceReceiveLog> queryByInvoiceId(Long invoiceId) throws Exception;

    void add(TabInvoiceReceiveLog invoiceReceiveLog) throws Exception;

    void addByInvoice(TabInvoiceReceiveLog invoiceReceiveLog) throws Exception;

    void deleteByInvoiceId(Long invoiceId) throws Exception;

    void deleteById(Long id) throws Exception;

}
