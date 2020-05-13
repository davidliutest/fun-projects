import java.awt.Color;
import java.awt.Graphics;

// https://github.com/davidliutest/fun-projects

public class Snowflake {
	
	private Application app;
	private double centerX, centerY, len;
	private double lenMult = 1.0/3;
	private Color color;
	private double rotate;
	
	public Snowflake(Application app, double x, double y, double l, Color c) {
		this.app = app;
		this.centerX = x;
		this.centerY = y;
		this.len = l;
		this.color = c;
	}
	
	public void drawStar(Graphics g) {
		g.setColor(color);
		drawStar(g, (int)(app.getWidth()*centerX), (int)(app.getHeight()*centerY), (int)((app.getWidth()+app.getHeight())/2*len));
	}
	
	public void drawStar(Graphics g, int x, int y, int len) {
		if(len > 2) {
			for(int i = 0; i < 6; i++) {
				int nx = x + (int)(len * Math.cos( rotate + Math.PI/3*i ));
				int ny = y - (int)(len * Math.sin( rotate + Math.PI/3*i ));
				g.drawLine(x, y, nx, ny);
				drawStar(g, nx, ny, (int)(len*lenMult));
			}
		}
	}
	
	public void tick() {
		centerX += app.getCurWindX() * 0.003;
		centerY += 0.002 + app.getCurWindY() * 0.003;
		
		if(app.getCurWindX() > 0)
			rotate -= 0.06 * Math.abs(app.getCurWindX());
		else if(app.getCurWindX() < 0)
			rotate += 0.06 * Math.abs(app.getCurWindX());
		
		if(centerY > app.getExitZone()) {
			centerX = Math.random() * 2 - 0.5;
			centerY = app.getSpawnZone()-Math.random();
		}
	}
	
	public Color getColor() {
		return color;
	}
	
}
