package com.ebusiness.common.dao;

import org.springframework.stereotype.Repository;

import com.ebusiness.common.model.InvoicesSequence;


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

    void selectSeq(InvoicesSequence seqParam) throws Exception;

    int insert(InvoicesSequence invoicesSequence) throws Exception;

    int update(InvoicesSequence invoicesSequence) throws Exception;
}
