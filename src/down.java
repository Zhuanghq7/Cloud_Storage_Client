import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class down extends Thread{
	private String file = null;
	private Socket s;
	private boolean isConnect = false;
	private static boolean stop = false;
	public down(String s){
		file = s;
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
	public static void Stop(){
		stop = true;
	}
	@Override 
	public void run(){
		try {
			s = new Socket("127.0.0.1",1234);
			isConnect = true;
			out("down");
			waitGet();
			out(file);
			if(!waitGet()){
				JOptionPane.showMessageDialog(null, "�Ҳ������ļ�"); 
			}else{
				DataInputStream dis = new DataInputStream(s.getInputStream());
                Long flength = dis.readLong();//�ӷ�������ȡ�ļ���С
                out("get");
                String fName = in();
                out("get");
                
                
                File f = new File(fName);
                if(!f.exists()){
                	f.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(f);      
                byte[] inputByte = new byte[1024];     
                System.out.println("��ʼ��������...");  
                double sumL = 0;
                int length;
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {  
                    fos.write(inputByte, 0, length);  
                    fos.flush();  
                    sumL+=length;
                    System.out.println("�Ѵ��䣺"+sumL/(flength/100)+"%");
                    if(sumL>=flength){
                    	//System.out.println("1");
                    	break;
                    }  
                    if(stop){   
                    	JOptionPane.showMessageDialog(null, "������ֹ"); 
	                	if(dis!=null){
	                		dis.close();
	                	}
	                	if(s!=null){
	                		s.close();
	                	}
	                	break;
    	            }
                }
                JOptionPane.showMessageDialog(null, "���سɹ�");
                fos.close();
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			stop = true;
			JOptionPane.showMessageDialog(null, "������ֹ"); 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			stop = true;
			JOptionPane.showMessageDialog(null, "������ֹ"); 
			e.printStackTrace();
		}
	}

}
