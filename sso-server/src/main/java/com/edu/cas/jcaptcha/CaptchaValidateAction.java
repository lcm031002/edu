package com.edu.cas.jcaptcha;

import com.edu.cas.exception.BadCaptchaAuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.validation.Errors;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.action.FormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.edu.cas.exception.BadCaptchaAuthenticationException;
import com.edu.cas.form.CasCredentials;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * @ClassName: CaptchaValidateAction
 * @Description:校验码验证action
 *
 */
public final class CaptchaValidateAction extends AbstractAction {
	private ImageCaptchaService captchaService;
	private String captchaValidationParameter = "captcha";

	protected Event doExecute(final RequestContext context)
			throws BadCaptchaAuthenticationException {
		String captcha_response = context.getRequestParameters().get(
				captchaValidationParameter);
		boolean valid = false;

		if (captcha_response != null) {
			String id = WebUtils.getHttpServletRequest(context).getSession()
					.getId();
			if (id != null) {
				try {
					valid = captchaService.validateResponseForID(id,
							captcha_response).booleanValue();
				} catch (CaptchaServiceException cse) {
				}
			}
		}

		if (valid) {
			return success();
		}
		
		populateErrorsInstance(context, BadCaptchaAuthenticationException.ERROR);
		
		return error();
	}

	private final void populateErrorsInstance(final RequestContext context,
			final BadCaptchaAuthenticationException e) {
		try {
			final Errors errors = new FormAction() {
				public Errors genErrors(RequestContext context) {
					try {
						setFormObjectClass(CasCredentials.class);
						setFormObjectName("credentials");
						return getFormErrors(context);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("create Errors faild.see:", e);
					}
					return null;
				}
			}.genErrors(context);
			if (null != errors) {
				errors.reject(e.getCode(), e.getCode());
			}
		} catch (final Exception fe) {
			logger.error(fe, fe);
		}
	}

	public void setCaptchaService(ImageCaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	public void setCaptchaValidationParameter(String captchaValidationParameter) {
		this.captchaValidationParameter = captchaValidationParameter;
	}
}
