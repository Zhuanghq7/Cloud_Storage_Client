import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class up extends Thread{
	private String file = null;
	private Socket s;
	private static boolean stop = false;
	private boolean isConnect = false;
	
	public static void Stop(){
		stop = true;
	}
	public up(String file){
		this.file = file;
	}
	private void out(String ss) throws UnsupportedEncodingException, IOException{
		if(isConnect){
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(),"UTF-8"));//�������߶�ʮһ���Ȱ�����ļ�����д���ǰ�
			bw.write(ss+"\n");
			bw.flush();
		}
	}
	private String in() throws UnsupportedEncodingException, IOException{
		if(isConnect){
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));
			return br.readLine();
		}
		return null;
	}
	private boolean waitGet() throws UnsupportedEncodingException, IOException{
		String temp = in();
		if(temp.equals("get"))
			return true;
		return false;
	}
	@Override
	public void run(){
		try {
			s = new Socket("127.0.0.1",1234);
			isConnect = true;
			out("up");
			waitGet();
			File f = new File(file);
			out(f.getName());
			if(!waitGet()){
				//JOptionPane.showMessageDialog(null, "��������ͬ���Ƶ��ļ����ڣ�������������"); 
			}else{
				
				Long l = f.length();
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				FileInputStream fis = new FileInputStream(f);
				dos.writeLong(l);
				dos.flush();
				if(!waitGet())
				{
					JOptionPane.showMessageDialog(null, "�����������ϴ�ʧ�ܣ�");
				}else{
					//��ʼ�ϴ�
					int length = 0;
					long sumL = 0;
					byte[] sendBytes = new byte[1024]; 
					while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0 && !stop) {  
		                sumL += length;    
		               // System.out.println("�Ѵ��䣺"+((sumL/l)*100)+"%");  
		                dos.write(sendBytes, 0, length);  
		                dos.flush(); 
		                if(stop){
		                	JOptionPane.showMessageDialog(null, "������ֹ"); 
		                	if(dos!=null){
		                		dos.close();
		                	}
		                	if(s!=null){
		                		s.close();
		                	}
			            	break;
			            }
	
		            }
					String uuid = in();
					//JOptionPane.showMessageDialog(null, "����������ȡ��"+uuid+"���μ�");
					JOptionPane.showInputDialog(MainFrame.MF,"����������ȡ�����μ�","ID",JOptionPane.PLAIN_MESSAGE,null,null,uuid); 
					out("get");
					if(dos!=null){
						dos.close();
					}
					if(s!=null){
						s.close();
					}
				}
				
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "�ϴ�ʧ��"); 
			isConnect = false;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "�ϴ�ʧ��"); 
			isConnect = false;
			e.printStackTrace();
		}
		
	}

}
