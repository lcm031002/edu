package com.ebusiness.hrm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ebusiness.hrm.jstree.TreeModel;

@Repository(value = "treeDao")
public interface TreeDao {

	List<TreeModel> queryCityNodes();
}
