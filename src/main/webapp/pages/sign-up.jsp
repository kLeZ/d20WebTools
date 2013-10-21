<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:layout>
	<jsp:attribute name="title">d20WebTools - Sign Up</jsp:attribute>
	<jsp:attribute name="header_title">
		<h3>Sign Up</h3>
	</jsp:attribute>
	<jsp:attribute name="body">
		<style type="text/css">
			table{width: 100%;}.wwFormTable label{color:#000000;}
			ul.errorMessage{margin-bottom:0;}
		</style>
		<script type="text/javascript">
			$('input[name=user.name]').focus();
		</script>
		<s:if test="hasFieldErrors()">
			<div class="alert alert-danger">
				<a class="close" data-dismiss="alert" href="#error-box">&times;</a>
				<strong><s:fielderror /></strong>
			</div>
		</s:if>
		<s:form id="the_form" action="sign-up" method="execute" namespace="/pages">
			<s:textfield name="user.name" label="Name" errorPosition="bottom" cssClass="form-control" />
			<s:textfield name="user.email" label="Email" errorPosition="bottom" cssClass="form-control" />
			<s:password name="user.password" label="Password" errorPosition="bottom" cssClass="form-control" />
			<s:password name="confirmPassword" label="Confirm" errorPosition="bottom" cssClass="form-control" />
			<s:submit cssClass="btn btn-primary" />
		</s:form>
	</jsp:attribute>
</t:layout>
