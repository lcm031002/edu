package com.edu.common.dao;

import com.edu.common.model.InvoicesSequence;
import org.springframework.stereotype.Repository;


@Repository("invoicesSequenceDao")
public interface InvoicesSequenceDao {

    /***
     * 根据单据类型 查询单据序列
     * 
     * @param invoicesType
     *            单据类型
     * @return InvoicesSequence 单据序列对象
     * @throws Exception
     */
    InvoicesSequence querySequenceByInvoicesType(Long invoicesType) throws Exception;

    InvoicesSequence selectSeq(Long invoiceType) throws Exception;

    void updateSeq(InvoicesSequence invoicesSequence);

    int insert(InvoicesSequence invoicesSequence) throws Exception;

    int update(InvoicesSequence invoicesSequence) throws Exception;
}
