package com.edu.erp.orders.ext.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edu.common.util.DateUtil;
import com.edu.erp.dao.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.edu.erp.attendance.service.AttendanceService;
import com.edu.erp.model.TAccount;
import com.edu.erp.model.TAccountChange;
import com.edu.erp.model.TCChangeCourse;
import com.edu.erp.model.TCChangeCourseTimes;
import com.edu.erp.model.TCCourseTimes;
import com.edu.erp.model.TCOrderCourse;
import com.edu.erp.model.TEncoder;
import com.edu.erp.model.TFee;
import com.edu.erp.model.TFeeDetail;
import com.edu.erp.model.TLock;
import com.edu.erp.model.TOrder;
import com.edu.erp.model.TOrderChange;
import com.edu.erp.model.TOrderCourse;
import com.edu.erp.model.TOrderCourseLog;
import com.edu.erp.model.TOrderCourseTimesLog;
import com.edu.erp.model.TabChangeCourse;
import com.edu.erp.orders.ext.IOrderFrozen;
import com.edu.erp.orders.service.ChangeCourseService;
import com.edu.erp.orders.service.ChangeCourseTimesService;
import com.edu.erp.orders.service.EncoderService;
import com.edu.erp.orders.service.FeeDetailService;
import com.edu.erp.orders.service.FeeService;
import com.edu.erp.orders.service.FinFeeService;
import com.edu.erp.orders.service.OrderChangeService;
import com.edu.erp.orders.service.OrderCourseTimesInfoService;
import com.edu.erp.orders.service.OrderInfoDetailService;
import com.edu.erp.orders.service.OrderPayCostService;
import com.edu.erp.orders.service.OrderService;
import com.edu.erp.orders.service.TcOrderCourseService;
import com.edu.erp.student.service.StudentAccountService;
import com.edu.erp.util.StringUtil;

@Service(value = "orderFrozen")
public class OrderFrozenImpl implements IOrderFrozen{

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
	@Resource(name = "tabChangeCourseDao")
	private TabChangeCourseDao tabChangeCourseDao;
	@Resource(name = "orderCourseTimesInfoDao")
	private OrderCourseTimesInfoDao orderCourseTimesInfoDao;
	
	@Resource(name = "tLockDao")
	private TLockDao tLockDao;
	@Resource(name = "tCOrderCourseDao")
	private TCOrderCourseDao tCOrderCourseDao;
	@Resource(name = "tOrderDao")
	private TOrderDao tOrderDao;
	@Resource(name = "tOrderCourseDao")
	private TOrderCourseDao tOrderCourseDao;
	@Resource(name = "tOrderCourseTimesDao")
	private TOrderCourseTimesDao tOrderCourseTimesDao;
	@Resource(name = "orderCourseTimesInfoService")
	private OrderCourseTimesInfoService orderCourseTimesInfoService; //课程课次服务
	@Resource(name = "attendanceService")
	private AttendanceService attendanceService; //考勤服务
	@Resource(name = "attendanceDao")
	private AttendanceDao attendanceDao;
	@Resource(name = "tOrderChangeDao")
	private TOrderChangeDao tOrderChangeDao;
	@Resource(name = "tOrderCourseLogDao")
	private TOrderCourseLogDao tOrderCourseLogDao;
	@Resource(name = "tFeeDetailDao")
    private TFeeDetailDao tFeeDetailDao;
	@Resource(name = "tFeeDao")
    private TFeeDao tFeeDao;
	@Resource(name = "encoderDao")
	private EncoderDao encoderDao;
	@Resource(name = "studentAccountDao")
	private StudentAccountDao studentAccountDao;

    /**
     * 订单退费数据准备
     * 
     * @param refundMap
     *            退费订单信息 order_detail_id 订单课程编号 course_cnt 退费课时 course_times
     *            退费课次 change_no 批改号，自动生成的唯一序号
     * @param businessType
     *            业务类型 1-班级课 2-个性化 3-晚辅导
     */
    @Override
    public void readyPremium(Map<String, Object> refundMap, Long businessType) throws Exception {
        
    }

