package treebbs;

import java.util.ArrayList;
import java.util.Date;

public class ListMaker {

	StringBuilder code = new StringBuilder();

	//日時
	Date date = new Date();
	long second = date.getTime();

	//リスト作成メソッド 親コメント一覧
	//親コメント
	public StringBuilder geneCode(ArrayList<TreeDB> data) { //このdataは親コメントのみ、最大10件(1ページ分)


		for (TreeDB item : data) {
			code.append("<li>\n");
			if(item.getDel_flag() != 1){
				code.append("<a href=\"/exee/treebbs/TreeServlet?mode=tree&id=" + item.getId() + "\">");
			}
			//del_flagによってタイトルの場合分け
			if(item.getDel_flag() == 1){
				code.append("--------削除されました--------");
			}else{
				code.append(item.getTitle());
			}
			if(item.getDel_flag() != 1){
				code.append("</a>\n");
			}
			//新着記事

			long postTime = item.getDate().getTime();
			long hour24 = second - postTime;

			if(hour24 < 24*60*60*1000){
				code.append("<span class=\"new\">←NEW!!!!!!</span>");
			}

			if(item.getReply_flag() == 1){
					//親コメントからツリー取得
				ArrayList<TreeDB> treedata = TreeDB.getTree(item.getId());

					//再帰用メソッドへ投げる
				code.append(geneCodeIn(item.getId(),item,treedata));

			}
			code.append("</li>\n");
			code.append("<br />\n");
		}
		return code;
	}

	public StringBuilder geneCode(ArrayList<TreeDB> data, ArrayList<Integer> resultId) { //このdataは親コメントのみ、最大10件(1ページ分)


		for (TreeDB item : data) {
			code.append("<li>\n");

			if(resultId != null){
				for(int result : resultId){
					if(item.getId() == result){
						code.append("<span class=\"result\">\n");
					}
				}
			}
			if(item.getDel_flag() != 1){
				code.append("<a href=\"/exee/treebbs/TreeServlet?mode=tree&id=" + item.getId() + "\">");
			}
			//del_flagによってタイトルの場合分け
			if(item.getDel_flag() == 1){
				code.append("--------削除されました--------");
			}else{
				code.append(item.getTitle());
			}
			if(item.getDel_flag() != 1){
				code.append("</a>\n");
			}

			if(resultId != null){
				for(int result : resultId){
					if(item.getId() == result){
						code.append("</span>\n");
					}
				}
			}

			//新着記事

			long postTime = item.getDate().getTime();
			long hour24 = second - postTime;

			if(hour24 < 24*60*60*1000){
				code.append("<span class=\"new\">←NEW!!!!!!</span>");
			}

			if(item.getReply_flag() == 1){
					//親コメントからツリー取得
				ArrayList<TreeDB> treedata = TreeDB.getTree(item.getId());

					//再帰用メソッドへ投げる
				code.append(geneCodeIn(item.getId(),item,treedata, resultId));

			}
			code.append("</li>\n");
			code.append("<br />\n");
		}
		return code;
	}

	//リスト作成メソッド　再帰する
	//親コメントのIDから、そのツリー一覧を表示
	public StringBuilder geneCodeIn(int orig_id, TreeDB item, ArrayList<TreeDB> data) {

		StringBuilder codeIn = new StringBuilder();
		codeIn.append("<ul>\n");

		for(TreeDB leaf : data){

			//reply_idが再帰を呼んだidと同じなら、リスト追加
			if(leaf.getReply_id() == orig_id){

				codeIn.append("<li>\n");
				if(leaf.getDel_flag() != 1){
					codeIn.append("<a href=\"/exee/treebbs/TreeServlet?mode=tree&id=" + leaf.getId() + "\">");
				}
				//del_flagによってタイトルの場合分け
				if(leaf.getDel_flag() == 1){
					codeIn.append("----削除されました----");
				}else{
					codeIn.append(leaf.getTitle());
				}
				if(leaf.getDel_flag() != 1){
					codeIn.append("</a>\n");
				}

				//新着記事
				long postTime = (leaf.getDate()).getTime();
				long hour24 = second - postTime;
				if(hour24 < 24*60*60*1000){
					codeIn.append("<span class=\"new\">←NEW!!!!!!!!!!!!!!!!!!</span>\n");
				}


				//そのコメントに返信があれば更に再帰、なければ次のループへ
				if(leaf.getReply_flag() == 1){
					codeIn.append(geneCodeIn(leaf.getId(), leaf, data));
				}
				codeIn.append("</li>\n");
			}
		}

		codeIn.append("</ul>\n");

		return codeIn;
	}

