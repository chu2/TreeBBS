package treebbs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void setCookie(HttpServletRequest request, HttpServletResponse response, Map<String, String> reqMap) throws ServletException, IOException {

    	String name = reqMap.get("name");
    	String email = reqMap.get("email");
    	String url = reqMap.get("url");

    	if (name != null) {
    	    try {
    	      name = URLEncoder.encode(name,"UTF-8");
    	      email = URLEncoder.encode(email,"UTF-8");
    	      url = URLEncoder.encode(url,"UTF-8");
    	    } catch (UnsupportedEncodingException ex) {
    	      ex.printStackTrace();
    	    }
    	    Cookie ckName = new Cookie("name",name);
    	    Cookie ckEmail = new Cookie("email",email);
    	    Cookie ckUrl = new Cookie("url",url);
    	    ckName.setMaxAge(60 * 60 * 24 * 1);
    	    ckEmail.setMaxAge(60 * 60 * 24 * 1);
    	    ckUrl.setMaxAge(60 * 60 * 24 * 1);
    	    response.addCookie(ckName);
    	    response.addCookie(ckEmail);
    	    response.addCookie(ckUrl);
    	  }

    }

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

		if(request.getParameter("mode") == null){
			//リクエストがない場合はpostに飛ばす
			this.getServletContext().getRequestDispatcher("/post.jsp").forward(request, response);

		}else if(request.getParameter("mode").equals("post")){

			//post.jspに飛ばすだけ
			this.getServletContext().getRequestDispatcher("/post.jsp").forward(request, response);

		}else if(request.getParameter("mode").equals("edit")){

			TreeDB cData = TreeDB.getCommentData(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("cData", cData);
			this.getServletContext().getRequestDispatcher("/edit.jsp").forward(request, response);

		}else if(request.getParameter("mode").equals("delete")){

			TreeDB cData = TreeDB.getCommentData(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("cData", cData);
			this.getServletContext().getRequestDispatcher("/delete.jsp").forward(request, response);

		}else if(request.getParameter("mode").equals("search")){


			this.getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//文字コード設定
		request.setCharacterEncoding("UTF-8");
		//
		String path = getServletContext().getRealPath("files");

		Map<String, String> reqMap = FileUpload.fileUploader(request, response, path);

		TreeDB req = new TreeDB();
		req.mode = reqMap.get("mode"); //mode

		setCookie(request, response, reqMap);

		int offset;
		//null回避
		if(req.mode == null){
			//トップに飛ばすだけ

			ArrayList<TreeDB> allTrees = TreeDB.getAllTrees(0);

			ListMaker list = new ListMaker();
			StringBuilder code = list.geneCode(allTrees);
			request.setAttribute("code", code);

			this.getServletContext().getRequestDispatcher("/treebbs/TreeServlet").forward(request, response);

			//コメント
		}else if(req.mode.equals("コメント")){

			//DBにコメントを登録してトップに飛ばす

			TreeDB tree = new TreeDB(
					TreeBbs.setHtml(reqMap.get("name")),
					TreeBbs.setHtml(reqMap.get("title")),
					TreeBbs.setHtml(reqMap.get("email")),
					TreeBbs.setHtml(reqMap.get("url")),
					reqMap.get("file"),
					DigestUtils.md5Hex(reqMap.get("password")),
					TreeBbs.setHtml(reqMap.get("comment")),
					Integer.parseInt(reqMap.get("del_flag")),
					Integer.parseInt(reqMap.get("reply_id")),
					Integer.parseInt(reqMap.get("reply_pa")),
					Integer.parseInt(reqMap.get("reply_flag"))
					);

			tree.regDB();

			ArrayList<TreeDB> topTree = TreeDB.getAllTrees(0);

			ListMaker list = new ListMaker();
			StringBuilder code = list.geneCode(topTree);
			request.setAttribute("code", code);

			this.getServletContext().getRequestDispatcher("/treebbs/TreeServlet").forward(request, response);

			//編集
		}else if(reqMap.get("mode").equals("編集")){
			//DBのコメントを修正してトップに飛ばす

			//入力されたデータを元にTreeDBをインスタンス化
			TreeDB tree = new TreeDB(
					Integer.parseInt(reqMap.get("id")),
					TreeBbs.setHtml(reqMap.get("name")),
					TreeBbs.setHtml(reqMap.get("title")),
					TreeBbs.setHtml(reqMap.get("email")),
					TreeBbs.setHtml(reqMap.get("url")),
					reqMap.get("file"),
					DigestUtils.md5Hex(reqMap.get("password")),
					TreeBbs.setHtml(reqMap.get("comment")),
					0,
					Integer.parseInt(reqMap.get("reply_id")),
					Integer.parseInt(reqMap.get("reply_pa")),
					Integer.parseInt(reqMap.get("reply_flag"))
					);

			//取得したIDから元コメントデータ取得
			TreeDB cData = TreeDB.getCommentData(tree.getId());

			//パスワードを照合して場合分け(´・ω・｀)
			if(cData.getPassword().equals(tree.getPassword())){
				//パスワードが正しかった場合、データベースを上書き
				tree.updateDB();

			}else{
				//パスワードが間違っていた場合、何もせずトップヘ戻る
			}

			ArrayList<TreeDB> allTrees = TreeDB.getAllTrees(0);

			ListMaker list = new ListMaker();
			StringBuilder code = list.geneCode(allTrees);
			request.setAttribute("code", code);
			this.getServletContext().getRequestDispatcher("/treebbs/TreeServlet").forward(request, response);

			//削除
		}else if(reqMap.get("mode").equals("削除")){
			//DBのコメントを削除してトップに飛ばす
			//入力されたデータを元にインスタンス化
			TreeDB tree = new TreeDB(
						Integer.parseInt(reqMap.get("id")),
						DigestUtils.md5Hex(reqMap.get("password"))
					);

			//取得したIDからコメントデータを取得
			TreeDB cData = TreeDB.getCommentData(tree.getId());
			//パスワードを照合して場合分け
			if(cData.getPassword().equals(tree.getPassword())){
				//パスワードが正しい場合は上書き(delete_flagを1)処理
				tree.deleteDB();

				ArrayList<TreeDB> allTrees = TreeDB.getAllTrees(0);

				ListMaker list = new ListMaker();
				StringBuilder code = list.geneCode(allTrees);
				request.setAttribute("code", code);
				this.getServletContext().getRequestDispatcher("/treebbs/TreeServlet").forward(request, response);

			}else{
				//パスワードが違う場合何もせずTOPへ戻る
				ArrayList<TreeDB> allTrees = TreeDB.getAllTrees(0);

				ListMaker list = new ListMaker();
				StringBuilder code = list.geneCode(allTrees);
				request.setAttribute("code", code);
				this.getServletContext().getRequestDispatcher("/treebbs/TreeServlet").forward(request, response);

			}

		}else if(reqMap.get("mode").equals("検索")){

			String word = TreeBbs.nullCheck(reqMap.get("word"));
			//インジェクション対策処理
			word = TreeBbs.setHtml(word);
			Search search = new Search(word);
			//検索タイプをセット
			search.setType(Integer.parseInt(reqMap.get("type")));

			int p = TreeBbs.tryParseInt(reqMap.get("p"),1);
			offset = (p - 1) * 10;

			//ページcountの取得
			int count = search.getResultCount();
			int pMax = search.getPageCount(count);
			int link = TreeBbs.linkMaker(p, pMax);
			//ページリンク用のオブジェクトを作成してlist化
			PageManager pageManager = new PageManager(p);
			ArrayList<PageManager> pageList = pageManager.pageListMaker(pMax);

			//pとcountPageをjspに投げる
			request.setAttribute("p", p);
			request.setAttribute("pMax", pMax);
			request.setAttribute("count", count);
			request.setAttribute("link", link);
			request.setAttribute("pageList",pageList);
			//wordも投げとく
			request.setAttribute("word",search.getWord());

			//検索結果取得
			ArrayList<TreeDB> allTrees = search.getAllTrees(offset);
			//検索結果該当部分のIDを送る

			ListMaker list = new ListMaker();
			StringBuilder code = list.geneCode(allTrees, search.getResultId());
			request.setAttribute("code", code);
			this.getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);

		}else if(reqMap.get("mode").equals("プレビュー")){

			TreeDB cData = new TreeDB(
					TreeBbs.setHtml(reqMap.get("name")),
					TreeBbs.setHtml(reqMap.get("title")),
					TreeBbs.setHtml(reqMap.get("email")),
					TreeBbs.setHtml(reqMap.get("url")),
					reqMap.get("file"),
					reqMap.get("password"),
					TreeBbs.setHtml(reqMap.get("comment")),
					0, //del_flag
					Integer.parseInt(reqMap.get("reply_id")),
					Integer.parseInt(reqMap.get("reply_pa")),
					Integer.parseInt(reqMap.get("reply_flag"))
					);

			cData.setComment(TreeBbs.nl2br(cData.getComment()));

			request.setAttribute("comment", cData);
			request.setAttribute("response", TreeBbs.commentSplit(TreeBbs.br2nl(cData.getComment())));
			this.getServletContext().getRequestDispatcher("/preview.jsp").forward(request, response);

		}else{

			//指定したもの以外のmodeだった場合はTOPへ戻る
			//System.out.println("その他");
			ArrayList<TreeDB> allTrees = TreeDB.getAllTrees(0);

			ListMaker list = new ListMaker();
			StringBuilder code = list.geneCode(allTrees);
			request.setAttribute("code", code);
			this.getServletContext().getRequestDispatcher("/treebbs/TreeServlet").forward(request, response);

		}

	}

}





