package com.edu.erp.dao;

/**
 * Created by zenglw on 2018/2/6.
 */

import com.edu.erp.model.SyncBusinessLog;
import org.springframework.stereotype.Repository;

@Repository(value = "syncBusinessLogDao")
public interface SyncBusinessLogDao {
    void insert(SyncBusinessLog syncBusinessLog);
}
