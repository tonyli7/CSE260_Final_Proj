import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.File;
public abstract class Sprite{

    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    
    protected Image[] right_imgs;
    protected Image[] left_imgs;
    protected Image[] up_imgs;
    protected Image[] down_imgs;
    protected Image curr_img;
    protected double x_pos;
    protected double y_pos;
    protected ImageView img_v;
    protected int dir;
    protected int form;
    protected double height;
    protected double width;
    

    
    public Sprite(String sprite_dir, double start_x, double start_y){
	File right_f = new File("img/" + sprite_dir + "/MovingRight/");
	File left_f = new File("img/" + sprite_dir + "/MovingLeft/");
	File up_f = new File("img/" + sprite_dir + "/MovingUp/");
	File down_f = new File("img/" + sprite_dir + "/MovingDown/");
	int num_imgs = right_f.list().length;
	right_imgs = new Image[num_imgs];
	left_imgs = new Image[num_imgs];
	up_imgs = new Image[num_imgs];
	down_imgs = new Image[num_imgs];

	loadImgs(right_imgs, num_imgs, sprite_dir, "/MovingRight/");
	loadImgs(left_imgs, num_imgs, sprite_dir, "/MovingLeft/");
	loadImgs(up_imgs, num_imgs, sprite_dir, "/MovingUp/");
	loadImgs(down_imgs, num_imgs, sprite_dir, "/MovingDown/");
	
	setXY(start_x, start_y);

	form = 0;
	curr_img = down_imgs[0];
	height = curr_img.getHeight() * 2;
	width = curr_img.getWidth() * 2;
	
	img_v = new ImageView(curr_img);

	
	img_v.setFitHeight(height);
	img_v.setFitWidth(width);
	
	img_v.setX(x_pos);
	img_v.setY(y_pos);

    }

    protected void loadImgs(Image[] imgs,int num, String sprite_dir, String folder){
	for (int i = 0; i < num; i++){
	    imgs[i] = new Image("img/" + sprite_dir + folder + i + ".png");
	    
	}
    }
    
    public void setXY(double new_x, double new_y){
	x_pos = new_x;
	y_pos = new_y;
    }

    public void changeXY(double d_x, double d_y){
	x_pos += d_x;
	y_pos += d_y;
    }

    public void setCurrImg(Image img){
	curr_img = img;
    }

    public void setForm(int form){
	this.form = form;
    }

    public void setDir(int dir){
	this.dir = dir;
    }
    
    public double getX(){
	return x_pos;
    }

    public double getY(){
	return y_pos;
    }

    public int getForm(){
	return form;
    }

    public int getDir(){
	return dir;
    }

    public Image[] getImgs(int dir){
	
	switch(dir){
	case UP: return up_imgs;
	case RIGHT: return right_imgs;
	case DOWN: return down_imgs;
	case LEFT: return left_imgs;
	default: return down_imgs;
	  
	}
    }

    public ImageView getImageView(){
	return img_v;
    }

    public double getHeight(){
	return height;
    }

    public double getWidth(){
	return width;
    }
    
    public void update(){
	img_v.setImage(curr_img);
	img_v.setFitWidth(curr_img.getWidth()*1.5);
	img_v.setFitHeight(curr_img.getHeight()*1.5);
	img_v.setX(x_pos);
	img_v.setY(y_pos);
	

    }
}
