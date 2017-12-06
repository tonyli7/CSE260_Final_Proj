import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Cloneable;
import java.lang.CloneNotSupportedException;

import java.util.LinkedList;

public class Monster extends Sprite implements Slashable, Collideable, Cloneable{
    
    private int health;
    private String name;
    private int damage;
    private boolean slashed;
    private int death;
    private Image[] death_imgs;
    private ImageView death_img_v;
    
    public Monster(String name, int health, String monster_dir, double start_x, double start_y){
	super(monster_dir, start_x, start_y);
	this.name = name;
	this.health = health;
	death_imgs = new Image[7];
	death = -1;
	loadImgs(death_imgs, 7, "Death", "/");
	slashed = false;
    }

    public <T> void slashed(Player player, LinkedList<T> monsters, Pane pane){

	if (monsters.get(0) instanceof Monster){

	    Movement.knockback(this, 30, player.getDir());
	    update();
	   
	    health -= 1;
	    slashed = true;
	    
	    if (health == 0){

		death = 0;
		death_img_v = new ImageView(death_imgs[0]);
		pane.getChildren().add(death_img_v);
		
		/*
		pane.getChildren().remove(getImageView());
		((LinkedList<Monster>)monsters).remove(this);
		*/
		
	    }
	    
	}
    }

    public void setSlashed(boolean cond){
	slashed = cond;
    }
    
    public void collided(Collideable c, int dir){
	if (c instanceof Player){
	    if (!((Player)c).isDamaged()){
		((Player)c).collided(this, KeyComs.getOppDir(dir));

		System.out.println("Player collided with Monster");
	    }
	}
	if (c instanceof GenericTile){
	    Movement.knockback(this, 1, dir);
	}

    }

    public void incDeath(){
	death += 1;
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

    public int death(){
	return death;
    }

    public Image[] getDeathImgs(){
	return death_imgs;
    }

    public ImageView getDeathImageView(){
	return death_img_v;
    }
    
    public Monster clone() throws CloneNotSupportedException{
	Monster clone = new Monster(name, health, name, x_pos, y_pos);
	clone.setSteps(steps);
	clone.setDirPattern(dir_pattern);
	return clone;
	
    }
}
