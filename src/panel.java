import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;

import javax.swing.JPanel;

public class panel extends JPanel{
	private int width = 0;
	private int height = 0;
	private int timeWindow = 0;
	public static Vector<ovel> vo = new Vector<ovel>();
	@Override
    public void paint(Graphics g) {
	    super.paint(g);
	    g.setFont(new Font("微软雅黑",Font.PLAIN,20));
	    g.drawString("YCloud Storage", 80, 55);
	    /*Graphics2D g2D = (Graphics2D) g;*/
	    timeWindow--;
	    if(timeWindow<=0){
	    	timeWindow = 60;
	    	vo.add(new ovel(0,0));
	    }
	    for(int i = 0;i<vo.size();i++){
	    	ovel o = vo.get(i);
	    	o.paint(g);
	    }
	    // 画你想要的东西
    }
}
