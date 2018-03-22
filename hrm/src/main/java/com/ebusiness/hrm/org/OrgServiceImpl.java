package com.ebusiness.hrm.org;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.ebusiness.hrm.dao.AccountOrgRelDao;
import com.ebusiness.hrm.dao.OrganizationInfoDao;
import com.ebusiness.hrm.dao.TreeDao;
import com.ebusiness.hrm.exception.DataBaseException;
import com.ebusiness.hrm.jstree.State;
import com.ebusiness.hrm.jstree.TreeModel;
import com.ebusiness.hrm.model.OrganizationInfo;
import com.ebusiness.hrm.model.StatusEnum;

@Service(value="organizationService")
public class OrgServiceImpl implements OrganizationService {
	
	@Resource(name="organizationDao")
	private OrganizationInfoDao organizationDao;

	@Resource(name="accountOrgRelDao")
	private AccountOrgRelDao accountOrgRelDao;
	
	@Resource(name="treeDao")
	private TreeDao treeDao;
	
	@Override
	public List<OrganizationInfo> queryOrg() throws Exception{
		// TODO Auto-generated method stub
		return organizationDao.queryOrg();
	}

	@Override
	public List<Map> querySubOrganizations(Integer parentId) throws Exception {
		// TODO Auto-generated method stub
		return organizationDao.querySubOrganizations(parentId);
	}
	
	
	/**
	 * 新增
	 * 
	 * @param orgInfo
	 * @throws Exception
	 */
	@Override
	public void addOrganizationInfo(OrganizationInfo orgInfo) throws Exception {
		orgInfo.setStatus(StatusEnum.VALID.getCode());
		Integer ret = organizationDao.addOrganizationInfo(orgInfo);
		if (ret < 1) {
			throw new Exception("新增失败！");
		}
	}
	
	/**
	 * 更新
	 * 
	 * @param orgInfo
	 *            动态参数
	 * @throws Exception
	 */
	@Override
	public void updateOrganizationInfo(OrganizationInfo orgInfo)
			throws Exception {
		Integer ret = organizationDao.updateOrganizationInfo(orgInfo);
		if (ret < 1) {
			throw new Exception("修改失败！");
		}
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	@Override
	public boolean removeOrganizationInfo(Integer id) throws Exception {
		return organizationDao.removeOrganizationInfo(id)==1;
	}
	
	//查询组织团队机构
	@Override
	public List<OrganizationInfo> queryAreas() throws Exception{
		return organizationDao.queryAreas();
	}
	
	//查询组织校区机构
	@Override
	public List<Map<String, Object>> querySch(Long buId) throws Exception{
		return organizationDao.querySch(buId);
	}
	
	//查询账户校区列表树
	@Override
	public List<TreeModel> queryOrgTreeModel(Long accountId) throws DataBaseException{
		List<TreeModel> treeList=new ArrayList<TreeModel>();
		try {
			treeList=treeDao.queryCityNodes();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataBaseException("查询校区列表失败:"+e.getMessage());
		}
		
		List<Integer> accountSchsList=accountOrgRelDao.queryAccountOrg(accountId);
		initSelectedMenu(treeList, accountSchsList);
		
		return treeList;
	}
	
	/**
	 * 设置选中的menu
	 * @param models 
	 * @param schList
	 */
	private void initSelectedMenu(List<TreeModel> treeList,List<Integer>schList) throws DataBaseException{
		try {
			for(TreeModel tree:treeList){
				List<TreeModel> childrens=tree.getChildren();
				if(null!=childrens&&childrens.size()>0){
					initSelectedMenu(childrens,schList);
				}
				else{
					if(null!=tree.getId()&&schList.indexOf(tree.getId())!=-1){
						State state=new State();
						state.setOpen(true);
						state.setSelected(true);
						tree.setState(state);
					}
				}
			}
			
		} catch (Exception e) {
			throw new DataBaseException(e.getMessage());
		}
	}

}
