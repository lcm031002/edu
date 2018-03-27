/**  
 * @Title: CasCredentials.java
 * @Package com.edu.cas.form
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月8日 下午8:44:16
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.form;

import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

/**
 * @ClassName: CasCredentials
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月8日 下午8:44:16
 * 
 */
public class CasCredentials extends UsernamePasswordCredentials implements
		Credentials {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -7705210594599051668L;
	private String captcha;

	public final String getCaptcha() {
		return captcha;
	}

	public final void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String toString() {
		return "[username: " + getUsername() + "]";
	}

	public boolean equals(final Object obj) {
		if (obj == null || !obj.getClass().equals(this.getClass())) {
			return false;
		}

		final CasCredentials c = (CasCredentials) obj;

		return super.equals(obj) && this.captcha.equals(c.getCaptcha());
	}

	public int hashCode() {
		return super.hashCode() ^ this.captcha.hashCode();
	}
}
