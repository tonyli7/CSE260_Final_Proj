import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
	if (dir == Sprite.UP || dir == Sprite.DOWN){
	    unitMoveY(sprite, dir);
	}else{
	    unitMoveX(sprite, dir);
	}
    }
    
    public static void stop(Sprite sprite, int form){
	Image[] temp = sprite.getImgs(sprite.getDir());
	sprite.setCurrImg(temp[form]);
	sprite.setForm(form);
	sprite.update();
	sprite.setForm(0);
    }

    public static void attack(Player player, Image[] attack_imgs, int attack_form){
	
	player.setCurrImg(attack_imgs[attack_form]);

	Weapon sword = player.getWeapon();
	Rotate rotation = sword.getRotation();
	
	ImageView img_v_player = player.getImageView();
	
	DoubleProperty DP_x = new SimpleDoubleProperty(img_v_player.xProperty().getValue() + player.getWidth()/2);
	
	DoubleProperty DP_y = new SimpleDoubleProperty(img_v_player.yProperty().getValue() + player.getHeight()/2);
	
	rotation.pivotXProperty().bind(DP_x);
	rotation.pivotYProperty().bind(DP_y);
	
	rotation.setAngle(165/attack_imgs.length * attack_form);
	
	sword.setX(player.getX() + player.getWidth()/2);
	sword.setY(player.getY() - player.getHeight()/6);
	player.update();
	
    }

    
}
