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
<c:if test="${item.del_flag == 0 }">
	<div class="article">
	<div>
 	${item.getReply_id()}への返信 &nbsp;&nbsp; ID： ${item.id}&nbsp;&nbsp;
	</div>
	<div>(時間) ${item.getDate()}</div>
	<div>名前： ${item.getName()}</div>
	<div>タイトル： ${item.getTitle()}</div>
	<div>E-mail： ${item.getEmail()}</div>
	<div>
	<c:if test="${item.getFilepath() != ''}"><a href="/exee/files/${item.getFilepath()}">添付ファイル</a></c:if>
	</div>
	<div>URL： ${item.getUrl()}</div>
	<div>コメント</div>
	<div class="comment"> ${item.getComment()}</div>
	<br />

	&nbsp;&nbsp;<a href="/exee/treebbs/PostServlet?mode=edit&id=${item.id}">編集</a>
	&nbsp;&nbsp;<a href="/exee/treebbs/PostServlet?mode=delete&id=${item.id}">削除</a>

	</div>
</c:if>
	<!-- del_flagが1の場合は記事内容を非公開 -->
<c:if test="${item.del_flag == 1 }">
	<div class="article">

	<div>
 	${fn:escapeXml(item.reply_id)}への返信 &nbsp;&nbsp; ID： ${fn:escapeXml(item.id)}&nbsp;&nbsp;
	</div>
	<div>(時間) ${fn:escapeXml(item.date)}</div>
	<div>名前： このコメントは削除されました。</div>
	<div>タイトル： ----削除されました----</div>
	<div>E-mail： </div>
	<div>URL： </div>
	<div>コメント</div>
	<div class="comment"> このコメントは削除されました。</div>
	<br />

	</div>

</c:if>

<!-- コメントのツリー -->
<div class="treeOfArticle">
<ul class="leaves">

${requestScope['code'] }

</ul>
</div>

<!-- 投稿フォームエリア開始 -->

<div class="postform">
<form method="post" action="/exee/treebbs/PostServlet" enctype="multipart/form-data">
<table class="layoutForm">
<tr>
<th><label for="name">名前：</label></th>
<td><input type="text" name="name" id="name" size="20" value="${fn:escapeXml(requestScope['ckname']) }"/></td>
</tr>
<tr><th><label for="title">タイトル：</label></th>
<td><input type="text" name="title" id="title" size="40" value="Re: ${item.title }"/></td>
</tr>
<tr><th><label for="email">メールアドレス：</label></th>
<td><input type="text" name="email" id="email" size="40" value="${fn:escapeXml(requestScope['ckemail']) }"/></td>
</tr>
<tr><th><label for="url">URL：</label></th><td>
<input type="text" name="url" id="url" size="40" value="${fn:escapeXml(requestScope['ckurl']) }"/></td></tr>
<tr><th><label for="file">ファイル：</label>
</th><td>
<input type="file" name="file" />
</td>
</tr>
<tr><th><label for="comment">コメント：</label></th>
<td>
<textarea name="comment" id="comment" rows="10" cols="40">
<c:forEach var="response" items="${requestScope['response']}" varStatus="i">&gt;${response }</c:forEach>
</textarea></td>
</tr>
<tr><th><label for="password">パスワード：</label></th><td><input type="password" name="password" id="password" /></td></tr>

</table>
<!-- フォームhidden部分 -->
<input type="hidden" name="id" value="0" />
<input type="hidden" name="del_flag" value="0" />
<input type="hidden" name="reply_id" value="${item.id}" />
<input type="hidden" name="reply_pa" value="${item.reply_pa}" />
<input type="hidden" name="reply_flag" value="0" />

<!-- フォームhidden部分/ -->

<div class="submit"><input type="submit" name="mode" value="コメント" />
　<input type="submit" name="mode" value="プレビュー" /></div>
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



