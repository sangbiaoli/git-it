package com.sangbill.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class ResourceUtils {
	/**
	 * 读取resource目录下的文件列表
	 */
	public static void readFolder() {
		String filePath = ResourceUtils.class.getClassLoader().getResource("logs").getPath();
		File file = new File(filePath);
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i].getName());
			}
		}
		
	}
	/**
	 * 读取resource目录下的文件
	 * @return 
	 */
	public static String readFile(String fileName) {
		String filePath = ResourceUtils.class.getClassLoader().getResource(fileName).getPath();
		StringBuffer sb = new StringBuffer();
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), "UTF-8"));
			String line = null;
			while ((line = input.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
