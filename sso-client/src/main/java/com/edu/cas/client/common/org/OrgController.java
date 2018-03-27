/**  
 * @Title: OrgController.java
 * @Package com.edu.cas.client.common.org
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月13日 下午7:09:06
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.client.common.org;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.HttpReponse;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.cas.client.common.model.Account;
import com.edu.cas.client.common.model.HttpReponse;
import com.edu.cas.client.common.model.OrgModel;
import com.edu.cas.client.common.util.WebContextUtils;

/**
 * @ClassName: OrgController
 * @Description: 组织结构查询控制类
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月13日 下午7:09:06
 * 
 */
@Controller
public class OrgController {
	private static Logger log = Logger.getLogger(OrgController.class);

	@SuppressWarnings("restriction")
	@Resource(name = "orgService")
	private OrgService orgService;

	/**
	 * 查询用户可见的组织结构树
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/common/orgservice" }, method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
    HttpReponse queryOrgs(HttpServletRequest request,
                          HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query user orgs info");
		}
		HttpReponse httpReponse = new HttpReponse();
		Account account = WebContextUtils.genUser(request);
		if (null != account) {
			try {
				OrgModel orgModel = null;
				if (WebContextUtils.isAdmin(request)) {
					orgModel = orgService.queryAdminOrgTree();
				} else {
					orgModel = orgService.queryRightOrg(account.getId());
				}
				initialOrgModel(orgModel);

				initialSelectedOrgModel(request, orgModel);
				httpReponse.setData(orgModel);
				httpReponse.setError(false);

			} catch (Exception e) {
				e.printStackTrace();
				log.error("error found ,see:", e);
				httpReponse.setError(true);
				httpReponse.setMessage("error found,see:" + e.getMessage());
			}
		} else {
			log.error("user has not login ,and username not found.");
			httpReponse.setError(true);
			httpReponse.setMessage("user not found.");
		}
		if (log.isDebugEnabled()) {
			log.debug("end to query user orgs info");
		}
		return httpReponse;
	}

	/**
	 * 查询用户当前选中的权限
	 * 
	 * @param request
	 * @param response
	 * @return 被选中的组织节点
	 */
	@RequestMapping(value = { "/common/orgservice/selected" }, method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	HttpReponse querySelectedOrg(HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query user selected org info");
		}
		HttpReponse httpReponse = new HttpReponse();
		Account account = WebContextUtils.genUser(request);
		if (null != account) {
			try {

				OrgModel orgModel = WebContextUtils
						.genSelectedOriginalOrg(request);

				httpReponse.setData(orgModel);
				httpReponse.setError(false);

			} catch (Exception e) {
				e.printStackTrace();
				log.error("error found ,see:", e);
				httpReponse.setError(true);
				httpReponse.setMessage("error found,see:" + e.getMessage());
			}
		} else {
			log.error("user has not login ,and username not found.");
			httpReponse.setError(true);
			httpReponse.setMessage("user not found.");
		}
		if (log.isDebugEnabled()) {
			log.debug("end to query selected org info");
		}
		return httpReponse;
	}

	private void initialOrgModel(OrgModel orgModel) {
		if (orgModel == null) {
			return;
		}
		orgModel.getState().put("opened", "true");
		if (!CollectionUtils.isEmpty(orgModel.getChildren())) {
			orgModel.setType("menus");
			for (OrgModel subNode : orgModel.getChildren()) {
				initialOrgModel(subNode);
			}
		} else {
			orgModel.setType("leaf");
		}
	}

	private void initialSelectedOrgModel(HttpServletRequest request,
			OrgModel root) {
		OrgModel org = WebContextUtils.genSelectedOriginalOrg(request);
		if (null != org) {
			OrgModel selectedModel = genSelectedModel(org.getId(), root);
			if (null != selectedModel) {
				selectedModel.getState().put("selected", "true");
			}
		}
	}

