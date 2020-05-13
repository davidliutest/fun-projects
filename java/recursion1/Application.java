import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

// https://github.com/davidliutest/fun-projects

public class Application extends JFrame {
	
	private Canvas canv;
	private BufferStrategy bs;
	private Graphics2D g;
	
	private double spawnZone = -0.2;
	private double exitZone = 1.2;
	private List<Snowflake> flakes;
	private Tree tree;
	
	public Application() {
		Dimension min = new Dimension(600, 600);
		Dimension prefer = new Dimension(1200, 800);
		canv = new Canvas();
		canv.setPreferredSize(prefer);
		canv.setMinimumSize(min);
		this.setTitle("Recursion Assignment");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setPreferredSize(prefer);
		this.setMinimumSize(min);
		this.setVisible(true);
		this.add(canv);
		this.pack();
		
		tree = new Tree(this, 0.5, 1.5, 0.7, Color.decode("#654321"));
		flakes = new ArrayList<Snowflake>();
		Color[] colors = {Color.white, Color.lightGray, Color.cyan, Color.magenta};
		for(int i = 0; i < 100; i++) {
			flakes.add(new Snowflake(
				this, Math.random()*2-0.5, spawnZone-Math.random()*1.5, Math.random() * 0.04, colors[(int)(Math.random()*(colors.length))]
			));
		}
		changeWind();
	}
	
	private double curWindX, curWindY;
	private double finWindX, finWindY;
	
	public void changeWind() {
		finWindX = Math.random()*5 - 3;
		finWindY = Math.random()*5 - 2;
	}
	
	public void render() {
		bs = canv.getBufferStrategy();
		if(bs == null) {
			canv.createBufferStrategy(3);
			return;
		}
		g = (Graphics2D)bs.getDrawGraphics();
		// Clear Rect
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		// Draw
		tree.drawTree(g);
		for(Snowflake s : flakes)
			s.drawStar(g);
		// Display
		bs.show();
		g.dispose();
	}
	
	public void tick() {
		tree.tick();
		for(Snowflake s : flakes)
			s.tick();
		if(curWindX < finWindX)
			curWindX += 0.01;
		else if(curWindX > finWindX)
			curWindX -= 0.01;
		if(curWindY < finWindY)
			curWindY += 0.01;
		else if(curWindY > finWindY && curWindY > 0.01)
			curWindY -= 0.01;
	}
	
	public double getCurWindX() {
		return curWindX;
	}

	public double getCurWindY() {
		return curWindY;
	}

	public double getSpawnZone() {
		return spawnZone;
	}

	public double getExitZone() {
		return exitZone;
	}

	public static void main(String[] args) {
		Application app = new Application();
		int FPS = 60;
        double timePerTick = 1000000000 / FPS;
        double delta = 0;
        double curTime;
        double lastTime = System.nanoTime();
        long timer = 0;
        while(true) {
            curTime = System.nanoTime();
            delta += (curTime - lastTime) / timePerTick;
            timer += curTime - lastTime;
            lastTime = curTime;
            if(delta >= 1) {
                app.tick();
                app.render();
                delta--;
            }
            if(timer >= 7000000000l) {
                timer = 0;
                app.changeWind();
            }
        }
	}
	
}
