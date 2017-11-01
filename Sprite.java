import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.File;
public abstract class Sprite{
    protected Image[] right_imgs;
    protected Image[] left_imgs;
    protected Image[] up_imgs;
    protected Image[] down_imgs;
    protected double x_pos;
    protected double y_pos;
    protected ImageView img_v;
    protected int dir;
    protected final int UP = 0;
    protected final int RIGHT = 1;
    protected final int DOWN = 2;
    protected final int LEFT = 3;
    

    
    public Sprite(String sprite_dir){
	File right_f = new File("img/" + sprite_dir + "/MovingRight/");
	File left_f = new File("img/" + sprite_dir + "/MovingLeft/");
	File up_f = new File("img/" + sprite_dir + "/MovingUp/");
	File down_f = new File("img/" + sprite_dir + "/MovingDown/");
	int num_imgs = right_f.list().length;
	right_imgs = new Image[num_imgs];
	left_imgs = new Image[num_imgs];
	up_imgs = new Image[num_imgs];
	down_imgs = new Image[num_imgs];
	for (int i = 0; i < num_imgs; i++){

	    right_imgs[i] = new Image("img/" + sprite_dir + "/MovingRight/" + i + ".png");
	    left_imgs[i] = new Image("img/" + sprite_dir + "/MovingLeft/" + i + ".png");
	    up_imgs[i] = new Image("img/" + sprite_dir + "/MovingUp/" + i + ".png");
	    down_imgs[i] = new Image("img/" + sprite_dir + "/MovingDown/" + i + ".png");
	    
	}
    }

    
}
