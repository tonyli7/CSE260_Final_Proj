import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.HashMap;

import java.lang.Cloneable;
import java.lang.CloneNotSupportedException;

public class Location implements Cloneable{
    private LinkedList<GenericTile> map;
    private LinkedList<Monster> monsters;
    private HashMap<GenericTile, Location> links;

    public Location(){
    }
    
    public Location(LinkedList<GenericTile> map){
	this.map = map;
    }

    public Location(LinkedList<GenericTile> map, LinkedList<Monster> monsters){
	this.map = map;
	this.monsters = monsters;
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

    public LinkedList<Monster> getMonsters(){
	return monsters;
    }

    public Location clone() throws CloneNotSupportedException{

	LinkedList<GenericTile> map_copy = new LinkedList<GenericTile>();
	LinkedList<Monster> monsters_copy = new LinkedList<Monster>();

	try{
	    for(GenericTile g: map){

		if (g instanceof Bush){
		
		    map_copy.add((Bush) g.clone());
		}
		if (g instanceof GenericBlock){
		    map_copy.add(((GenericBlock) g).clone());
		}else{
		    
		    map_copy.add((GenericTile) g.clone());
		}
	    }



	    for(Monster m: monsters){
		monsters_copy.add((Monster) m.clone());
	    }
	}catch(CloneNotSupportedException ex){
	    System.out.println(ex.getMessage());
	}
	
	Location clone = new Location(map_copy, monsters_copy);
	return clone;
	
    }

    
}
