package treebbs;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TreeServlet
 */

@WebServlet("/TreeServlet")
public class TreeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public TreeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//Cookieをとってきて&で分割し代入
		if(request.getCookies() != null){
			Cookie c[] = request.getCookies();
			for (Cookie cookie : c){
				if(cookie.getName().equals("name")){
					request.setAttribute("ckname", URLDecoder.decode(cookie.getValue(),"UTF-8"));
				}
				if(cookie.getName().equals("email")){
					request.setAttribute("ckemail", URLDecoder.decode(cookie.getValue(),"UTF-8"));
				}
				if(cookie.getName().equals("url")){
					request.setAttribute("ckurl", URLDecoder.decode(cookie.getValue(),"UTF-8"));
				}
			}
		}

		int offset = 0;
		//modeによって場合分け
		if(request.getParameter("mode") == null){

			//ページNoが指定しているか、指定されていたらoffsetに代入

			int p = TreeBbs.tryParseInt(request.getParameter("p"),1);
			offset = (p - 1) * 10;

			//ページcountの取得
			int pMax = TreeBbs.countPage();
			int link = TreeBbs.linkMaker(p, pMax);
			//ページリンク用のオブジェクトを作成してlist化
			PageManager pageManager = new PageManager(p);
			ArrayList<PageManager> pageList = pageManager.pageListMaker(pMax);

			//pとcountPageをjspに投げる

			request.setAttribute("p", p);
			request.setAttribute("count", pMax);
			request.setAttribute("link", link);
			request.setAttribute("pageList",pageList);

			ArrayList<TreeDB> allTrees = TreeDB.getAllTrees(offset);

			ListMaker list = new ListMaker();
			StringBuilder code = list.geneCode(allTrees);
			request.setAttribute("code", code);
			this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

				//コメント詳細ページ
		}else if(request.getParameter("mode").equals("tree")) {

				//Listmakerをインスタンス化
			ListMaker list = new ListMaker();
				//URLからIDを取得
			int id = Integer.parseInt(request.getParameter("id"));
				//IDからそのコメントデータを取得
			TreeDB cData = TreeDB.getCommentData(id);
			//生データを最初からテキストボックスに入っている分として送信
			request.setAttribute("response", TreeBbs.commentSplit(cData.getComment()));
				//コメントの改行コードを<br />タグに変換
				//コメント内のURLに自動的にアンカーをつける
			cData.setComment(TreeBbs.commentAnchor(TreeBbs.nl2br(cData.getComment())));

				//コメントデータから親コメントのデータを取得
			TreeDB cParentData = TreeDB.getCommentData(cData.getReply_pa());
				//コメントデータを元にツリーのHTMLを取得
			ArrayList<TreeDB> cParentArray = new ArrayList<TreeDB>();
			cParentArray.add(cParentData);

			StringBuilder code = list.geneCode(cParentArray);
				//requestにのせてページ遷移
			request.setAttribute("comment", cData);

			request.setAttribute("code", code);
			this.getServletContext().getRequestDispatcher("/tree.jsp").forward(request, response);

		}else if(request.getParameter("mode").equals("new")) {

			Search newTree = new Search();

			ArrayList<TreeDB> newTrees = newTree.getNewTrees(offset);

			ListMaker list = new ListMaker();
			StringBuilder code = list.geneCode(newTrees);
			if(newTrees.size() == 0){
				code.append("新着記事はありません。");
			}
			request.setAttribute("code", code);
			this.getServletContext().getRequestDispatcher("/new.jsp").forward(request, response);

		}else{
			ArrayList<TreeDB> allTrees = TreeDB.getAllTrees(offset);

			ListMaker list = new ListMaker();
			StringBuilder code = list.geneCode(allTrees);
			request.setAttribute("code", code);

			this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		int offset;

		if(request.getParameter("mode") == null){

			int p = TreeBbs.tryParseInt(request.getParameter("p"),1);
			offset = (p - 1) * 10;
			ArrayList<TreeDB> topTree = TreeDB.getAllTrees(offset);

			ListMaker list = new ListMaker();
			StringBuilder code = list.geneCode(topTree);
			request.setAttribute("code", code);

		}else if(request.getParameter("mode").equals("コメント")){

			ArrayList<TreeDB> topTree = TreeDB.getAllTrees(0);

			ListMaker list = new ListMaker();
			StringBuilder code = list.geneCode(topTree);
			request.setAttribute("code", code);

		}else{

			ArrayList<TreeDB> topTree = TreeDB.getAllTrees(0);

			ListMaker list = new ListMaker();
			StringBuilder code = list.geneCode(topTree);
			request.setAttribute("code", code);

		}
		//ページNoが指定しているか、指定されていたらoffsetに代入

		int p = TreeBbs.tryParseInt(request.getParameter("p"),1);
		//ページcountの取得
		int pMax = TreeBbs.countPage();
		int link = TreeBbs.linkMaker(p, pMax);
		//ページリンク用のオブジェクトを作成してlist化
		PageManager pageManager = new PageManager(p);
		ArrayList<PageManager> pageList = pageManager.pageListMaker(pMax);

		//pとpMaxとlinkとpageListをjspに投げる

		request.setAttribute("p", p);
		request.setAttribute("count", pMax);
		request.setAttribute("link", link);
		request.setAttribute("pageList",pageList);

		this.getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);

	}

}
