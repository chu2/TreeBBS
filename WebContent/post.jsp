<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>投稿画面</title>
<link rel="stylesheet" type="text/css" href="/exee/tree.css" />
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
<h1>ハッテン場 意見表明所</h1>
</div>

<!-- MENUdiv -->
<jsp:include page="header.jsp" />
<!-- あとでなおす　inline -->
<!-- MENU/div -->
<div class="explanation">
説明文
</div>

<!-- 投稿フォームエリア開始 -->

<div class="postform">
<form method="post" action="/exee/treebbs/PostServlet" enctype="multipart/form-data">
<table class="layoutForm">
<tr>
<th><label for="name">名前：</label></th>
<td><input type="text" name="name" id="name" value="${fn:escapeXml(requestScope['ckname']) }"/></td></tr>
<tr><th><label for="title">タイトル：</label></th>
<td><input type="text" name="title" id="title"/></td></tr>
<tr><th><label for="email">メールアドレス：</label></th>
<td><input type="text" name="email" id="email" value="${fn:escapeXml(requestScope['ckemail']) }"/></td></tr>
<tr><th><label for="url">URL：</label></th>
<td><input type="text" name="url" id="url" value="${fn:escapeXml(requestScope['ckurl']) }"/></td></tr>
<tr><th><label for="file">ファイル：</label>
</th><td>
<input type="file" name="file" />
</td>
</tr>
<tr><th><label for="comment">コメント：</label></th><td>
<textarea name="comment" id="comment" rows="10" cols="40"></textarea></td></tr>
<tr><th><label for="password">パスワード：</label></th><td><input type="password" name="password" id="password" /></td></tr>
</table>
<input type="hidden" name="del_flag" value="0" />
<input type="hidden" name="reply_id" value="0" />
<input type="hidden" name="reply_pa" value="0" />
<input type="hidden" name="reply_flag" value="0" />
<div class="submit"><input type="submit" name="mode" value="コメント" />　
　<input type="submit" name="mode" value="プレビュー" /></div>
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
