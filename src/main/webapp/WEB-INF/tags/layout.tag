<%@ tag description="Jumbotron Bootstrap Responsive Layout" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@attribute name="head" fragment="true"%>
<%@attribute name="title" fragment="true"%>
<%@attribute name="header_title" fragment="true"%>
<%@attribute name="header_description" fragment="true"%>
<%@attribute name="navbar_ext" fragment="true"%>
<%@attribute name="body" fragment="true"%>
<%@attribute name="row1" fragment="true"%>
<%@attribute name="row2" fragment="true"%>
<%@attribute name="row3" fragment="true"%>
<%@attribute name="footer" fragment="true"%>
<%
	String uri = request.getRequestURI();
	String pageName = uri.substring(uri.lastIndexOf("/") + 1);
	pageName = pageName.subSequence(0, pageName.lastIndexOf('.')).toString();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="" />
<title><jsp:invoke fragment="title" /></title>
<link rel="shortcut icon" href="../images/favicon.png" />
<link href="../bootstrap/themes/jumbotron/theme.css" rel="stylesheet" />
<!-- Start - Bootstrap inclusions -->
<link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="../bootstrap/css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="../bootstrap/css/bootstrap-responsive.min.css" />
<!-- End - Bootstrap inclusions -->
<link href="../css/custom.css" rel="stylesheet" />

<!-- JavaScript -->
<script src="../jquery/jquery-1.10.2.min.js"></script>
<script src="../bootstrap/js/bootstrap.min.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="../js/html5shiv.js"></script>
	<script src="../js/respond.min.js"></script>
<![endif]-->
	<jsp:invoke fragment="head" />
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">d20 Web Tools</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li <%if (pageName.contentEquals("index"))
			{%> class="active" <%}%>>
						<a href="index">Home</a>
					</li>
					<li <%if (pageName.contentEquals("about"))
			{%> class="active" <%}%>>
						<a href="about">About</a>
					</li>
					<li <%if (pageName.contentEquals("contact"))
			{%> class="active" <%}%>>
						<a href="contact">Contact</a>
					</li>
					<t:session>
						<jsp:attribute name="logged">
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">Rooms <b class="caret"></b>
								</a>
								<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
									<li>
										<a href="#NewRoom" data-toggle="modal" data-target="#NewRoom">Create new room</a>
									</li>
									<li>
										<a href="join-room-init">Join existent room</a>
									</li>
									<li>
										<a href="exit-room">Exit room</a>
									</li>
								</ul>
							</li>
						</jsp:attribute>
					</t:session>
					<jsp:invoke fragment="navbar_ext" />
				</ul>
				<t:session>
					<jsp:attribute name="logged">
					<div class="navbar-right" style="vertical-align: middle; height: 100%;">
						<span class="nav" style="color: white; vertical-align: middle; height: 100%;">
							You're logged in as <s:property value="#session.user.webEmail" default="root@d20webtools.org" escape="false" escapeHtml="false"
									escapeCsv="false" escapeJavaScript="false" escapeXml="false" />
						</span> <a href="logout">Logout</a>
					</div>
					</jsp:attribute>
					<jsp:attribute name="notlogged">
					<form class="navbar-form navbar-right" action="sign-in" method="post">
						<div class="form-group">
							<input type="text" name="user.email" placeholder="Email" class="form-control" />
						</div>
						<div class="form-group">
							<input type="password" name="user.password" placeholder="Password" class="form-control" />
						</div>
						<button type="submit" class="btn btn-success">Sign in</button>
						<div class="form-group">
							<a class="btn btn-primary" href="sign-up-input">Sign up</a>
						</div>
					</form>
				</jsp:attribute>
				</t:session>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</div>
	<s:if test="hasActionErrors()">
		<div id="error-box" class="alert alert-danger">
			<a class="close" data-dismiss="alert" href="#error-box">&times;</a>
			<strong><s:actionerror /></strong>
		</div>
	</s:if>
	<s:if test="hasActionMessages()">
		<div id="info-box" class="alert alert-success">
			<a class="close" data-dismiss="alert" href="#info-box">&times;</a>
			<strong><s:actionmessage /></strong>
		</div>
	</s:if>
	<t:session>
		<jsp:attribute name="logged">
			<div id="NewRoom" class="modal fade in" style="background-color: white; width: auto; display: none;" tabindex="-1" role="dialog"
				aria-labelledby="NewRoomLabel" aria-hidden="true">
				<form action="new-room" method="post">
					<div class="modal-header">
						<button data-dismiss="modal" class="close">&times;</button>
						<h3>Create new room</h3>
					</div>
					<div class="modal-body">
						<div class="form-group">
							Please insert room's name: 
							<input type="text" name="room.name" placeholder="Name" class="form-control" />
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary">Create</button>
						<a data-dismiss="modal" class="btn" href="#">Cancel</a>
					</div>
				</form>
			</div>
		</jsp:attribute>
	</t:session>
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div id="jumbo_header" class="jumbotron">
		<div class="container">
			<jsp:invoke fragment="header_title" />
			<jsp:invoke fragment="header_description" />
		</div>
	</div>
	<div class="container">
		<jsp:invoke fragment="body" />
		<!-- Example row of columns -->
		<div class="row">
			<div id="row1" class="col-lg-4">
				<jsp:invoke fragment="row1" />
			</div>
			<div id="row2" class="col-lg-4">
				<jsp:invoke fragment="row2" />
			</div>
			<div id="row3" class="col-lg-4">
				<jsp:invoke fragment="row3" />
			</div>
		</div>
		<hr />
		<footer>
			<jsp:invoke fragment="footer" />
			<p>&copy; D4nGuARd 2013</p>
		</footer>
	</div>
	<!-- /container -->
</body>
</html>
