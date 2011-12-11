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
<c:set var="p" value="${requestScope['p']}" />
<div class="explanation">
" ${requestScope['word'] } " での検索結果 <br />
<c:choose>
<c:when test="${requestScope['count'] == 0}">
見つかりませんでした。
</c:when>
<c:when test="${requestScope['count'] <= p*10 && requesstScope['count'] != 0}">
${requestScope['count'] } 件中 ${(p-1)*10 + 1 } 件～${requestScope['count'] }件
</c:when>
<c:when test="${requestScope['count'] > p*10 && requesstScope['count'] != 0}">
${requestScope['count'] } 件中 ${(p-1)*10 + 1 } 件～${p*10 }件
</c:when>
</c:choose>

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

<div class="bottom"><br />

<c:set var="link" value="${requestScope['link']}" />

<c:set var="pMax" value="${requestScope['pMax']}" />

<div class="bottomChild">
<!-- 送信用hiddenフォーム -->
<form name="page" action="/exee/treebbs/PostServlet" method="post">
<input type="hidden" name="mode" value="検索"/>
<input type="hidden" name="word" value="${requestScope['word'] }" />
<input type="hidden" name="p" value="" />
</form>
<c:if test="${link != 0 && link != 3}">[ <a href="#" onclick="paging(${p-1});">前のページ</a> ]</c:if>
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
<c:if test="${link != 1 && link != 3}">[ <a href="#" onclick="paging(${p+1});">次のページ</a> ]</c:if>
</div>


</div>

<!-- footer end -->

</div>
<!-- 画面/div -->

</div>
<!-- 全体/div -->
</body>
</html>