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
<c:set var="item" value="${requestScope['cData']}" />
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
<!-- あとでなおす　inline -->
<!-- MENU/div -->
<div class="explanation">
${fn:escapeXml(item.id)} の記事を編集します。<br />
編集パスワードを入力してください。
</div>

<!-- 投稿フォームエリア開始 -->


<div class="postform">
<form method="post" action="/exee/treebbs/PostServlet" enctype="multipart/form-data">
<table class="layoutForm">
<tr>
<th><label for="name">名前：</label></th>
<td><input type="text" name="name" id="name" value="${item.name}"/></td></tr>
<tr><th><label for="title">タイトル：</label></th>
<td><input type="text" name="title" id="title" value="${item.title}"/></td></tr>
<tr><th><label for="email">メールアドレス：</label></th>
<td><input type="text" name="email" id="email" value="${item.email}"/></td></tr>
<tr><th><label for="url">URL：</label></th>
<td><input type="text" name="url" id="url" value="${item.url}"/></td></tr>
<tr><th><label for="comment">コメント：</label></th><td>
<textarea name="comment" id="comment" rows="10" cols="40">${item.comment}</textarea></td></tr>
<tr><th colspan="2"><label for="password">編集パスワードを入力してください。</label></th></tr>
<tr><td colspan="2" class="center">
<input type="password" name="password" id="password" />
&nbsp;&nbsp;
<input type="submit" name="mode" value="編集" />
</td></tr>

</table>
<input type="hidden" name="id" value="${fn:escapeXml(item.id)}" />
<input type="hidden" name="reply_id" value="${fn:escapeXml(item.reply_id)}" />
<input type="hidden" name="reply_pa" value="${fn:escapeXml(item.reply_pa)}" />
<input type="hidden" name="reply_flag" value="${fn:escapeXml(item.reply_flag)}" />

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