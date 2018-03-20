<jsp:directive.include file="includes/top.jsp" />
		<a href="<%= request.getContextPath() %>/logout"><spring:message code="management.services.link.logout" /></a>
		<div id="msg" class="success">
			<h2><spring:message code="screen.success.header" /></h2>
			<p><a href="/klxxedu?" target="_self"><spring:message code="screen.success.success" /></a></p>
			<p><spring:message code="screen.success.security" /></p>
		</div>
<jsp:directive.include file="includes/bottom.jsp" />

