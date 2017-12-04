import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.File;

public class Bush extends GenericTile implements Slashable, Collideable{

    private double height;
    private double width;
    //private ImageView img_v;

    public Bush(double x, double y){
	super("Bush.png", x, y);
    }

    public ImageView getImageView(){
	return img_v;
    }
    
    public double getWidth(){
	return width;
    }

    public double getHeight(){
	return height;
    }

    public void slashed(){
	img_v.setImage(new Image("img/Tiles/Grass.png"));
    }

    public void collided(){
	// do nothing
    }
    
}
