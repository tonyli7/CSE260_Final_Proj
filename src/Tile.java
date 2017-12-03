import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.Serializable;
import java.io.File;

public class Tile implements Serializable{

    //private ImageView img_v;
    //private Image img;
    protected String img_path;
    protected double x_pos;
    protected double y_pos;
    
    public Tile(String name, String setting){
	img_path = "img/Tiles/" + setting + "/" + name;	
    }

    public String getPath(){
	return img_path;
    }

    public void setX(double new_x){
	x_pos = new_x;
    }

    public void setY(double new_y){
	y_pos = new_y;
    }
    
}
