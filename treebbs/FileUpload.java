package treebbs;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUpload {


	public static Map<String,String> fileUploader(HttpServletRequest request, HttpServletResponse response, String path){

		Map<String, String> reqMap = new HashMap<String, String>();
		String filepath = "";
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		factory.setSizeThreshold(1024);
		upload.setSizeMax(1024000000);
		upload.setHeaderEncoding("UTF-8");

		List list = null;
		try{
			//FileItemオブジェクトを取得し、Listオブジェクトに入れる
			list = upload.parseRequest(request);
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				FileItem fItem = (FileItem)iterator.next();
				reqMap.put(fItem.getFieldName(), fItem.getString("UTF-8"));
				//ファイルデータの場合
				if(!(fItem.isFormField())){
					//ファイルデータのファイル名を読み込む
					String fileName = fItem.getName();
					if((fileName != null) && (!fileName.equals(""))){
						//path名を除くファイル名のみを取得
						fileName=(new File(fileName)).getName();
						//ファイル名は現在時刻＋ファイル名
						Date date = new Date();
						filepath = date.getTime() + fileName;
						reqMap.put("file", filepath);
						fItem.write(new File(path + "/" + filepath));
					}
				}
			}

		}catch(FileUploadException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return reqMap;
	}
}
