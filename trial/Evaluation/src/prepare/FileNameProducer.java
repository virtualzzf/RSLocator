package prepare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNameProducer {
	
	File rootDir=null;
	String path=null,line=null;
	StringBuffer content=new StringBuffer("");
	BufferedReader in=null;
	Boolean bool;
	PrintWriter pw1=null;

	/**构造方法，读取root路径文件，路径写入root1*/
	public FileNameProducer(String root,String out1){
		rootDir=new File(root);
		try {
			pw1=new PrintWriter(new File(out1));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		showAllFiles(rootDir,pw1);
		
		pw1.close();
	}
	
	/**递归方法，读取dir中文件，对路径和内容分别写，目前为分开写版本*/
	public void showAllFiles(File dir,PrintWriter pw1){
		File[] fs=dir.listFiles();
		for(int i=0;i<fs.length;i++){
			path=fs[i].getAbsolutePath();
			Pattern pattern = Pattern.compile(".*\\.java$|.*\\.scala$|.*\\.py$|.*\\.R$|.*\\.md$|.*\\.sh$");
			Matcher matcher = pattern.matcher(path);
			
			if(matcher.matches()&&!fs[i].isDirectory()){
				pw1.println(path.substring(path.lastIndexOf("\\")+1, path.lastIndexOf(".")));
			}
			
			if(fs[i].isDirectory()){
				showAllFiles(fs[i],pw1);
			}
		}
	}

	public static void main(String[] args) {
		FileNameProducer file=new FileNameProducer("E:/研究生/项目/spark/软件备份/spark-1.5.2",
				"C:/Users/Administrator/Desktop/path.txt");
	}
}
