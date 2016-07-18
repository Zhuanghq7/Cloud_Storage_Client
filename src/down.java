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
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(),"UTF-8"));//不管三七二十一咱先把这个文件名字写了是吧
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
				JOptionPane.showMessageDialog(null, "找不到该文件"); 
			}else{
				DataInputStream dis = new DataInputStream(s.getInputStream());
                Long flength = dis.readLong();//从服务器获取文件大小
                out("get");
                String fName = in();
                out("get");
                
                
                File f = new File(fName);
                if(!f.exists()){
                	f.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(f);      
                byte[] inputByte = new byte[1024];     
                System.out.println("开始接收数据...");  
                double sumL = 0;
                int length;
                while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {  
                    fos.write(inputByte, 0, length);  
                    fos.flush();  
                    sumL+=length;
                    System.out.println("已传输："+sumL/(flength/100)+"%");
                    if(sumL>=flength){
                    	//System.out.println("1");
                    	break;
                    }  
                    if(stop){   
                    	JOptionPane.showMessageDialog(null, "传输终止"); 
	                	if(dis!=null){
	                		dis.close();
	                	}
	                	if(s!=null){
	                		s.close();
	                	}
	                	break;
    	            }
                }
                JOptionPane.showMessageDialog(null, "下载成功");
                fos.close();
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			stop = true;
			JOptionPane.showMessageDialog(null, "传输终止"); 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			stop = true;
			JOptionPane.showMessageDialog(null, "传输终止"); 
			e.printStackTrace();
		}
	}

}
