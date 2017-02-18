/*
 * Interface.java
 *
 * Created on __DATE__, __TIME__
 */

package UI;

import javax.swing.*;

import rankingSVM.ReturnResult;
import rankingSVM.TestSetProducer;
import utils.PublicValue;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Interface extends javax.swing.JFrame {

	private String report, component;

	/** Creates new form Interface */
	public Interface() {
		initComponents();
	}
	private String[] readFileName() throws IOException{
		String[] fileNames=new String[PublicValue.srcNum];

		String path;
		BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(new File("./Data/filePath.txt")),"utf-8"));
		int i=0;
		while(true){
			path=in.readLine();
			if(path!=null){
				fileNames[i]=path;
				i++;
			}else{
				break;
			}
		}
		for(int j=0;j<10;j++){
			System.out.println(fileNames[j]);
		}
		return fileNames;
	}
	private void initComponents() {

		jScrollPane1 = new JScrollPane();
		jTextAreaReport = new JTextArea();
		jTextFieldComponent = new JTextField();
		jLabelReport = new JLabel();
		jLabelCoponent = new JLabel();
		jButtonSearch = new JButton();
		jLabelFiles = new JLabel();
		jScrollPane2 = new JScrollPane();
		jTextAreaFiles = new JTextArea();
		
		setTitle("BugLocatorDemo");
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jTextAreaReport.setColumns(15);
		jTextAreaReport.setRows(5);
		jTextAreaReport.setLineWrap(true);
		jScrollPane1.setViewportView(jTextAreaReport);

		jLabelReport.setText("bug report:");

		jLabelCoponent.setText("component");
		
		jButtonSearch.setText("search");
		jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				report=jTextAreaReport.getText().trim();
				component=jTextFieldComponent.getText().trim();
				int[] result=new int[PublicValue.srcNum];
				String[] files=new String[PublicValue.srcNum];
				try {
					files=readFileName();
				} catch (IOException e) {
					e.printStackTrace();
				}

				//读入结果写入文件
				try {
					PrintWriter writeReport=new PrintWriter(new File("./Data/raw_report.txt"));
					PrintWriter writeComponent=new PrintWriter(new File("./Data/raw_component.txt"));
					writeReport.print(report);
					writeComponent.print(component);
					writeReport.close();
					writeComponent.close();
				} catch (FileNotFoundException e1) {
					System.out.println("writer error");
				}
				//进行预处理和BM25算法
		        Runtime r = Runtime.getRuntime();
		        try {
					r.exec("python ./BM25/reportPreprocess.py").waitFor();
					System.out.println("p1 done!");
					r.exec("python ./BM25/getIdentifier.py").waitFor();
					System.out.println("p2 done!");
					r.exec("python ./BM25/main.py").waitFor();
					System.out.println("p3 done!");
				} catch (IOException e1) {
					System.out.println("Error exec!");
				}catch (InterruptedException e2) {
					System.out.println("InterruptedException!");
				}
		        //产生测试集
				TestSetProducer svm=new TestSetProducer("./Data/CodeResult.txt", "./Data/ComponentResult.txt", "./Data/IdentifierResult.txt",
						1, 2638);
				svm.writeToFile("./RankingsVM/testSet.txt");
				//推荐结果
				try {
					r.exec("./RankingSVM/svm_rank_classify.exe ./RankingSVM/testSet.txt ./RankingSVM/model.dat ./RankingSVM/result.txt").waitFor();
				} catch (IOException e3) {
					System.out.println("Error exec!");
				}catch (InterruptedException e4) {
					System.out.println("InterruptedException!");
				}
				ReturnResult a=new ReturnResult("./RankingSVM/result.txt");
				result=a.read();
				//显示结果
				for(int i=0;i<10;i++){
					jTextAreaFiles.append(files[result[i]-1]+"\n");
				}
			}
		});

		jLabelFiles.setText("potential files:");

		jTextAreaFiles.setColumns(20);
		jTextAreaFiles.setRows(5);
		jTextAreaFiles.setLineWrap(true);
		jScrollPane2.setViewportView(jTextAreaFiles);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jScrollPane2,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														376, Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						jScrollPane1,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						220,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						jLabelReport,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						78,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.LEADING)
																												.addComponent(
																														jLabelCoponent)
																												.addComponent(
																														jTextFieldComponent,
																														javax.swing.GroupLayout.DEFAULT_SIZE,
																														149,
																														Short.MAX_VALUE)))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(42,
																										42,
																										42)
																								.addComponent(
																										jButtonSearch))))
												.addComponent(jLabelFiles))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabelReport)
												.addComponent(jLabelCoponent))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jTextFieldComponent,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(37, 37,
																		37)
																.addComponent(
																		jButtonSearch))
												.addComponent(
														jScrollPane1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jLabelFiles)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane2,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										129, Short.MAX_VALUE).addContainerGap()));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Interface().setVisible(true);
			}
		});
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private JButton jButtonSearch;
	private JLabel jLabelCoponent;
	private JLabel jLabelFiles;
	private JLabel jLabelReport;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane3;
	private JTextArea jTextAreaFiles;
	private JTextArea jTextAreaReport;
	private JTextField jTextFieldComponent;
	// End of variables declaration//GEN-END:variables

}