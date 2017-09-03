package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.filechooser.FileSystemView;

public class FileOperator {
	
	private String recordPath;
	
	private static FileOperator fileOperatorInstance = new FileOperator();
	
	public static FileOperator getFileOperator(){
		return fileOperatorInstance;
	}
	
	private FileOperator(){
		String preRecordPath = getDir();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
		String fdate = df.format(new Date());
		recordPath = preRecordPath + "\\" + fdate + ".txt";
	}
	
	private String getDir(){
		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
		String desktopPath = desktopDir.getAbsolutePath();
		String recordPath = desktopPath + "\\MyoDriving";
		File file = new File(recordPath);
		if(!file.exists()){
			file.mkdirs();
		}
		return recordPath;
	}
	
	public void saveToFile(String data){
		
		FileOutputStream outStream = null;
		String outData = MyoTool.getDate() + "> " + data +"\r\n";
		try {
			outStream = new FileOutputStream(recordPath,true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outStream.write(outData.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}
}
