package treebbs;

import java.util.ArrayList;

public class PageManager {

	private int page;
	private boolean link;

	public PageManager(int page){
		this.page = page;
	}

	public PageManager(int page, boolean link){
		this.page = page;
		this.link = link;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public boolean isLink() {
		return link;
	}

	public void setLink(boolean link) {
		this.link = link;
	}

	public ArrayList<PageManager> pageListMaker(int pMax){

		ArrayList<PageManager> pageList = new ArrayList<PageManager>();

		if(pMax <= 5){

			for(int i = 0; i < pMax; i++){
				if(i+1 == this.page){
					pageList.add(new PageManager(i+1,false));
				}else{
					pageList.add(new PageManager(i+1,true));
				}
			}
		}else{

			if(this.page <= 1){
				//1,2,3,...pMax
				pageList.add(new PageManager(1,false));
				pageList.add(new PageManager(2,true));
				pageList.add(new PageManager(3, true));
				pageList.add(new PageManager(0, true));
				pageList.add(new PageManager(pMax, true));

			}else if(this.page == 2){

				pageList.add(new PageManager(1, true));
				pageList.add(new PageManager(2, false));
				pageList.add(new PageManager(3, true));
				pageList.add(new PageManager(0, true));
				pageList.add(new PageManager(pMax, true));

			}else if(this.page == 3){

				pageList.add(new PageManager(1, true));
				pageList.add(new PageManager(2, true));
				pageList.add(new PageManager(3, false));
				pageList.add(new PageManager(4, true));
				pageList.add(new PageManager(0, true));
				pageList.add(new PageManager(pMax, true));

			}else if(this.page == pMax-2){
				//1,...,pMax-2,pMax-1,pMax
				pageList.add(new PageManager(1, true));
				pageList.add(new PageManager(0, true));
				pageList.add(new PageManager(this.page-1, true));
				pageList.add(new PageManager(this.page, false));
				pageList.add(new PageManager(pMax-1, true));
				pageList.add(new PageManager(pMax, true));

			}else if(this.page == pMax-1){
				//1,...,pMax-2,pMax-1,pMax
				pageList.add(new PageManager(1, true));
				pageList.add(new PageManager(0, true));
				pageList.add(new PageManager(this.page-1, true));
				pageList.add(new PageManager(this.page, false));
				pageList.add(new PageManager(pMax, true));

			}else if(this.page == pMax){

				pageList.add(new PageManager(1, true));
				pageList.add(new PageManager(0, true));
				pageList.add(new PageManager(pMax-2, true));
				pageList.add(new PageManager(pMax-1, true));
				pageList.add(new PageManager(pMax, false));

			}else {
				//1,...p-1,p,p+1,....,pMax

				pageList.add(new PageManager(1, true));
				pageList.add(new PageManager(0, true));
				pageList.add(new PageManager(this.page-1, true));
				pageList.add(new PageManager(this.page, false));
				pageList.add(new PageManager(this.page+1, true));
				pageList.add(new PageManager(0, true));
				pageList.add(new PageManager(pMax, false));

			}
		}

		return pageList;
	}

}