    @Override
    public void frozenOrder(TOrderChange orderChange,  Long businessType) throws Exception {
    	
    	ArrayList<TLock> lockList=new ArrayList<TLock>();
		try{
			//生成批改申请记录--用于从临时表数据生成正式订单数据
			//1.行级独占锁
			Map<String, Object> param=new HashMap<String,Object>();
			param.put("change_no", orderChange.getChange_no());
			param.put("order_id", orderChange.getOrder_id());
			tLockDao.queryLockOrderId(param);
			//2.申请校验接口-校验当前订单是否可以做该批改项
			//2.1判断当前订单是否被锁定
			param.put("lock_type", 1);
			int lockFlag=tLockDao.queryLockOrderFlag(param);
			if(lockFlag>0){
				throw new Exception("当前订单被锁定，不能申请冻结!");
			}
			TOrder tOrder=orderService.queryOrderInfo(orderChange.getOrder_id());
			if(tOrder.getOrder_status()==0){
				throw new Exception("该订单已作废,不能再冻结！");
			}
			//2.2 校验如果存在当前变更课程的主课程、当前课程、当前课程的子课程 变更记录，则不允许变更
			Integer changeCourseNum=tOrderChangeDao.queryChangeCourseNum(param);
			if(changeCourseNum>0){
				throw new Exception("当前订单课程或其主订单课程或其子课程存在未完成的冻结操作，不能再次申请冻结。！");
			}
			
			//2.3.新增p_create_order_change
			orderChange.setChange_type(5l);
			orderChange.setApply_time(new Date());
			orderChange.setCreate_time(new Date());
			orderChange.setChange_status(2l);
			orderChange.setUpdate_time(new Date());
			orderChange.setCreate_time(new Date());
			tOrderChangeDao.saveOrderChange(orderChange);
			param.put("change_id", orderChange.getId());
			//3.更新web数据中的 change_id
			orderCourseTimesInfoDao.updateChangeIdOnChangeTimes(param);
			tOrderChangeDao.updateChangeIdOnChangeCourse(param);
			//4.导入到批改表--生成录入数据  新增批改表tc_order_course，tc_order_course_times
			addTcOrderCourse(param);

			//5.录入完成--订单批改信息录入完成后的处理逻辑
			//6.1  锁校验
			int courseLockNum=tLockDao.checkCourseLock(param);
			if(courseLockNum>0){
				throw new Exception("本次退费课程或联报课程存在被锁定的课程，不允许冻结！");
			}
			int courseTimesLockNum=tLockDao.checkCourseTimesLock(param);
			if(courseTimesLockNum>0){
				throw new Exception("本次退费所退课次存在被锁定的课次，不允许冻结！");
			}
			//6.2 加锁
			List<TCOrderCourse> tCOrderCourseTmpList=tCOrderCourseDao.queryAllChangeTimes(orderChange.getId());
			for(TCOrderCourse TCOrderCourse:tCOrderCourseTmpList){
				TLock tLock=new TLock();
				tLock.setType(2l);
				tLock.setResourceId(TCOrderCourse.getOrder_course_id());
				tLock.setBusiType(5l);
				tLock.setBusiId(orderChange.getId());
				tLockDao.saveLock(tLock);
				lockList.add(tLock);
			}
			//检验录入信息
			//校验剩余课次数量
			List<TCOrderCourse>  tCOrderCourseList=tCOrderCourseDao.queryTcOrderCourseByChangeId(orderChange.getId());
			for(TCOrderCourse tCOrderCourse:tCOrderCourseList){
				if(tCOrderCourse.getCourse_times()>tCOrderCourse.getCourse_surplus_count()){
					throw new Exception("课程"+tCOrderCourse.getCourse_name()+"本次所退次数大于剩余次数("+tCOrderCourse.getCourse_surplus_count());
				}
				param.put("change_course_id", tCOrderCourse.getId());
				//校验课次考勤状态
				List<Map<String,Object>> tempList=orderCourseTimesInfoDao.queryCourseTimesAttendType(param);
				if(tempList !=null&&tempList.size()>0){
					Long course_times=(Long) tempList.get(0).get("course_times");
					throw new Exception("课程"+tCOrderCourse.getCourse_name()+"本次所退课次("+course_times+")已考勤或被挂起,不可进行冻结操作");
				}
			}
			//6.3 生成业务费用
			//vip退费
			//标准退费
			//生成费用明细
			
			List<TabChangeCourse> tChangeCourseList=tabChangeCourseDao.queryChangeCourseInfo(param);
			int premiumType=1;
			if(CollectionUtils.isNotEmpty(tChangeCourseList)&&tChangeCourseList.get(0).getPremium_type()!=null){
				premiumType=tChangeCourseList.get(0).getPremium_type();
			}
			
			TOrderChange tOrderChange=tOrderChangeDao.queryOrderChangeByChangId(orderChange.getId());
			//7.填写冻结补扣的信息
			Double p_premium_deduction_amount=orderChange.getFee_deduction_amount();
			if(p_premium_deduction_amount!=null&&p_premium_deduction_amount!=0){
				BigDecimal fee_amount=tOrderChange.getFee_amount();
				param.put("fee_return_amount", fee_amount);
				param.put("premium_deduction_amount", p_premium_deduction_amount);
				tOrderChangeDao.updateAmountByChangeId(param);
			}
			//个性话业务省略--写转班单（与原单非同一个校区）恢复原价产生的金额 从原校区转出 转入到对应校区，如果转入的已考勤则产生补结转 进行消耗  
			if(premiumType==1){
				addFee(tOrderChange,param);
			}
			if(premiumType==2){
				addFee2(tOrderChange,param);
			}
			
			//--更新批改状态为生效
			param.put("change_stuatus", "5");
			tOrderChangeDao.updateOrderChangeStatus(param);

			//8.财务处理---订单批改财务确认后的处理逻辑
			//8.1费用进账户
			TEncoder encoder=new TEncoder();
			encoder.setBusi_type(5l);
			encoder.setBusi_id(orderChange.getId());
			encoder=encoderDao.queryEncoderInfo(encoder);
			TOrder tOrderTemp=orderService.queryOrderInfo(encoder.getOrder_id());
			param.put("studentId", tOrderTemp.getStudent_id());
			param.put("buId", tOrderTemp.getBu_id());
			param.put("accountType", 0);
			Integer tAccountCount=studentAccountService.queryAccountInfoCount(param);
			//8.2账户不存在则创建账户
			TAccount tAccount =null;
			if(tAccountCount==0){
				studentAccountService.createAccount(tOrderTemp.getStudent_id(), tOrderTemp.getBu_id(), tOrderTemp.getCreate_user());
			}
			tAccount=studentAccountService.queryAccountInfo(param);
			//8.3 生成账户变动记录
			TAccountChange tAccountChange=new TAccountChange();
			tAccountChange.setChange_flag(0l);
			tAccountChange.setChange_type(13l);
			tAccountChange.setAccount_type(1l);
			tAccountChange.setOrder_id(encoder.getOrder_id());
			tAccountChange.setEncoder_id(encoder.getId());
			tAccountChange.setAccount_id(tAccount.getId());
			tAccountChange.setChange_amount(encoder.getFee_amount());
			tAccountChange.setPre_amount(tAccount.getFee_amount()==null?0:tAccount.getFee_amount());
			tAccountChange.setNext_amount(tAccountChange.getPre_amount()+encoder.getFee_amount());
			tAccountChange.setPay_mode(1l);
			studentAccountService.saveAccountChange(tAccountChange);
			//8.4 更新账户余额
			studentAccountService.updateFrozenAccount(tAccount.getId(), tAccount.getFee_amount()==null?encoder.getFee_amount():tAccount.getFee_amount()+encoder.getFee_amount());
			//8.5 生成财务费用
			TFee fee=new TFee();
			fee.setOrder_id(encoder.getOrder_id());
			fee.setFee_type(32l);
			fee.setFee_flag(2l);
			fee.setFee_status(1l);
			fee.setOperate_type(5l);
			fee.setFee_amount(encoder.getFee_amount());
			fee.setInsert_time(new Date());
			fee.setFinish_time(new Date());
			fee.setEncoder_id(encoder.getId());
			fee.setOperate_no(orderChange.getId().toString());
			tFeeDao.saveFee(fee);
			//8.6 确认单据
			encoder.setStatus(1);
			encoderDao.updateEncoderById(encoder);
			//8.7 确认费用
			param.put("fee_status", 1);
			param.put("finish_time", DateUtil.getCurrDateTime());
			tFeeDao.updateFeeStatusByEncoderId((HashMap<String, Object>) param);
			tOrderChangeDao.updateOrderChangeStatus(param);
			//9 生效接口---订单批改正式生效后的处理逻辑
			updateTOrderCoursePremiumInfo(tOrderChange,param);
			orderCourseTimesInfoService.updateValidOrderCourseTimes(param);
		}catch(Exception e){
			throw e;
		} finally {
			// 13.释放批改业务锁
			for(TLock tLock:lockList){
				tLockDao.releaseLock(tLock);
			}
		}
    }
    
    
    /**
     * 导入到批改表--生成录入数据  新增批改表tc_order_course，tc_order_course_times
     * @throws Exception 
     * 
     */
    private void addTcOrderCourse(Map<String,Object> param) throws Exception{
    	List<TCChangeCourse> tCChangeCourseList=tOrderCourseDao.queryTabChangeCourse(param);
		for(TCChangeCourse tCChangeCourse:tCChangeCourseList){
			TCOrderCourse tCOrderCourse=new TCOrderCourse();
			tCOrderCourse.setChange_id(tCChangeCourse.getChange_id());
			tCOrderCourse.setOrder_id(tCChangeCourse.getOrder_id());
			tCOrderCourse.setOrder_course_id(tCChangeCourse.getOrder_course_id());
			tCOrderCourse.setCourse_times(tCChangeCourse.getCourse_times());
			tCOrderCourse.setTotal_amount(tCChangeCourse.getTotal_amount());
			tCOrderCourse.setAttend_amount(tCChangeCourse.getAttend_amount());
			tCOrderCourse.setPre_amount(tCChangeCourse.getPre_amount());
			tCOrderCourseDao.saveTcOrderCourse(tCOrderCourse);
			param.put("change_course_id", tCChangeCourse.getId());
			List<TCChangeCourseTimes> tCChangeCourseTimesList=orderCourseTimesInfoService.queryTabChangeCourseTimesInfo(param);
			for(TCChangeCourseTimes tCChangeCourseTimes:tCChangeCourseTimesList){
				TCCourseTimes tCCourseTimes=new TCCourseTimes();
				tCCourseTimes.setChangeCourseId(tCOrderCourse.getId());
				tCCourseTimes.setChangeId(tCChangeCourse.getChange_id());
				tCCourseTimes.setCourseTimes(tCChangeCourseTimes.getCourse_times());
				tCCourseTimes.setOrderCourseId(tCChangeCourse.getOrder_course_id());
				tCCourseTimes.setOrderId(tCChangeCourse.getOrder_id());
				orderCourseTimesInfoService.saveTcOrderCourseTimes(tCCourseTimes);
			}
		}
    }
    /**
     * 标准退费
     * @param tOrderChange
     * @param param
     * @throws Exception
     */
    private void addFee(TOrderChange tOrderChange,Map<String,Object> param) throws Exception{
    	List<TCOrderCourse> tcOrderCourseList=tCOrderCourseDao.queryTcOrderCourseByChangeId(tOrderChange.getId());
		Double fee_deduction_amount=0.0;
		TFeeDetail tFeeDetail=new TFeeDetail();
		TFee tFee=new TFee();
		for(TCOrderCourse tcOrderCourse:tcOrderCourseList){
			
			tFeeDetail.setOrder_id(tOrderChange.getOrder_id());
			tFeeDetail.setOperate_type(5l);
			tFeeDetail.setFee_type(55l);
			tFeeDetail.setFee_flag(2l);
			tFeeDetail.setCourse_sum(tcOrderCourse.getCourse_times());
			tFeeDetail.setOperate_no(tcOrderCourse.getChange_id());
			tFeeDetail.setOrder_detail_id(tcOrderCourse.getOrder_course_id());
			param.put("order_course_id", tcOrderCourse.getOrder_course_id());
			param.put("root_course_id", tcOrderCourse.getRoot_course_id());
			int attend_cnt=attendanceService.countAttend1ByOrderCourseId(param);
			int attend_cnt2=attendanceService.countAttend2ByOrderCourseId(param);
			int attend_cnt3=attendanceService.countAttend3ByOrderCourseId(param);
			param.put("id", tcOrderCourse.getOrder_course_id());
			List<TOrderCourse> tOrderCourseList=tOrderCourseDao.queryOrderCourse(param);
			Double attendAmount=	(attend_cnt+attend_cnt2+attend_cnt3)*(tOrderCourseList.get(0).getFormer_unit_price()-tOrderCourseList.get(0).getDiscount_unit_price());
			Double totalAmount=0.0;
			if(tOrderCourseList!=null&&tOrderCourseList.size()>0){
			    totalAmount=tcOrderCourse.getTotal_amount();
				if(totalAmount<0 && tcOrderCourse.getAttend_amount()>0){
					attendAmount+=totalAmount;
					totalAmount=0.0;
					if(attendAmount<0){
						fee_deduction_amount+=attendAmount;
						attendAmount=0.0;
					}
				}
			}
			tFeeDetail.setFee_amount(totalAmount);
			feeDetailService.saveFeeDetail(tFeeDetail);
			if(attendAmount>0){
				tFeeDetail.setFee_type(62l);
				tFeeDetail.setFee_flag(1l);
				tFeeDetail.setFee_amount(attendAmount);
				tFeeDetail.setCourse_sum(tcOrderCourse.getCourse_times());
				feeDetailService.saveFeeDetail(tFeeDetail);
			}
			if(tcOrderCourse.getPre_amount()>0){
				tFeeDetail.setFee_type(42l);
				tFeeDetail.setFee_flag(1l);
				tFeeDetail.setFee_amount(-1*tcOrderCourse.getPre_amount());
				tFeeDetail.setCourse_sum(tcOrderCourse.getCourse_times());
				feeDetailService.saveFeeDetail(tFeeDetail);
			}
		}
		
		if(tOrderChange.getFee_deduction_amount()!=null&&tOrderChange.getFee_deduction_amount()!=0){
			tFeeDetail.setFee_type(54l);
			tFeeDetail.setFee_flag(2l);
			tFeeDetail.setFee_amount(-1.0*tOrderChange.getFee_deduction_amount());
			tFeeDetail.setCourse_sum(0l);
			tFeeDetail.setOrder_detail_id(tOrderChange.getOrderCourseId());
			feeDetailService.saveFeeDetail(tFeeDetail);
		}
		//生成费用
		tFee.setOperate_type(5l);
		tFee.setOperate_no(tOrderChange.getId().toString());
		tFee.setInsert_time(new Date());
		tFee.setFee_status(0l);
		tFeeDetail.setOperate_no(tOrderChange.getId());
		List<TFeeDetail> tFeeDetailList=feeDetailService.queryFeeDetailByChangeId(tFeeDetail);
		for(TFeeDetail tFeeDetailTmp:tFeeDetailList){
			tFee.setFee_type(tFeeDetailTmp.getFee_type());
			tFee.setOrder_id(tFeeDetailTmp.getOrder_id());
			tFee.setFee_flag(tFeeDetailTmp.getFee_flag());
			tFee.setFee_amount(tFeeDetailTmp.getFee_amount());
			feeService.saveFee(tFee);
			tFeeDetailTmp.setFee_id(tFee.getId());
			tFeeDetailTmp.setOperate_no(Long.valueOf(tFee.getOperate_no()));
			feeDetailService.updateFeeIdByFeeChangeId(tFeeDetailTmp);
		}
		param.put("operate_no", tOrderChange.getId());
		param.put("operate_type", 5);
		param.put("fee_type", "'54','55'");
		TFee tFeeTmp=feeService.queryFeeAmountByChangeId(param);
		if(tFeeTmp.getFee_amount()<0){
			tFeeTmp.setFee_amount(0.0);
		}
		if(tFeeTmp.getFee_amount()>=0){
			TEncoder tEncoder=new TEncoder();
			tEncoder.setFee_amount(tFeeTmp.getFee_amount());
			tEncoder.setStatus(0);
			tEncoder.setBusi_type(5l);
			tEncoder.setEncoder_type(7l);
			tEncoder.setFee_flag(2l);
			tEncoder.setOrder_id(tOrderChange.getOrder_id());
			tEncoder.setEncoder_no("OC"+tOrderChange.getId());
			tEncoder.setBusi_id(tOrderChange.getId());
			encoderService.saveTEncoder(tEncoder);
			param.put("encoder_id",tEncoder.getId());
			//更新费用字段
			feeService.updateFeeEncoderIdByChangeId(param);
			//冻结补扣的数据 
//			orderChangeService.updateAmountByChangeId(param);
		}
    }
    /**
     * VIP退费
     * @param tOrderChange
     * @param param
     * @throws Exception
     */
    private void addFee2(TOrderChange tOrderChange,Map<String,Object> param) throws Exception{
    	List<TCOrderCourse> tcOrderCourseList=tCOrderCourseDao.queryTcOrderCourseByChangeId(tOrderChange.getId());
		TFeeDetail tFeeDetail=new TFeeDetail();
		TFee tFee=new TFee();
		for(TCOrderCourse tcOrderCourse:tcOrderCourseList){
			tOrderChange.setOrderCourseId(tcOrderCourse.getOrder_course_id());
			tFeeDetail.setOrder_id(tOrderChange.getOrder_id());
			tFeeDetail.setOperate_type(5l);
			tFeeDetail.setFee_type(55l);
			tFeeDetail.setFee_flag(2l);
			tFeeDetail.setFee_amount(tcOrderCourse.getTotal_amount());
			tFeeDetail.setCourse_sum(tcOrderCourse.getCourse_times());
			tFeeDetail.setOperate_no(tcOrderCourse.getChange_id());
			tFeeDetail.setOrder_detail_id(tcOrderCourse.getOrder_course_id());
			param.put("order_course_id", tcOrderCourse.getOrder_course_id());
			param.put("root_course_id", tcOrderCourse.getRoot_course_id());
			param.put("id", tcOrderCourse.getOrder_course_id());
			
			tFeeDetail.setFee_amount(tcOrderCourse.getTotal_amount());
			feeDetailService.saveFeeDetail(tFeeDetail);
			if(tcOrderCourse.getPre_amount()>0){
				Map<String, Object> paramMap = new HashMap<String, Object>();
		        paramMap.put("orderCourseId", tcOrderCourse.getOrder_course_id());
		        paramMap.put("rootCourseId", tcOrderCourse.getRoot_course_id());
				Map<String, Object> manageFeeMap=tOrderCourseDao.queryTotalManageFee(paramMap);
				Double manageFee = ((BigDecimal) manageFeeMap.get("MANAGE_FEE")).doubleValue();
				
				tFeeDetail.setFee_type(42l);
				tFeeDetail.setFee_flag(1l);
				tFeeDetail.setFee_amount(-1*manageFee);
				tFeeDetail.setCourse_sum(tcOrderCourse.getCourse_times());
				feeDetailService.saveFeeDetail(tFeeDetail);
				
				tFeeDetail.setFee_type(42l);
				tFeeDetail.setFee_flag(1l);
				tFeeDetail.setFee_amount(manageFee-tcOrderCourse.getPre_amount());
				tFeeDetail.setCourse_sum(tcOrderCourse.getCourse_times());
				feeDetailService.saveFeeDetail(tFeeDetail);
				
				Long v_transfer_out_id=0l;
				List<TOrderCourse> tOrderCourseManageFee=tOrderCourseDao.queryManageFee(param);
				if(tOrderCourseManageFee!=null&&tOrderCourseManageFee.size()>0){
					v_transfer_out_id=tOrderCourseManageFee.get(0).getId();
				}
				if(v_transfer_out_id.longValue()!=tcOrderCourse.getOrder_course_id().longValue()){
					tFeeDetail.setOrder_detail_id(v_transfer_out_id);
					tFeeDetail.setOperate_type(5l);
					tFeeDetail.setOperate_no(tOrderChange.getId());
					tFeeDetail.setFee_type(52l);
					tFeeDetail.setFee_flag(2l);
					tFeeDetail.setFee_amount(tcOrderCourse.getPre_amount());
					tFeeDetail.setCourse_sum(0l);
					feeDetailService.saveFeeDetail(tFeeDetail);
					
					tFeeDetail.setFee_type(42l);
					tFeeDetail.setFee_amount(-1*tcOrderCourse.getPre_amount());
					feeDetailService.saveFeeDetail(tFeeDetail);
					
					tFeeDetail.setOrder_detail_id(tcOrderCourse.getOrder_course_id());
					tFeeDetail.setFee_type(42l);
					tFeeDetail.setFee_flag(1l);
					tFeeDetail.setFee_amount(tcOrderCourse.getPre_amount());
					feeDetailService.saveFeeDetail(tFeeDetail);
					
					tFeeDetail.setOrder_detail_id(tcOrderCourse.getOrder_course_id());
					tFeeDetail.setFee_type(52l);
					tFeeDetail.setFee_flag(1l);
					tFeeDetail.setFee_amount(tcOrderCourse.getPre_amount());
					feeDetailService.saveFeeDetail(tFeeDetail);
				}
				
			}
		}
		
		if(tOrderChange.getFee_deduction_amount()!=null&&tOrderChange.getFee_deduction_amount()!=0){
			tFeeDetail.setFee_type(54l);
			tFeeDetail.setFee_flag(2l);
			tFeeDetail.setFee_amount(-1.0*tOrderChange.getFee_deduction_amount());
			tFeeDetail.setCourse_sum(0l);
			tFeeDetail.setOrder_detail_id(tOrderChange.getOrderCourseId());
			feeDetailService.saveFeeDetail(tFeeDetail);
		}
		//生成费用
		tFee.setOperate_type(5l);
		tFee.setOperate_no(tOrderChange.getId().toString());
		tFee.setInsert_time(new Date());
		tFee.setFee_status(0l);
		tFeeDetail.setOperate_no(tOrderChange.getId());
		List<TFeeDetail> tFeeDetailList=feeDetailService.queryFeeDetailByChangeId(tFeeDetail);
		for(TFeeDetail tFeeDetailTmp:tFeeDetailList){
			tFee.setFee_type(tFeeDetailTmp.getFee_type());
			tFee.setOrder_id(tFeeDetailTmp.getOrder_id());
			tFee.setFee_flag(tFeeDetailTmp.getFee_flag());
			tFee.setFee_amount(tFeeDetailTmp.getFee_amount());
			feeService.saveFee(tFee);
			tFeeDetailTmp.setFee_id(tFee.getId());
			tFeeDetailTmp.setOperate_no(Long.valueOf(tFee.getOperate_no()));
			feeDetailService.updateFeeIdByFeeChangeId(tFeeDetailTmp);
		}
		param.put("operate_no", tOrderChange.getId());
		param.put("operate_type", 5);
		param.put("fee_type", "'54','55'");
		TFee tFeeTmp=feeService.queryFeeAmountByChangeId(param);
		if(tFeeTmp.getFee_amount()<0){
			tFeeTmp.setFee_amount(0.0);
		}
		if(tFeeTmp.getFee_amount()>=0){
			TEncoder tEncoder=new TEncoder();
			tEncoder.setFee_amount(tFeeTmp.getFee_amount());
			tEncoder.setStatus(0);
			tEncoder.setBusi_type(5l);
			tEncoder.setEncoder_type(7l);
			tEncoder.setFee_flag(2l);
			tEncoder.setOrder_id(tOrderChange.getOrder_id());
			tEncoder.setEncoder_no("OC"+tOrderChange.getId());
			tEncoder.setBusi_id(tOrderChange.getId());
			encoderService.saveTEncoder(tEncoder);
			param.put("encoder_id",tEncoder.getId());
			//更新费用字段
			feeService.updateFeeEncoderIdByChangeId(param);
			//冻结补扣的数据
			param.put("fee_return_amount",tFeeTmp.getFee_amount());
			param.put("fee_amount",tFeeTmp.getFee_amount()+tOrderChange.getFee_deduction_amount());
			param.put("premium_deduction_amount",tOrderChange.getFee_deduction_amount());
			tOrderChangeDao.updateAmountsByChangeId(param);
		}
    }
    /**
     * 更新订单课程的价格明细
     * @param tOrderChange
     * @param param
     * @throws Exception
     */
    private void updateTOrderCoursePremiumInfo(TOrderChange tOrderChange,Map<String,Object> param) throws Exception{
		//备份数据
		Map<String,Object> queryParam=new HashMap<String,Object>();
		queryParam.put("changeId", tOrderChange.getId());
		tOrderCourseLogDao.insertOrderCourseLog(queryParam);
		tOrderCourseLogDao.insertOrderCourseTimesLog(queryParam);

    	TOrderCourse tOrderCourse = null;
		List<TCOrderCourse> tcOrderCourseList=tCOrderCourseDao.queryTcOrderCourseByChangeId(tOrderChange.getId());
		for(TCOrderCourse tcOrderCourse:tcOrderCourseList){
			param.put("id", tcOrderCourse.getOrder_course_id());
			List<TOrderCourse> tOrderCourseList=tOrderCourseDao.queryOrderCourse(param);
			if(tOrderCourseList!=null&&tOrderCourseList.size()>0){
				tOrderCourse=tOrderCourseList.get(0);
			}
			Double v_manage_fee=0.0;
			Long v_course_surplus_count=0l;
			Long v_transfer_out_id=null;
			List<TOrderCourse> tOrderCourseManageFee=tOrderCourseDao.queryManageFee(param);
			if(tOrderCourseManageFee!=null&&tOrderCourseManageFee.size()>0){
				v_manage_fee=tOrderCourseManageFee.get(0).getManage_fee();
				v_transfer_out_id=tOrderCourseManageFee.get(0).getId();
			}
			List<TOrderCourse> tOrderCourseSurplusCount=tOrderCourseDao.queryCourseSurplusCount(param);
			if(tOrderCourseSurplusCount!=null&&tOrderCourseSurplusCount.size()>0){
				v_course_surplus_count=tOrderCourseSurplusCount.get(0).getCourse_surplus_count();
			}
			List<TabChangeCourse> tChangeCourseList=tabChangeCourseDao.queryChangeCourseInfo(param);
			if(tChangeCourseList!=null&&tChangeCourseList.size()>0){
				int premium_type=tChangeCourseList.get(0).getPremium_type()==null?1:tChangeCourseList.get(0).getPremium_type();
				Double manage_fee=tOrderCourse.getManage_fee();
				Long course_surplus_count=tOrderCourse.getCourse_surplus_count();
				int quit_flag=1;
				Double surplus_cost=0.0;
				Double discount_unit_price=tOrderCourse.getDiscount_unit_price();
				if(premium_type==2){
					manage_fee=  manage_fee - Math.floor((manage_fee *(tcOrderCourse.getCourse_times().doubleValue()/tOrderCourse.getCourse_surplus_count())));//返预结转
					course_surplus_count=course_surplus_count-tcOrderCourse.getCourse_times();
					surplus_cost=(course_surplus_count) *tOrderCourse.getDiscount_unit_price();
				}else{
					discount_unit_price=tOrderCourse.getFormer_unit_price();  //修改单价
					manage_fee=0.0;   //返预结转
					course_surplus_count=course_surplus_count-tcOrderCourse.getCourse_times();
					surplus_cost=(course_surplus_count) *tOrderCourse.getFormer_unit_price(); //订单剩余价值=单价*剩余次数
				}
				param.put("root_course_id", null);
				param.put("manage_fee", manage_fee);
				param.put("quit_flag", quit_flag);
				param.put("surplus_cost", surplus_cost);

				if (tOrderCourse.getCourse_schedule_count() != null) {
					param.put("course_schedule_count", tOrderCourse.getCourse_schedule_count()-tcOrderCourse.getCourse_times());
				}

				param.put("course_surplus_count", course_surplus_count);
				param.put("discount_unit_price", discount_unit_price);
				tOrderCourseDao.updateTOrderCoursePrice(param);
				if(!StringUtil.isEmpty(tOrderCourse.getRoot_course_id())){
					if(premium_type==2){
						manage_fee=manage_fee-Math.floor(manage_fee*(tcOrderCourse.getCourse_times().doubleValue()/(course_surplus_count==0?1:course_surplus_count))); //返预结转
						surplus_cost=course_surplus_count*discount_unit_price;
						param.put("id", tOrderCourse.getRoot_course_id());
						param.put("root_course_id", null);

						param.put("manage_fee", manage_fee);
						param.put("quit_flag", quit_flag);
						param.put("surplus_cost", surplus_cost);
						param.put("course_surplus_count", course_surplus_count);
						param.put("discount_unit_price", discount_unit_price);
						tOrderCourseDao.updateTOrderCoursePrice(param);
						//将所有的衍生子单的单价恢复到原价
						if(course_surplus_count==0){
							manage_fee=0.0;
						}else{
							manage_fee=manage_fee- Math.floor((manage_fee *(tcOrderCourse.getCourse_times().doubleValue()/course_surplus_count))); //返预结转
						}
						param.put("manage_fee", manage_fee);
						param.put("id", null);
						param.put("root_course_id",  tOrderCourse.getRoot_course_id());
						tOrderCourseDao.updateTOrderCoursePrice(param);
					}else{
						discount_unit_price=tOrderCourse.getFormer_unit_price();
						manage_fee=0.0;
						surplus_cost=tOrderCourse.getCourse_surplus_count()*tOrderCourse.getFormer_unit_price();
						param.put("id", tOrderCourse.getRoot_course_id());
						param.put("root_course_id", null);

						param.put("manage_fee", manage_fee);
						param.put("quit_flag", quit_flag);
						param.put("surplus_cost", surplus_cost);
						param.put("course_surplus_count", course_surplus_count);
						param.put("discount_unit_price", discount_unit_price);
						tOrderCourseDao.updateTOrderCoursePrice(param);
						//所有的衍生子单的单价恢复到原价
						param.put("id", null);
						param.put("root_course_id",  tOrderCourse.getRoot_course_id());
						tOrderCourseDao.updateTOrderCoursePrice(param);
					}
					
				}

				TOrderCourse orderCourse=new TOrderCourse();
				if(premium_type!=2){
					discount_unit_price=tOrderCourse.getFormer_unit_price();
					orderCourse.setDiscount_unit_price(discount_unit_price);
					orderCourse.setRoot_course_id( tOrderCourse.getId());
					if(orderCourse.getId()!=null){
						tOrderCourseDao.updateOrderCourse(orderCourse);
					}
				}
				if(premium_type==2){
					if(v_transfer_out_id!=null) {
						manage_fee = v_manage_fee - Math.floor((v_manage_fee * (tcOrderCourse.getCourse_times().doubleValue() / v_course_surplus_count)));//返预结转
						manage_fee=Math.floor(manage_fee);
						orderCourse.setId(v_transfer_out_id);
						orderCourse.setManage_fee(manage_fee);
						tOrderCourseDao.updateOrderCourse(orderCourse);
					}
				}

			}
		}
    }
    /**
     * 冻结作废
     */
	@Override
	public void cancelFrozenOrder(TOrderChange orderChange, Long businessType) throws Exception {
		TOrderChange cancelOrderChange=null;
		try{
			//1.查询变更记录
			if(orderChange.getChange_type() != 5L){
				throw new Exception("本次批改未生效不能执行作废操作，未生效可能是未审批或审批未通过！");
			}

			TOrder tOrder=tOrderDao.queryOrderInfoByChangeId(orderChange.getId());
			
			//2.生成冻结作废的变动信息
			TOrderChange TOrderChangeNew=(TOrderChange) orderChange.clone();
			cancelOrderChange=createOrderChangeInfo(TOrderChangeNew,tOrder); 
			//3.更新web数据中的 change_id
			Map<String,Object> queryParam=new HashMap<String,Object>();
			queryParam.put("invalid_id", orderChange.getId());
			queryParam.put("change_id", cancelOrderChange.getId());
			tOrderChangeDao.updateInvalidIdByOrderChangeId(queryParam);

			//checkExpiredChangeCourseTimes(orderChange.getId());

			//4.锁的校验
			checkLock(cancelOrderChange);
			//5.录入信息的校验
			checkInput(orderChange,cancelOrderChange);
			queryParam.put("change_id", cancelOrderChange.getId());
			queryParam.put("input_user", orderChange.getInput_user());
			tOrderChangeDao.updateInputUserByOrderChangeId(queryParam);
			//6.添加费用明细
			addCancelFrozenFee(orderChange,cancelOrderChange);
			//7.财务处理费用
			finConfirm(cancelOrderChange);
			//8.订单批改正式生效后的处理逻辑
			validate(orderChange,cancelOrderChange);
			//9.完成订单批改
			queryParam.put("invalid_id", cancelOrderChange.getId());
			queryParam.put("change_id", orderChange.getId());
			tOrderChangeDao.updateInvalidIdByOrderChangeId(queryParam);
		}catch(Exception e){
			throw new Exception(e.getMessage(),e);
		}finally{
			//释放锁
			TLock tLock=new TLock();
			tLock.setBusiId(5l);
			tLock.setBusiType(cancelOrderChange.getId());
			tLockDao.releaseLock(tLock);
		}
	}
	/**
	 * 生成冻结作废的变动信息
	 * @param orderChange
	 * @param tOrder
	 * @return
	 * @throws Exception
	 */
	private TOrderChange createOrderChangeInfo(TOrderChange orderChange,TOrder tOrder) throws Exception{
		//1.行级独占锁
		Map<String, Object> param=new HashMap<String,Object>();
		param.put("change_no", orderChange.getChange_no());
		param.put("order_id", orderChange.getOrder_id());
		tLockDao.queryLockOrderId(param);
		//2.申请校验接口-校验当前订单是否可以做该批改项
		//2.1判断当前订单是否被锁定
		param.put("lock_type", 1);
		int lockFlag=tLockDao.queryLockOrderFlag(param);
		if(lockFlag>0){
			throw new Exception("当前订单被锁定，不能申请冻结作废!");
		}
		if(tOrder.getOrder_status()==0l){
			throw new Exception("该订单已作废,不能再冻结作废！");
		}
		//2.3.新增p_create_order_change
		orderChange.setChange_type(6l);
		orderChange.setOrder_id(tOrder.getId());
		orderChange.setBranch_id(tOrder.getBranch_id());
		orderChange.setApply_time(new Date());
		orderChange.setCreate_time(new Date());
		orderChange.setChange_status(2l);
		orderChange.setUpdate_time(new Date());
		orderChange.setCreate_time(new Date());
		orderChangeService.saveOrderChange(orderChange);
		return orderChange;
	}
	
