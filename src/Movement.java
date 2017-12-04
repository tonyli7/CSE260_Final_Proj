import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.HashSet;
import java.util.LinkedList;
public class Movement{

    public static void move(Sprite sprite, double x_units, double y_units, int dir, int form){
	
	Image[] temp = sprite.getImgs(dir);
	sprite.setDir(dir);
	sprite.setCurrImg(temp[form]);
	sprite.update();
	for (int i = form + 1; i < form + Math.abs(x_units) + 1; i++){
	    sprite.changeXY(x_units*3, 0);
	    sprite.setCurrImg(temp[i % temp.length]);
	    
	    sprite.setForm(i % temp.length);
	    sprite.update();
	}

	for (int i = form + 1; i < form + Math.abs(y_units) + 1; i++){
	    sprite.changeXY(0, y_units*3);
	    sprite.setCurrImg(temp[i % temp.length]);
	    
	    sprite.setForm(i % temp.length);
	    sprite.update();
	}
    }

    private static void unitMoveX(Sprite sprite, int dir){
	Image[] temp = sprite.getImgs(dir);
	sprite.setDir(dir);
	sprite.setForm((sprite.getForm() + 1) % (temp.length * 5));
	sprite.setCurrImg(temp[sprite.getForm() / 5]);
	
	if (dir == Sprite.LEFT){
	    sprite.changeXY(-2, 0);
	}
	if (dir == Sprite.RIGHT){
	    sprite.changeXY(2,0);
	}
	sprite.update();
    }

    private static void unitMoveY(Sprite sprite, int dir){
	Image[] temp = sprite.getImgs(dir);
	sprite.setDir(dir);
	sprite.setForm((sprite.getForm() + 1) % (temp.length * 5));
	sprite.setCurrImg(temp[sprite.getForm() / 5]);
	
	if (dir == Sprite.UP){
	    sprite.changeXY(0, -2);
	}
	if (dir == Sprite.DOWN){
	    sprite.changeXY(0, 2);
	}
	sprite.update();
    }

    public static void unitMove(Sprite sprite, int dir){
	if (!checkCollisions(sprite, Demo.location, dir)){
	   
	    if (dir == Sprite.UP || dir == Sprite.DOWN){
		unitMoveY(sprite, dir);
	    }else{
		unitMoveX(sprite, dir);
	    }
	    //System.out.println(dir);
	}

	
	
	sprite.getImageView().toFront();
    }

    private static boolean checkCollisions(Sprite sprite, HashSet<GenericTile> tiles, int dir){
	for (GenericTile g: tiles){
	    if (g instanceof Collideable){
		int touched = isTouching(sprite, ((Collideable)g));
		if (touched == 0){
		    sprite.changeXY(0, 2);
		}
		if (touched == 1){
		    sprite.changeXY(0, -2);
		}
		if (touched == 2){
		    sprite.changeXY(0, 2);
		}
		if (touched == 3){
		    sprite.changeXY(0, -2);
		}
		
	    }
	}
	return false;
       
	
    }

    private static int isTouching(Sprite sprite, Collideable c){
	//ImageView sprite_img_v = sprite.getImageView();
	//ImageView obj_img_v = c.getImageView();

	double sprite_x = sprite.getX();
	double sprite_y = sprite.getY();

	double obj_x = c.getX();
	double obj_y = c.getY();

	
	if ((sprite_x <= obj_x + c.getWidth() && sprite_x > obj_x) ||
	    (sprite_x + sprite.getWidth() <= obj_x + c.getWidth() && sprite_x + sprite.getWidth() > obj_x)){
	    if (sprite_y <= obj_y + c.getHeight() && sprite_y > obj_y){ //if from bottom
		return 0;
	    }
	    if (sprite_y  + sprite.getHeight() >= obj_y && sprite_y + sprite.getHeight() < obj_y + c.getHeight()){ //if from top
		return 1;
	    }
	}
	
	if ((sprite_y <= obj_y + c.getHeight() && sprite_y > obj_y) ||
	    (sprite_y + sprite.getHeight() <= obj_y + c.getHeight() && sprite_y + sprite.getHeight() > obj_y)){
	    if (sprite_x <= obj_x + c.getWidth() && sprite_x > obj_x){ // if from right
		return 2;
	    }
	    if (sprite_x + sprite.getWidth() >= obj_x && sprite_x + sprite.getWidth() < obj_x + c.getWidth()){ // if from left
		return 3;
	    }
	}
	return -1;

	    
    }

