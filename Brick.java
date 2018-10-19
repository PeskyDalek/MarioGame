import java.awt.Color;
import java.awt.Graphics;

public class Brick extends Sprite
{

	Model model;
	
	Brick(int _x, int _y, int _w, int _h, Model m)
	{
		model = m;
		x = _x;
		y = _y;
		w = _w;
		h = _h;
	}
	
	boolean update()
	{
		return false;
	}
	
	void draw(Graphics g)
	{
		//Bricks
		g.setColor(new Color(66, 244, 194));
		g.drawRect(x - model.scrollPos, y, w, h); 
		g.fillRect(x - model.scrollPos, y, w, h);
	}
	
	boolean isBrick()
	{
		return true;
	}

	Brick(Json ob, Model m)
	{
        x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
		model = m;
	}
	
	Json marshall()
	{
		Json ob = Json.newObject();
        ob.add("x", x);
		ob.add("y", y);
		ob.add("w", w);
		ob.add("h", h);
		ob.add("type", "Brick");
        return ob;
	}
}