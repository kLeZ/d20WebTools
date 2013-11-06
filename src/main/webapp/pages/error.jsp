<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<t:layout>
	<jsp:attribute name="title">d20WebTools - Exception</jsp:attribute>
	<jsp:attribute name="header_title">
		<h1>Oops!</h1>
	</jsp:attribute>
	<jsp:attribute name="header_description">
		<p>
			Something gone wrong with your action.<br />
			Please mail me a report of what you've done before catching this error.
		</p>
	</jsp:attribute>
	<jsp:attribute name="body">
		<s:actionmessage />
		<s:actionerror />
		<s:property value="%{exception}" />
		<s:debug />
	</jsp:attribute>
</t:layout>
