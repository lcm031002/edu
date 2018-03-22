package com.ebusiness.hrm.org;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebusiness.cas.client.common.model.HttpReponse;
import com.ebusiness.hrm.jstree.TreeModel;
import com.ebusiness.hrm.model.OrganizationInfo;

/**
 * 组织机构
 * 
 * @author WP
 *
 */
@Controller
public class OrganizationController {
	private static Logger log = Logger.getLogger(OrganizationController.class);
	
	@Resource(name = "organizationService")
	private OrganizationService organizationService;

	public HttpReponse init(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	/**
	* 
	* @Title: queryOrg
	* @Description: 查询组织机构
	* @param     设定文件
	* @return   httpReponse  返回类型
	* @throws
	*/
	@RequestMapping(value = { "/hrmSystemSettings/hrmOrgmgr/orgservice" },method = RequestMethod.GET)
	public @ResponseBody HttpReponse queryOrg() {
		if (log.isDebugEnabled()) {
			log.debug("begin to query Org info");
		}
		HttpReponse httpReponse=new HttpReponse();
		try {
			List<OrganizationInfo> data = organizationService.queryOrg();
			
			httpReponse.setData(data);
			httpReponse.setError(false);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("end to query Org info");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: querySubOrg
	* @Description: 根据父节点ID查询子节点
	* @param     设定文件
	* @return httpReponse    返回类型
	* @throws
	*/
	@RequestMapping(value = { "/hrmSystemSettings/hrmOrgmgr/suborgservice" }, method = RequestMethod.GET)
	public @ResponseBody HttpReponse querySubOrg(Integer parentId) {
		if (log.isDebugEnabled()) {
			log.debug("begin to query SubOrg info");
		}
		HttpReponse httpReponse=new HttpReponse();
		try {
			List<Map> data = organizationService.querySubOrganizations(parentId);
			httpReponse.setData(data);
			httpReponse.setError(false);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("end to query SubOrg info");
		}
		return httpReponse;
	}
	
	/**
	 * 新增
	 * 
	 * @param orgInfo 新增表单
	 * @param org_type	新增类型  1:一级组织
	 * @return httpReponse 返回类型
	 */
	@RequestMapping(value = {"/hrmSystemSettings/hrmOrgmgr/orgservice"},method=RequestMethod.POST)
	public @ResponseBody HttpReponse addOrganizationInfo(@RequestBody OrganizationInfo orgInfo){
		if (log.isDebugEnabled()) {
			log.debug("begin to add Org info");
		}
		HttpReponse httpReponse=new HttpReponse();
		try { 
			/*if(StringUtils.isEmpty(org_type)){
				throw new Exception();
			}
			org_type=1时.为一级父类
			if("1".equals(org_type)){
				orgInfo.setParent_id(0l);
			}*/
			organizationService.addOrganizationInfo(orgInfo);
			httpReponse.setError(false);
			
			
		} catch (Exception e) {
			System.err.println(e);
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
			
			
		}
		if (log.isDebugEnabled()) {
			log.debug("end to add Org info");
		}
		return httpReponse;
	}
	
	/**
	 * 更新
	 * 
	 * @param orgInfo
	 * @return httpReponse 返回类型
	 */
	@RequestMapping(value = {"/hrmSystemSettings/hrmOrgmgr/orgservice"},method = RequestMethod.PUT)
	public @ResponseBody HttpReponse updateOrganizationInfo(@RequestBody OrganizationInfo orgInfo){
		if (log.isDebugEnabled()) {
			log.debug("begin to update Org info");
		}
		HttpReponse httpReponse=new HttpReponse();
		try {
			organizationService.updateOrganizationInfo(orgInfo);
			httpReponse.setData("update");
			httpReponse.setError(false);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("end to update Org info");
		}
		return httpReponse;
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return httpReponse 返回类型
	 */
	@RequestMapping(value = {"/hrmSystemSettings/hrmOrgmgr/orgservice"},method = RequestMethod.DELETE)
	public @ResponseBody HttpReponse removeOrganizationInfo(HttpServletRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("begin to delete Org info");
		}
		HttpReponse httpReponse=new HttpReponse();
		try {
		    Integer id = Integer.parseInt(request.getParameter("id"));
			boolean suc=organizationService.removeOrganizationInfo(id);			
			httpReponse.setError(suc?false:true);
			httpReponse.setData("remove");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("end to delete Org info");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: queryAreas
	* @Description: 查询组织团队机构
	* @param     设定文件
	* @return   httpReponse  返回类型
	* @throws
	*/
	@RequestMapping(value={"/hrmSystemSettings/hrmOrgmgr/orgservice/areas"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryAreas(){
		if(log.isDebugEnabled()){
			log.debug("begin to query areas");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<OrganizationInfo> data = organizationService.queryAreas();
			httpReponse.setData(data);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query areas");
		}
		return httpReponse;
	}
	
	/**
	* 
	* @Title: querySch
	* @Description: 根据团队id查询组织校区机构
	* @param     设定文件
	* @return   httpReponse  返回类型
	* @throws
	*/
	@RequestMapping(value={"/hrmSystemSettings/hrmOrgmgr/orgservice/schools"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse querySch(HttpServletRequest request, Long buId){
		if(log.isDebugEnabled()){
			log.debug("begin to query schools");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<Map<String, Object>> result = organizationService.querySch(buId);
			httpReponse.setData(result);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query schools");
		}
		return httpReponse;
	}
	
	//查询账户校区列表树
	@RequestMapping(value={"/hrmSystemSettings/hrmAccountOrg/queryOrgWithAccount"},method=RequestMethod.GET)
	public @ResponseBody HttpReponse queryOrgTreeModel(HttpServletRequest request,Long accountId){
		if(log.isDebugEnabled()){
			log.debug("begin to query orgTreeModel");
		}
		HttpReponse httpReponse = new HttpReponse();
		try{
			List<TreeModel> treeMap = organizationService.queryOrgTreeModel(accountId);
			httpReponse.setData(treeMap);
			httpReponse.setError(false);
		}catch(Exception e){
			e.printStackTrace();
			log.error("error found,see:",e);
			httpReponse.setError(true);
			httpReponse.setMessage(e.getMessage());
		}
		if(log.isDebugEnabled()){
			log.debug("end to query orgTreeModel");
		}
		return httpReponse;
	}
}