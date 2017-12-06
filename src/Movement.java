import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

import javafx.geometry.Bounds;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.HashSet;
import java.util.LinkedList;
public class Movement{

    private static void unitMoveX(Sprite sprite, int dir, double x_units){
	Image[] temp = sprite.getImgs(dir);
	sprite.setDir(dir);
	sprite.setForm((sprite.getForm() + 1) % (temp.length * 5));
	sprite.setCurrImg(temp[sprite.getForm() / 5]);
	
	if (dir == Sprite.LEFT){
	    sprite.changeXY(-x_units, 0);
	}
	if (dir == Sprite.RIGHT){
	    sprite.changeXY(x_units,0);
	}
	sprite.update();
    }
   

    private static void unitMoveY(Sprite sprite, int dir, double y_units){
	Image[] temp = sprite.getImgs(dir);
	sprite.setDir(dir);
	sprite.setForm((sprite.getForm() + 1) % (temp.length * 5));
	sprite.setCurrImg(temp[sprite.getForm() / 5]);
	
	if (dir == Sprite.UP){
	    sprite.changeXY(0, -y_units);
	}
	if (dir == Sprite.DOWN){
	    sprite.changeXY(0, y_units);
	}
	sprite.update();
    }

    public static void unitMove(Sprite sprite, Player player, int dir){
	
	
	if (!checkCollisions(sprite, player, Demo.curr_location, dir)){
	   
	    if (dir == Sprite.UP || dir == Sprite.DOWN){
		unitMoveY(sprite, dir, 2);
	    }else{
		unitMoveX(sprite, dir, 2);
	    }
	    
	}

	sprite.getImageView().toFront();
    }

  

    public static void unitMove(Sprite sprite, Player player, int dir, int steps){
	
	if (sprite.getSteps() > 0){
	    sprite.incSteps(-1);
	    unitMove(sprite, player, dir);
	}else{
	    sprite.setSteps(steps);
	    sprite.changeDir();
	}
	
	sprite.getImageView().toFront();
    }

    
    
