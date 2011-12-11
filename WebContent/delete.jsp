<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>編集画面</title>
<link rel="stylesheet" type="text/css" href="/exee/tree.css" />
<style type="text/css">
<!--
.submit{
	display:inline;
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
<h1>ハッテン場 意見表明所</h1>
</div>

<!-- MENUdiv -->
<div class="menu">
<div class="menuChild">[ <a href="/exee/treebbs/TreeServlet">HOME</a> ]</div>
<div class="menuChild">[ <a href="/exee/post.jsp">新規投稿</a> ]</div>
<div class="menuChild">[ 新着記事 ]</div>
<div class="menuChild">[ スレッド形式 ]</div>
<div class="menuChild">[ <a href="/exee/search.jsp">SEARCH</a> ]</div>
</div>

<c:set var="item" value="${requestScope['cData']}" />

<!-- あとでなおす　inline -->
<!-- MENU/div -->
<div class="explanation">
${fn:escapeXml(item.id)} の記事を編集します。<br />
編集パスワードを入力してください。
</div>

<!-- 投稿フォームエリア開始 -->


<div class="article">

<div>
 ${item.reply_id}への返信 &nbsp;&nbsp; ID： ${fn:escapeXml(item.id)}&nbsp;&nbsp;
</div>
<div>(時間) ${item.date}</div>
<div>名前： ${item.name}</div>
<div>タイトル： ${item.title}</div>
<div>E-mail： ${item.email}</div>
<div>URL： ${item.url}</div>
<div>コメント</div>
<div class="comment"> ${item.comment}</div>
</div>
<br />
<div class="submitComment">
上記のコメントを削除します。パスワードを入力して削除を押してください。
</div>

<div class="left submit">
<form method="post" action="/exee/treebbs/PostServlet" enctype="multipart/form-data">
<input type="password" name="password" id="password" />
&nbsp;&nbsp;
<input type="submit" name="mode" value="削除" />
<input type="hidden" name="id" value="${fn:escapeXml(item.id)}" />
</form>

</div>


<!-- 投稿フォームエリア終了 -->
<!-- footer start -->



<!-- footer end -->

</div>
<!-- 画面/div -->

</div>
<!-- 全体/div -->
</body>
</html>