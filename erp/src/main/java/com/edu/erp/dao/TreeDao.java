package com.edu.erp.dao;

import java.util.List;

import com.edu.erp.jstree.TreeModel;
import org.springframework.stereotype.Repository;

@Repository(value = "treeDao")
public interface TreeDao {
	List<TreeModel> queryCityNodes();
}
