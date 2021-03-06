<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<t:layout>
	<jsp:attribute name="head">
		<link href="../css/tablecloth.css" rel="stylesheet" />
		<link href="../css/prettify.css" rel="stylesheet" />
		<script src="../js/jquery.metadata.js"></script>
		<script src="../js/jquery.tablesorter.min.js"></script>
	</jsp:attribute>
	<jsp:attribute name="title">d20WebTools - Room <s:property value="room.name" />
	</jsp:attribute>
	<jsp:attribute name="header_title">
		<h1>
			<s:property value="room.name" />
		</h1>
	</jsp:attribute>
	<jsp:attribute name="header_description">
		<p style="font-size: x-small;">by <s:property value="room.master.webEmail" />
		</p>
	</jsp:attribute>
	<jsp:attribute name="body">
		<style type="text/css">
<!--
#jumbo_header {
	padding: 0;
	margin: 0;
}

#row1 {
	width: 66.66666666666666% !important;
}

#chatbox {
	text-align: left;
	margin: 0 auto;
	margin-top: 25px;
	margin-bottom: 25px;
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
	</jsp:attribute>
	<jsp:attribute name="row1">
		<script type="text/javascript">
			var animateScroll = false;

			function scrollLog() {
				if (animateScroll) {
					$("#chatbox").animate({
						scrollTop : $('#chatbox')[0].scrollHeight
					}, 'normal');
				} else {
					$('#chatbox').scrollTop($('#chatbox')[0].scrollHeight);
				}
			}

			function ReloadPage() {
				$('#chatbox').load('chatbox');
				scrollLog();
			};
			$(document).ready(function() {
				ReloadPage();
				$('input[name="message"]').focus();
				setInterval('ReloadPage()', 500);
				$.ajaxSetup({
					cache : true
				});
			});

			$('#send').click(function() {
				scrollLog();
			});
		</script>
		<div id="chatbox" class="row"></div>
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
		<script type="text/javascript">
			$("#members").tablecloth({ theme : "dark" });
		</script>
		<h4>Logged members (<s:property value="members.size" />)</h4>
		<div class="table-responsive">
		<table id="members" class="table table-striped table-condensed table-hover">
		<s:iterator value="members">
			<s:if test="user.webEmail == #session.user.webEmail">
				<tr><td class="success"><s:property value="user.webEmail" /></td></tr>
			</s:if>
			<s:else>
				<tr><td><s:property value="user.webEmail" /></td></tr>
			</s:else>
		</s:iterator>
		</table>
		</div>
	</jsp:attribute>
</t:layout>