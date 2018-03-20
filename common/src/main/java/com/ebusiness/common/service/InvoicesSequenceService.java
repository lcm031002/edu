package com.ebusiness.common.service;

import com.ebusiness.common.model.InvoicesSequence;

public interface InvoicesSequenceService {
    
    /***
     * 根据单据类型 查询单据序列
     * @param   invoicesType        单据类型
     * @return  InvoicesSequence    单据序列对象
     * @throws  Exception
     */
    InvoicesSequence genSequenceByInvoicesType(Long invoicesType) throws Exception;
    
    /***
     * 修改单据编号序列
     * @param   invoicesType    单据类型
     * @return  boolean         true:成功   false：失败
     * @throws  Exception
     */
    boolean updateInvoicesSequenceByInvoicesType(Long invoicesType)throws Exception;
    
    /**
     * 初始化所有单据编号序列
     * @return  Boolean     true:成功   false:失败
     * @throws Exception
     */
    boolean updateInvoicesSequence()throws Exception;
}
