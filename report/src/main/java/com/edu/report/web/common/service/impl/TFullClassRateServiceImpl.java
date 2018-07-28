package com.edu.report.web.common.service.impl;

import com.edu.report.framework.dao.TFullClassRateDao;
import com.edu.report.model.TFULLClassRateLast;
import com.edu.report.model.TFullClassRate;
import com.edu.report.web.common.service.TFullClassRateService;
import com.edu.report.framework.dao.TFullClassRateDao;
import com.edu.report.model.TFULLClassRateLast;
import com.edu.report.model.TFullClassRate;
import com.edu.report.web.common.service.TFullClassRateService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TFullClassRateServiceImpl
 * @Description: 满班率表实现类
 *
 */
@Service("tFullClassRateService")
public class TFullClassRateServiceImpl implements TFullClassRateService {

	@Resource(name = "tFullClassRateDao")
    private TFullClassRateDao tFullClassRateDao;



	@Override
	public List<TFullClassRate> queryReport(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"), "团队不能为空！");

		return tFullClassRateDao.queryReport(param);
	}

//	@Override
//	public void updateByTaskFlow(String taskFlow, Long minOperateNo, Long maxOperateNo) throws Exception {
//		Assert.hasText(taskFlow, "任务批次不能为空！");
//		Assert.notNull(minOperateNo, "最小序号不能为空！");
//		Assert.notNull(maxOperateNo, "最大序号不能为空！");
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("taskFlow", taskFlow);
//		param.put("minOperateNo", minOperateNo);
//		param.put("maxOperateNo", maxOperateNo);
//		// 删除批次数据
//		tFullClassRateDao.removeByTaskFlow(param);
//		// 2.重新插入数据
//		tFullClassRateDao.addByTaskFlow(param);
//	}

	@Override
	public List<TFULLClassRateLast> queryDetail(Map<String, Object> param) throws Exception {
		Assert.notEmpty(param);
		Assert.notNull(param.get("bu_id"),"团队不能为空！");
		return tFullClassRateDao.queryDetail(param);
	}
}
