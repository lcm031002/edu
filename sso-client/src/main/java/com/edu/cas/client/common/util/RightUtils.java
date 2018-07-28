package com.edu.cas.client.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.edu.cas.client.common.model.Permission;
import com.edu.cas.client.common.model.RightModel;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.edu.cas.client.common.model.Permission;
import com.edu.cas.client.common.model.RightModel;

/**
 * @ClassName: RightUtils
 * @Description: 权限工具类
 *
 */
public class RightUtils {

	private static final RightUtils INSTANCE = new RightUtils();
	// 权限控制路径
	private Set<String> pathSet = new HashSet<String>();
	// 非权限控制路径
	private Set<String> pathIgnoreSet = new HashSet<String>();
	private String projectName = "";

	public static RightUtils getInstance() {
		return INSTANCE;
	}

	private RightModel root;

	public void loadRightModel(JSONObject jsonObject) {
		Assert.notNull(jsonObject);
		root = new RightModel();
		String name = jsonObject.getString("name");
		String key = jsonObject.getString("key");
		RightUtils.getInstance().projectName = key;
		JSONArray array = jsonObject.getJSONArray("rightDir");
		loadRightPaths(array);

		array = jsonObject.getJSONArray("rightIgnoreDir");
		loadRightIgnorePaths(array);

		root.setName(name);
		root.setKey(key);
		JSONArray jsonArray = jsonObject.getJSONArray("menus");
		if (null != jsonArray && jsonArray.isArray() && !jsonArray.isEmpty()) {
			@SuppressWarnings("unchecked")
			Iterator<Object> iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
				Object obj = iterator.next();

				if (obj instanceof JSONObject) {
					JSONObject json_sub = (JSONObject) obj;
					RightModel rightModelSub = new RightModel();
					load(rightModelSub, json_sub);
					root.getMenus().add(rightModelSub);
				} else {
					// TODO
				}
			}
		}
	}

	private void loadRightPaths(JSONArray array) {
		if (null != array) {
			if (array.isArray() && !array.isEmpty()) {
				@SuppressWarnings("unchecked")
				Iterator<Object> iterator = array.iterator();
				while (iterator.hasNext()) {
					Object obj = iterator.next();
					if (obj instanceof String) {
						RightUtils.getInstance().pathSet.add((String) obj);
						RightUtils.getInstance().pathSet.add("/"
								+ RightUtils.getInstance().projectName
								+ (String) obj);
					}
				}
			}
		}
	}

	private void loadRightIgnorePaths(JSONArray array) {
		if (null != array) {
			if (array.isArray() && !array.isEmpty()) {
				@SuppressWarnings("unchecked")
				Iterator<Object> iterator = array.iterator();
				while (iterator.hasNext()) {
					Object obj = iterator.next();
					if (obj instanceof String) {
						RightUtils.getInstance().pathIgnoreSet
								.add((String) obj);
						RightUtils.getInstance().pathIgnoreSet.add("/"
								+ RightUtils.getInstance().projectName
								+ (String) obj);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @Title: isRightPath
	 * @Description: 判断给定的路径是否是权限控制路径
	 * @param path
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean isRightPath(String path) {
		Iterator<String> iterator = RightUtils.getInstance().pathSet.iterator();
		while (iterator.hasNext()) {
			String rightPath = iterator.next();
			if (path.startsWith(rightPath)) {
				return true;
			}
		}
		return false;
	}

	public boolean isRightIgnorePath(String path) {
		Iterator<String> iterator = RightUtils.getInstance().pathIgnoreSet
				.iterator();
		while (iterator.hasNext()) {
			String rightPath = iterator.next();
			if (path.startsWith(rightPath)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @Title: genUserRightModel
	 * @Description: 获取指定范围内权限树
	 * @param permissionList
	 * @return 设定文件
	 * @return RightModel 返回类型
	 */
	public RightModel genUserRightModel(List<Permission> permissionList) {
		RightModel rootRight = new RightModel();
		rootRight.setName(root.getName());
		rootRight.setKey(root.getKey());
		rootRight.setVersion(root.getVersion());

		if (!CollectionUtils.isEmpty(permissionList)
				&& !CollectionUtils.isEmpty(root.getMenus())) {

			Map<String, Permission> permissionMap = new HashMap<String, Permission>();
			for (Permission permission : permissionList) {
				permissionMap.put(permission.getPermissionId(), permission);
			}
			List<RightModel> subNode = genSubUserRightModel(permissionMap, root);
			rootRight.setMenus(subNode);
		}

		return rootRight;
	}

	RightModel genAdminRightModel() {
		RightModel rootRight = new RightModel();
		rootRight.setName(root.getName());
		rootRight.setKey(root.getKey());
		rootRight.setVersion(root.getVersion());

		if (!CollectionUtils.isEmpty(root.getMenus())) {
			List<RightModel> subNode = genSubAdminRightModel(root);
			rootRight.setMenus(subNode);
		}

		return rootRight;
	}

	private List<RightModel> genSubAdminRightModel(RightModel rootRight) {
		List<RightModel> children = new ArrayList<RightModel>();
		if (!CollectionUtils.isEmpty(rootRight.getMenus())) {
			for (RightModel subNode : rootRight.getMenus()) {
				if (!CollectionUtils.isEmpty(subNode.getMenus())) {
					List<RightModel> subChildren = genSubAdminRightModel(subNode);
					RightModel thisSubRightModel = (RightModel) subNode.clone();
					thisSubRightModel.setMenus(subChildren);
					children.add(thisSubRightModel);
				} else {
					children.add((RightModel) subNode.clone());
				}
			}
		}
		return children;
	}

	private List<RightModel> genSubUserRightModel(
			Map<String, Permission> permissionMap, RightModel rootRight) {
		List<RightModel> children = new ArrayList<RightModel>();
		if (!CollectionUtils.isEmpty(rootRight.getMenus())) {
			for (RightModel subNode : rootRight.getMenus()) {
				if (!CollectionUtils.isEmpty(subNode.getMenus())) {
					List<RightModel> subChildren = genSubUserRightModel(
							permissionMap, subNode);
					if (!CollectionUtils.isEmpty(subChildren)) {
						RightModel thisSubRightModel = (RightModel) subNode
								.clone();
						thisSubRightModel.setMenus(subChildren);
						children.add(thisSubRightModel);
					}
				} else {
					if (permissionMap.get(subNode.getIndex()) != null) {
						children.add((RightModel) subNode.clone());
					}
				}
			}
		}
		return children;
	}

	private void load(RightModel rightModel, JSONObject jsonObject) {
		Assert.notNull(rightModel);
		Assert.notNull(jsonObject);
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = jsonObject.keys();
		while (iterator.hasNext()) {
			String key = iterator.next();
			loadKey(rightModel, jsonObject, key);
		}
	}

	private void loadKey(RightModel rightModel, JSONObject jsonObject,
			String key) {
		if ("index".equals(key))
			rightModel.setIndex(jsonObject.getString(key));
		if ("name".equals(key))
			rightModel.setName(jsonObject.getString(key));
		if ("key".equals(key))
			rightModel.setKey(jsonObject.getString(key));
		if ("icon".equals(key))
			rightModel.setIcon(jsonObject.getString(key));
		if ("type".equals(key))
			rightModel.setType(jsonObject.getString(key));
		if ("active".equals(key))
			rightModel.setActive(jsonObject.getString(key));
		if ("value".equals(key))
			rightModel.setValue(jsonObject.getString(key));
		if ("href".equals(key))
			rightModel.setHref(jsonObject.getString(key));
		if ("path".equals(key))
			rightModel.setPath(jsonObject.getString(key));
		if ("showMenu".equals(key))
			rightModel.setShowMenu(jsonObject.getString(key));
		if ("menus".equals(key)) {
			JSONArray jsonArray = jsonObject.getJSONArray("menus");

			if (null != jsonArray && jsonArray.isArray()
					&& !jsonArray.isEmpty()) {
				@SuppressWarnings("unchecked")
				Iterator<Object> iterator = jsonArray.iterator();
				while (iterator.hasNext()) {
					Object obj = iterator.next();

					if (obj instanceof JSONObject) {
						JSONObject json_sub = (JSONObject) obj;
						RightModel rightModelSub = new RightModel();
						load(rightModelSub, json_sub);
						rightModel.getMenus().add(rightModelSub);
					} else {
						// TODO
					}
				}
			}
		}

		if ("services".equals(key)) {
			JSONArray jsonArray = jsonObject.getJSONArray("services");

			if (null != jsonArray && jsonArray.isArray()
					&& !jsonArray.isEmpty()) {
				@SuppressWarnings("unchecked")
				Iterator<Object> iterator = jsonArray.iterator();
				while (iterator.hasNext()) {
					Object obj = iterator.next();

					if (obj instanceof String) {
						String json_sub = (String) obj;
						rightModel.getServicesSet().add(
								"/" + RightUtils.getInstance().projectName
										+ json_sub);
						rightModel.getServicesSet().add(json_sub);
					}
				}
			}
		}
	}

	public final RightModel getRoot() {
		return root;
	}

	public final void setRoot(RightModel root) {
		this.root = root;
	}

}
