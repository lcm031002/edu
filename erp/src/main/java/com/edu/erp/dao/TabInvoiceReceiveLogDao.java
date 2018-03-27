package com.edu.erp.dao;

import com.edu.erp.model.TabInvoiceReceiveLog;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository(value = "tabInvoiceReceiveLogDao")
public interface TabInvoiceReceiveLogDao {
    List<TabInvoiceReceiveLog> queryByInvoiceId(Long invoiceId) throws Exception;

    void add(TabInvoiceReceiveLog tabInvoiceReceiveLog) throws Exception;

    void deleteByInvoiceId(Long invoiceId) throws Exception;

    void deleteById(Long id) throws Exception;
}
