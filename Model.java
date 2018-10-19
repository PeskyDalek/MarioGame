import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Graphics;


class Model
{
	ArrayList<Sprite> sprites;
	int scrollPos;
	Mario mario;
		
	Model()
	{
		mario = new Mario(this);
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);
	}
	
	public void update()
	{
		for(int i = 0; i < sprites.size(); i++)
		{
			Sprite s = sprites.get(i);
			boolean valid = s.update();
			if(valid)
			{
				sprites.remove(i);
				i--;
			}
		}
	}
	
	void unmarshall(Json ob)
	{
		sprites.clear();
		Json json_sprites = ob.get("sprites");
		
		for(int i = 0; i < json_sprites.size(); i++)
		{
			Json j = json_sprites.get(i);
			String type = j.getString("type");
			Sprite s;
			if(type.equals("Mario"))
				s = new Mario(j, this);
			else if(type.equals("Brick"))
				s = new Brick(j, this);
			else if(type.equals("Coinblock"))
				s = new Coinblock(j, this);
			else if(type.equals("Coins"))
				s = new Coin(j);
			else
				s = null;
			sprites.add(s);
		}
	}
	
	Json marshall()
	{
		Json ob = Json.newObject();
		Json json_sprites = Json.newList();
		ob.add("sprites", json_sprites);
		
		for(int i = 0; i <sprites.size(); i++)
		{
			Sprite s = sprites.get(i);
			Json j = s.marshall();
			json_sprites.add(j);
		}
        return ob;
	}
	
	void save(String filename)
	{
		Json ob = marshall();
		ob.save(filename);
	}
	
	void load(String filename)
	{
		Json ob = Json.load(filename);
		unmarshall(ob);
	}
}