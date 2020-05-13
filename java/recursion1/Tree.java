import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

// https://github.com/davidliutest/fun-projects

public class Tree {
	
	private Application app;
	private double rootX, rootY, len;
	private double lenMult = 0.7;
	private double[] ang = {Math.PI/3, 3*Math.PI/4};
	private double rotate;
	private boolean swingBack;
	private Color color;
	
	public Tree(Application app, double x, double y, double l, Color c) {
		this.app = app;
		this.rootX = x;
		this.rootY = y;
		this.len = l;
		this.color = c;
	}
	
	public void drawTree(Graphics2D g) {
		g.setColor(color);
		int x = (int)(app.getWidth()*rootX);
		int y = (int)(app.getHeight()*rootY);
		int l = (int)(app.getHeight()*len);
		g.setStroke(new BasicStroke(l/9));
		g.drawLine(x, y, x, y-l);
		drawTree(g, x, y-l, (int)(l*lenMult), 0);
	}
	
	public void drawTree(Graphics2D g, int x, int y, int len, double prevAng) {
		if(len > 2) {
			for(int i = 0; i < 2; i++) {
				int nl = len / 2;
				double na = ang[i] + rotate - prevAng;
				int nx = x - (int)(nl * Math.cos(na));
				int ny = y - (int)(nl * Math.sin(na));
				g.setStroke(new BasicStroke(len/9));
				g.drawLine(x, y, nx, ny);
				drawTree(g, nx, ny, (int)(len*lenMult), Math.PI/2-na);
			}
		}
	}
	
	public void tick() {
		if(!swingBack) {
			if(app.getCurWindX() > 1) {
				if(rotate >= 0.1)
					swingBack = true;
				else
					rotate += 0.001;
			} else if(app.getCurWindX() < -1) {
				if(rotate <= -0.2)
					swingBack = true;
				else
					rotate -= 0.001;
			}
		} else {
			if(app.getCurWindX() > 0) {
				rotate -= 0.001;
				if(rotate <= 0.025)
					swingBack = false;
			} else {
				rotate += 0.001;
				if(rotate >= -0.0125)
					swingBack = false;
			}
		}
		
	}

	public Color getColor() {
		return color;
	}

}
