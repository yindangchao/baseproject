package heyhou.com.baseproject.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

public class FileUtils {
	
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/Photo_LJ/";


	public static String saveBitmap(Bitmap bm, String picName) {
		File f = null;
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			f = new File(SDPATH, picName);
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		MediaStore.Images.Media.insertImage(BaseApplication.m_appContext.getContentResolver(),
//				f, fileName, null);
//// 最后通知图库更新
//		Uri localUri = Uri.fromFile(file);
//		Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
//		sendBroadcast(localIntent);
		return f.getAbsolutePath();
	}

	/**
	 * 保存图片到本地
	 * @param bm
	 * @param picName 文件名，.jpg
	 * @param savePath
     * @return
     */
	public static boolean saveBitmap(Bitmap bm, String picName,String savePath) {
		File f = null;
		try {
			File pathFile = new File(savePath);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			f = new File(savePath, picName);
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean saveBitmapToPNG(Bitmap bm, String picName,String savePath) {
		File f = null;
		try {
			File pathFile = new File(savePath);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			f = new File(savePath, picName);
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean saveBitmapAndInsertImage(Context context, Bitmap bm, String picName, String savePath){
		File f = null;
		try {
			File pathFile = new File(savePath);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			String fileName = picName + ".jpg";
			f = new File(savePath, fileName);
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();

			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					f.getAbsolutePath(), fileName, null);
			// 最后通知图库更新
			Uri localUri = Uri.fromFile(f);
			Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
			context.sendBroadcast(localIntent);

			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
	
	public static void delFile(String fileName){
		File file = new File(SDPATH + fileName);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); 
			else if (file.isDirectory())
				deleteDir(); 
		}
		dir.delete();
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

	/**
	 * 删除文件
	 * @param path 需要删除的文件的全路径
	 * @return 是否删除成功
	 */
	public static boolean deleteFile(String path) {
		if (TextUtils.isEmpty(path)) { return true; }

		File file = new File(path);
		if (!file.exists()) { return true; }
		if (file.isFile()) { return file.delete(); }
		if (!file.isDirectory()) { return false; }
		for (File f : file.listFiles())
		{
			if (f.isFile())
			{
				f.delete();
			}
			else if (f.isDirectory())
			{
				deleteFile(f.getAbsolutePath());
			}
		}
		return file.delete();
	}

	/**
	 * 删除文件(不论成功失败)
	 * @param path 需要删除的文件的全路径
	 * @return 是否删除成功
	 */
	public static void deleteFileAutarchy(final String path) {
		if (TextUtils.isEmpty(path)) { return ; }

		try {
			File file = new File(path);
			if (!file.exists()) {
				return;
			}
			if (file.isFile()) {
				file.delete();
			}
			if (!file.isDirectory()) {
				return;
			}
			for (File f : file.listFiles()) {
				if (f.isFile()) {
					f.delete();
				} else if (f.isDirectory()) {
					deleteFileAutarchy(f.getAbsolutePath());
				}
			}
			file.delete();
		} catch (Exception e) {

		}
	}

	/**
	 * 删除某个文件夹的文件，过滤掉指定路径（即指定路径的文件不删除，其余全部删除）
	 * @param folderPath 文件夹路径
	 * @param filePaths 需要过滤的文件路径（非文件名）
     */
	public static void deleteFiltrationFile(String folderPath,ArrayList<String> filePaths){
		File[] files = new File(folderPath).listFiles();
		file:for(int i=0;i<files.length;i++){
			for(int j=0;j<filePaths.size();j++){
				String localPath = files[i].getAbsolutePath();
				if(localPath.equals(filePaths.get(j))){
					continue file;
				}
				if(j == filePaths.size()-1){
					FileUtils.deleteFile(localPath);
				}
			}
		}
	}

	public static String getUriRealFilePath( final Context context, final Uri uri ) {
		if ( null == uri ) return null;
		final String scheme = uri.getScheme();
		String data = null;
		if ( scheme == null )
			data = uri.getPath();
		else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
			data = uri.getPath();
		} else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
			Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
			if ( null != cursor ) {
				if ( cursor.moveToFirst() ) {
					int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
					if ( index > -1 ) {
						data = cursor.getString( index );
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	public static boolean copyFile(String oldFilePath, String newPath,String newName) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File newFilePath = new File(newPath);
			if(!newFilePath.exists()){
				newFilePath.mkdirs();
			}
			File oldfile = new File(oldFilePath);
			if (oldfile.exists()) { //文件存在时
				InputStream inStream = new FileInputStream(oldFilePath); //读入原文件
				FileOutputStream fs = new FileOutputStream(newPath + File.separator + newName);
				byte[] buffer = new byte[1444];
				int length;
				while ( (byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; //字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}
		return true;
	}

	/**
	 * 获取文件的mimetype
	 * @param filePath
	 * @return
     */
	public static String getMimeType(String filePath) {
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		String mime = "text/plain";
		if (filePath != null) {
			try {
				mmr.setDataSource(filePath);
				mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
			} catch (IllegalStateException e) {
				return mime;
			} catch (IllegalArgumentException e) {
				return mime;
			} catch (RuntimeException e) {
				return mime;
			}
		}
		return mime;
	}
	public static String getLocalFileFormJsonString(String filePath){
		StringBuilder builder = new StringBuilder();
		try {
			File file = new File(filePath);
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fileInputStream);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while((line = br.readLine()) != null){
				builder.append(line);
			}
			br.close();
			isr.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	public static long getFileSize(String filePath){
		File file = new File(filePath);
		if(!file.exists()){
			return 0;
		}
		return file.length();
	}


	public static String getVideoPathFromUri(Uri url){
		// MediaStore.Video.Thumbnails.DATA:视频缩略图的文件路径
		String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA,
				MediaStore.Video.Thumbnails.VIDEO_ID};
		// MediaStore.Video.Media.DATA：视频文件路径；
		// MediaStore.Video.Media.DISPLAY_NAME : 视频文件名，如 testVideo.mp4
		// MediaStore.Video.Media.TITLE: 视频标题 : testVideo
		String[] mediaColumns = {MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
				MediaStore.Video.Media.MIME_TYPE,
				MediaStore.Video.Media.DISPLAY_NAME};

		String path = "";

		Cursor cursor = AppUtil.getApplicationContext().getContentResolver().query(url,
				mediaColumns, null, null, null);
		if (cursor == null) {
			return path;
		}

		if (cursor.moveToFirst()) {
//			int id = cursor.getInt(cursor
//					.getColumnIndex(MediaStore.Video.Media._ID));
			path = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));

		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return path;
	}

	public static boolean object2File(Object object,String filePath){
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			File distFile = new File(filePath);
			if (!distFile.getParentFile().exists()){
				distFile.getParentFile().mkdirs();
			}
			fileOutputStream = new FileOutputStream(distFile);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(object);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				if(fileOutputStream != null){
					fileOutputStream.close();
				}
				if(objectOutputStream != null){
					objectOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static Object file2Object(String filePath){
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		Object object = null;
		try {
			fileInputStream = new FileInputStream(new File(filePath));
			objectInputStream = new ObjectInputStream(fileInputStream);
			object = objectInputStream.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			try {
				if(fileInputStream != null){
					fileInputStream.close();
				}
				if(objectInputStream != null){
					objectInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return object;
	}


	/**
	 * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
	 *
	 * @param res            原字符串
	 * @param filePath 文件路径
	 * @return 成功标记
	 */
	public static boolean string2File(String res, String filePath) {
		boolean flag = true;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			File distFile = new File(filePath);
			if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
			bufferedReader = new BufferedReader(new StringReader(res));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024];         //字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
			return flag;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 文本文件转换为指定编码的字符串
	 *
	 * @param file         文本文件
	 * @param encoding 编码类型
	 * @return 转换后的字符串
	 * @throws IOException
	 */
	public static String file2String(File file) {
		InputStreamReader reader = null;
		StringWriter writer = new StringWriter();
		try {
//			if (encoding == null || "".equals(encoding.trim())) {
//				reader = new InputStreamReader(new FileInputStream(file), encoding);
//			} else {
				reader = new InputStreamReader(new FileInputStream(file));
//			}
			//将输入流写入输出流
			char[] buffer = new char[1024];
			int n = 0;
			while (-1 != (n = reader.read(buffer))) {
				writer.write(buffer, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		//返回转换结果
		if (writer != null)
			return writer.toString();
		else return null;
	}

}
