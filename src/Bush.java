import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.File;

import java.lang.Cloneable;
import java.lang.CloneNotSupportedException;
import java.util.LinkedList;

public class Bush extends GenericTile implements Slashable, Collideable{

    
    //private ImageView img_v;

    public Bush(double x, double y){
	super("Bush.png", x, y);
	img_v.setFitHeight(width);
	height = width;
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

    public <T> void slashed(Player player, LinkedList<T> tiles, Pane pane){

	if (tiles.get(0) instanceof GenericTile){
	
	    GenericTile new_g = new GenericTile("Grass.png", getX(), getY());
	    ((LinkedList<GenericTile>)tiles).add(new_g);
	    pane.getChildren().remove(getImageView());
	    pane.getChildren().add(new_g.getImageView());
	    ((LinkedList<GenericTile>)tiles).remove(this);
	}
    }

    public void collided(Collideable c, int dir){
	/*
	if (c instanceof Sprite){
	    Movement.knockback((Sprite)c, dir, 2);
	    System.out.println("Sprite collided with bush");
	}
	*/
    }

    public Bush clone(){
	Bush clone = new Bush(x_pos, y_pos);
	return clone;
    }
    
}
