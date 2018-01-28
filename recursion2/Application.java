import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

// https://github.com/davidliutest/fun-projects

public class Application extends JFrame {
	
	public Application() {		
		this.setTitle("Recursion Assignment");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(1000, 700);
		this.setResizable(true);
		this.setVisible(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		double len = (getWidth() + getHeight()) / 2 * 0.5;
		double x = (int)(getWidth()*0.5 - len/2);
		double y = (int)(getHeight()*0.8);
		drawTriangle(g, x, y, len, false);
		drawSierTriangle(g, x, y, len);
	}
	
	public void drawSierTriangle(Graphics g, double x, double y, double len) {
		if(len > 4) {
			double nx = x + len/4;
			double ny = y - len/4*Math.sqrt(3);
			drawTriangle(g, nx, ny, len/2, true);
			drawSierTriangle(g, x, y, len/2);
			drawSierTriangle(g, nx, ny, len/2);
			drawSierTriangle(g, x+len/2, y, len/2);
		}
	}
	
	public void drawTriangle(Graphics g, double x, double y, double len, boolean xReflect) {
		g.drawLine((int)x, (int)y, (int)(x+len), (int)y);
		g.drawLine((int)x, (int)y, (int)(x+len/2), (int)(y-(xReflect?-1:1)*len/2*Math.sqrt(3)));
		g.drawLine((int)(x+len/2), (int)(y-(xReflect?-1:1)*len/2*Math.sqrt(3)), (int)(x+len), (int)y);
	}
	
	public static void main(String[] args) {
		new Application();
	}

}