<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<t:layout>
	<jsp:attribute name="title">d20WebTools - Exception</jsp:attribute>
	<jsp:attribute name="header_title">
		<h1>Oops!</h1>
		<h2>An unexpected error has occurred</h2>
	</jsp:attribute>
	<jsp:attribute name="header_description">
		<p>
			Something gone wrong with your action.<br />
			Please mail me a report of what you've done before catching this error.
		</p>
	</jsp:attribute>
	<jsp:attribute name="body">
		<p>
			Please report this error to the Admin or your Game Master.
			Thank you for your cooperation.
		</p>
		<hr />
		<h3>Error</h3>
		<p>
			<s:actionmessage />
			<s:actionerror />
		</p>
		<hr />
		<p>
			<s:property value="%{exception}" />
			<s:property value="%{exception.message}" />
		</p>
		<hr />
		<h3>Technical Details</h3>
		<p><s:property value="%{exceptionStack}" /></p>
		<s:debug />
	</jsp:attribute>
</t:layout>
