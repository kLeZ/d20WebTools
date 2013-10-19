<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<t:layout>
	<jsp:attribute name="title">d20WebTools - Room <s:property value="#session.room.name" />
	</jsp:attribute>
	<jsp:attribute name="header_title">
		<h1>Room {<s:property value="room.name" />}
		</h1>
		<p>Master: &lt;<s:property value="room.master.webEmail" />&gt;
		</p>
	</jsp:attribute>
	<jsp:attribute name="header_description">
		<p>&nbsp;</p>
	</jsp:attribute>
	<jsp:attribute name="body">
		
	</jsp:attribute>
	<jsp:attribute name="row1">
		<h4>Logged members</h4>
		<table class="table table-striped table-condensed">
		<s:iterator value="room.members" status="membersStatus">
			<tr>
				<s:if test="#membersStatus.even == true">
					<td style="background: #CCCCCC"><s:property value="webEmail" /></td>
				</s:if>
				<s:else>
					<td><s:property value="webEmail" /></td>
				</s:else>
			</tr>
		</s:iterator>
		</table>
	</jsp:attribute>
	<jsp:attribute name="row2">&nbsp;</jsp:attribute>
	<jsp:attribute name="row3">&nbsp;</jsp:attribute>
	<jsp:attribute name="footer">&nbsp;</jsp:attribute>
</t:layout>
