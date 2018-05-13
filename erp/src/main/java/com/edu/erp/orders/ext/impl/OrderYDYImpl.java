package com.edu.erp.orders.ext.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.common.constants.Constants;
import com.edu.erp.dao.*;
import com.edu.erp.model.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.edu.common.util.DateUtil;
import com.edu.erp.attendance.service.AttendanceService;
import com.edu.erp.orders.ext.IOrderFrozen;
import com.edu.erp.orders.ext.IOrderYDY;
import com.edu.erp.orders.service.ChangeCourseService;
import com.edu.erp.orders.service.ChangeCourseTimesService;
import com.edu.erp.orders.service.EncoderService;
import com.edu.erp.orders.service.FeeDetailService;
import com.edu.erp.orders.service.FeeService;
import com.edu.erp.orders.service.FinFeeService;
import com.edu.erp.orders.service.OrderChangeService;
import com.edu.erp.orders.service.OrderCourseTimesInfoService;
import com.edu.erp.orders.service.OrderInfoDetailService;
import com.edu.erp.orders.service.OrderInfoService;
import com.edu.erp.orders.service.OrderPayCostService;
import com.edu.erp.orders.service.OrderService;
import com.edu.erp.orders.service.TcOrderCourseService;
import com.edu.erp.student.service.StudentAccountService;
import com.edu.erp.util.StringUtil;
/**
 * 1对1个性化业务
 * @author yecb
 *
 */
@Service(value = "iOrderYDY")
public class OrderYDYImpl implements IOrderYDY {
	
	@Resource(name = "orderInfoService")
	private OrderInfoService orderInfoService; //临时订单服务
	@Resource(name = "orderService")
	private OrderService orderService; //正式订单服务
	@Resource(name = "feeService")
	private FeeService feeService; //费用总表服务
	@Resource(name = "feeDetailService")
	private FeeDetailService feeDetailService; //费用明细表服务
	@Resource(name = "orderInfoDetailService")
	private OrderInfoDetailService OrderInfoDetailService; //订单详情服务
	@Resource(name = "encoderService")
	private EncoderService encoderService; //业务单据服务
	@Resource(name = "orderPayCostService")
	private OrderPayCostService orderPayCostService; 
	@Resource(name = "finFeeService")
	private FinFeeService finFeeService; //资金往来服务
	@Resource(name = "studentAccountService")
	private StudentAccountService studentAccountService; //账户信息查询
	@Resource(name = "orderChangeService")
	private OrderChangeService orderChangeService; //订单变动服务
	@Resource(name = "changeCourseService")
	private ChangeCourseService changeCourseService;
	@Resource(name = "changeCourseTimesService")
	private ChangeCourseTimesService changeCourseTimesService;
	
	@Resource(name = "tcOrderCourseService")
	private TcOrderCourseService tcOrderCourseService;
	
	@Resource(name = "tLockDao")
	private TLockDao tLockDao;
	@Resource(name = "tCOrderCourseDao")
	private TCOrderCourseDao tCOrderCourseDao;
	@Resource(name = "tOrderCourseDao")
	private TOrderCourseDao tOrderCourseDao;
	@Resource(name = "orderCourseTimesInfoService")
	private OrderCourseTimesInfoService orderCourseTimesInfoService; //课程课次服务
	@Resource(name = "attendanceDao")
	private AttendanceDao attendanceDao;
	@Resource(name = "tOrderChangeDao")
	private TOrderChangeDao tOrderChangeDao;
	@Resource(name = "tOrderCourseLogDao")
	private TOrderCourseLogDao tOrderCourseLogDao;
	@Resource(name = "orderFrozen")
	private IOrderFrozen orderFrozen;

	@Resource(name = "tOrderCourseTimesDao")
	TOrderCourseTimesDao tOrderCourseTimesDao;
	
