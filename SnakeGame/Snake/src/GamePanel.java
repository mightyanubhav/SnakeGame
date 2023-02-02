import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import java.util.Random;
import javax.swing.Timer;



public class GamePanel extends JPanel implements ActionListener {

	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	
	int bodyParts = 6;
	int applesEaten = 0;
	int appleX;      //for x position fo apple 
	int appleY;		// for the y position for the apple ( since the apple will be displayed Randomly.
	
	char direction= 'R';               // r for right , l = left , u for up and d for down
	
	boolean running = false;		// to begin 
	Timer timer;
	Random random;
	
	
	
			// if we want to calculate how many objects we can actually fit into the screen
	GamePanel(){
		// constructor
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		
		this.addKeyListener(new	MyKeyAdapter());
		startGame();
		
	}
	public void startGame() {
		newApple();
		
		running = true;
		timer = new Timer(DELAY,this);
		
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g){
		
		if(running) {
//			for(int i = 0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
//				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);//x1y2x2y2
//				//for x axis .. now doing for y axis
//				g.drawLine(0,i*UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE);
//				
//								
//			}
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			for(int i = 0;i<bodyParts;i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i],UNIT_SIZE , UNIT_SIZE);
				}
				else
				{
					g.setColor(Color.yellow);
					g.fillRect(x[i], y[i],UNIT_SIZE , UNIT_SIZE);
				}
			}
			
			
			// drawing the score after the snake is being formed after loop.
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,30));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("SCORE : "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("SCORE : "+applesEaten))/2,g.getFont().getSize());

		}
		else
		{
			gameOver(g);
		}
		
		
	}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void move() {
		for(int i = bodyParts ;i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
			
		}
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
			
		}
	}
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY))
		{
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkCollisions() {
		
		// checks if head collides with the body.
		for(int i = bodyParts;i>0;i--) {
			if((x[0] == x[i]) && (y[0] == y[i]))
				running = false;
		}
		
		// checks if the head touches the Left border;
		if(x[0]<0)running = false;
		// checks if the head touches the Right border;
		if(x[0]>SCREEN_WIDTH)running = false;
		
		// checks if the head touches the top border;
		if(y[0]<0)running = false;
		
		// check if the head touches bottom border
		if(y[0]>SCREEN_HEIGHT)running = false;
		
		if(running == false)
			timer.stop();
				
	}
	public void gameOver(Graphics g) {
		// score of the final result that is being shown.
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,30));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("SCORE : "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("SCORE : "+applesEaten))/2,g.getFont().getSize());

		
		
		
		// setting over text
		
		
		
		
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(running) {
			move();
			checkApple();
			checkCollisions();			
		}
		repaint();
		
	}
	public 	class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent	e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction!='R')
					direction = 'L';
				break;
				
			case KeyEvent.VK_RIGHT:
				if(direction!='L')
					direction = 'R';
				break;
			case KeyEvent.VK_UP:
				if(direction!='D')
					direction = 'U';
				break;
			case KeyEvent.VK_DOWN:
				if(direction!='U')
					direction = 'D';
				break;
			
			}
		}
	}
}
