<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="treebbs.TreeBbs"%>
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

<!-- CONTENTdiv -->
<div class="contents">
<!-- コメント部分 -->
	<!-- サーブレットからコメントデータ取得 -->
<c:set var="item" value="${requestScope['comment']}" />

	<!-- del_flagが0の場合は記事内容を公開 -->
<div class="article">
<div>
<c:if test="${item.reply_id != 0 }">
 ${item.reply_id}への返信&nbsp;&nbsp;  </c:if>ID： ${item.id}&nbsp;&nbsp;
</div>
<div>(時間) ${item.date}</div>
<div>名前： ${item.name}</div>
<div>タイトル： ${item.title}</div>
<div>E-mail： ${item.email}</div>
<div>URL： ${item.url}</div>
<div>
<c:if test="${item.getFilepath() != ''}"><a href="/exee/files/${item.getFilepath()}">添付ファイル</a></c:if>
</div>
<div>コメント</div>
<div class="comment"> ${item.comment}</div>
<br />
&nbsp;&nbsp;<a href="/exee/treebbs/PostServlet?mode=edit&id=${item.id}">編集</a>
&nbsp;&nbsp;<a href="/exee/treebbs/PostServlet?mode=delete&id=${item.id}">削除</a>
</div>
<!-- コメントのツリー -->
<div class="treeOfArticle">
<ul class="leaves">

${requestScope['code'] }

</ul>
</div>

<!-- 投稿フォームエリア開始 -->

<div class="postform">
<form method="post" action="/exee/treebbs/PostServlet"  enctype="multipart/form-data">
<table class="layoutForm">
<tr>
<th><label for="name">名前：</label></th>
<td><input type="text" name="name" id="name" size="20" value="${item.name }"/></td>
</tr>
<tr><th><label for="title">タイトル：</label></th>
<td><input type="text" name="title" id="title" size="40" value="${item.title }"/></td>
</tr>
<tr><th><label for="email">メールアドレス：</label></th>
<td><input type="text" name="email" id="email" size="40" value="${item.email }"/></td>
</tr>
<tr><th><label for="url">URL：</label></th><td>
<input type="text" name="url" id="url" size="40" value="${item.url }"/></td></tr>
<tr><th><label for="file">ファイル：</label>
</th><td>
<input type="file" name="file" />
</td>
</tr>
<tr><th><label for="comment">コメント：</label></th>
<td>
<textarea name="comment" id="comment" rows="10" cols="40">
<c:forEach var="response" items="${requestScope['response']}" varStatus="i">${response }</c:forEach>
</textarea></td>
</tr>
<tr><th><label for="password">パスワード：</label></th><td><input type="password" name="password" id="password"/></td></tr>

</table>
<!-- フォームhidden部分 -->
<input type="hidden" name="del_flag" value="0" />
<input type="hidden" name="reply_id" value="${item.reply_id}" />
<input type="hidden" name="reply_pa" value="${item.reply_pa}" />
<input type="hidden" name="reply_flag" value="0" />
<!-- フォームhidden部分/ -->

<div class="submit"><input type="submit" name="mode" value="コメント" /></div>
</form>
</div>

<!-- 投稿フォームエリア終了 -->


<!-- CONTENTS/div -->
</div>


<!-- 画面/div -->
</div>
<!-- 全体/div -->
</div>
</body>
</html>



