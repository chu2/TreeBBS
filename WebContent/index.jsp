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
説明文
</div>

<!-- メイン掲示板エリア開始 -->
<div class="bbs">

	<!-- 1 -->

	<ul class="leaves">

${requestScope['code']}

	</ul>

	<!-- 1/ -->


</div>
<!-- メイン掲示板エリア終了 -->
<!-- footer start -->

<div class="bottom"><br />
<c:set var="p" value="${requestScope['p']}" />
<c:set var="link" value="${requestScope['link']}" />
<c:set var="pMax" value="${requestScope['count']}" />

<!-- POST送信用form -->

<form action="/exee/treebbs/TreeServlet" method="post" name="page">
<input type="hidden" name="p" value="" />
</form>

<div class="bottomChild">
<c:if test="${link != 0}">[ <a href="#" onclick="paging(${p-1 })">前のページ</a> ]</c:if>
</div>
<div class="bottomChild">
<!-- ページナンバーでリンク -->

<c:forEach var="item" items="${requestScope['pageList'] }" varStatus="i">
	<c:if test="${item.page == 0 }">
	...,
	</c:if>
	<c:if test="${item.page != 0 }">
		<c:if test="${item.link }">
			<a href="#" onclick="paging(${item.page})">${item.page }</a></c:if>
		<c:if test="${!item.link }">${item.page }</c:if>
		<c:if test="${!i.last }">,</c:if>
	</c:if>

</c:forEach>

</div>
<div class="bottomChild">
<c:if test="${link != 1}">[ <a href="#" onclick="paging(${p+1 })">次のページ</a> ]</c:if>
</div>


</div>

<!-- footer end -->

</div>
<!-- 画面/div -->

</div>
<!-- 全体/div -->
</body>
</html>












