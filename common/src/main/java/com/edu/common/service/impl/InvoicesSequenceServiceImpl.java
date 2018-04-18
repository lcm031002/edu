package com.edu.common.service.impl;

import javax.annotation.Resource;

import com.edu.common.dao.InvoicesSequenceDao;
import com.edu.common.model.InvoicesSequence;
import com.edu.common.service.InvoicesSequenceService;
import org.springframework.stereotype.Service;

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
            InvoicesSequence invoicesSequence = invoicesSequenceDao.selectSeq(invoicesType);
            if (invoicesSequence == null) {
                invoicesSequence = new InvoicesSequence();
                invoicesSequence.setInvoices_type(invoicesType);
                invoicesSequence.setSequence(1L);
                int count = invoicesSequenceDao.insert(invoicesSequence);
                return count > 0 ? invoicesSequence : null;
            } else {
                invoicesSequenceDao.updateSeq(invoicesSequence);
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
