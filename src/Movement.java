import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.HashSet;
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
	System.out.println(sprite.getHeight());
	if (!checkCollisions(sprite, Demo.location, dir)){
	   
	    if (dir == Sprite.UP || dir == Sprite.DOWN){
		unitMoveY(sprite, dir);
	    }else{
		unitMoveX(sprite, dir);
	    }
	}

	
	
	sprite.getImageView().toFront();
    }

    private static boolean checkCollisions(Sprite sprite, HashSet<GenericTile> tiles, int dir){
	for (GenericTile g: tiles){
	    if (g instanceof Collideable){
		if (isTouching(sprite, (Collideable)g)){
		    if (dir == Sprite.UP){
			sprite.setY(g.getY() + sprite.getHeight() + 7);
		    }
		    if (dir == Sprite.DOWN){
			sprite.setY(g.getY() - sprite.getHeight() - 7);
		    }
		    if (dir == Sprite.LEFT){
			sprite.setX(g.getX() + sprite.getWidth() + 7);
		    }
		    if (dir == Sprite.RIGHT){
			sprite.setX(g.getX() - sprite.getWidth() - 7);
		    }
		    
		    sprite.update();
		    return true;
		}
	    }
	}
	return false;
       
	
    }

    private static boolean isTouching(Sprite sprite, Collideable c){
	ImageView img_v = sprite.getImageView();
	int padding = 3;
	return (img_v.contains(c.getX() + padding, c.getY() + padding) ||
		img_v.contains(c.getX() + c.getWidth() - padding, c.getY() + padding) ||
		img_v.contains(c.getX() + padding, c.getY() + c.getHeight() - padding) ||
		img_v.contains(c.getX() + c.getWidth() - padding, c.getY() + c.getHeight() - padding));
    }

   
    public static void stop(Sprite sprite, int form){
	Image[] temp = sprite.getImgs(sprite.getDir());
	sprite.setCurrImg(temp[form]);
	sprite.setForm(form);
	sprite.update();
	sprite.setForm(0);
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
	for (GenericTile g: tiles){
	    if (g instanceof Slashable){
		if (isSlashed(player, sword.getImageView(), (Slashable)g, angle)){
		    ((Slashable)g).slashed();
		    System.out.println("dsadasadadadas");
		}
	    }
	}
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