	@Override
	public void createOrder(TabOrderInfo orderInfo) throws Exception {
		HashMap<String,Object> resultMap=new HashMap<String,Object>();
		//1.审核通过
		auditConfirm(orderInfo,resultMap);
		TEncoder tEncoder=(TEncoder) resultMap.get("tEncoder");
		//2.资金到账
		finReceive(orderInfo,tEncoder.getId());
		//3.财务确认
		finConfirm(tEncoder,orderInfo);
	}
	/**
	 * 审核通过
	 * @param orderInfo 
	 * @param resultMap
	 * @throws Exception
	 */
	private void auditConfirm(TabOrderInfo orderInfo,HashMap<String,Object> resultMap) throws Exception{
		try {
		//1.1 更新订单审核人信息
		orderInfo.setApprove_time(DateUtil.getCurrDate("yyyy-MM-dd"));
		orderInfo.setCheck_status(3L);
		orderInfoService.updateOrderApproved(orderInfo);
		//1.2 先删除41，42类型的费用明细
		HashMap<String,String> feeParam=new HashMap<String,String>();
		feeParam.put("order_id", orderInfo.getId().toString());
		feeParam.put("fee_type", "'41','42'");
		feeService.deleteFee(feeParam);
		feeDetailService.deleteFeeDetail(feeParam);
		//1.3新增业务费用明细表
		List<TabOrderInfoDetail>  tabOrderInfoDetailList=OrderInfoDetailService.queryTabOrderInfoDetail(orderInfo.getId());
		orderInfo.setDetails(tabOrderInfoDetailList);
		if(tabOrderInfoDetailList!=null){
			for(TabOrderInfoDetail tabOrderInfoDetail:tabOrderInfoDetailList){
				TFeeDetail tFeeDetail=new TFeeDetail();
				tFeeDetail.setFee_flag(1l);   //收付费标识
				tFeeDetail.setOperate_type(4l); //操作类型:4=报班,5=批改,6=考勤,7=理赔
				tFeeDetail.setOperate_no(orderInfo.getId()); //操作编号
				tFeeDetail.setOrder_id(orderInfo.getId()); //订单ID
				tFeeDetail.setFee_type(41l); //费用类型
				tFeeDetail.setFee_amount(Double.valueOf((tabOrderInfoDetail.getDiscount_sum_price()-tabOrderInfoDetail.getPre_forward()))); //费用总额
				tFeeDetail.setOrder_detail_id(tabOrderInfoDetail.getId());  //订单分录ID
				tFeeDetail.setCourse_sum(tabOrderInfoDetail.getCourse_total_count());   //课次总数
				feeDetailService.saveFeeDetail(tFeeDetail);
				if(tabOrderInfoDetail.getPre_forward()>0){
					tFeeDetail.setFee_type(42l);
					tFeeDetail.setFee_amount(Double.valueOf((tabOrderInfoDetail.getPre_forward())));
					tFeeDetail.setCourse_sum(tabOrderInfoDetail.getCourse_total_count());
					feeDetailService.saveFeeDetail(tFeeDetail);
				}
			}
		}
		//1.4 新增业务费用表
		TFee fee=new TFee();
		fee.setOrder_id(orderInfo.getId());
		fee.setInsert_time(new Date());
		fee.setFee_status(0l);
		fee.setOperate_type(4l);
		fee.setOperate_no(orderInfo.getId().toString());
		List<TFeeDetail> tFeeDetailList=feeDetailService.queryFeeDetailByOrderId(orderInfo.getId());
		for(TFeeDetail tFeeDetail:tFeeDetailList){
			fee.setFee_type(tFeeDetail.getFee_type());
			fee.setFee_flag(tFeeDetail.getFee_flag());
			fee.setFee_amount(tFeeDetail.getFee_amount());
			feeService.saveFee(fee);
			tFeeDetail.setFee_id(fee.getId());
			tFeeDetail.setOrder_id(fee.getOrder_id());
			feeDetailService.updateFeeIdByFee(tFeeDetail);
		}
		//1.5 生成单据信息
		TFee tFee=feeService.queryFeeAmountByOrderId(feeParam);
		TEncoder tEncoder=new TEncoder();
		if(!StringUtil.isEmpty(tFee)){
			Double feeAmount=tFee.getFee_amount();
			tEncoder.setEncoder_type(3l);
			tEncoder.setOrder_id(orderInfo.getId());
			tEncoder.setBusi_id(orderInfo.getId());
			tEncoder.setBusi_type(4l);
			tEncoder.setCreate_user(orderInfo.getCreate_user());
			tEncoder.setCreate_time(new Date());
			tEncoder.setUpdate_user(orderInfo.getCreate_user());
			tEncoder.setUpdate_time(new Date());
			tEncoder.setFee_amount(feeAmount);
			tEncoder.setStatus(0);
			if(feeAmount>0){
				tEncoder.setFee_flag(1l);
			}else if(feeAmount==0){
				tEncoder.setFee_flag(0l);
			}else{
				tEncoder.setFee_flag(2l);
			}
			encoderService.saveTEncoder(tEncoder);
		}
		String encoder_id=tEncoder.getId().toString();
		feeParam.put("encoder_id", encoder_id);
		//1.6 更新单据编号到费用明细表的编号字段
		feeService.updateFeeEncoderIdByOrderId(feeParam);
		resultMap.put("tEncoder", tEncoder);
		}catch (Exception e) {
			throw new Exception(" 审核环节出现错误:"+e.getMessage(),e);
		}
	}
	
