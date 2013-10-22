<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<t:layout>
	<jsp:attribute name="head">
		<link href="../css/tablecloth.css" rel="stylesheet" />
		<link href="../css/prettify.css" rel="stylesheet" />
		<script src="../js/jquery.metadata.js"></script>
		<script src="../js/jquery.tablesorter.min.js"></script>
		<script src="../js/jquery.tablecloth.js"></script>
	</jsp:attribute>
	<jsp:attribute name="title">d20WebTools - Join room</jsp:attribute>
	<jsp:attribute name="header_title">
		<h1>Join an existent room</h1>
	</jsp:attribute>
	<jsp:attribute name="header_description"></jsp:attribute>
	<jsp:attribute name="body">
		<script type="text/javascript">$("table").tablecloth();</script>
		<h4>Available rooms</h4>
		<form action="join-room" method="post">
			<table class="table table-striped table-condensed">
			<s:iterator value="rooms.values()" status="room">
				<tr>
					<td><s:property value="name" /></td>
					<td>
						<button name="room.id" class="btn btn-primary" type="submit" value="<s:property value="id" />">Join</button>
					</td>
				</tr>
			</s:iterator>
			</table>
		</form>
	</jsp:attribute>
	<jsp:attribute name="row1">&nbsp;</jsp:attribute>
	<jsp:attribute name="row2">&nbsp;</jsp:attribute>
	<jsp:attribute name="row3">&nbsp;</jsp:attribute>
	<jsp:attribute name="footer">&nbsp;</jsp:attribute>
</t:layout>
