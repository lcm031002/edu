package com.edu.report.db.datasource;

import com.edu.report.db.enums.DataSourceType;

/**
 * @ClassName: DataSourceTypeManager
 * @Description: 数据源管理
 *
 */
public class DataSourceTypeManager {
	private static final ThreadLocal<DataSourceType> dataSourceTypes = new ThreadLocal<DataSourceType>() {
		@Override
		protected DataSourceType initialValue() {
			return DataSourceType.ORACLE;
		}
	};

	public static DataSourceType get() {
		return dataSourceTypes.get();
	}

	public static void set(DataSourceType dataSourceType) {
		dataSourceTypes.set(dataSourceType);
	}

	public static void reset() {
		dataSourceTypes.set(DataSourceType.ORACLE);
	}
}
