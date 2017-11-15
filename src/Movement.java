import javafx.scene.image.Image;
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

    public static void unitMoveX(Sprite sprite, int dir){
	Image[] temp = sprite.getImgs(dir);
	sprite.setDir(dir);
	sprite.setForm((sprite.getForm() + 1) % (temp.length * 2));
	sprite.setCurrImg(temp[sprite.getForm() / 2]);
	
	if (dir == Sprite.LEFT){
	    sprite.changeXY(-2, 0);
	}
	if (dir == Sprite.RIGHT){
	    sprite.changeXY(2,0);
	}
	sprite.update();
    }

    public static void unitMoveY(Sprite sprite, int dir){
	Image[] temp = sprite.getImgs(dir);
	sprite.setDir(dir);
	sprite.setForm((sprite.getForm() + 1) % (temp.length * 2));
	sprite.setCurrImg(temp[sprite.getForm() / 2]);

	if (dir == Sprite.UP){
	    sprite.changeXY(0, -1);
	}
	if (dir == Sprite.DOWN){
	    sprite.changeXY(0, 1);
	}
	sprite.update();
    }

    public static void stop(Sprite sprite, int form){
	Image[] temp = sprite.getImgs(sprite.getDir());
	sprite.setCurrImg(temp[form]);
	sprite.setForm(form);
	sprite.update();
	sprite.setForm(0);
    }

    
}
