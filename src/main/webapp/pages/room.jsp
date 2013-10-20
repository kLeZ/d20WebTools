<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<style>
<!--
#jumbo_header {
	padding: 0;
	margin: 15px;
}

#row1 {
	width: 66.66666666666666% !important;
}

#chatbox {
	text-align: left;
	margin: 0 auto;
	margin-top: 25px; margin-bottom : 25px;
	padding: 10px;
	background: #fff;
	height: 230px;
	width: auto;
	border: 1px solid #ACD8F0;
	overflow: auto;
	margin-bottom: 25px;
}

.form-group,.form-group button,.form-group input {
	float: left;
	display: inline;
}

.form-group {
	margin-right: 5px
}
-->
</style>
<t:layout>
	<jsp:attribute name="title">d20WebTools - Room <s:property value="room.name" />
	</jsp:attribute>
	<jsp:attribute name="header_title">
		<h1>
			<s:property value="room.name" />
		</h1>
		<p style="font-size: x-small;">by <s:property value="room.master.webEmail" />
		</p>
	</jsp:attribute>
	<jsp:attribute name="header_description">&nbsp;</jsp:attribute>
	<jsp:attribute name="body">&nbsp;</jsp:attribute>
	<jsp:attribute name="row1">
		<div id="chatbox" class="row">
			<s:iterator value="room.messages" status="msg">
				<div>
					[<s:date name="time" format="dd/MM/yyyy hh:mm:ss" />]
					<strong><s:property value="user.account.webEmail" /></strong>:
					<s:property value="text" />
				</div>
			</s:iterator>
		</div>
		<form action="chat" method="post" role="form" class="form-inline">
			<div class="input-group" style="width: 100%;">
				<input name="message" type="text" class="form-control" />
				<span class="input-group-btn">
					<button id="send" type="submit" class="btn btn-primary">Send</button>
				</span>
			</div>
		</form>
	</jsp:attribute>
	<jsp:attribute name="row3">
		<h4>Logged members</h4>
		<table class="table table-striped table-condensed">
		<s:iterator value="room.members" status="member">
			<tr>
				<s:if test="#member.even == true">
					<td class="even"><s:property value="webEmail" /></td>
				</s:if>
				<s:else>
					<td><s:property value="webEmail" /></td>
				</s:else>
			</tr>
		</s:iterator>
		</table>
	</jsp:attribute>
	<jsp:attribute name="footer">&nbsp;</jsp:attribute>
</t:layout>
<script type="text/javascript">
	function scrollLog(animate) {
		if (animate) {
			$("#chatbox").animate({
				scrollTop : $('#chatbox')[0].scrollHeight
			}, 'normal');
		} else {
			$('#chatbox').scrollTop($('#chatbox')[0].scrollHeight);
		}
	}

	$(document).ready(function() {
		scrollLog(false);
		$('input[name="message"]').focus();
	});

	$('#send').click(function() {
		scrollLog(false);
	});
</script>