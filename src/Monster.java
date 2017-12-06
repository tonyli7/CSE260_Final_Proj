import javafx.scene.image.ImageView;
import java.lang.Cloneable;
import java.lang.CloneNotSupportedException;

public class Monster extends Sprite implements Slashable, Collideable, Cloneable{
    
    private int health;
    private String name;
    private int damage;
    private boolean slashed;
    
    public Monster(String name, int health, String monster_dir, double start_x, double start_y){
	super(monster_dir, start_x, start_y);
	this.name = name;
	this.health = health;
    }

    public void slashed(){
	if (!slashed){
	    health -= 1;
	    slashed = true;
	}
    }

    public void setSlashed(boolean cond){
	slashed = cond;
    }
    
    public void collided(Collideable c, int dir){
	if (c instanceof Player){
	    if (!((Player)c).isDamaged()){
		((Player)c).collided(this, KeyComs.getOppDir(dir));
	    }
	}
    }
    public double getX(){
	return x_pos;
    }
    public double getY(){
	return y_pos;
    }
    public double getWidth(){
	return width;
    }
    public double getHeight(){
	return height;
    }
    public ImageView getImageView(){
	return img_v; 
    }

    public boolean isSlashed(){
	return slashed;
    }

    public Object clone() throws CloneNotSupportedException{
	Monster clone = new Monster(name, health, name, x_pos, y_pos);
	clone.setSteps(steps);
	clone.setDirPattern(dir_pattern);
	return clone;
	
    }
}
