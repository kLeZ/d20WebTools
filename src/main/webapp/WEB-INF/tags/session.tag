<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ attribute name="logged" fragment="true" required="false"%>
<%@ attribute name="notlogged" fragment="true" required="false"%>

<s:if test="#session.logged">
	<jsp:invoke fragment="logged" />
</s:if>
<s:else>
	<jsp:invoke fragment="notlogged" />
</s:else>