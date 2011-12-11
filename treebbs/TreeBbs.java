package treebbs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeBbs {

	public static String nl2br(String comment){
		comment = comment.replace("\n", "<br />\n");
		return comment;
	}

	public static String br2nl(String comment){
		comment = comment.replace("<br />\n", "\n");
		return comment;
	}
	public static String setHtml(String comment){
		comment = comment.replace("&", "&amp;");
		comment = comment.replace("\"", "&quot;");
		comment = comment.replace("<", "&lt;");
		comment = comment.replace(">", "&gt;");
		comment = comment.replace(",", "&#44;");
		comment = comment.replace("'", "&#39");
		comment = comment.replace("\r\n", "\n");
		comment = comment.replace("\r", "\n");
		comment = comment.replace(" ", "&nbsp;");
		return comment;
	}

	public static int countPage(){

		//DBのデータ数取得
		int countData = TreeDB.countDB();
		int count = countData / 10 + (countData % 10 != 0 ? 1:0);

		return count;
	}

	//tryParseInt
	public static int tryParseInt(String str, int value){
		try{
			return Integer.parseInt(str);
		}catch(Exception e){
			return value;
		}
	}

	//nullチェック
	public static String nullCheck(String str){
		if(str == null){
			str = "";
		}
		return str;
	}

	//前、次のリンクをつけるかどうか
	public static int linkMaker(int page,int pMax){
		int mode = 0;
		if( pMax <= 1) {
			mode = 3;
		}else if(page <= 1){
			mode = 0;
		}else if(page >= pMax ){
			mode = 1;
		}else{
			mode = 2;
		}
		return mode;
	}

	//コメント内容を改行ごとに分ける
	public static String[] commentSplit(String comment){
		String[] comments;
		comments = comment.split("\n");
		return comments;
	}

	//コメント内容に"http://"また"https://"があった場合は自動リンク
	public static String commentAnchor(String comment){
		String MATCH_URL =
				  "(https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)";
		String MATCH_YOUTUBE =
				  "https?(:\\/\\/www.youtube.com[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)";
		String MATCH_NICONICO =
				  "https?(:\\/\\/www.nicovideo.jp[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)";
		Pattern pYoutube = Pattern.compile(MATCH_YOUTUBE);
		Pattern pNiconico = Pattern.compile(MATCH_NICONICO);
		Pattern p = Pattern.compile(MATCH_URL);
		Matcher mYoutube = pYoutube.matcher(comment);
		Matcher mNiconico = pNiconico.matcher(comment);
		Matcher m = p.matcher(comment);

		if(mYoutube.find()){
			//youtubeのURL部分を取り出す
			String strYoutube = mYoutube.group();
			String youtubeId = strYoutube.replaceAll("https?:\\/\\/www.youtube.com\\/watch\\?v=", "");
			String youtubeHtml = "<div class=\"center\"><iframe width=\"560\" height=\"315\" src=\"http://www.youtube.com/embed/" + youtubeId +
					"\" frameborder=\"0\" allowfullscreen></iframe></div>";
			comment = comment.replaceAll(MATCH_YOUTUBE, "<a href=\"$0\" target=\"_blank\">$0</a><br />\n" + youtubeHtml);

		}else if(mNiconico.find()){
			//niconicoのURL部分を取り出す
			String strNiconico = mNiconico.group();
			String niconicoId = strNiconico.replaceAll("https?:\\/\\/www.nicovideo.jp\\/watch\\/", "");
			String niconicoHtml = "<div class=\"center\"><script type=\"text/javascript\" src=\"http://ext.nicovideo.jp/thumb_watch/" + niconicoId
					+ "?w=490&h=307\"></script></div>\n";
			comment = comment.replaceAll(MATCH_NICONICO, "<a href=\"$0\" target=\"_blank\">$0</a><br />\n" + niconicoHtml);

		}else if(m.find()){
			comment = comment.replaceAll(MATCH_URL, "<a href=\"$0\" target=\"_blank\">$0</a><br />\n");
		}

		return comment;
	}
}










