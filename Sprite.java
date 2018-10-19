import java.awt.Graphics;

abstract class Sprite
{
	Model model;
	int x;
	int y;
	int w = 60;
	int h = 95;
	int coins;
	double vvel;
	
	abstract boolean update();
	
	abstract void draw(Graphics g);
	
	Sprite(Model m)
	{
		model = m;
	}
	
	Sprite()
	{
		
	}

	void previouslocation()
	{
		
	}
	
	boolean isBrick()
	{
		return false;
	}
	
	boolean isCoinblock()
	{
		return false;
	}
	//abstract void unmarshall(Json b);
	
	abstract Json marshall();
}