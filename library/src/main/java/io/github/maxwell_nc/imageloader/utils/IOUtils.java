package io.github.maxwell_nc.imageloader.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * IO工具类
 */
public class IOUtils {

	/**
	 * 保存流为文件
	 * @param inputStream 输入流
	 * @param file 文件对象
	 */
	public static void writeStreamToFile(InputStream inputStream, File file) {

		// 创建用于保存图片的输出流
		BufferedOutputStream bufferedOutputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

		// 写数据
		byte[] buffer = new byte[1024];
		int len = 0;
		try {
			while ((len = inputStream.read(buffer)) != -1) {
				bufferedOutputStream.write(buffer, 0, len);
			}
			bufferedOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭流
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					bufferedOutputStream = null;
				}
			}
		}

	}

	/**
	 * 获得SD卡缓存目录中的存储File对象
	 */
	public static File getDiskCacheFile(String filename, Context context) {
		return new File(getLocalCachePath(context), filename);
	}


	/**
	 * 获取本地缓存位置
	 */
	public static String getLocalCachePath(Context context) {

		// sdcard位置
		String cachePath = null;

		// SD存在则使用SD缓存
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {

			File externalCacheDir = context.getExternalCacheDir();
			if (externalCacheDir != null) {
				cachePath = externalCacheDir.getPath();
			}
		}

		if (cachePath == null) {
			cachePath =context.getCacheDir().getPath();
		}

		cachePath += "/httpcache";

		// 如果文件夹不存在, 创建文件夹
		File dirFile = new File(cachePath);

		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		return cachePath;
	}

	/**
	 * 删除目录下的文件，非递归，遇到目录不删除
	 *
	 * @param dirFile
	 *            目录File对象
	 * @return 是否成功删除
	 */
	public static boolean removeDir(File dirFile) {

		if (!dirFile.isDirectory()) {
			return false;
		}
		File files[] = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {

			if (files[i].isDirectory()) {
				break;
			}

			if (!files[i].delete()) {
				return false;
			}
		}
		return true;
	}

}
