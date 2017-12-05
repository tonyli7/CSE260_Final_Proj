import javafx.scene.layout.Pane;

import java.util.HashSet;
import java.util.HashMap;

public class Location{
    private HashSet<GenericTile> map;
    private HashMap<GenericTile, Location> links;
    
    public Location(HashSet<GenericTile> map, HashMap<GenericTile, Location> links){
	this.map = map;
    }

    public void replace(GenericTile old, GenericTile new_, Pane pane){
	map.add(new_);
	pane.getChildren().add(new_.getImageView());
	pane.getChildren().remove(old.getImageView());
	map.remove(old);
    }

    public HashSet<GenericTile> getMap(){
	return map;
    }

    public HashMap<GenericTile, Location> getLinks(){
	return links;
    }
    
}
