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
				//JOptionPane.showMessageDialog(null, "云中有相同名称的文件存在，请重命名后尝试"); 
			}else{
				
				Long l = f.length();
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				FileInputStream fis = new FileInputStream(f);
				dos.writeLong(l);
				dos.flush();
				if(!waitGet())
				{
					JOptionPane.showMessageDialog(null, "服务器已满上传失败！");
				}else{
					//开始上传
					int length = 0;
					long sumL = 0;
					byte[] sendBytes = new byte[1024]; 
					while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0 && !stop) {  
		                sumL += length;    
		               // System.out.println("已传输："+((sumL/l)*100)+"%");  
		                dos.write(sendBytes, 0, length);  
		                dos.flush(); 
		                if(stop){
		                	JOptionPane.showMessageDialog(null, "传输终止"); 
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
					//JOptionPane.showMessageDialog(null, "这是您的提取码"+uuid+"请牢记");
					JOptionPane.showInputDialog(MainFrame.MF,"这是您的提取码请牢记","ID",JOptionPane.PLAIN_MESSAGE,null,null,uuid); 
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
			JOptionPane.showMessageDialog(null, "上传失败"); 
			isConnect = false;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "上传失败"); 
			isConnect = false;
			e.printStackTrace();
		}
		
	}

}
