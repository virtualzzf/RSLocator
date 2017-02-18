package utils;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dataStructure.FeatureMatrix;
import dataStructure.TargetFile;

/**工具类之输出*/
public class Print{

	/**int一维数组的输出*/
	public static void print(int[] a){
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]+" ");
		}
		System.out.println();
	}
	/**double一维数组的输出*/
	public static void print(double[] a){
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]+" ");
		}
		System.out.println();
	}
	/**int二维数组的输出*/
	public static void print(int[][] a){
		for(int i=0;i<a.length;i++){	
				print(a[i]);
		}			
		System.out.println();
	}
	/**double二维数组的输出*/
	public static void print(double[][] a){
		for(int i=0;i<a.length;i++){
				print(a[i]);
		}
		System.out.println();
	}
	/**特殊类型元素的输出*/
	/**targetFile数组的输出*/
	public static void print(TargetFile[] a){
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]);
		}
		System.out.println();
	}
	/**featureMatrix二维数组的输出*/
	public static void print(FeatureMatrix[][] a){
		for(int i=0;i<a.length;i++){
				for(int j=0;j<a[0].length;j++){
					System.out.print(new DecimalFormat("0.0000000").format(a[i][j].getCodeScore())+","
				              +new DecimalFormat("0.0000000").format(a[i][j].getComponentScore())+","
							  +new DecimalFormat("0.0000000").format(a[i][j].getIdentifierScore())+"\t");
				}
				System.out.println();
		}
	}
	
	/**List<Integer>数组的输出*/
	public static void print(List<Integer>[] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[i].size();j++){
				System.out.print(a[i].get(j)+" ");
			}
			System.out.println();
		}
	}
	/**Map的输出*/
	public static void print(Map<String, String> m){
			for(Map.Entry<String, String> entry:m.entrySet()){    
			     System.out.print(entry.getKey()+":"+entry.getValue()+"\t");    
			}   
	}
	/**Map数组的输出*/
	public static void print(Map<String, String>[] m){
		for(int i=0;i<m.length;i++){
			System.out.print("Map["+i+"]\t");
			print(m[i]);
			System.out.println();
		}
	}
	/**Set的输出*/
	public static void print(Set s){
		for(Iterator iterator = s.iterator();iterator.hasNext();){  
            System.out.print(iterator.next()+" ");  
        }  
	}
	/**Set数组的输出*/
	public static void print(Set[] s){
		for(int i=0;i<s.length;i++){
			System.out.print("Set["+i+"]\t");
			print(s[i]);
			System.out.println();
		}
	}
}
