<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<t:layout>
	<jsp:attribute name="title"></jsp:attribute>
	<jsp:attribute name="header_title">
		<h1>Join an existent room</h1>
	</jsp:attribute>
	<jsp:attribute name="header_description"></jsp:attribute>
	<jsp:attribute name="body">
		<h4>Available rooms</h4>
		<form action="join-room" method="post" class="class java.util.HashMap">
			<table class="table table-striped table-condensed">
			<s:iterator value="rooms.values()" status="room">
				<tr>
					<s:if test="#room.even == true">
						<td class="even"><s:property value="name" /></td>
						<td class="even">
							<button name="room.id" class="btn btn-primary" type="submit" value="<s:property value="id" />">Join</button>
						</td>
					</s:if>
					<s:else>
						<td><s:property value="name" /></td>
						<td>
							<button name="room.id" class="btn btn-primary" type="submit" value="<s:property value="id" />">Join</button>
						</td>
					</s:else>
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
