package treebbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Search {

	private String word; //検索ワード
	private ArrayList<Integer> resultId;
	private int type;

	public Search(){}
	public Search(String word){
		this.word = word;
	}

	public String getWord(){
		return this.word;
	}
	public int getType(){
		return this.type;
	}
	public void setType(int type){
		//セットで数値からTitleかCommentにわける
		this.type = type;
	}
	public void setResultId(ArrayList<Integer> resultId){
		this.resultId = resultId;
	}

	public ArrayList<Integer> getResultId(){
		return this.resultId;
	}

	public String geneSql(){

		String sql = "";
		String[] words = searchWord();

		if(type == 0){
			for(int i = 0; i <= words.length-1; i++){
				sql += "(title  like '%" + words[i] + "%' ";
				if(i!=words.length-1){
					sql += " or ";
				}
			}
			sql += " ) or ";

			for(int i = 0; i <= words.length-1; i++){
				sql += "(comment  like '%" + words[i] + "%' ";
				if(i!=words.length-1){
					sql += " or ";
				}
			}
			sql += " )";

		}else if(type == 1){
			for(int i = 0; i <= words.length-1; i++){
				sql += "title  like '%" + words[i] + "%' ";
				if(i!=words.length-1){
					sql += " or ";
				}
			}
		}else if(type == 2){
			for(int i = 0; i <= words.length-1; i++){
				sql += " comment like '%" + words[i] + "%' ";
				if(i!=words.length-1){
					sql += " or ";
				}
			}
		}

		return sql;
	}

	//DBから検索結果の取得処理(10件分)
	public ArrayList<TreeDB> getAllTrees(int offset) {
		ArrayList<TreeDB> allTrees = new ArrayList<TreeDB>();

		TreeDB tree = null;
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");
			db = ds.getConnection();
			String sql = "";
			sql += "Select bbsdata.id From bbsdata inner join treedata on treedata.id = bbsdata.id where ";
			sql += geneSql();
			sql += " Order by treedata.uptime DESC";
			ps = db.prepareStatement(sql);
			rs = ps.executeQuery();
			//検索結果として出たIDを覚えておくためのリスト
			ArrayList<Integer> resultId = new ArrayList<Integer>();
			while(rs.next()){
				resultId.add(rs.getInt("id"));
			}

			//検索結果のIDをセット
			setResultId(resultId);

			sql = "";
			sql += "Select bbsdata.id, reply_pa, 1 as result From bbsdata inner join treedata on treedata.id = bbsdata.id where ";
			sql += geneSql();
			sql += " group by reply_pa Order by treedata.uptime DESC limit ?,10";
			ps = db.prepareStatement(sql);
			ps.setInt(1, offset);
			rs = ps.executeQuery();

			//取得したデータからreply_paの値を取り出して、配列にいれる
			ArrayList<Integer> paId = new ArrayList<Integer>();

			while(rs.next()){
				paId.add(rs.getInt("reply_pa"));
			}

			//取得したreply_paがIDの記事を取得

			//検索結果SQLが0件だった場合は以下を飛ばす
			if(paId.size() != 0){
				sql = "";
				sql += "Select * From bbsdata inner join treedata on treedata.id = bbsdata.id where bbsdata.id in (";
				for(int i = 0; i < paId.size(); i++){
					sql += "'" + paId.get(i) + "'";
					if(i < paId.size()-1){
						sql += ", ";
					}
				}
				sql += ") Order by treedata.uptime DESC";

				ps = db.prepareStatement(sql);
				rs = ps.executeQuery();

				//結果セットを順に読み込み
				while(rs.next()){

					tree = new TreeDB(
							rs.getInt("id"),
							rs.getString("name"),
							rs.getString("title"),
							rs.getString("email"),
							rs.getString("url"),
							rs.getString("filepath"),
							rs.getString("password"),
							rs.getString("comment"),
							rs.getTimestamp("date"),
							rs.getInt("del_flag"),
							rs.getInt("reply_id"),
							rs.getInt("reply_pa"),
							rs.getInt("reply_flag")
							);

					allTrees.add(tree);
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try{
				if(db != null){ db.close(); }
				if(ps != null){ ps.close(); }
				if(rs != null){ rs.close(); }
			}catch(Exception e){}
		}

		return allTrees;
	}


	public int getResultCount(){

		int count = 0;
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");
			db = ds.getConnection();
			String sql = "";
			sql += "Select SQL_CALC_FOUND_ROWS count(*) From bbsdata inner join treedata on treedata.id = bbsdata.id where ";
			sql += geneSql();
			sql += " group by reply_pa Order by treedata.uptime DESC ";
			ps = db.prepareStatement(sql);
			ps.executeQuery();
			ps = db.prepareStatement("SELECT FOUND_ROWS();");
			rs = ps.executeQuery();

			if(rs.next()){
				count = rs.getInt(1);
			}

		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try{
				if(db != null){ db.close(); }
				if(ps != null){ ps.close(); }
				if(rs != null){ rs.close(); }
			}catch(Exception e){}
		}

		return count;
	}

	//pagecountを取得
	public int getPageCount(int resultcount){
		return (resultcount / 10 + (resultcount % 10 != 0 ? 1:0));
	}

	public String[] searchWord(){
		String[] words;
		String key = getWord();
		key = key.replace("　", " ");
		words = key.split("[\\s]");
		return words;
	}

	//新着記事取得
	public ArrayList<TreeDB> getNewTrees(int offset) {
		ArrayList<TreeDB> allTrees = new ArrayList<TreeDB>();

		TreeDB tree = null;
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;


		try{
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");
			db = ds.getConnection();
			String sql = "";
			sql += "Select * From bbsdata inner join treedata on treedata.id = bbsdata.id ";
			sql += " where  reply_id = '0' AND uptime >= ? ";
			sql += "group by reply_pa Order by treedata.uptime DESC limit ?,10";

			ps = db.prepareStatement(sql);
			Date datenow = new Date();
			Date datepast = new Date(datenow.getTime() - 86400000);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			ps.setString(1, sdf.format(datepast));
			ps.setInt(2, offset);
			rs = ps.executeQuery();
			System.out.println(sdf.format(datenow));

			//結果セットを順に読み込み
			while(rs.next()){

				tree = new TreeDB(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("title"),
						rs.getString("email"),
						rs.getString("url"),
						rs.getString("filepath"),
						rs.getString("password"),
						rs.getString("comment"),
						rs.getTimestamp("date"),
						rs.getInt("del_flag"),
						rs.getInt("reply_id"),
						rs.getInt("reply_pa"),
						rs.getInt("reply_flag")
						);

				allTrees.add(tree);
			}

		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try{
				if(db != null){ db.close(); }
				if(ps != null){ ps.close(); }
				if(rs != null){ rs.close(); }
			}catch(Exception e){}
		}

		return allTrees;
	}


}