	/**
	 * 锁校验
	 * @param cancelOrderChange
	 * @throws Exception 
	 */
	private void checkLock(TOrderChange cancelOrderChange) throws Exception{
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("change_id", cancelOrderChange.getId());
		int courseLockNum=tLockDao.checkCourseLock(param);
		if(courseLockNum>0){
			throw new Exception("本次课程或联报课程存在被锁定的课程，不允许操作！");
		}
		int courseTimesLockNum=tLockDao.checkCourseTimesLock(param);
		if(courseTimesLockNum>0){
			throw new Exception("本次操作存在被锁定的课次，不允许操作！");
		}
		//6.2 加锁
		List<TCOrderCourse> tCOrderCourseTmpList=tCOrderCourseDao.queryAllChangeTimes(cancelOrderChange.getId());
		for(TCOrderCourse TCOrderCourse:tCOrderCourseTmpList){
			TLock tLock=new TLock();
			tLock.setType(2l);
			tLock.setResourceId(TCOrderCourse.getOrder_course_id());
			tLock.setBusiType(5l);
			tLock.setBusiId(cancelOrderChange.getId());
			tLockDao.saveLock(tLock);
		}
//		List<TCCourseTimes> tcCourseTimesList=tCOrderCourseDao.queryTcOrderCourseTimesByChangeId(orderChange.getId());
//		for(TCCourseTimes TCCourseTimes:tcCourseTimesList){
//			TLock tLock=new TLock();
//			tLock.setType(3l);
//			tLock.setResourceId(TCCourseTimes.getId());
//			tLock.setBusiType(5l);
//			tLock.setBusiId(orderChange.getId());
//			tLockDao.saveLock(tLock);
//		}
	}
	/**
	 * 订单批改信息录入完成后对录入数据进行校验
	 * @param orderChange
	 * @throws Exception
	 */
	private void checkInput(TOrderChange orderChange,TOrderChange cancelOrderChange) throws Exception{
		int changePriceNum=tOrderCourseLogDao.queryChangePriceNumByChangeId(orderChange.getId());
		if(orderChange.getChange_status()==7l){
			throw new Exception("订单已经作废，不能重复作废！");
		}
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("validateTime", orderChange.getValidate_time());
		param.put("orderId", orderChange.getOrder_id());
		param.put("changeId", orderChange.getId());
		param.put("pChangeId", cancelOrderChange.getId());
		param.put("inputTime", orderChange.getInput_time());
		int num=tOrderCourseLogDao.queryAttendNumsByChangeId(param);
		if(num>0&&changePriceNum>0){
			throw new Exception("订单冻结后已有新考勤，不能作废，如要作废需将考勤置空！");
		}
		num=tOrderCourseLogDao.queryChangesNumsByChangeId(param);
		if(num>0&&changePriceNum>0){
			throw new Exception("该订单课程冻结后已有批改操作，请撤销做作废批改操作后，进行作废处理！");
		}
		num=tOrderCourseLogDao.queryTuiFeiNumsByChangeId(param);
		if(num>0){
			throw new Exception("请先作废当前冻结单据发生以后的其他冻结单据");
		}
		num=tOrderCourseLogDao.queryRootCourseTuiFeiNumsByChangeId(param);
		if(num>0){
			throw new Exception("请先作废当前冻结单据发生以后的其他冻结单据");
		}
	}

