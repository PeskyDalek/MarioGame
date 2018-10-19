import java.util.Iterator;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

class Mario extends Sprite
{
	int lastx;
	int lasty;
	
	int frame_from_ground_count;
	int frame_since_last_coin;

	double vvel;
	
	static Image[] mario_images = null;
	
	Mario(Model m)
	{
		model = m;
		frame_from_ground_count = 0;
	}
	
	void previousLocation()
	{
		lastx = x;
		lasty = y;
	}
	
	//Takes in values from an object and uses mario's stats to see if they collide
	boolean doesCollide(int _x, int _y, int _w, int _h)
	{
		if(x + w <= _x)
			return false;
		else if(x >= _x + _w)
			return false;
		else if(y + h <= _y)
			return false;
		else if(y >= _y + _h)
			return false;
		else
			return true;
	}
	
	void get_out(int _x, int _y, int _w, int _h)
	{
		//Left
		if(x + w > _x && lastx + w <= _x) 
		{
			x = _x - w;
		}
		//Right
		else if(x < _x + _w && lastx >= _x + _w)
		{
			x = _x + _w;
		}
		//Top
		else if(y + h > _y && lasty + h <= _y)
		{
			y = _y - h;
			vvel = 0;
			frame_from_ground_count = 0;
		}
		//Bottom
		else if(y < _y + _h && lasty >= _y + _h)
		{
			y = _y + _h;
			vvel = 0;
		}
	}
	
	boolean update()
	{
		model.scrollPos = x - 200;
		
		vvel += 3.1415; 
		y += vvel;
		
		if(y > 500)
		{
			y = 500;
			frame_from_ground_count = 0;
		}
		
		//Collison detection

		for(int i = 0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			if(s.isBrick() )
			{
				if(doesCollide(s.x, s.y, s.w, s.h))
				{
					this.get_out(s.x, s.y, s.w, s.h);
				}
			}
			else if(s.isCoinblock())
			{
				if(doesCollide(s.x, s.y, s.w, s.h))
				{
					Coinblock cb = (Coinblock)s;
					if((y < s.y + s.h && lasty >= s.y + s.h) && frame_since_last_coin >= 7 && cb.coins < 5)
					{
						cb.SpawnCoin(s.x, s.y);
						cb.coins++;
					}
					this.get_out(s.x, s.y, s.w, s.h);
					frame_since_last_coin = 0;
				}
			}
		}
		frame_since_last_coin++;
		frame_from_ground_count++;
		return false;
	}
	
	void draw(Graphics g)
	{
		if(mario_images == null)
		{
		mario_images = new Image[5];
		}

		try
		{
			mario_images[0] = ImageIO.read(new File("mario1.png"));
			mario_images[1] = ImageIO.read(new File("mario2.png"));
			mario_images[2] = ImageIO.read(new File("mario3.png"));
			mario_images[3] = ImageIO.read(new File("mario4.png"));
			mario_images[4] = ImageIO.read(new File("mario5.png"));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
		int marioFrame = Math.abs(model.mario.x / 20) % 5;
		g.drawImage(mario_images[marioFrame], model.mario.x - model.scrollPos, model.mario.y, null);
		
	}
	
	Mario(Json ob, Model m)
	{
        x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		vvel = (double)ob.getDouble("vvel");
		model = m;
		model.mario = this;
	}
	
	Json marshall()
	{
		Json ob = Json.newObject();
		ob.add("type", "Mario");
		ob.add("x", x);
		ob.add("y", y);
		ob.add("w", w);
		ob.add("h", h);
		ob.add("vvel", vvel);
		return ob;
	}
	
}