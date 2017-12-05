import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.HashMap;

public class Location{
    private LinkedList<GenericTile> map;
    
    private HashMap<GenericTile, Location> links;

    public Location(){
    }
    
    public Location(LinkedList<GenericTile> map){
	this.map = map;
    }
    
    public Location(LinkedList<GenericTile> map, HashMap<GenericTile, Location> links){
	this.map = map;
    }

    public void replace(GenericTile old, GenericTile new_, Pane pane){
	map.add(new_);
	pane.getChildren().add(new_.getImageView());
	pane.getChildren().remove(old.getImageView());
	map.remove(old);
    }

    public LinkedList<GenericTile> getMap(){
	return map;
    }

    public HashMap<GenericTile, Location> getLinks(){
	return links;
    }
    
}
