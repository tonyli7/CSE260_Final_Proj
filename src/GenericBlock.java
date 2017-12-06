import javafx.scene.image.ImageView;
import java.lang.Cloneable;
import java.lang.CloneNotSupportedException;

public class GenericBlock extends GenericTile implements Collideable, Cloneable{

    
    public GenericBlock(String name, double x, double y){
	super(name, x, y);
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
    public void collided(Collideable c, int dir){
	// do nothing
    }
    public ImageView getImageView(){
	return img_v;
    }

    public GenericBlock clone() throws CloneNotSupportedException{
	GenericBlock clone = new GenericBlock(name, x_pos, y_pos);
	return clone;
    }
    
}
