package com.rong.common.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class DownFileUtil {
	
	public static boolean downFile(String fileUrl , String localFileUrl){
		InputStream in = null;
		OutputStream out = null;
		try{
			URL url = new URL(fileUrl);
			in = url.openStream();
			out = new BufferedOutputStream(new FileOutputStream(localFileUrl));
			for (int b; (b = in.read()) != -1;) {
				out.write(b);
			}
			return true;
		}catch(Throwable e){
			e.printStackTrace();
			return false;
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