	//private void checkExpiredChangeCourseTimes(Long changeId) throws Exception {
	//	Integer count = this.tOrderChangeDao.queryExpiredChangeCourseTimesCount(changeId);
	//	if (count > 0) {
	//		throw new Exception("存在课次已经开始上课，冻结作废失败！");
	//	}
	//}
	
	  /**
     * 添加费用明细
     * @param tOrderChange
     * @param cancelTorderChange
     * @throws Exception
     */
    private void addCancelFrozenFee(TOrderChange tOrderChange,TOrderChange cancelTorderChange) throws Exception{
    	Map<String,Object> param=new HashMap<String,Object>();
    	param.put("changeId", tOrderChange.getId());
    	param.put("operateType", "5");
    	List<TFeeDetail> tFeeDetailList=tFeeDetailDao.queryCancelFeeDetailByChangeId(param);
		for(TFeeDetail tFeeDetailTmp:tFeeDetailList){
			TFeeDetail tFeeDetail=new TFeeDetail();
			tFeeDetail.setOrder_id(tFeeDetailTmp.getOrder_id());
			tFeeDetail.setOperate_type(tFeeDetailTmp.getOperate_type());
			tFeeDetail.setOrder_detail_id(tFeeDetailTmp.getOrder_detail_id());
			tFeeDetail.setFee_type(tFeeDetailTmp.getFee_type());
			tFeeDetail.setFee_flag(tFeeDetailTmp.getFee_flag());
			tFeeDetail.setCourse_sum(-1*tFeeDetailTmp.getCourse_sum());
			tFeeDetail.setFee_amount(-1*tFeeDetailTmp.getFee_amount());
			tFeeDetail.setOperate_no(cancelTorderChange.getId());
			tFeeDetailDao.saveFeeDetail(tFeeDetail);
		}
		List<TFee> tFeeList=tFeeDao.queryCancelFeeByChangeId(param);
		TFeeDetail tFeeDetailTmp=new TFeeDetail();
		for(TFee tFeeTmp:tFeeList){
			TFee tFee=new TFee();
			tFee.setFee_type(tFeeTmp.getFee_type());
			tFee.setOrder_id(tFeeTmp.getOrder_id());
			tFee.setFee_flag(tFeeTmp.getFee_flag());
			tFee.setFee_amount(-1*tFeeTmp.getFee_amount());
			tFee.setInsert_time(new Date());
			tFee.setFinish_time(new Date());
			tFee.setFee_status(tFeeTmp.getFee_status());
			tFee.setOperate_type(tFeeTmp.getOperate_type());
			tFee.setOperate_no(cancelTorderChange.getId().toString());
			tFeeDao.saveFee(tFee);
			//修改费用详情外键关联
			tFeeDetailTmp.setFee_id(tFee.getId());
			tFeeDetailTmp.setOperate_no(Long.valueOf(tFee.getOperate_no()));
			tFeeDetailTmp.setFee_type(tFeeTmp.getFee_type());
			tFeeDetailDao.updateFeeIdByFeeChangeId(tFeeDetailTmp);
		}
		param.put("operate_no", cancelTorderChange.getId());
		param.put("operate_type", 5);
		param.put("fee_type", "'55','54'");
		TFee tFeeTmp=tFeeDao.queryFeeAmountByChangeId(param);
		TEncoder tEncoder=new TEncoder();
		tEncoder.setFee_amount(tFeeTmp.getFee_amount());
		tEncoder.setStatus(0);
		tEncoder.setBusi_type(5l);
		tEncoder.setEncoder_type(15l);
		tEncoder.setFee_flag(1l);
		tEncoder.setOrder_id(tOrderChange.getOrder_id());
		tEncoder.setEncoder_no("OC"+cancelTorderChange.getId());
		tEncoder.setBusi_id(cancelTorderChange.getId());
		encoderDao.saveEncoder(tEncoder);
		param.put("encoder_id",tEncoder.getId());
		//更新费用字段
		tFeeDao.updateFeeEncoderIdByChangeId(param);
		param.put("fee_amount", tFeeTmp.getFee_amount());
		param.put("change_id", cancelTorderChange.getId());
		tOrderChangeDao.updateFeeAmountByChangeId(param);
    }
    
