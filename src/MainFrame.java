import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

//ok new begin
public class MainFrame {
	public void CreateWindow(){
		JFrame JF = new JFrame("YCloud");
		JF.setSize(300, 200);
		JF.setVisible(true);
		JF.setLayout(new FlowLayout());
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		JF.setLocation(screenSize.width/2-150,screenSize.height/2-100);
		JF.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JF.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		JButton Up = new JButton("up");
		Up.addActionListener(new ActionListener(){
			@Override
	    	public void actionPerformed(ActionEvent e){
				 File f = new File(".");
				 System.out.println(f.getAbsolutePath());
				 JFileChooser jfc=new JFileChooser(f.getAbsolutePath().substring(0,f.getAbsolutePath().length()-1));  
			     jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
			     jfc.showDialog(new JLabel(), "选择");  
			     File file=jfc.getSelectedFile();
			     if(file!=null){
				     if(file.isDirectory()){  
				         System.out.println("文件夹:"+file.getAbsolutePath());  
				     }else if(file.isFile()){  
				         System.out.println("文件:"+file.getAbsolutePath());  
				     }  
				     System.out.println(jfc.getSelectedFile().getName());
			     }
	    	}
		});
		JF.add(Up);
	}
	public static void main(String[] args){
		MainFrame MF = new MainFrame();
		MF.CreateWindow();
	}

}
