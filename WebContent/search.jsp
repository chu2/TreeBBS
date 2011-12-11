<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ツリー掲示板</title>
<link rel="stylesheet" type="text/css" href="/exee/tree.css" />
<style type="text/css">
<!--
div.reply{
	width:800px;
}
-->
</style>
</head>
<body>

<!-- 全体div -->
<div class="whole">
<!-- 画面div -->
<div class="main">


<div class="title">
<h1><img src="/exee/hatten.jpg" alt="ハッテン場"  class="logo" /></h1>
</div>

<!-- MENUdiv -->
<jsp:include page="header.jsp" />
<!-- MENU/div -->
</div>
<!-- CONTENTdiv -->
<div class="contents">
<br />
<div class="postform">
<form method="post" action="/exee/treebbs/PostServlet" enctype="multipart/form-data">
<input type="text" name="word" size="30"/><br /><br />
<select name="type">
<option value="0">タイトル・本文から検索</option>
<option value="1">タイトルから検索</option>
<option value="2">本文から検索</option>
</select>
<br /><br />
<input type="submit" name="mode" value="検索" />
</form>
</div>
<br />

<!-- CONTENTS/div -->
</div>


<!-- 画面/div -->
</div>
<!-- 全体/div -->
</div>
</body>
</html>