import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.Serializable;
import java.io.File;
import java.lang.CloneNotSupportedException;

public class Tile implements Serializable, Cloneable{

    //private ImageView img_v;
    //private Image img;
    protected String img_path;
    protected String name;
    protected double x_pos;
    protected double y_pos;

    public Tile(){
	img_path = "";
    }
    
    public Tile(String name, String setting){
	img_path = "img/Tiles/" + setting + "/" + name;
	this.name = name;
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

    public double getX(){
	return x_pos;
    }

    public double getY(){
	return y_pos;
    }
    
    public Tile clone() throws CloneNotSupportedException{
	return (Tile)super.clone();
    }

    public String toString(){
	return name;
    }
    
    
}
