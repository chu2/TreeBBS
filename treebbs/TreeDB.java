package treebbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class TreeDB {

	private int id;
	private String name;
	private String title;
	private String email;
	private String url;
	private String filepath;
	private String password;
	private String comment;
	private Date date;
	private int del_flag;

	private int reply_flag;
	private int reply_id;
	private int reply_pa;
	private Date uptime;

	//mode分け用
	String mode;
	//検索のtype分け用
	int type;

	public TreeDB(){}
	//データを取得時に初期化するコンストラクタ
	public TreeDB(int id, String name, String title, String email, String url, String filepath, String password, String comment, Date date, int del_flag, int reply_id, int reply_pa, int reply_flag){
		this.id = id;
		this.name = name;
		this.title = title;
		this.email=email;
		this.url=url;
		this.filepath = filepath;
		this.password=password;
		this.comment=comment;
		this.date = date;
		this.del_flag=del_flag;
		this.reply_id= reply_id;
		this.reply_pa= reply_pa;
		this.reply_flag= reply_flag;
	}
	//登録時にデータを初期化するコンストラクタ
	public TreeDB(String name, String title, String email, String url, String filepath, String password, String comment, int del_flag, int reply_id, int reply_pa, int reply_flag){
		this.name = name;
		this.title = title;
		this.email=email;
		this.url=url;
		this.filepath = filepath;
		this.password=password;
		this.comment=comment;
		this.del_flag=del_flag;
		this.reply_id= reply_id;
		this.reply_pa= reply_pa;
		this.reply_flag= reply_flag;
	}

	//編集時にデータを初期化するコンストラクタ
	public TreeDB(int id, String name, String title, String email, String url, String filepath, String password, String comment, int del_flag, int reply_id, int reply_pa, int reply_flag){
		this.id = id;
		this.name = name;
		this.title = title;
		this.email=email;
		this.url=url;
		this.password=password;
		this.comment=comment;
		this.del_flag=del_flag;
		this.reply_id= reply_id;
		this.reply_pa= reply_pa;
		this.reply_flag= reply_flag;
	}

	//削除時にデータを初期化するコンストラクタ
	public TreeDB(int id, String password){
		this.id = id;
		this.password=password;
	}

	public int getId() {
		return id;
	}

	//void setId(int id) {
	//	this.id = id;
	//}

	public String getName() {
		return name;
	}

	//void setName(String name) {
	//	this.name = name;
	//}

	public String getTitle() {
		return title;
	}

	//void setTitle(String title) {
	//	this.title = title;
	//}

	public String getEmail() {
		return email;
	}

	//void setEmail(String email) {
	//	this.email = email;
	//}

	public String getUrl() {
		return url;
	}

	//void setUrl(String url) {
	//	this.url = url;
	//}

	public String getFilepath(){
		return filepath;
	}

	public String getPassword() {
		return password;
	}

	//void setPassword(String password) {
	//	this.password = password;
	//}

	public String getComment() {
		return comment;
	}

	void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	//void setDate(String date) {
	//	this.date = date;
	//}

	public int getDel_flag() {
		return del_flag;
	}

	//void setDel_flag(int del_flag) {
	//	this.del_flag = del_flag;
	//}

	public int getReply_flag() {
		return reply_flag;
	}

	//void setReply_flag(int reply_flag) {
	//	this.reply_flag = reply_flag;
	//}

	public int getReply_id() {
		return reply_id;
	}

	//void setReply_id(int reply_id) {
	//	this.reply_id = reply_id;
	//}

	public int getReply_pa() {
		return reply_pa;
	}

	//void setReply_pa(int reply_pa) {
	//	this.reply_pa = reply_pa;
	//}

	public Date getUptime() {
		return uptime;
	}

	//DBからの取得処理
	//最初の10件の取得
	public static ArrayList<TreeDB> getAllTrees(int offset) {
		ArrayList<TreeDB> allTrees = new ArrayList<TreeDB>();

		TreeDB tree = null;

		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");
			db = ds.getConnection();
			ps = db.prepareStatement("Select * From treedata inner join bbsdata on treedata.id = bbsdata.id " +
					"where reply_id = 0 Order by treedata.uptime DESC limit " + offset + ",10");
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

	//DBからの取得処理
	//親コメントのIDから、1ツリーを取得
	public static ArrayList<TreeDB> getTree(int id) {
		ArrayList<TreeDB> allTrees = new ArrayList<TreeDB>();

		TreeDB tree = null;
		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");
			db = ds.getConnection();
			ps = db.prepareStatement("Select * From treedata inner join bbsdata on treedata.id = bbsdata.id " +
					"where treedata.reply_pa = " + id +" Order by treedata.id");
			rs = ps.executeQuery();

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
		}finally{
			try{
				if(db != null){db.close();}
				if(ps != null){ps.close();}
				if(rs != null){rs.close();}
			}catch(Exception e){}

		}
		return allTrees;

	}

	//DBから取得処理
	//IDからコメントデータを取得するメソッド
	public static TreeDB getCommentData(int id){

		TreeDB cData = null;

		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");
			db = ds.getConnection();
			ps = db.prepareStatement("select * from treedata inner join bbsdata on treedata.id = bbsdata.id" +
					" where treedata.id = " + id);

			rs = ps.executeQuery();

			if(rs.next()){

				cData = new TreeDB(
						id,
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
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(db != null){db.close();}
				if(ps != null){ps.close();}
				if(rs != null){rs.close();}
			}catch(Exception e){}
		}

		return cData;

	}

	//DBへの更新処理
	//コメントを登録、2つのテーブルに登録する
	public void regDB() {

		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int reg_id = 0;

		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");
			db = ds.getConnection();
			ps = db.prepareStatement("INSERT into bbsdata (name, title, email, url, filepath, comment, password) VALUES (?, ?, ?, ?, ?, ?, ?);");
			ps.setString(1, getName());
			ps.setString(2, getTitle());
			ps.setString(3, getEmail());
			ps.setString(4, getUrl());
			ps.setString(5, getFilepath());
			ps.setString(6, getComment());
			ps.setString(7, getPassword());

			ps.executeUpdate();

		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				//bbsdataに入ったidを取得
				ps = db.prepareStatement("select last_insert_id() FROM bbsdata");
				rs = ps.executeQuery();

				if(rs.next()){
					reg_id = rs.getInt(1);
				}

			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try{
					ps = db.prepareStatement("INSERT into treedata (id, reply_id, reply_flag, reply_pa) VALUES (?, ?, ?, ?);");

					ps.setInt(1, reg_id);
					ps.setInt(2, getReply_id());
					ps.setInt(3, getReply_flag());
					//親コメントかどうかでreply_paの値を場合分け
					//親コメントだったら自分のIDを入れる
					if(getReply_pa() == 0){
						ps.setInt(4, reg_id);
					}else{
						ps.setInt(4, getReply_pa());
					}

					ps.executeUpdate();
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try{
						if(ps != null){ps.close();}
						if(db != null){db.close();}
					}catch(Exception e){}
				}
			}
		}

		//リプライ(返信)だった場合

		if(getReply_id() != 0){
			//リプライ元のIDを引数にupdateDBを呼ぶ
			updateFlag(getReply_id());
		}

		//update時間を更新する。
		updateTimestamp();

	}

	//update時間を更新するメソッド
	public void updateTimestamp(){

		Connection db = null;
		PreparedStatement ps = null;


		try{
		     Context context = new InitialContext();
		     DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");
		     db = ds.getConnection();
		     ps = db.prepareStatement("update treedata set uptime = CURRENT_TIMESTAMP where id = ?");
		     ps.setInt(1, getReply_pa());
		     ps.executeUpdate();

		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(db != null){db.close();}
				if(ps != null){ps.close();}
			}catch(Exception e){}

		}
	}

	//コメントを編集しデータベースを更新する
	public void updateDB() {

		Connection db = null;
		PreparedStatement ps = null;

		try{
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");
			db = ds.getConnection();
			ps = db.prepareStatement("UPDATE  bbsdata set name = ?, title = ?, email = ?, url = ?, filepath = ?, comment = ? where id = ?");
			ps.setString(1, getName());
			ps.setString(2, getTitle());
			ps.setString(3, getEmail());
			ps.setString(4, getUrl());
			ps.setString(5, getFilepath());
			ps.setString(6, getComment());
			ps.setInt(7, getId());

			ps.executeUpdate();

		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(db != null){db.close();}
				if(ps != null){ps.close();}
			}catch(Exception e){}
		}
	}

	//データベースに登録後の関連フィールドアップデート
	//新規登録後にtreedata
	//返信された側のフラグやnum_replyの更新をする
	public void updateFlag(int id) {

		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");
			db = ds.getConnection();
			ps = db.prepareStatement("UPDATE  treedata set reply_flag = '1' where id = ?");
			ps.setInt(1, id);
			ps.executeUpdate();

		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(db != null){db.close();}
				if(ps != null){ps.close();}
				if(rs != null){rs.close();}
			}catch(Exception e){}
		}
	}

	//コメントデータをDBから削除

	public void deleteDB(){

		Connection db = null;
		PreparedStatement ps = null;

		try{
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");

			db = ds.getConnection();
			ps = db.prepareStatement("UPDATE bbsdata set del_flag = ? where id = ?");
			ps.setInt(1, 1);
			ps.setInt(2, getId());
			ps.executeUpdate();

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(db != null){db.close();}
				if(ps != null){ps.close();}
			}catch(Exception e){}
		}
	}


	public static int countDB(){
		int count = 0;

		Connection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try{
			Context context = new InitialContext();
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/test");
			db = ds.getConnection();
			ps = db.prepareStatement("Select count(*) from treedata where reply_id = '0'");
			rs = ps.executeQuery();

			if(rs.next()){
				count = rs.getInt(1);
			}

		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(db != null){db.close();}
				if(ps != null){ps.close();}
				if(rs != null){rs.close();}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return count;

	}

}















