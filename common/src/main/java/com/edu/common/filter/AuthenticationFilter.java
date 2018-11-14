package com.edu.common.filter;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter extends AbstractCasFilter {
        private String casServerLoginUrl;
        private String ignorePattern;
        private boolean renew = false;
        private boolean gateway = false;
        private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();

        public AuthenticationFilter() {
        }

        @Override
        protected void initInternal(FilterConfig filterConfig) throws ServletException {
            if (!this.isIgnoreInitConfiguration()) {
                super.initInternal(filterConfig);
                this.setCasServerLoginUrl(this.getPropertyFromInitParams(filterConfig, "casServerLoginUrl", (String)null));
                this.log.trace("Loaded CasServerLoginUrl parameter: " + this.casServerLoginUrl);
                this.setRenew(this.parseBoolean(this.getPropertyFromInitParams(filterConfig, "renew", "false")));
                this.log.trace("Loaded renew parameter: " + this.renew);
                this.setGateway(this.parseBoolean(this.getPropertyFromInitParams(filterConfig, "gateway", "false")));
                this.log.trace("Loaded gateway parameter: " + this.gateway);
                String gatewayStorageClass = this.getPropertyFromInitParams(filterConfig, "gatewayStorageClass", (String)null);
                if (gatewayStorageClass != null) {
                    try {
                        this.gatewayStorage = (GatewayResolver)Class.forName(gatewayStorageClass).newInstance();
                    } catch (Exception var4) {
                        this.log.error(var4, var4);
                        throw new ServletException(var4);
                    }
                }
                this.setIgnorePattern(this.getPropertyFromInitParams(filterConfig, "ignorePattern", null));
            }

        }

        @Override
        public void init() {
            super.init();
            CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
        }

        @Override
        public final void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            HttpServletResponse response = (HttpServletResponse)servletResponse;
            HttpSession session = request.getSession(false);
            Assertion assertion = session != null ? (Assertion)session.getAttribute("_const_cas_assertion_") : null;
            if (assertion != null) {
                filterChain.doFilter(request, response);
            } else {
                String serviceUrl = this.constructServiceUrl(request, response);
                if (StringUtils.isNotEmpty(this.ignorePattern) && serviceUrl.contains(this.ignorePattern)) {
                    filterChain.doFilter(request, response);
                } else {
                    String ticket = CommonUtils.safeGetParameter(request, this.getArtifactParameterName());
                    boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);
                    if (!CommonUtils.isNotBlank(ticket) && !wasGatewayed) {
                        this.log.debug("no ticket and no assertion found");
                        String modifiedServiceUrl;
                        if (this.gateway) {
                            this.log.debug("setting gateway attribute in session");
                            modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
                        } else {
                            modifiedServiceUrl = serviceUrl;
                        }

                        if (this.log.isDebugEnabled()) {
                            this.log.debug("Constructed service url: " + modifiedServiceUrl);
                        }

                        String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, this.getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);
                        if (this.log.isDebugEnabled()) {
                            this.log.debug("redirecting to \"" + urlToRedirectTo + "\"");
                        }

                        response.sendRedirect(urlToRedirectTo);
                    } else {
                        filterChain.doFilter(request, response);
                    }
                }
            }
        }

        public final void setRenew(boolean renew) {
            this.renew = renew;
        }

        public final void setGateway(boolean gateway) {
            this.gateway = gateway;
        }

        public final void setCasServerLoginUrl(String casServerLoginUrl) {
            this.casServerLoginUrl = casServerLoginUrl;
        }

        public final void setGatewayStorage(GatewayResolver gatewayStorage) {
            this.gatewayStorage = gatewayStorage;
        }

    public String getIgnorePattern() {
        return ignorePattern;
    }

    public void setIgnorePattern(String ignorePattern) {
        this.ignorePattern = ignorePattern;
    }
}
