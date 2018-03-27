/**  
 * @Title: BadCaptchaAuthenticationException.java
 * @Package com.edu.cas.exception
 * @author zhuliyong zly@entstudy.com  
 * @date 2016年7月8日 下午7:46:28
 * @version KLXX ERPV4.0  
 */
package com.edu.cas.exception;

import org.jasig.cas.authentication.handler.AuthenticationException;

/**
 * @ClassName: BadCaptchaAuthenticationException
 * @Description: 验证码校验错误异常
 * @author zhuliyong zly@entstudy.com
 * @date 2016年7月8日 下午7:46:28
 *
 */
public class BadCaptchaAuthenticationException  extends
	AuthenticationException {

	/**
	 * Static instance of class to prevent cost incurred by creating new
	 * instance.
	 */
	public static final BadCaptchaAuthenticationException ERROR = new BadCaptchaAuthenticationException();

	/** UID for serializable objects. */
	private static final long serialVersionUID = 3256719585087797044L;
	
	/**
	 * Default constructor that does not allow the chaining of exceptions and
	 * uses the default code as the error code for this exception.
	 */
	private static final String CODE = "error.authentication.credentials.bad.captcha";
	
	/**
	 * Default constructor that does not allow the chaining of exceptions and
	 * uses the default code as the error code for this exception.
	 */
	public BadCaptchaAuthenticationException() {
	    super(CODE);
	}
	
	/**
	 * Constructor to allow for the chaining of exceptions. Constructor defaults
	 * to default code.
	 * 
	 * @param throwable the chainable exception.
	 */
	public BadCaptchaAuthenticationException(final Throwable throwable) {
	    super(CODE, throwable);
	}
	
	/**
	 * Constructor method to allow for providing a custom code to associate with
	 * this exception.
	 * 
	 * @param code the code to use.
	 */
	public BadCaptchaAuthenticationException(final String code) {
	    super(code);
	}
	
	/**
	 * Constructor to allow for the chaining of exceptions and use of a
	 * non-default code.
	 * 
	 * @param code the user-specified code.
	 * @param throwable the chainable exception.
	 */
	public BadCaptchaAuthenticationException(final String code,
	    final Throwable throwable) {
	    super(code, throwable);
	}
}