	/**
	 * 资金到账
	 * @param orderInfo  临时订单信息
	 * @param encoder_id  单据编号
	 * @throws Exception
	 */
	private void finReceive(TabOrderInfo orderInfo,Long encoder_id) throws Exception{
		try {
			//2.1 根据订单ID查询单据信息
			//2.2 查询组织机构，地区，校区，学生ID
			TabOrderInfo tabOrderInfo=orderInfoService.queryTemporaryOrderInfo(orderInfo.getId());
			//2.3 保存收付费明细的资金流水
			TabOrderPayCost tabOrderPayCost=orderPayCostService.queryTabOrderPayCost(orderInfo.getId());
			List<TabOrderPayCostDetail> tabOrderPayCostDetailList=tabOrderPayCost.getDetails();
			TFinFee tFinFee=new TFinFee();
			tFinFee.setPay_flag(1l);
			TFinFeeUse tFinFeeUse=new TFinFeeUse();
			tFinFeeUse.setUse_type(3l);
			tFinFeeUse.setOrder_id(orderInfo.getId());
			tFinFeeUse.setEncoder_id(encoder_id);
			TAccountChange tAccountChange=new TAccountChange();
			tAccountChange.setChange_flag(1l);
			tAccountChange.setChange_type(1l);
			tAccountChange.setPay_mode(1l);
			for(TabOrderPayCostDetail tabOrderPayCostDetail:tabOrderPayCostDetailList){
				if(tabOrderPayCostDetail.getPayment_way()==1||tabOrderPayCostDetail.getPayment_way()==2||tabOrderPayCostDetail.getPayment_way()==3){
					//记录资金到账
					tFinFee.setStudent_id(tabOrderPayCost.getStudentId());
					tFinFee.setPay_mode(tabOrderPayCostDetail.getPayment_way());
					tFinFee.setFee_amount(Double.valueOf(tabOrderPayCostDetail.getStaffappprem()));
					tFinFee.setCreate_time(new Date());
					tFinFee.setCreate_user(orderInfo.getCreate_user());
					finFeeService.saveTFinFee(tFinFee);
					if(tabOrderPayCostDetail.getPayment_way()==2||tabOrderPayCostDetail.getPayment_way()==3){
						//记录资金到账对应的银行账号
						TBankAccount tBankAccount=new TBankAccount();
						tBankAccount.setFin_fee_id(tFinFee.getId());
						tBankAccount.setAccount_owner(tabOrderPayCostDetail.getCity_name());
						tBankAccount.setBank_code(null);
						tBankAccount.setAccount_no(tabOrderPayCostDetail.getClient_card_no());
						finFeeService.saveTBankAccount(tBankAccount);
					}
					tFinFeeUse.setFin_fee_id(tFinFee.getId());
					tFinFeeUse.setFee_amount(Double.valueOf(tabOrderPayCostDetail.getStaffappprem()));
					finFeeService.saveTFinFeeUse(tFinFeeUse);
				}
				//2.4 保存账户变动，更新账户余额
				else if (tabOrderPayCostDetail.getPayment_way()==4){
					Map<String, Object> param=new HashMap<String, Object>();
					param.put("buId", tabOrderInfo.getBu_id());
					param.put("cityId", tabOrderInfo.getCity_id());
					param.put("studentId",  tabOrderPayCost.getStudentId());
					param.put("accountType", 0);
					TAccount tAccount=studentAccountService.queryAccountInfo(param);
					if(tAccount.getFee_amount()<tabOrderPayCostDetail.getStaffappprem()){
						throw new Exception("储值账户余额不足");
					}
					tAccountChange.setAccount_id(tAccount.getId());
					tAccountChange.setOrder_id(orderInfo.getId());
					tAccountChange.setEncoder_id(encoder_id);
					tAccountChange.setChange_amount(Double.valueOf(tabOrderPayCostDetail.getStaffappprem()));
					tAccountChange.setPre_amount(tAccount.getFee_amount());
					tAccountChange.setNext_amount(tAccount.getFee_amount()-tabOrderPayCostDetail.getStaffappprem());
					tAccountChange.setAccount_type(1l);
					studentAccountService.saveAccountChange(tAccountChange);
					studentAccountService.updateFeeAccount(tAccount.getId(),tAccountChange.getNext_amount());
				}
				else if (tabOrderPayCostDetail.getPayment_way()==9){
					Map<String, Object> param=new HashMap<String, Object>();
					param.put("buId", tabOrderInfo.getBu_id());
					param.put("cityId", tabOrderInfo.getCity_id());
					param.put("studentId",  tabOrderPayCost.getStudentId());
					param.put("accountType", 0);
					TAccount tAccount=studentAccountService.queryAccountInfo(param);
					if(tAccount.getFrozen_account()<tabOrderPayCostDetail.getStaffappprem()){
						throw new Exception("冻结账户余额不足");
					}
					tAccountChange.setAccount_id(tAccount.getId());
					tAccountChange.setOrder_id(orderInfo.getId());
					tAccountChange.setEncoder_id(encoder_id);
					tAccountChange.setChange_amount(Double.valueOf(tabOrderPayCostDetail.getStaffappprem()));
					tAccountChange.setPre_amount(tAccount.getFrozen_account());
					tAccountChange.setNext_amount(tAccount.getFrozen_account()-tabOrderPayCostDetail.getStaffappprem());
					tAccountChange.setAccount_type(2l);
					studentAccountService.saveAccountChange(tAccountChange);
					studentAccountService.updateFrozenAccount(tAccount.getId(),tAccountChange.getNext_amount());
				}
			}
		}catch (Exception e) {
			throw new Exception("资金到账环节出现错误:"+e.getMessage(),e);
		}
	}
	
	
	/**
	 * 财务确认
	 * @param tEncoder 单据信息
	 * @param orderInfo 临时订单信息
	 * @throws Exception
	 */
	private void finConfirm(TEncoder tEncoder,TabOrderInfo orderInfo) throws Exception{
		
		try {
			//3.1.确认单据，将单据状态设置成1
			tEncoder.setStatus(1);
			encoderService.updateEncoderStatus(tEncoder);
			
			//3.2 生成应收
			TFee tfee=new TFee();
			tfee.setOrder_id(tEncoder.getOrder_id());
			tfee.setFee_type(11l);
			tfee.setFee_flag(tEncoder.getFee_flag());
			tfee.setFee_amount(tEncoder.getFee_amount());
			tfee.setInsert_time(new Date());
			tfee.setFinish_time(new Date());
			tfee.setFee_status(1l);
			tfee.setEncoder_id(tEncoder.getId());
			tfee.setOperate_type(4l);
			tfee.setOperate_no(tEncoder.getOrder_id().toString());
			feeService.saveFee(tfee);
			//3.3 生成订单总表t_order
			orderInfo.setStatus(1);
			orderService.saveOrderInfo(orderInfo);
			//3.4 生成订单课程表 t_order_course(处理跨校区报班的情况，课程和当前订单校区不同，订单校区取订单的校区)
			List<TabOrderInfoDetail> tabOrderInfoDetailList=orderInfo.getDetails();
			for(TabOrderInfoDetail tabOrderInfoDetail:tabOrderInfoDetailList){
				orderService.saveOrderCourse(tabOrderInfoDetail,orderInfo);
			}
			//3.5 如果是大小班，新增order_course_times,如果是晚辅导，个性化 TODO 目前个性化省略
			if (Constants.BusinessType.BJK.longValue() == orderInfo.getBusiness_type()) {
				tOrderCourseTimesDao.addByTabOrderId(orderInfo.getId());
			}

			//3.6 状态更新成有效
			HashMap<String,Object> hashMap=new HashMap<String,Object>();
			hashMap.put("order_id", orderInfo.getId().toString());
			hashMap.put("fee_type", "41,42");
			hashMap.put("fee_status", "1");
			feeService.updateFeeStatusByOrderId(hashMap);
			TOrder tOrderTmp=new TOrder();
			tOrderTmp.setOrder_status(1l);
			tOrderTmp.setId(orderInfo.getId());
			orderService.updateOrderStatus(tOrderTmp);
			TabOrderInfo tabOrderInfoTmp=new TabOrderInfo();
			tabOrderInfoTmp.setValid_status(1l);
			tabOrderInfoTmp.setId(orderInfo.getId());
			tabOrderInfoTmp.setApproved(orderInfo.getApproved());
			orderInfoService.updateOrderApproved(tabOrderInfoTmp);
		} catch (Exception e) {
			throw new Exception("财务确认环节出现错误:"+e.getMessage(),e);
		}
	}
	

