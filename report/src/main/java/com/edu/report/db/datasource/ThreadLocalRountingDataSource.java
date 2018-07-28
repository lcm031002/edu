package com.edu.report.db.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName: ThreadLocalRountingDataSource
 * @Description: 线程安全方式进行数据源路由
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
