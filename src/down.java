import java.io.BufferedReader;
import java.io.BufferedWriter;
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
	@Override 
	public void run(){
		try {
			s = new Socket("127.0.0.1",1234);
			isConnect = true;
			out("down");
			waitGet();
			out(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));
			String Return = br.readLine();
			if(!Return.equals("false")){
				JOptionPane.showMessageDialog(null, "找不到该文件"); 
			}else{
			
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