	@Override
	public void cancelOrder(Long orderId) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void refundOrder(TOrderChange orderChange, String changeNo) throws Exception {
        Long orderChangeId = orderChange.getId();
        TabChangeCourse changeCourse = new TabChangeCourse();
        changeCourse.setChange_no(changeNo);
        changeCourse.setOrder_id(orderChange.getOrder_id());
        changeCourse.setChange_id(orderChangeId);
        this.changeCourseService.updateByChangeNo(changeCourse);
        
        TabChangeCourseTimes changeCourseTimes = new TabChangeCourseTimes();
        changeCourseTimes.setChange_no(changeNo);
        changeCourseTimes.setChange_id(orderChangeId);
        this.changeCourseTimesService.updateByChangeNo(changeCourseTimes);
        this.tcOrderCourseService.addByChangeNo(changeNo);
        
        this.tcOrderCourseService.addLock(orderChangeId, orderChange.getOrderCourseId());
        this.tcOrderCourseService.checkSurplusCountAndAttendStatus(orderChangeId);
        
        orderChange.setInput_user(orderChange.getCreate_user());
        orderChange.setInput_time(DateUtil.getCurrDateTime());
        orderChange.setChange_status(3L);
        this.tOrderChangeDao.updateOrderChange(orderChange);
	}

