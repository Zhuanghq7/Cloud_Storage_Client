import java.awt.Graphics;

public class ovel {
	private int width = 0;
	private int height = 0;
	
	public ovel(int i,int y ){
		width = i;
		height = y;
	}
	
	public void paint(Graphics g){
		g.drawOval(150-width/2, 50-height/2, width, height);
	    width+=2;
	    height+=2;
	    if(width>=300){
	    	panel.vo.remove(this);
	    }
	}
}
