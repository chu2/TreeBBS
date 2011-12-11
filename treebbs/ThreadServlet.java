package treebbs;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ThreadServlet
 */
@WebServlet("/ThreadServlet")
public class ThreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ThreadServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int offset = 0;

		ArrayList<TreeDB> allTrees = TreeDB.getAllTrees(offset);
		//コメントの改行、リンク調整
		//レスの調整はListMakerで行う
		for(TreeDB cData : allTrees){
			cData.setComment(TreeBbs.commentAnchor(TreeBbs.nl2br(cData.getComment())));
		}

		ListMaker list = new ListMaker();
		StringBuilder code = list.geneThreadCode(allTrees);
		request.setAttribute("code", code);

		this.getServletContext().getRequestDispatcher("/thread.jsp").forward(request, response);


	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int offset = 0;

		if(request.getParameter("mode") == null){

			int p = TreeBbs.tryParseInt(request.getParameter("p"),1);
			offset = (p - 1) * 10;

			ArrayList<TreeDB> allTrees = TreeDB.getAllTrees(offset);


			ListMaker list = new ListMaker();
			StringBuilder code = list.geneThreadCode(allTrees);
			request.setAttribute("code", code);

			this.getServletContext().getRequestDispatcher("/thread.jsp").forward(request, response);

		}else{

			int p = TreeBbs.tryParseInt(request.getParameter("p"),1);
			offset = (p - 1) * 10;

			ArrayList<TreeDB> allTrees = TreeDB.getAllTrees(offset);


			ListMaker list = new ListMaker();
			StringBuilder code = list.geneThreadCode(allTrees);
			request.setAttribute("code", code);

			this.getServletContext().getRequestDispatcher("/thread.jsp").forward(request, response);
		}


	}

}
