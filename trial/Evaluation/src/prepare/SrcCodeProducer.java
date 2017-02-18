package prepare;

import java.io.*;
import java.util.regex.*;

/**该类用于产生源代码文件，包括文件名和源代码，每个源代码文件为一行*/
public class SrcCodeProducer {

	File rootDir=null;
	String fileName=null,line=null;
	StringBuffer content=new StringBuffer("");
	BufferedReader in=null;
	Boolean bool;
	PrintWriter pw=null;

	/**构造方法，读取root路径文件，路径写入root1，内容写入root2*/
	public SrcCodeProducer(String root,String out){
		rootDir=new File(root);
		try {
			pw=new PrintWriter(new File(out));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		showAllFiles(rootDir,pw);

		pw.close();
	}
	
	/**递归方法，读取dir中文件，对路径和内容分别写，目前为分开写版本*/
	public void showAllFiles(File dir,PrintWriter pw){
		File[] fs=dir.listFiles();
		for(int i=0;i<fs.length;i++){
			fileName=fs[i].getAbsolutePath();
			Pattern pattern = Pattern.compile(".*\\.java$|.*\\.scala$|.*\\.py$|.*\\.R$|.*\\.md$|.*\\.sh$");
			Matcher matcher = pattern.matcher(fileName);
			
			if(matcher.matches()&&!fs[i].isDirectory()){
				fileName=fileName.substring(fileName.lastIndexOf("\\")+1, fileName.lastIndexOf("."));
				
				try {
					content.delete(0, content.length());
					content.append(fileName+" ");
					in=new BufferedReader(new FileReader(fs[i]));
					while((line=in.readLine())!=null){
			            content.append(line+" ");
			        }
			        in.close();
			        pw.println(content);
				} catch (FileNotFoundException e1) {
					System.out.println("FileNotFoundException");
				} catch(IOException e2){
					System.out.println("IOException");
				}
			}
			
			if(fs[i].isDirectory()){
				showAllFiles(fs[i],pw);
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		SrcCodeProducer src=new SrcCodeProducer("E:/研究生/项目/spark/软件备份/spark-1.5.2",
				"C:/Users/Administrator/Desktop/SourceCode.txt");
	}
}
