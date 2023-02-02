 
import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	GameFrame(){
//		GamePanel panel = new GamePanel();
		
		this.add(new GamePanel());   // creating an ananomous gamepanel instance and adding it to current class ie gameFrame
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();// this function is going to take our j frame and fit it snugly around all of the components that we add to the frame.
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);	// window set frame in middle.
		
		
		
	}

}