    private void finConfirm(TOrderChange cancelOrderChange) throws Exception{
    	TEncoder encoder=new TEncoder();
		encoder.setBusi_type(5l);
		encoder.setBusi_id(cancelOrderChange.getId());
		encoder=encoderDao.queryEncoderInfo(encoder);
		TOrder tOrderTemp=orderService.queryOrderInfo(encoder.getOrder_id());
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("studentId", tOrderTemp.getStudent_id());
		param.put("buId", tOrderTemp.getBu_id());
		param.put("cityId", tOrderTemp.getCity_id());
		param.put("accountType", 0);
		TAccount tAccount=studentAccountService.queryAccountInfo(param);
	
		//8.3 生成账户变动记录
		TAccountChange tAccountChange=new TAccountChange();
		tAccountChange.setChange_flag(1l);  //取出
		tAccountChange.setChange_type(14l); //冻结作废
		tAccountChange.setAccount_type(1l); //冻结账户
		tAccountChange.setOrder_id(encoder.getOrder_id());
		tAccountChange.setEncoder_id(encoder.getId());
		tAccountChange.setAccount_id(tAccount.getId());
		tAccountChange.setChange_amount(encoder.getFee_amount());
		tAccountChange.setPre_amount(tAccount.getFrozen_account());
		tAccountChange.setNext_amount(tAccount.getFrozen_account()+encoder.getFee_amount());
		tAccountChange.setPay_mode(1l);
		studentAccountDao.saveAccountChange(tAccountChange);
		//8.4 更新账户余额
		HashMap<String,Object> queryParam=new HashMap<String,Object>();
		Double amount=tAccount.getFee_amount()==null?0:tAccount.getFee_amount()+encoder.getFee_amount();
		queryParam.put("amount", amount);
		queryParam.put("accountId", tAccount.getId());
		if(amount<0){
			throw new Exception("冻结账户没有足够的金额！");
		}
		studentAccountDao.updateFrozenAccount(queryParam);
		//8.5 生成财务费用
		TFee fee=new TFee();
		fee.setOrder_id(encoder.getOrder_id());
		fee.setFee_type(31l);  //订单批改业务中客户实际缴费
		fee.setFee_flag(1l);
		fee.setFee_status(1l);
		fee.setOperate_type(5l);
		fee.setFee_amount(encoder.getFee_amount());
		fee.setInsert_time(new Date());
		fee.setFinish_time(new Date());
		fee.setEncoder_id(encoder.getId());
		fee.setOperate_no(cancelOrderChange.getId().toString());
		tFeeDao.saveFee(fee);
		//8.6 确认单据
		encoder.setStatus(1);
		encoderDao.updateEncoderById(encoder);
		//8.7 确认费用
		param.put("fee_status", 1);
		param.put("encoder_id", encoder.getId());
		param.put("finish_time", DateUtil.getCurrDateTime());
		tFeeDao.updateFeeStatusByEncoderId((HashMap<String, Object>) param);
		
    }
    