	/**
	 * 
	 * @Title: 选中当前的组织结构
	 * @Description: 选中一个组织结构
	 * @param orgId
	 *            组织结构
	 * @param request
	 * @param response
	 * @return 设定文件
	 * @return HttpReponse 返回类型
	 * @throws
	 */
	@RequestMapping(value = { "/common/orgservice" }, method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody
	HttpReponse selectOrg(@RequestParam Long id, HttpServletRequest request,
			HttpServletResponse response) {
		HttpReponse httpReponse = new HttpReponse();
		if (log.isDebugEnabled()) {
			log.debug("begin to select org.");
		}

		Account account = WebContextUtils.genUser(request);
		if (null != account) {
			try {
				// 验证当前用户组织结构确实有权限
				OrgModel orgModel = orgService.queryRightOrg(account.getId());
				initialOrgModel(orgModel);
				
				if(WebContextUtils.isAdmin(request)){
					orgModel = orgService.queryAdminOrgTree();
				}
				OrgModel selectedModel = genSelectedModel(id, orgModel);
				if (selectedModel != null) {
				    OrgModel oldOrgModel = WebContextUtils.genSelectedOriginalOrg(request);
				    if (oldOrgModel != null && (
				    	oldOrgModel.getBuId() == null && selectedModel.getBuId() != null ||
				    	oldOrgModel.getBuId() != null && selectedModel.getBuId() == null ||
				    	oldOrgModel.getBuId() != null && selectedModel.getBuId() != null && 
				    	oldOrgModel.getBuId().longValue() != selectedModel.getBuId().longValue() )) {
				        httpReponse.setChangeTeam(true);
				    }
				    
					// 更新到数据库
					orgService.selectedOrg(selectedModel, account.getId());

					httpReponse.setError(false);
				} else {
					httpReponse.setError(true);
					httpReponse.setMessage("error org model");
				}

			} catch (Exception e) {
				e.printStackTrace();
				log.error("error found ,see:", e);
				httpReponse.setError(true);
				httpReponse.setMessage("error found,see:" + e.getMessage());
			}
		} else {
			log.error("user has not login ,and username not found.");
			httpReponse.setError(true);
			httpReponse.setMessage("user not found.");
		}
		if (log.isDebugEnabled()) {
			log.debug("end to select org.");
		}
		return httpReponse;
	}

	private OrgModel genSelectedModel(Long orgModelId, OrgModel orgModel) {
		if (orgModel == null) {
			return null;
		}
		if (orgModel.getId().longValue() == orgModelId.longValue()) {
			return orgModel;
		} else {
			if (!CollectionUtils.isEmpty(orgModel.getChildren())) {
				for (OrgModel subModel : orgModel.getChildren()) {
					OrgModel selectedModel = genSelectedModel(orgModelId,
							subModel);
					if (selectedModel != null) {
						return selectedModel;
					}
				}

			}
			return null;

		}
	}
	
	/**
	 * 查询用户可见的团队
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/common/orgservice/bu" }, method = RequestMethod.GET, headers = "Accept=application/json")
	public HttpReponse queryOrgsBu(HttpServletRequest request,HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query user orgs info");
		}
		HttpReponse httpReponse = new HttpReponse();
		Account account = WebContextUtils.genUser(request);
		if (null != account) {
			try {
				List<OrgModel> orgModelList= null;
				boolean isAdmin = WebContextUtils.isAdmin(request);
				if (isAdmin) {
					orgModelList = orgService.queryAdminOrgList();
				} else {
					orgModelList = orgService.queryOrgBranchList(account.getId());
				}
				List<OrgModel> orgModelsList=new ArrayList<OrgModel>();
				for (OrgModel orgModel : orgModelList) {
					if(isAdmin) {
						if("3".equals(orgModel.getType())){
							orgModelsList.add(orgModel);
						}
					} else {
						orgModelsList.add(orgModel);
					}
				}
				httpReponse.setData(orgModelsList);
				httpReponse.setError(false);

			} catch (Exception e) {
				e.printStackTrace();
				log.error("error found ,see:", e);
				httpReponse.setError(true);
				httpReponse.setMessage("error found,see:" + e.getMessage());
			}
		} else {
			log.error("user has not login ,and username not found.");
			httpReponse.setError(true);
			httpReponse.setMessage("user not found.");
		}
		if (log.isDebugEnabled()) {
			log.debug("end to query user orgs info");
		}
		return httpReponse;
	}
	
	
	/**
	 * 查询用户可见的校区列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/common/orgservice/branchs" }, method = RequestMethod.GET, headers = "Accept=application/json")
	public HttpReponse queryOrgsBranchs(HttpServletRequest request,HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query user orgs info");
		}
		HttpReponse httpReponse = new HttpReponse();
		Account account = WebContextUtils.genUser(request);
		if (null != account) {
			try {
				List<OrgModel> orgModelList= null;
				boolean isAdmin = WebContextUtils.isAdmin(request);
				if (isAdmin) {
					orgModelList = orgService.queryAdminOrgList();
				} else {
					orgModelList = orgService.queryOrgBranchList(account.getId());
				}
				List<OrgModel> orgModelsList=new ArrayList<OrgModel>();
				for (OrgModel orgModel : orgModelList) {
					if(isAdmin) {
						if("4".equals(orgModel.getType())){
							orgModelsList.add(orgModel);
						}
					} else if(!CollectionUtils.isEmpty(orgModel.getChildren())){
						orgModelsList.addAll(orgModel.getChildren());
					}
				}
				httpReponse.setData(orgModelsList);
				httpReponse.setError(false);

			} catch (Exception e) {
				e.printStackTrace();
				log.error("error found ,see:", e);
				httpReponse.setError(true);
				httpReponse.setMessage("error found,see:" + e.getMessage());
			}
		} else {
			log.error("user has not login ,and username not found.");
			httpReponse.setError(true);
			httpReponse.setMessage("user not found.");
		}
		if (log.isDebugEnabled()) {
			log.debug("end to query user orgs info");
		}
		return httpReponse;
	}
}
