import java.util.HashSet;
import java.util.HashMap;


public class Location{
    private HashSet<GenericTile> map;
    private HashMap<GenericTile, Location> links;

    public Location(HashSet<GenericTile> map){
	this.map = map;
    }
    
}
