import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



//ok new begin
public class MainFrame {
	public static JFrame MF;
	public void CreateWindow(){
		final JFrame JF = new JFrame("YCloud");
		JF.setSize(315, 200);
		
		JF.setLayout(null);
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		JF.setLocation(screenSize.width/2-150,screenSize.height/2-100);
		JF.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JButton Up = new JButton("上传");
		JButton Down = new JButton("下载");
		JButton Rename = new JButton("重命名");
		JButton Delete = new JButton("删除");
		MF = JF;
		Up.addActionListener(new ActionListener(){
			
	    	public void actionPerformed(ActionEvent e){
				 File f = new File(".");
				 JFileChooser jfc=new JFileChooser(f.getAbsolutePath().substring(0,f.getAbsolutePath().length()-1));  
			     jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );  
			     jfc.showDialog(new JLabel(), "选择");  
			     File file=jfc.getSelectedFile();
			     if(file!=null){
				     new up(file.getAbsolutePath()).start();
				     System.out.println(jfc.getSelectedFile().getName());
			     }
	    	}
		});
		Down.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String inputValue = JOptionPane.showInputDialog(JF,"请输入文件ID","下载",JOptionPane.PLAIN_MESSAGE); 
				if(inputValue!=null){
					new down(inputValue).start();
				}
			}
		});
		Rename.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String inputValue = JOptionPane.showInputDialog(JF,"请输入文件ID与修改名（例：\"FILE FILE1\")","改名",JOptionPane.PLAIN_MESSAGE); 
				if(inputValue!=null){
					for(int i = 0;i<inputValue.length();i++){
						if(inputValue.charAt(i)==' '){
							new rename(inputValue.substring(0,i),inputValue.substring(i+1)).start();
							break;
						}
					}
				}
			}
		});
		Delete.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				String inputValue = JOptionPane.showInputDialog(JF,"请输入文件ID","删除",JOptionPane.PLAIN_MESSAGE); 
				if(inputValue!=null){
					new delete(inputValue).start();
				}
			}
		});
		JF.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				up.Stop();
				down.Stop();
				System.exit(0);
			}
		});
		JPanel jp = new JPanel();
		panel p = new panel();
		p.setLocation(0, 0);
		jp.setLocation(0, 100);
		p.setSize(300, 100);
		jp.setSize(300, 100);
		jp.add(Up);
		jp.add(Down);
		jp.add(Rename);
		jp.add(Delete); 
		JF.add(p);
		JF.add(jp);
		JF.setVisible(true);
		long begin = System.nanoTime();
		long end = 0;
		while(true){
			end = System.nanoTime();
			if((end - begin)/1000000L>= 17){
				begin = end;
				p.repaint();
			}
		}
	}
	public static void main(String[] args){
		MainFrame MF = new MainFrame();
		MF.CreateWindow();
	}

}
