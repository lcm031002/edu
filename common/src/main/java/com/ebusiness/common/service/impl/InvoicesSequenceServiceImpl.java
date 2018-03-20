package com.ebusiness.common.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.ebusiness.common.dao.InvoicesSequenceDao;
import com.ebusiness.common.model.InvoicesSequence;
import com.ebusiness.common.service.InvoicesSequenceService;

@Service("invoicesSequenceService")
public class InvoicesSequenceServiceImpl implements InvoicesSequenceService {

    @Resource(name = "invoicesSequenceDao")
    private InvoicesSequenceDao invoicesSequenceDao;

    /***
     * 根据单据类型 查询单据序列
     * 
     * @param invoicesType
     *            单据类型
     * @return InvoicesSequence 单据序列对象
     * @throws Exception
     */
    @Override
    public InvoicesSequence genSequenceByInvoicesType(Long invoicesType)
            throws Exception {
        try {

            InvoicesSequence invoicesSequence = new InvoicesSequence();
            invoicesSequence.setInvoices_type(invoicesType);
            invoicesSequenceDao.selectSeq(invoicesSequence);
            if (invoicesSequence == null || "".equals(invoicesSequence) || "null".equals(invoicesSequence)) {
                invoicesSequence = new InvoicesSequence();
                invoicesSequence.setInvoices_type(invoicesType);
                invoicesSequence.setSequence(1L);
                int count = invoicesSequenceDao.insert(invoicesSequence);
                return count > 0 ? invoicesSequence : null;
            } else {
                return invoicesSequence;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /***
     * 修改单据编号序列
     * 
     * @param invoicesType
     *            单据类型
     * @return boolean true:成功 false：失败
     * @throws Exception
     */
    @Override
    public synchronized boolean updateInvoicesSequenceByInvoicesType(
            Long invoicesType) throws Exception {
        try {
            InvoicesSequence invoicesSequence = invoicesSequenceDao
                    .querySequenceByInvoicesType(invoicesType);
            if (invoicesSequence == null) {
                return false;
            } else {
                invoicesSequence
                        .setSequence(invoicesSequence.getSequence() + 1);
                return invoicesSequenceDao.update(invoicesSequence) > 0 ? true
                        : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 初始化所有单据编号序列
     * 
     * @return Boolean true:成功 false:失败
     * @throws Exception
     */
    @Override
    public boolean updateInvoicesSequence() throws Exception {
        try {
            return invoicesSequenceDao.update(new InvoicesSequence()) > 0 ? true
                    : false;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
