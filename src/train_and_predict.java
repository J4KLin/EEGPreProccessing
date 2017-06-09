import java.util.*;
import java.lang.Runtime;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class train_and_predict extends Frame implements ActionListener{
	JFrame frame;
	JPanel panel;
	String folder_path;
	String svm_dpath;
	Button set_path;
	Button svm_path;
	Button train;
	
	public train_and_predict(){
		folder_path = "";
		frame = new JFrame("train and predict");
		frame.setSize(400,200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Set up the JPanel and buttons
		panel = new JPanel();
		FlowLayout GUILayout = new FlowLayout();
		panel.setLayout(GUILayout);
		GUILayout.setAlignment(FlowLayout.TRAILING);
		
		set_path = new Button("set directory path for training data");
		svm_path = new Button("set directory path for SVM");
		train = new Button("train");
		set_path.addActionListener(this);
		svm_path.addActionListener(this);
		train.addActionListener(this);
		
		panel.add(set_path);
		panel.add(svm_path);
		panel.add(train);
		
		frame.add(panel);
		
		frame.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e){
		Object holder = e.getSource();
		if (holder == set_path || holder == svm_path){
			JFileChooser chose = new JFileChooser(new File(System.getProperty("user.home") + System.getProperty("file.seperator")+"Desktop"));
			chose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int result = chose.showSaveDialog(this);
			
			if (result == JFileChooser.APPROVE_OPTION){
				if (holder == set_path)
					folder_path = add_quotes(chose.getSelectedFile().getAbsolutePath());
				else if (holder == svm_path)
					svm_dpath = add_quotes(chose.getSelectedFile().getAbsolutePath());
			}
		}
		System.out.println(folder_path + " : " + svm_dpath);
		if (holder == train){
			if (svm_path.equals("") || set_path.equals(""))
				System.out.print("error: paths are not both set");
			else{
				try{
				ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "set classpath = \"C:\\Users;C;\\Users\\SVMjava\\libsvm.jar\" && cd "+svm_dpath+" && java -classpath libsvm.jar svm_train " +folder_path);
		        builder.redirectErrorStream(true);
		        Process p = builder.start();
		        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		        String line;
		        while (true) {
		            line = r.readLine();
		            if (line == null) { break; }
		            System.out.println(line);
		        }
			}catch(IOException p){
				System.out.print("erororororor");
			}
		}
	}
}
	public String add_quotes(String p){
		String w = "\""+p+"\"";
		return w;
	}
	
	public static void main(String[] args){
		new train_and_predict();
	}
}