	public StringBuilder geneCodeIn(int orig_id, TreeDB item, ArrayList<TreeDB> data, ArrayList<Integer> resultId) {

		StringBuilder codeIn = new StringBuilder();
		codeIn.append("<ul>\n");

		for(TreeDB leaf : data){

			//reply_idが再帰を呼んだidと同じなら、リスト追加
			if(leaf.getReply_id() == orig_id){

				codeIn.append("<li>\n");
				if(resultId != null){
					for(int result : resultId){
						if(leaf.getId() == result){
							codeIn.append("<span class=\"result\">\n");
						}
					}
				}
				if(leaf.getDel_flag() != 1){
					codeIn.append("<a href=\"/exee/treebbs/TreeServlet?mode=tree&id=" + leaf.getId() + "\">");
				}
				//del_flagによってタイトルの場合分け
				if(leaf.getDel_flag() == 1){
					codeIn.append("----削除されました----");
				}else{
					codeIn.append(leaf.getTitle());
				}
				if(leaf.getDel_flag() != 1){
					codeIn.append("</a>\n");
				}

				if(resultId != null){
					for(int result : resultId){
						if(leaf.getId() == result){
							codeIn.append("</span>\n");
						}
					}
				}
				//新着記事
				long postTime = (leaf.getDate()).getTime();
				long hour24 = second - postTime;
				if(hour24 < 24*60*60*1000){
					codeIn.append("<span class=\"new\">←NEW!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!</span>\n");
				}


				//そのコメントに返信があれば更に再帰、なければ次のループへ
				if(leaf.getReply_flag() == 1){
					codeIn.append(geneCodeIn(leaf.getId(), leaf, data, resultId));
				}
				codeIn.append("</li>\n");
			}
		}

		codeIn.append("</ul>\n");

		return codeIn;
	}

	public StringBuilder geneThreadCode(ArrayList<TreeDB> data){

		for (TreeDB item : data) {
			code.append("<div class=\"article\">");
			if(item.getDel_flag() != 1){
				code.append(item.getId() + "&nbsp;" + item.getDate() +"<br />\n");
				code.append("<a href=\"/exee/treebbs/TreeServlet?mode=tree&id=" + item.getId() + "\">\n");
				code.append(item.getTitle());
				code.append("</a>\n");
				code.append("&nbsp;by&nbsp;" + item.getName() + "\n");

				//新着記事
				long postTime = item.getDate().getTime();
				long hour24 = second - postTime;
				if(hour24 < 24*60*60*1000){
					code.append("<span class=\"new\">←NEW!!!!!!</span>\n");
				}
				code.append("<br />\n");
				code.append("<div class=\"thread_pa\">\n");
				code.append(item.getComment());
				code.append("\n</div>\n<br />\n");
				if(item.getReply_flag() == 1){
						//親コメントからツリー取得
					ArrayList<TreeDB> treedata = TreeDB.getTree(item.getId());
						//再帰用メソッドへ投げる
					code.append(geneThreadCodeIn(item.getId(),item,treedata));
				}
			//del_flagによってタイトルの場合分け
			}else{
				code.append(item.getId() + "&nbsp;" + item.getDate() +"<br />\n");
				code.append("<div class=\"thread_pa\">\n");
				code.append("--------削除されました--------<br />\n");
				code.append("</div>\n");
			}
			code.append("\n</div>\n<br />\n");
		}
		return code;
	}

public StringBuilder geneThreadCodeIn(int orig_id, TreeDB item,ArrayList<TreeDB> data){

	StringBuilder codeIn = new StringBuilder();

	for(TreeDB leaf : data){
		//先にコメントの改行、リンク調整
		leaf.setComment(TreeBbs.commentAnchor(TreeBbs.nl2br(leaf.getComment())));

		//reply_idが再帰を呼んだidと同じなら、リスト追加
		if(leaf.getReply_id() == orig_id){
			if(leaf.getDel_flag() != 1){
				codeIn.append("<div class=\"thread\">\n");
				codeIn.append(leaf.getId() + "&nbsp;" + leaf.getDate() +"<br />\n");
				codeIn.append("<a href=\"/exee/treebbs/TreeServlet?mode=tree&id=" + leaf.getId() + "\">\n");
				codeIn.append(leaf.getTitle());
				codeIn.append("</a>\n");
				codeIn.append("&nbsp;by&nbsp;" + leaf.getName() + "\n");

				//新着記事
				long postTime = (leaf.getDate()).getTime();
				long hour24 = second - postTime;
				if(hour24 < 24*60*60*1000){
					codeIn.append("<span class=\"new\">←NEW!!!!!!</span>\n");
				}
				codeIn.append("<br />\n");
				codeIn.append("<div class=\"threadin\">\n");
				codeIn.append(leaf.getComment());
				codeIn.append("\n</div></div>\n<br />\n");
				//そのコメントに返信があれば更に再帰、なければ次のループへ
				if(leaf.getReply_flag() == 1){
					codeIn.append(geneThreadCodeIn(leaf.getId(), leaf, data));
				}
			//del_flagによってタイトルの場合分け
			}else{

				codeIn.append("<div class=\"thread\">\n");
				codeIn.append(item.getId() + "&nbsp;" + item.getDate() +"<br />\n");
				codeIn.append("<div class=\"threadin\">");
				codeIn.append("----削除されました----");
				codeIn.append("</div></div>\n");
			}
		}
	}


	return codeIn;

	}


}