    private void validate(TOrderChange orderChange,TOrderChange cancelTorderChange) throws Exception{
    	//备份数据
    	Map<String,Object> queryParam=new HashMap<String,Object>();
    	queryParam.put("changeId", cancelTorderChange.getId());
    	tOrderCourseLogDao.insertOrderCourseLog(queryParam);
    	tOrderCourseLogDao.insertOrderCourseTimesLog(queryParam);
    	//批改生效
    	Map<String,Object> param=new HashMap<String,Object>();
    	param.put("changeId", orderChange.getId());
    	param.put("pchangeId", cancelTorderChange.getId());
    	param.put("orderId", orderChange.getOrder_id());
    	Integer pgNums=tOrderChangeDao.queryPGOrderChangeNums(param);
    	param.put("change_stuatus", 7);
    	param.put("change_id", orderChange.getId());
    	tOrderChangeDao.updateOrderChangeStatus(param);
    	List<TOrderCourseLog> tOrderCourseLogList=tOrderCourseLogDao.queryTOrderCourseLogByChangeId(orderChange.getId());
    	TOrderCourse orderCourse=new TOrderCourse();
    	orderCourse.setUpdate_user(orderChange.getInput_user());
    	for(TOrderCourseLog tOrderCourseLog:tOrderCourseLogList){
    		if(tOrderCourseLog.getFormer_unit_price().doubleValue()!=tOrderCourseLog.getCur_former_unit_price().doubleValue()
    				|| (tOrderCourseLog.getDiscount_unit_price().doubleValue()!=tOrderCourseLog.getCur_discount_unit_price().doubleValue())){
    			orderCourse.setDiscount_unit_price(tOrderCourseLog.getDiscount_unit_price());
    			orderCourse.setDiscount_sum_price(tOrderCourseLog.getDiscount_sum_price());
    			orderCourse.setManage_fee(tOrderCourseLog.getManage_fee());
    			orderCourse.setCourse_surplus_count(tOrderCourseLog.getCourse_surplus_count());
				orderCourse.setCourse_schedule_count(tOrderCourseLog.getCourse_schedule_count());
    			orderCourse.setQuit_flag(tOrderCourseLog.getQuit_flag());
    			orderCourse.setSurplus_cost(tOrderCourseLog.getSurplus_cost());
    			orderCourse.setId(tOrderCourseLog.getId());
    			tOrderCourseDao.updateOrderCourse(orderCourse);
    		}else{
    			orderCourse.setDiscount_unit_price(tOrderCourseLog.getDiscount_unit_price());
    			orderCourse.setDiscount_sum_price(tOrderCourseLog.getDiscount_sum_price());
    			orderCourse.setManage_fee(tOrderCourseLog.getManage_fee());
    			orderCourse.setCourse_surplus_count(tOrderCourseLog.getCur_course_surplus_count()+tOrderCourseLog.getCharge_back_num());
				orderCourse.setCourse_schedule_count((tOrderCourseLog.getCur_course_schedule_count()==null?0:tOrderCourseLog.getCur_course_schedule_count())+tOrderCourseLog.getCharge_back_num());
    			orderCourse.setQuit_flag(pgNums==null||pgNums==0?0l:1l);
    			orderCourse.setSurplus_cost((tOrderCourseLog.getCur_course_surplus_count()+tOrderCourseLog.getCharge_back_num())*tOrderCourseLog.getDiscount_unit_price());
    			orderCourse.setId(tOrderCourseLog.getId());
    			tOrderCourseDao.updateOrderCourse(orderCourse);
    		}
    		if(tOrderCourseLog.getDiscount_unit_price().doubleValue()!=tOrderCourseLog.getDiscount_sum_price().doubleValue()){
    			//转班子单恢复
    			orderCourse=new TOrderCourse();
    	    	orderCourse.setUpdate_user(orderChange.getInput_user());
    			orderCourse.setDiscount_unit_price(tOrderCourseLog.getDiscount_unit_price());
    			orderCourse.setQuit_flag(pgNums==null||pgNums==0?0l:1l);
    			orderCourse.setRoot_course_id(tOrderCourseLog.getId());
    			tOrderCourseDao.updateOrderCourse(orderCourse);
    			//转班root单价恢复
    			param.put("discount_unit_price", tOrderCourseLog.getDiscount_unit_price());
    			param.put("v_return_count",pgNums);
    			param.put("change_id",orderChange.getId());
    			param.put("id",tOrderCourseLog.getRoot_course_id());
    			if(tOrderCourseLog.getRoot_course_id()!=null){
    				tOrderCourseDao.updateOrderCourseByOrderCourseLog(param);
        			//同主单的子单也要恢复
    				param.put("id", null);
        			param.put("root_course_id",tOrderCourseLog.getRoot_course_id());
        			tOrderCourseDao.updateOrderCourseByOrderCourseLog(param);
    			}
    		}
    	}
    	List<TOrderCourseTimesLog> tOrderCourseTimesLogList=tOrderCourseLogDao.queryTOrderCourseTimesLogByChangeId(param);
    	for(TOrderCourseTimesLog tOrderCourseTimesLog:tOrderCourseTimesLogList){
    		//还原订单课程课次信息
    		Map<String,Object> map=new HashMap<String,Object>();
    		map.put("is_valid", 1);
    		map.put("id", tOrderCourseTimesLog.getId());
    		tOrderCourseTimesDao.
					updateTOrderCourseTimes(map);
    	}
    	//更新批改状态为生效
    	param.put("change_stuatus", 5);
    	param.put("change_id", cancelTorderChange.getId());
    	tOrderChangeDao.updateOrderChangeStatus(param);
    }

}
