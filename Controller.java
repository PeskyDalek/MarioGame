import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements MouseListener, KeyListener
{
	View view;
	Model model;
	Brick brick;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keySpace;
	int mouseDownX;
	int mouseDownY;
	
	Controller(Model m)
	{
		model = m;
	}	
	
	public void mousePressed(MouseEvent e)
	{
		//model.setDestination(e.getX(), e.getY());
		mouseDownX = e.getX();
		mouseDownY = e.getY();
	}

	public void mouseReleased(MouseEvent e) 
	{
		
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			int x1 = mouseDownX;
			int x2 = e.getX();
			int y1 = mouseDownY;
			int y2 = e.getY();
			int left = Math.min(x1, x2);
			int right = Math.max(x1, x2);
			int top = Math.min(y1, y2);
			int bottom = Math.max(y1, y2);
			addBrick(left + model.scrollPos, top, right - left, bottom - top);
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			addCoinblock(mouseDownX + model.scrollPos, mouseDownY);
		}
	}
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_S: model.save("map.json"); break;
			case KeyEvent.VK_L: model.load("map.json"); break;
			case KeyEvent.VK_SPACE : keySpace = true; break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_SPACE : keySpace = false; break;
		}
	}

	public void keyTyped(KeyEvent e)
	{
	}
	
	void addBrick( int x, int y, int w, int h)
	{
		Brick b = new Brick(x, y, w, h, model);
		model.sprites.add(b);
	}
	
	void addCoinblock( int x, int y)
	{
		Coinblock c = new Coinblock(x, y, model);
		model.sprites.add(c);
	}
	
	void update()
	{
		//model.mario = mario;
		model.mario.previousLocation();
		
		if(keyRight) 
		{
			//model.scrollPos++;
			model.mario.x += 10;
		}
		if(keyLeft) 
		{
			//model.scrollPos--;
			model.mario.x -= 10;
		}
		if(keySpace)
		{
			if(model.mario.frame_from_ground_count < 7)
			{
			  model.mario.vvel = -35;
			}
		}
	}
}
