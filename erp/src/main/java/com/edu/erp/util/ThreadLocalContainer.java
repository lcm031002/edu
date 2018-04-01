package com.edu.erp.util;

import com.edu.erp.model.SyncBusinessLog;

/**
 * Created by zenglw on 2018/1/24.
 */
public class ThreadLocalContainer {
    public static final ThreadLocal<SyncBusinessLog> SYNC_BUSINESS_LOG_THREAD_LOCAL = new ThreadLocal<>();
}