    /*
    private static boolean isTouching(Sprite sprite, Collideable c){
	ImageView img_v = sprite.getImageView();

	return c.getImageView().intersects(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }
    */

   
    public static void stop(Sprite sprite, int form){
	Image[] temp = sprite.getImgs(sprite.getDir());
	sprite.setCurrImg(temp[form]);
	sprite.setForm(form);
	sprite.update();
	sprite.setForm(0);
	//System.out.println(1);
    }

    
    public static void attack(Player player, Image[] attack_imgs, int attack_form){
	
	player.setCurrImg(attack_imgs[attack_form]); // sets attack form

	Weapon sword = player.getWeapon();
	Rotate rotation = sword.getRotation();
	
	ImageView img_v_player = player.getImageView();

	// sets pivot point of rotation
	DoubleProperty DP_x = new SimpleDoubleProperty(img_v_player.xProperty().getValue() + player.getWidth()/2);
	
	DoubleProperty DP_y = new SimpleDoubleProperty(img_v_player.yProperty().getValue() + player.getHeight()/2);
	
	rotation.pivotXProperty().bind(DP_x);
	rotation.pivotYProperty().bind(DP_y);

	
	sword.getImageView().toFront(); // moves sword to the front
	img_v_player.toFront(); // moves Player in front of the sword
	int player_dir = player.getDir();

	// rotates and positions sword based on Player direction
	double angle = 90;
	if (player_dir == Sprite.RIGHT){
	    angle = 180/attack_imgs.length * (attack_form + 1);
	    rotation.setAngle(angle);
	    
	}else if (player_dir == Sprite.LEFT){
	    angle = -180/attack_imgs.length * (attack_form + 1);
	    rotation.setAngle(angle);
	    
	}else if (player_dir == Sprite.UP){
	    angle = (180/attack_imgs.length * attack_form - 55);
	    rotation.setAngle(angle);
	    
	}else if (player_dir == Sprite.DOWN){
	    angle = (180/attack_imgs.length * attack_form - 235);
	    rotation.setAngle(angle);
	    
	}

	// makes sure the sword is always positioned based on Player position
	sword.setX(player.getX() + player.getWidth()/2);
	sword.setY(player.getY() - player.getHeight()/2);
	checkSlashed(player, sword, Demo.location, angle);
	player.update();
    }

    private static void checkSlashed(Player player, Weapon sword, HashSet<GenericTile> tiles, double angle){
	//System.out.println(tiles);
	LinkedList<GenericTile> ll = new LinkedList<GenericTile>();
	ll.addAll(tiles);

	for(int i = 0; i < ll.size(); i++){
	    GenericTile g = ll.get(i);
	    if (g instanceof Slashable){
		if (isSlashed(player, sword.getImageView(), (Slashable)g, angle)){
		    GenericTile new_g = new GenericTile("Grass.png", g.getX(), g.getY());
		    //new_g.getImageView().toFront();
		    tiles.add(new_g);
		    Demo.pane.getChildren().remove(g.getImageView());
		    Demo.pane.getChildren().add(new_g.getImageView());
		    tiles.remove(g);
		    
		}
	    }
	}

	/*
	for (GenericTile g: tiles){
	    if (g instanceof Slashable){
		if (isSlashed(player, sword.getImageView(), (Slashable)g, angle)){
		    ((Slashable)g).slashed();
		    //System.out.println("dsadasadadadas");
		}
	    }
	}
	*/
    }

    private static boolean isSlashed(Player player, ImageView sword, Slashable s, double angle){
	double pivot_x = player.getX() + player.getWidth()/2;
	double pivot_y = player.getY() + player.getHeight()/2;

	double d_x = sword.getX() - pivot_x;
	double d_y = sword.getY() - pivot_y;

	double r = Math.pow((Math.pow(d_x, 2) + Math.pow(d_y, 2)), 0.5);

	double rads = Math.toRadians(angle);

	double real_x = r*Math.cos(rads);
	double real_y = r*Math.sin(rads);

	
	return s.getImageView().contains(real_x + pivot_x, pivot_y - real_y);
	
    }
    
}
