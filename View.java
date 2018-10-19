import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
	int anim_pic;
	Model model;
	static Image background = null;

	View(Controller c, Model m)
	{		
		model = m;
		try {
				background = ImageIO.read(new File("background.png"));
			} catch (IOException e) {
				e.printStackTrace(System.err);
				System.exit(1);
			}
	}
	
	
	public void paintComponent(Graphics g)
	{
		//Clearing the screen
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//Background load
		g.drawImage(background, -model.scrollPos - 400, 0, null);
		
		//Ground
		g.setColor(new Color(146, 67, 27));
		g.fillRect(0, 595, 900, 700 );
		
		for(int i =  0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			s.draw(g);
		}
	}
}
