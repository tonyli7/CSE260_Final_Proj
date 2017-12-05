import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.File;
import java.lang.Cloneable;
import java.lang.CloneNotSupportedException;

public class GenericTile extends Tile{

    protected ImageView img_v;
    protected double height;
    protected double width;
    
    public GenericTile(String name, double x, double y){
	super(name);
	Image img = new Image("img/Tiles/" + name);
	img_v = new ImageView(img);

	x_pos = x;
	y_pos = y;

	width = img.getWidth() * 2;
	height = img.getHeight() * 2;
	
	img_v.setFitWidth(width);
	img_v.setFitHeight(height);

	img_v.setX(x_pos);
	img_v.setY(y_pos);
    }

    public ImageView getImageView(){
	return img_v;
    }

    public GenericTile clone() throws CloneNotSupportedException{
	GenericTile clone = new GenericTile(name, x_pos, y_pos);
	return clone;
    }
  
    
}
