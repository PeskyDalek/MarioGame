import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Coinblock extends Sprite
{

	int coins;
	Model model;
	static Image Coinblock_image;
	static Image SpentCoinblock_image;
	static Random r = new Random();
	
	Coinblock(Sprite s)
	{
		x = s.x;
		y = s.y;
		w = s.w;
		h = s.h;
		coins = s.coins;
	}
	
	Coinblock(int _x, int _y, Model m)
	{
		model = m;
		x = _x;
		y = _y;
		coins = 0;
	}
	
	void SpawnCoin(int x, int y)
	{
		double h_vel = r.nextDouble() * 10 - 5;
		double v_vel = -38;
		Coin c = new Coin(x, y, h_vel, v_vel, model); 
		model.sprites.add(c); 
	}
	
	boolean update()
	{
		return false;		
	}
	
	void draw(Graphics g)
	{
		try {
			Coinblock_image = ImageIO.read(new File("Coinblock.png"));
			SpentCoinblock_image = ImageIO.read(new File("SpentCoinblock.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(coins < 5)
		{
			g.drawImage(Coinblock_image, x - model.scrollPos, y, null);
		}
		else if(coins >= 5)
		{
			g.drawImage(SpentCoinblock_image, x - model.scrollPos, y, null);
		}
	}
	
	boolean isCoinblock()
	{
		return true;
	}
	
	Coinblock(Json ob, Model m)
	{
        x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
		coins = (int)ob.getLong("coins");
		model = m;
	}
	
	Json marshall()
	{
		Json ob = Json.newObject();
        ob.add("x", x);
		ob.add("y", y);
		ob.add("w", w);
		ob.add("h", h);
		ob.add("coins", coins);
		ob.add("type", "Coinblock");
        return ob;
	}
}



class Coin extends Sprite
{
	double h_vel;
	double v_vel;
	Model model;
	static Image Coin_image;
	
	Coin(int _x, int _y, double _h_vel, double _v_vel, Model m)
	{
		model = m;
		x = _x;
		y = _y;
		w = 75;
		h = 75;
		h_vel = _h_vel;
		v_vel = _v_vel;
	}
	
	Coin(Json ob)
	{
        x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
		h_vel = (double)ob.getDouble("h_vel");
		v_vel = (double)ob.getDouble("v_vel");
		coins = (int)ob.getLong("coins");
	}
	
	boolean update()
	{
		v_vel += 3.1415; 
		y += v_vel;
		x += h_vel;
		
		if(y > 700)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	void draw(Graphics g)
	{
		try {
			Coin_image = ImageIO.read(new File("coin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		g.drawImage(Coin_image, x - model.scrollPos, y, null);
	}
	
	void unmarshall(Json ob)
	{
		model.sprites.clear();
		Json json_coin = ob.get("Coin");
		
		for(int i = 0; i < json_coin.size(); i++)
		{
			Json j = json_coin.get(i);
			Coin c = new Coin(j);
			model.sprites.add(c);
		}
	}
	
	Json marshall()
	{
		Json ob = Json.newObject();
        ob.add("x", x);
		ob.add("y", y);
		ob.add("w", w);
		ob.add("h", h);
		ob.add("h_vel", h_vel);
		ob.add("v_vel", v_vel);
		ob.add("coins", coins);
		ob.add("type", "Coin");
        return ob;
	}
}