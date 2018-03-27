/**  
 * @Title: WebContextUtils.java
 * @Package com.edu.cas.client.common.util
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月12日 下午4:12:54
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.client.common.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.model.Permission;
import com.edu.cas.client.common.model.RightModel;
import com.edu.cas.client.common.right.RightService;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.model.Permission;
import com.edu.cas.client.common.model.RightModel;
import com.edu.cas.client.common.org.OrgService;
import com.edu.cas.client.common.right.RightService;

/**
 * @ClassName: WebContextUtils
 * @Description: Web上下文工具类
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月12日 下午4:12:54
 * 
 */
public class WebContextUtils {
	private static final Logger log = Logger.getLogger(WebContextUtils.class);
	/** 用户对象标示 */
	public static final String USER = "USER_OBJ";

	/** 用户选择的校区标示 */
	public static final String SELECTED_ORG = "selected_org";
	
	private static final String ORG_TYPE_BU = "3";
	private static final String ORG_TYPE_BRANCH = "4";

	/**
	 * 
	 * @Title: genUser
	 * @Description: 返回用户信息对象
	 * @param request
	 *            设定文件
	 * @return Account 返回类型
	 * @throws
	 */
	public static Account genUser(HttpServletRequest request) {
		Assert.notNull(request);
		Object obj = request.getSession().getAttribute(USER);
		if (obj instanceof Account) {
			return (Account) obj;
		} else {
			return null;
		}
	}

	public static Account genUser(HttpSession session) {
		Assert.notNull(session);
		Object obj = session.getAttribute(USER);
		if (obj instanceof Account) {
			return (Account) obj;
		} else {
			return null;
		}
	}