	@Override
	public void cancelRefundOrder(TOrderChange orderChange) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void frozenOrder(TOrderChange orderChange) throws Exception {
		orderFrozen.frozenOrder(orderChange, 2l);
	}

	@Override
	public void cancelFrozenOrder(TOrderChange orderChange) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void transferOrder(TOrderChange orderChange, Integer businessType) throws Exception {
		// 1.行级独占锁
		Map<String, Object> queryParam = new HashMap<String,Object>();
		queryParam.put("order_id", orderChange.getOrder_id());
		tLockDao.queryLockOrderId(queryParam);
		// 2.保存批改主表
		orderChangeService.saveOrderChange(orderChange);
		// 3.将转班-转出课程信息，导入到批改子表
		TCOrderCourse outputTcOrderCourse = orderChange.getTcOrderCourse().get(0);
		outputTcOrderCourse.setChange_id(orderChange.getId());
		tCOrderCourseDao.saveTcOrderCourse(outputTcOrderCourse);

		if (!CollectionUtils.isEmpty(outputTcOrderCourse.getTcCourseTimes())) {
			for (TCCourseTimes tcCourseTimes : outputTcOrderCourse.getTcCourseTimes()) {
				tcCourseTimes.setChangeId(orderChange.getId());
				tcCourseTimes.setChangeCourseId(outputTcOrderCourse.getId());
				orderCourseTimesInfoService.saveTcOrderCourseTimes(tcCourseTimes);
			}

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("change_id", orderChange.getId());
			orderCourseTimesInfoService.updateValidOrderCourseTimes(paramMap);
		}
		// 4.将转班-转入课程信息，导入到批改子表
		TCOrderCourse inputTcOrderCourse = orderChange.getTcOrderCourse().get(1);
		inputTcOrderCourse.setChange_id(orderChange.getId());

		// 5.锁校验
		queryParam.clear();
		queryParam.put("orderCourseId", outputTcOrderCourse.getOrder_course_id());
		Map courseLockMap = tLockDao.queryCourseLock(queryParam);
		if(courseLockMap!=null){
			throw new Exception("本次转班课程存在被锁定的课程，不允许转班！" +
					"(锁定详情如下：  订单号：["+courseLockMap.get("ORDERNO")+"],课程名称：["+courseLockMap.get("COURSENAME")+"],"+"类型：["+courseLockMap.get("CHANGENAME")+"])");
		}
		// 6.加课程锁
		TLock tLock = new TLock();
		tLock.setType(2l); //2=课程锁
		tLock.setResourceId(outputTcOrderCourse.getOrder_course_id());
		tLock.setBusiType(5l); //5=批改
		tLock.setBusiId(orderChange.getId());
		tLockDao.saveLock(tLock);
		
		try {
			// 7.校验录入信息
			queryParam.clear();
			queryParam.put("id", outputTcOrderCourse.getOrder_course_id());
			List<TOrderCourse> orderDetailCourse = tOrderCourseDao.queryOrderCourse(queryParam);
			if (CollectionUtils.isEmpty(orderDetailCourse)) {
				throw new Exception("没有找到订单详情！");
			}
			// 转出订单课程信息
			TOrderCourse outputOrderCourse = orderDetailCourse.get(0);
			queryParam.clear();
			queryParam.put("order_course_id", outputTcOrderCourse.getOrder_course_id());
			Integer outputAttendCount = attendanceDao.countYdyAttendByOrderCourseId(queryParam);
			// 判断：转出课时 > 剩余课时 - 未考勤且已排课数
			if(outputTcOrderCourse.getCourse_times() > outputOrderCourse.getCourse_schedule_count()) {
				throw new Exception("转出课程数量大于剩余可排课时，不能进行转班！");
			}
			// 8.更新订单变动主表的 录入人信息
			TOrderChange updateOrderChange = new TOrderChange();
			updateOrderChange.setId(orderChange.getId());
			updateOrderChange.setUpdate_user(orderChange.getUpdate_user());
			updateOrderChange.setInput_user(orderChange.getUpdate_user());
			updateOrderChange.setInput_time(new Date());
			tOrderChangeDao.updateOrderChange(updateOrderChange);
			// 9.转班 转出 数据 备份
			TOrderCourseLog outputOrderCourseLog = new TOrderCourseLog();
			// 属性复制
			PropertyUtils.copyProperties(outputOrderCourseLog, outputOrderCourse);
			outputOrderCourseLog.setChange_id(orderChange.getId());
			outputOrderCourseLog.setOld_new(1l); //新旧标识:1修改前数据
			tOrderCourseLogDao.insert(outputOrderCourseLog);

            queryParam.put("changeId", orderChange.getId());
			tOrderCourseLogDao.insertOrderCourseTimesLog(queryParam);

			// 10.减少转出订单  剩余课程次数，剩余费用
			// 剩余课程（课时）总次数 
			outputOrderCourse.setCourse_surplus_count(outputOrderCourse.getCourse_surplus_count() 
					- outputTcOrderCourse.getCourse_times());
			outputOrderCourse.setSurplus_cost(outputOrderCourse.getSurplus_cost() 
					- outputTcOrderCourse.getTotal_amount());
			// 计算剩余可排课时
			if (outputOrderCourse.getCourse_surplus_count() != null) {
				outputOrderCourse.setCourse_schedule_count(outputOrderCourse.getCourse_schedule_count() - outputTcOrderCourse.getCourse_times());
			}

			outputOrderCourse.setUpdate_user(orderChange.getUpdate_user());
			tOrderCourseDao.updateOrderCourse(outputOrderCourse);

			// 11.生成转入订单
			TOrderCourse inputOrderCourse = new TOrderCourse();
			inputOrderCourse.setOrder_id(inputTcOrderCourse.getOrder_id());
			inputOrderCourse.setCourse_id(inputTcOrderCourse.getCourse_id());
			inputOrderCourse.setBranch_id(inputTcOrderCourse.getBranch_id());
			inputOrderCourse.setFormer_unit_price(outputOrderCourse.getFormer_unit_price());
			BigDecimal former_unit_price = new BigDecimal(inputOrderCourse.getFormer_unit_price());    
			BigDecimal inputCourseTimes = new BigDecimal(inputTcOrderCourse.getCourse_times());    
			inputOrderCourse.setFormer_sum_price(inputCourseTimes.multiply(former_unit_price).doubleValue());
			inputOrderCourse.setDiscount_unit_price(outputOrderCourse.getDiscount_unit_price());
			inputOrderCourse.setDiscount_sum_price(inputTcOrderCourse.getTotal_amount());
			inputOrderCourse.setDiscount_rate(1d);
			inputOrderCourse.setDiscount_amount(0d);
			inputOrderCourse.setSurplus_cost(inputTcOrderCourse.getTotal_amount());
			inputOrderCourse.setManage_fee(0d);
			
			// 转出订单课程-剩余课程（课时）总次数 = 0时，表示课次全部转出
			if(outputOrderCourse.getCourse_surplus_count() == 0) {
				inputOrderCourse.setManage_fee(outputOrderCourse.getManage_fee());
				inputOrderCourse.setDiscount_sum_price(inputOrderCourse.getDiscount_sum_price() 
						+ inputOrderCourse.getManage_fee());
				
				outputOrderCourse.setManage_fee(0d); //将原来的预结转费用更新为0
				tOrderCourseDao.updateOrderCourse(outputOrderCourse);
			}
			
			inputOrderCourse.setCourse_total_count(inputTcOrderCourse.getCourse_times());
			inputOrderCourse.setCourse_surplus_count(inputTcOrderCourse.getCourse_times());
			inputOrderCourse.setCourse_schedule_count(inputTcOrderCourse.getCourse_times());
			inputOrderCourse.setCreate_user(orderChange.getUpdate_user());
			inputOrderCourse.setUpdate_user(orderChange.getUpdate_user());
			
			if(!StringUtil.isEmpty(outputOrderCourse.getRoot_course_id())) {
				inputOrderCourse.setRoot_course_id(outputOrderCourse.getRoot_course_id());
			} else {
				inputOrderCourse.setRoot_course_id(outputOrderCourse.getId());
			}
			
			inputOrderCourse.setQuit_flag(0l); //退费标识 1：退费，  0：未退费
			inputOrderCourse.setOrder_type(3l);  //订单类型:1：原单  2：赠单  3：转班单  4：续单
			inputOrderCourse.setStatus(1);
			
			tOrderCourseDao.insertOrderCourseWithId(inputOrderCourse);

			inputTcOrderCourse.setOrder_course_id(inputOrderCourse.getId());
			tCOrderCourseDao.saveTcOrderCourse(inputTcOrderCourse);
			if (!CollectionUtils.isEmpty(inputTcOrderCourse.getTcCourseTimes())) {
				for (TCCourseTimes tcCourseTimes : inputTcOrderCourse.getTcCourseTimes()) {
					tcCourseTimes.setChangeId(orderChange.getId());
					tcCourseTimes.setOrderCourseId(inputOrderCourse.getId());
					tcCourseTimes.setChangeCourseId(inputTcOrderCourse.getId());
					orderCourseTimesInfoService.saveTcOrderCourseTimes(tcCourseTimes);
				}
				orderCourseTimesInfoService.addOrderCourseTimesByChangeId(orderChange.getId(), inputOrderCourse.getId());
			}
			
			// 12.更新批改状态为生效
			updateOrderChange = new TOrderChange();
			updateOrderChange.setId(orderChange.getId());
			updateOrderChange.setUpdate_user(orderChange.getUpdate_user());
			updateOrderChange.setChange_status(5l); //5-生效
			updateOrderChange.setValidate_time(new Date());
			tOrderChangeDao.updateOrderChange(updateOrderChange);
		} catch (Exception e) {
			throw e;
		} finally {
			// 13.释放批改业务锁
			tLockDao.releaseLock(tLock);
		}
		
	}

	@Override
	public void attendanceOrder(TAttendance attendance) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkOrderChangeTimes(Long orderId, Long changeTimes) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", orderId);
		paramMap.put("changeTimes", changeTimes);
		String flag = this.tOrderCourseDao.checkOrderChangeTimes(paramMap);
		return Constants.YES.equals(flag);
	}

}
