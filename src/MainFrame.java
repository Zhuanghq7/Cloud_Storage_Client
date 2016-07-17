import java.awt.Dimension;
import java.awt.FlowLayout;
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

//ok new begin
public class MainFrame {
	public void CreateWindow(){
		final JFrame JF = new JFrame("YCloud");
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
		JButton Up = new JButton("�ϴ�");
		JButton Down = new JButton("����");
		JButton Rename = new JButton("������");
		JButton Delete = new JButton("ɾ��");
		Up.addActionListener(new ActionListener(){
			
	    	public void actionPerformed(ActionEvent e){
				 File f = new File(".");
				 JFileChooser jfc=new JFileChooser(f.getAbsolutePath().substring(0,f.getAbsolutePath().length()-1));  
			     jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );  
			     jfc.showDialog(new JLabel(), "ѡ��");  
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
				String inputValue = JOptionPane.showInputDialog(JF,"�������ļ���","����",JOptionPane.PLAIN_MESSAGE); 
				if(inputValue!=null){
					new down(inputValue).start();
				}
			}
		});
		Rename.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String inputValue = JOptionPane.showInputDialog(JF,"�������ļ������޸���������\"FILE FILE1\")","����",JOptionPane.PLAIN_MESSAGE); 
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
				String inputValue = JOptionPane.showInputDialog(JF,"�������ļ���","ɾ��",JOptionPane.PLAIN_MESSAGE); 
				if(inputValue!=null){
					new delete(inputValue).start();
				}
			}
		});
		JF.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0) {
				up.Stop();
				dowm.Stop();
				delete.Stop();
				rename.Stop();
				System.exit(0);
			}
		});
		JF.add(Up);
		JF.add(Down);
		JF.add(Rename);
		JF.add(Delete);
	}
	public static void main(String[] args){
		MainFrame MF = new MainFrame();
		MF.CreateWindow();
	}

}
