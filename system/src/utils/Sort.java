package utils;

import dataStructure.TargetFile;

/**工具类之排序*/
public class Sort {
	/**一维int数组的冒泡排序，降序*/
	public static void bubble(int[] a){
		int length=a.length,temp;
		for(int i=0;i<length;i++){
			for(int j=0;j<length-i-1;j++){
				if(a[j]<a[j+1]){
					temp=a[j];
					a[j]=a[j+1];
					a[j+1]=temp;
				}
			}
		}
	}
	/**一维double数组的冒泡排序，降序*/
	public static void bubble(double[] a){
		int length=a.length;
		double temp;
		for(int i=0;i<length;i++){
			for(int j=0;j<length-i-1;j++){
				if(a[j]<a[j+1]){
					temp=a[j];
					a[j]=a[j+1];
					a[j+1]=temp;
				}
			}
		}
	}
	/**一维TargetFile数组的冒泡排序，按行降序*/
	public static void bubble(TargetFile[] a){
		int length=a.length;
		TargetFile temp;
		for(int i=0;i<length;i++){
			for(int j=0;j<length-i-1;j++){
				if(a[j].getScore()<a[j+1].getScore()){
					temp=a[j];
					a[j]=a[j+1];
					a[j+1]=temp;
				}
			}
		}
	}
	
	/**二维int数组的冒泡排序，降序*/
	public static void bubble(int[][] a ){
		int row=a.length;
		for(int i=0;i<row;i++){
			bubble(a[i]);
		}
	}
	/**按行二维double数组的冒泡排序，降序*/
	public static void bubble(double[][] a ){
		int row=a.length;
		for(int i=0;i<row;i++){
			bubble(a[i]);
		}
	}
	/**按行targetFile数组的冒泡排序，降序*/
	public static void bubbleByRow(TargetFile[][] a ){
		int row=a.length;
		for(int i=0;i<row;i++){
			bubble(a[i]);
		}
	}
	/**按列targetFile数组的冒泡排序，降序*/
	public static void bubbleByCol(TargetFile[][] a ){
		int row=a.length;
		for(int i=0;i<row;i++){
			bubble(a[i]);
		}
	}
}