	/**
	 * 获取当前组织结构的叶子节点
	 * 
	 * @param request
	 *            请求上下文
	 * @return 叶子节点
	 */
	public static List<OrgModel> genSelectedOrg(HttpServletRequest request) {
		Assert.notNull(request);
		List<OrgModel> selectedOrgs = new ArrayList<OrgModel>();
		OrgModel selectdeOrgModel = genSelectedOriginalOrg(request);
		genAllLeaf(selectdeOrgModel, selectedOrgs);
		return selectedOrgs;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static List<OrgModel> queryRightBu(HttpServletRequest request) {
		Assert.notNull(request);
		Account account = WebContextUtils.genUser(request);
		OrgService orgService = (OrgService) ApplicationContextUtil
				.getBean("orgService");
		List<OrgModel> orgModelsList=new ArrayList<OrgModel>();
		if (null != account) {
			try {
				List<OrgModel> orgModelList= null;
				boolean isAdmin = WebContextUtils.isAdmin(request);
				if (isAdmin) {
					orgModelList = orgService.queryAdminOrgList();
				} else {
					orgModelList = orgService.queryOrgBranchList(account.getId());
				}
				for (OrgModel orgModel : orgModelList) {
					if(isAdmin) {
						if(ORG_TYPE_BU.equals(orgModel.getType())){
							orgModelsList.add(orgModel);
						}
					} else {
						orgModelsList.add(orgModel);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error("error found ,see:", e);
			}
		} 
		return orgModelsList;
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static List<OrgModel> queryRightBranchs(HttpServletRequest request,Long buId) {
		Assert.notNull(request);
		Account account = WebContextUtils.genUser(request);
		OrgService orgService = (OrgService) ApplicationContextUtil
				.getBean("orgService");
		List<OrgModel> orgModelsList=new ArrayList<OrgModel>();
		if (null != account) {
			try {
				List<OrgModel> orgModelList= null;
				boolean isAdmin = WebContextUtils.isAdmin(request);
				if (isAdmin) {
					orgModelList = orgService.queryAdminOrgList();
				} else {
					orgModelList = orgService.queryOrgBranchList(account.getId());
				}
				for (OrgModel orgModel : orgModelList) {
					if(buId != null && !orgModel.getBuId().equals(buId)) {
						continue;
					}
					if(isAdmin) {
						if(ORG_TYPE_BRANCH.equals(orgModel.getType())){
							orgModelsList.add(orgModel);
						}
					} else if(!CollectionUtils.isEmpty(orgModel.getChildren())){
						orgModelsList.addAll(orgModel.getChildren());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("error found ,see:", e);
			}
		} 
		return orgModelsList;
	}
	
	

	/**
	 * 
	 * @Title: genRightBuOrgs
	 * @Description: 查询用户可见的团队
	 * @param request
	 * @return 设定文件
	 * @return List<OrgModel> 返回类型
	 */
	public static List<OrgModel> genRightBuOrgs(HttpServletRequest request) {
		Assert.notNull(request);
		List<OrgModel> rightOrgs = new ArrayList<OrgModel>();
		OrgService orgService = (OrgService) ApplicationContextUtil
				.getBean("orgService");
		Account account = genUser(request);
		OrgModel orgModel = null;
		try {
			if (isAdmin(request)) {
				orgModel = orgService.queryAdminOrgTree();
			} else {
				orgModel = orgService.queryRightOrg(account.getId());
			}

			if (orgModel != null
					&& !CollectionUtils.isEmpty(orgModel.getChildren())) {
				for (OrgModel cityOrg : orgModel.getChildren()) {
					if (null != cityOrg
							&& !CollectionUtils.isEmpty(cityOrg.getChildren())) {
						rightOrgs.addAll(cityOrg.getChildren());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:", e);
		}

		return rightOrgs;
	}

	public static List<OrgModel> genRightBranchOrgs(HttpServletRequest request,
			Long buId) {
		Assert.notNull(request);
		List<OrgModel> rightOrgs = new ArrayList<OrgModel>();
		OrgService orgService = (OrgService) ApplicationContextUtil
				.getBean("orgService");
		Account account = genUser(request);
		OrgModel orgModel = null;
		try {
			if (isAdmin(request)) {
				orgModel = orgService.queryAdminOrgTree();
			} else {
				orgModel = orgService.queryRightOrg(account.getId());
			}

			if (orgModel != null
					&& !CollectionUtils.isEmpty(orgModel.getChildren())) {
				for (OrgModel cityOrg : orgModel.getChildren()) {
					if (null != cityOrg && null != buId
							&& !CollectionUtils.isEmpty(cityOrg.getChildren())) {
						boolean founded = false;
						for (OrgModel buOrg : cityOrg.getChildren()) {
							if (null != buOrg.getId()
									&& buOrg.getId().intValue() == buId
											.intValue()) {
								rightOrgs.addAll(buOrg.getChildren());
								founded = true;
								break;
							}
						}
						if (founded) {
							break;
						}
					} else if (null != cityOrg && null == buId
							&& !CollectionUtils.isEmpty(cityOrg.getChildren())) {
						for (OrgModel buOrg : cityOrg.getChildren()) {
							rightOrgs.addAll(buOrg.getChildren());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:", e);
		}

		return rightOrgs;
	}

	/**
	 * 从请求的上下文获取当前选中的原始组织结构
	 * 
	 * @param request
	 *            请求上下文
	 * @return 被选中的组织结构节点
	 */
	public static OrgModel genSelectedOriginalOrg(HttpServletRequest request) {
		Assert.notNull(request);
		OrgModel selectedOrg = null;
		OrgService orgService = (OrgService) ApplicationContextUtil
				.getBean("orgService");
		Account account = genUser(request);
		Assert.notNull(account);
		try {
			selectedOrg = orgService.querySelectedOrg(account.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error founded,see:", e);
		}
		return selectedOrg;
	}

	/**
	 * 
	 * @Title: genUserRights
	 * @Description: 获取当前用户的权限模型
	 * @param request
	 *            当前会话上下文
	 * @return 设定文件
	 * @return RightModel 返回类型
	 * 
	 */
	public static RightModel genUserRights(HttpServletRequest request) {
		RightModel rightModel = null;

		try {
			if (isAdmin(request)) {
				rightModel = RightUtils.getInstance().genAdminRightModel();
			} else {
				Account account = genUser(request);
				RightService rightService = (RightService) ApplicationContextUtil
						.getBean("rightService");
				List<Permission> permissionList = rightService
						.queryUserRightModel(account.getId());
				rightModel = RightUtils.getInstance().genUserRightModel(
						permissionList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rightModel;

	}

	/**
	 * 
	 * @Title: isAdmin
	 * @Description: 判定是否是管理员
	 * @param request
	 *            当前请求的上下文
	 * @return 设定文件
	 * @return boolean 返回类型
	 * 
	 */
	public static boolean isAdmin(HttpServletRequest request) {
		Assert.notNull(request);
		Account account = genUser(request);
		Assert.notNull(account);
		Assert.notNull(account.getId());
		return account.getId().longValue() == 0;
	}

	private static void genAllLeaf(OrgModel selectdeOrgModel,
			List<OrgModel> allLeaf) {
		if (CollectionUtils.isEmpty(selectdeOrgModel.getChildren())) {
			allLeaf.add(selectdeOrgModel);
		} else {
			for (OrgModel subModel : selectdeOrgModel.getChildren()) {
				genAllLeaf(subModel, allLeaf);
			}
		}
	}
}
