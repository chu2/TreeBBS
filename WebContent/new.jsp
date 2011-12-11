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
<script type="text/javascript" src="/exee/js/paging.js"></script>
<style type="text/css">
<!--

-->
</style>
</head>
<body>

<!-- 全体div -->
<div class="whole">
<!-- 画面div -->
<div class="main">


<div class="title">
<img src="/exee/hatten.jpg" alt="ハッテン場" class="logo" />
</div>

<!-- MENUdiv -->
<jsp:include page="header.jsp" />
<!-- MENU/div -->
<div class="explanation">
24時間以内に投稿された新着記事 10件
</div>

<!-- メイン掲示板エリア開始 -->
<div class="bbs">

	<!-- 1 -->
	<div class="tree">
	<ul class="leaves">

${requestScope['code']}

	</ul>
	</div>
	<!-- 1/ -->


</div>
<!-- メイン掲示板エリア終了 -->
<!-- footer start -->

<!-- footer end -->

</div>
<!-- 画面/div -->

</div>
<!-- 全体/div -->
</body>
</html>
