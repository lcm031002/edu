/**  
 * @Title: ThreadLocalRountingDataSource.java
 * @Package com.edu.report.db.datasource
 * @author zhuliyong zly@entstudy.com  
 * @date 2017年4月25日 下午6:16:51
 * @version KLXX ERPV4.0  
 */
package com.edu.report.db.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName: ThreadLocalRountingDataSource
 * @Description: 线程安全方式进行数据源路由
 * @author zhuliyong zly@entstudy.com
 * @date 2017年4月25日 下午6:16:51
 * 
 */
public class ThreadLocalRountingDataSource extends AbstractRoutingDataSource {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#
	 * determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceTypeManager.get();
	}

}