    public static void follow(Sprite sprite1, Sprite sprite2){
	double sprite1_x = sprite1.getX();
	double sprite1_y = sprite1.getY();

	double sprite2_x = sprite2.getX();
	double sprite2_y = sprite2.getY();

	if (sprite1_x != sprite2_x && sprite1_y != sprite2_y){
	    double dx = sprite2_x - sprite1_x;
	    double dy = sprite2_y - sprite1_y;
	    
	    double dist = Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 0.5);

	    double x_u = dx / dist * 2;
	    double y_u = dy / dist * 2;

	    if (x_u < 0){
		unitMoveX(sprite1, Sprite.LEFT, -x_u);
	    }
	    if (x_u > 0){
		unitMoveX(sprite1, Sprite.RIGHT, x_u);
	    }
	    if (y_u < 0){
		unitMoveY(sprite1, Sprite.UP, -y_u);
	    }
	    if (y_u > 0){
		unitMoveY(sprite1, Sprite.DOWN, y_u);
	    }
	       
	}
    }

    
    private static boolean checkCollisions(Sprite sprite, Player player, Location loc, int dir){
	Demo.dmg_rect.setVisible(false);
	LinkedList<GenericTile> tiles = loc.getMap();
	LinkedList<Monster> mons = loc.getMonsters();
	LinkedList<Sprite> others = new LinkedList<Sprite>();
	others.add(player);

	isTouching(sprite, tiles);
	isTouching(sprite, mons);
	for (Monster m: mons){
	    if (m instanceof Collideable){
		isTouching(m, tiles);
		isTouching(m, others);
	    }
	}

	
	return false;
    }

    private static <T> void isTouching(Sprite sprite, LinkedList<T> units){
	for (T u: units){
	    if (u instanceof Collideable){
		
		
		int touched = isTouching((Collideable)sprite, (Collideable)u);

		if (touched >= 0){
		    sprite.collided((Collideable)u, touched);
		    
		    
		}

		//knockback(sprite, touched, 2);
		
	    }
	}
    }

    private static int isTouching(Collideable sprite, Collideable c){

	ImageView sprite_img_v = sprite.getImageView();
	ImageView c_img_v = c.getImageView();

	double sprite_x = sprite_img_v.getX();
	double sprite_y = sprite_img_v.getY();

	double sprite_right = sprite_img_v.getFitWidth() + sprite_x;
	double sprite_bot = sprite_img_v.getFitHeight() + sprite_y;
	
	double c_x = c_img_v.getX();
	double c_y = c_img_v.getY();

	double c_right = c_img_v.getFitWidth() + c_x;
	double c_bot = c_img_v.getFitHeight() + c_y;


	double b_collision = Math.abs(c_bot - sprite_y);
	double t_collision = Math.abs(sprite_bot - c_y);
	double l_collision = Math.abs(sprite_right - c_x);
	double r_collision = Math.abs(c_right - sprite_x);

	
	if (sprite_x < c_right &&
	    sprite_right > c_x &&
	    sprite_y < c_bot &&
	    sprite_bot > c_y &&
	    !sprite.equals(c)){

	    // Collision detected
	    //System.out.println(sprite + "," + c);
	    //System.out.println(sprite.getX() + "," + c.getX());
	    //System.out.println(sprite.getY() + "," + c.getY());
	    
	    
	    if (t_collision < b_collision && t_collision < l_collision && t_collision < r_collision ){
		//Top collision
		return Sprite.UP;
	    
	    }
	    if (b_collision < t_collision && b_collision < l_collision && b_collision < r_collision){
		//bottom collision
		return Sprite.DOWN;
	    }
	    if (l_collision < r_collision && l_collision < t_collision && l_collision < b_collision){
		//Left collision
		return Sprite.LEFT;
	    }
	    if (r_collision < l_collision && r_collision < t_collision && r_collision < b_collision ){
		//Right collision
		return Sprite.RIGHT;
	    }
	}

	return -1;

	    
    }
   
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
	checkSlashed(player, sword, Demo.curr_location, angle);
	player.update();
    }

    private static void checkSlashed(Player player, Weapon sword, Location loc, double angle){
	//System.out.println(tiles);
	LinkedList<GenericTile> ll = new LinkedList<GenericTile>();
	LinkedList<Monster> ll_mon = new LinkedList<Monster>();
	LinkedList<GenericTile> tiles = loc.getMap();
	LinkedList<Monster> mons = loc.getMonsters();
	
	ll.addAll(tiles);

	for(int i = 0; i < ll.size(); i++){
	    GenericTile g = ll.get(i);
	    if (g instanceof Slashable){
		if (isSlashed(player, sword.getImageView(), (Slashable)g, angle)){
		    ((Slashable)g).slashed(player, tiles, Demo.pane);
		    
		}
	    }
	}

	
	ll_mon.addAll(mons);
	for (int i = 0; i < ll_mon.size(); i++){
	    Monster m = ll_mon.get(i);
	    if (m instanceof Slashable){
		if (isSlashed(player, sword.getImageView(), (Slashable)m, angle)){
		    ((Slashable)m).slashed(player, mons, Demo.pane);
		}
	    }
	}
	
	
      
    }

    private static boolean isSlashed(Player player, ImageView sword, Slashable s, double angle){
	double pivot_x = player.getX() + player.getWidth()/2;
	double pivot_y = player.getY() + player.getHeight()/2;

	double d_x = sword.getBoundsInLocal().getMinX() - pivot_x;
	double d_y = sword.getBoundsInLocal().getMinY() - pivot_y;

	double r = Math.pow((Math.pow(d_x, 2) + Math.pow(d_y, 2)), 0.5);

	double rads = Math.toRadians(angle);

	double real_x = r*Math.cos(rads);
	double real_y = r*Math.sin(rads);

	
	return s.getImageView().contains(real_x + pivot_x, pivot_y - real_y);
	
    }

    public static void knockback(Sprite sprite, double units, int dir){
	
	if (dir == Sprite.DOWN){
	    sprite.changeXY(0, units);
	    //System.out.println(1111);
	}
	if (dir == Sprite.UP){
	    sprite.changeXY(0, -units);
	    //System.out.println(1111);
	}
	if (dir == Sprite.RIGHT){
	    sprite.changeXY(units, 0);
	    //System.out.println(1111);
	}
	if (dir == Sprite.LEFT){
	    sprite.changeXY(-units, 0);
	    //System.out.println(1111);
	}

	sprite.update();

	
    }
    
}
