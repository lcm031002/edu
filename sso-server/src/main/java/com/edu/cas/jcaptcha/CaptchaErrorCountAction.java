/**  
 * @Title: CaptchaErrorCountAction.java
 * @Package com.edu.cas.jcaptcha
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月8日 下午6:44:26
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.jcaptcha;


import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import javax.sql.DataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * @ClassName: CaptchaErrorCountAction
 * @Description: 验证码错误计数限制
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月8日 下午6:44:26
 *
 */
public final class CaptchaErrorCountAction extends AbstractAction {
	protected static final Log log = LogFactory.getLog(CaptchaErrorCountAction.class);

	private SimpleJdbcTemplate jdbcTemplate;
	private DataSource dataSource;
	private String sql;

	protected Event doExecute(final RequestContext context) {
		int count = 1;
		try {
			getJdbcTemplate().update(this.sql, new Object[]{new Date(), "登录失败"});
		} catch (Exception e) {
			log.error(e);
		}

		return success();
	}

	public final void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.dataSource = dataSource;
	}

	protected final SimpleJdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	protected final DataSource getDataSource() {
		return this.dataSource;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
