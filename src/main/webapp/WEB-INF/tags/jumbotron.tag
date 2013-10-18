<%@ tag description="Jumbotron Bootstrap Responsive Layout"
	language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@attribute name="title" fragment="true"%>
<%@attribute name="header_title" fragment="true"%>
<%@attribute name="header_description" fragment="true"%>
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

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="" />
<meta name="author" content="" />

<title><jsp:invoke fragment="title" /></title>

<link rel="shortcut icon" href="../images/favicon.png" />

<link href="../bootstrap/themes/jumbotron.css" rel="stylesheet" />

<!-- Start - Bootstrap inclusions -->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css" />

<!-- Optional theme -->
<link rel="stylesheet" href="../bootstrap/css/bootstrap-theme.min.css" />
<!-- End - Bootstrap inclusions -->
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.min.js"></script>
<![endif]-->
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">d20 Web Tools</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li <%if (pageName.contentEquals("index")){%>class="active"<%}%>>
						<a href="index">Home</a>
					</li>
					<li <%if (pageName.contentEquals("about")){%>class="active"<%}%>>
						<a href="about">About</a>
					</li>
					<li <%if (pageName.contentEquals("contact")){%>class="active"<%}%>>
						<a href="contact">Contact</a>
					</li>
				</ul>
				<s:if test="logged == true">
					<div class="navbar-right" style="vertical-align: middle; height: 100%;">
						<p style="color: white; vertical-align: middle; height: 100%;">You're logged in as <s:property value="credential.email"/></p>
					</div>
				</s:if>
				<s:else>
					<form class="navbar-form navbar-right" action="sign-in" method="post" class="class java.util.HashMap">
						<div class="form-group">
							<input type="text" name="credential.email" placeholder="Email"
								class="form-control" />
						</div>
						<div class="form-group">
							<input type="password" name="credential.password"
								placeholder="Password" class="form-control" />
						</div>
						<button type="submit" class="btn btn-success">Sign in</button>
					</form>
				</s:else>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</div>
	<s:if test="hasActionErrors()">
		<!-- Modal -->
		<div class="modal show fade in" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="false">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
					</div>
					<div class="modal-body">
						<s:actionerror />
					</div>
					<div class="modal-footer">
						<a href="#" onclick="$('#myModal').css('display', 'none');">boo!</a>
						<button type="button" onclick="$('#myModal').css('display', 'none');alert('boo!')" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</s:if>
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<div class="container">
			<jsp:invoke fragment="header_title" />
			<jsp:invoke fragment="header_description" />
			<p>
				<a class="btn btn-primary btn-lg" href="learn-more">Learn more
					&raquo;</a>
			</p>
		</div>
	</div>

	<div class="container">
		<jsp:invoke fragment="body" />
		<!-- Example row of columns -->
		<div class="row">
			<div class="col-lg-4">
				<jsp:invoke fragment="row1" />
			</div>
			<div class="col-lg-4">
				<jsp:invoke fragment="row2" />
			</div>
			<div class="col-lg-4">
				<jsp:invoke fragment="row3" />
			</div>
		</div>

		<hr />

		<footer> <jsp:invoke fragment="footer" />
		<p>&copy; D4nGuARd 2013</p>
		</footer>
	</div>
	<!-- /container -->

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="../bootstrap/js/bootstrap.min.js" />
	<script src="../jquery/jquery-1.10.2.min.js" />
</body>
</html>
