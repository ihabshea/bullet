import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;


public class Bullet
{
	protected Vector2f pos; //A vector (of 2 float points) for the current position of the bullet
	protected Vector2f dir; //Another one for the direction it's headed
	protected int lived = 0; //The time (in ms) the bullet has been alive
	protected boolean active = true; //Whether or not the bullet has been activated
	Sound bulletSound; //Sound effect
	Image BulletS; // The bullet 2D image/sprite
	protected int MAX_LIFETIME = 2000; //The bullet should disappear after 2 seconds
	protected int BulletSize = 100; //Size of the bullet, will be useful to detect collisions
	protected int DAMAGE = 5; //The damage caused by the bullet to the character's HP
	
	public Bullet ( Vector2f pos, Vector2f dir )
	{ // A constructor for the bullet object with an initial position and direction
		this.pos = pos;
		this.dir = dir;
		
		dir.scale(500); //scale the direction vector by 500 - so it would move faster
	}
	
	public Bullet init ( Vector2f pos, Vector2f dir ) throws SlickException
	{ //A built in method (inherited from Slick 2D) to initialize certain objects and other things like images.
		this.pos = pos;
		this.dir = dir;
/*		try {
			bulletSound = new Sound("res/fire.mp3");
		} catch (SlickException e) {
			e.printStackTrace();
		}*/
		BulletS = new Image("res/new_bullet.png"); //The image file
		dir.scale(500); //scale the direction vector by 500 
		setActive(true); //Make the bullet active when initialized
		return this;
	}
	
	public Bullet ()
	{ //An empty constructor initializing the bullet's active status to false
		active = false;
	}
	
	public void update( int t )
	{ //Another built-in method from Slick2D , usually used to program logic, movements, and AI
		if( active )
		{ //check if bullet is active
			Vector2f realSpeed = dir.copy(); //Copy the value of the direction vector into the speed vector
			realSpeed.scale( (t/1000.0f) ); //Scale the speed vector by time divided by 1000 (so it would move)
			pos.add( realSpeed ); //Add the new speed vector to the original position vector
			
			lived += t; //increase the time the bullet has been alive for, so it can disappear after a certain time
			if( lived > MAX_LIFETIME ) active = false; //Make the bullet disappear after the maximum life time
		}
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException 
	{ //A built-in method from Slick2D, used to render the graphics and make them appear on screen
		if( active )
		{
			g.drawImage(BulletS, pos.getX()-10, pos.getY()-10); //Use the image stored in the variable BulletS, put it in the current X and Y positions of the bullet
//			bulletSound.play();
		}
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void setActive( boolean active )
	{
		this.active = active;
	}
	
	
	public boolean collideWith ( Vector2f otherPos , int theObject )
	{ //Did the bullet collide with another object (ie: an enemy)?
		int dis = (int) otherPos.copy().sub(pos).lengthSquared(); // Subtract the two position vectors of the current bullet and the object it's colliding with and then get the length squared
		
		if( dis < ( theObject + BulletSize ) ) 
		{ //if the length squared of the two vectors is less than the the size of both objects combined, then the two objects are colliding
			return true;
		}
		return false;
	}
	
	public int getDamage ()
	{
		return DAMAGE;
	}
	
}
