package treebbs;

public class Paging {

	private int page;
	private int mode;

	public Paging(int page){
		this.page = page;
	}
	//linkにサーブレット側で判定して色々ぶち込む
	//link=trueなら<a href>
	//falseなら表示だけ

	//前、次のリンクをつけるかどうか
	public int linkMaker(int pMax){
		if(page <= 1){
			mode = 0;
		}else if(page >= pMax ){
			mode = 1;
		}else{
			mode = 2;
		}
		return mode;
	}


}




