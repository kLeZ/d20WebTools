<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:layout>
	<jsp:attribute name="title">d20WebTools - Sign Up</jsp:attribute>
	<jsp:attribute name="header_title">
		<h3>Sign Up</h3>
	</jsp:attribute>
	<jsp:attribute name="body">
		<form action="sign-up" method="post">
			<div class="form-group">
				Name: <input type="text" name="user.name" class="form-control" />
			</div>
			<div class="form-group">
				Email: <input type="text" name="user.email" class="form-control" />
			</div>
			<div class="form-group">
				Password: <input type="password" name="user.password" class="form-control" />
			</div>
			<div class="form-group">
				Confirm: <input type="password" name="confirmPassword" class="form-control" />
			</div>
			<button type="submit" class="btn btn-primary">Sign Up</button>
		</form>
	</jsp:attribute>
</t:layout>
