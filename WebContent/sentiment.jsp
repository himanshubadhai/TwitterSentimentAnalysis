<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sentiment Page</title>
<style>
.error {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}

.msg {
	padding: 15px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}

.sentiment-box {
	width: 500px;
	padding: 20px;
	margin: 50px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}

body {
	font-size: 32px;
	color: #188BC0;
	font-family: Calibri;
}

h3 {
	font-size: 18px;
	color: #188BC0;
	font-family: Calibri;
}

td {
	font-size: 15px;
	color: black;
	width: 150px;
	height: 22px;
	text-align: left;
}

.heading {
	font-size: 18px;
	color: white;
	font: bold;
	background-color: orange;
	border: thick;
}

.styled-button {
	background: #25A6E1;
	background: -moz-linear-gradient(top, #25A6E1 0%, #188BC0 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #25A6E1),
		color-stop(100%, #188BC0));
	background: -webkit-linear-gradient(top, #25A6E1 0%, #188BC0 100%);
	background: -o-linear-gradient(top, #25A6E1 0%, #188BC0 100%);
	background: -ms-linear-gradient(top, #25A6E1 0%, #188BC0 100%);
	background: linear-gradient(top, #25A6E1 0%, #188BC0 100%);
	filter: progid: DXImageTransform.Microsoft.gradient( startColorstr='#25A6E1',
		endColorstr='#188BC0', GradientType=0);
	padding: 8px 13px;
	color: #fff;
	font-family: 'Helvetica Neue', sans-serif;
	font-size: 17px;
	border-radius: 4px;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border: 1px solid #1A87B9
}
}
</style>
</head>
<body onload='document.sentimentForm.username.focus();'>

	<div align="center">
		<a href="http://www.twitter.com" title="Return to the homepage" id="logo" target="_blank">
			<img src="images/twitter-logo.jpg" alt="Twitter logo" height="100px"
			width="200px" />
		</a>

		<center>Twitter Sentiment Calculator</center>

	</div>

	<jsp:useBean id="twitterBean" scope="session"
		class="edu.uic.ids594.bean.TwitterBean">
	</jsp:useBean>


	<div class="sentiment-box">

		<h3>Please Enter your Tweet</h3>

		<form name='sentimentForm' action="SentimentServlet" method='POST'>

			<table>
				<tr>
					<td width="auto">Tweet:</td>
					<td><input type='text' name='tweet' value='' size="52"></td>
				</tr>
			</table>
			<table>
				<tr>
					<td>Classification Method:</td>
					<td><input type='radio' name='classifier' value="1" />Naive
						Bayes</td>
					<td><input type='radio' name='classifier' value="2" />Logistic
						Regression</td>
				</tr>
				<tr>
					<td></td>
				</tr>

			</table>

			<div>
				<table align="center">
					<tr>
						<td colspan='2'><input class="styled-button" name="submit"
							type="submit" value="submit" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>

	<div class="sentiment-box">
		<h3>Sentiment Results</h3>

		<table>
			<tr>
				<td>Tweet:</td>
				<td><%=twitterBean.getTweet()%></td>
			</tr>
			<tr>
				<td>Result:</td>
				<td><%=twitterBean.getMessage()%></td>
			</tr>
		</table>
	</div>
</body>
</html>