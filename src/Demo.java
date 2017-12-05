import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.scene.input.KeyCode;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Random;
import java.util.LinkedList;
import java.util.HashSet;

public class Demo extends Application {

    public static Location curr_location = new Location();
    public static LinkedList<GenericTile> curr_tiles = new LinkedList<GenericTile>();
    public static HashSet<Location> world = new HashSet<Location>();
    
    public static Pane pane = new Pane();
    @Override
    public void start(Stage primaryStage) {
	Random rand = new Random();

	//Pane pane = new Pane();
	Player player = new Player("Link", "Link", 400, 300);
	
	
	loadLocations();
	pane.getChildren().addAll(player.getWeapon().getImageView(), player.getImageView());


	//animation.play();
	Scene scene = new Scene(pane, 800, 600, Color.BLACK);
	setKeyPressed(scene);
	setKeyReleased(scene);
	

	KeyComs key_coms = new KeyComs(player);
	key_coms.start();

	primaryStage.setScene(scene);
	primaryStage.show();

    }


    public void setKeyPressed(Scene scene){
	
	scene.setOnKeyPressed(e -> {
		KeyCode key = e.getCode();
		if (key == KeyCode.RIGHT){
		    KeyComs.right_pressed = true;
		    
		    
		}
		if (key == KeyCode.LEFT){
		    KeyComs.left_pressed = true;
		    
		}
		if (key == KeyCode.UP){
		    KeyComs.up_pressed = true;

		    
		}
		if (key == KeyCode.DOWN){
		    KeyComs.down_pressed = true;
		    
		}
		if (key == KeyCode.Z){
		    KeyComs.z_pressed = true;
		    
		}
		if (key == KeyCode.ENTER){
		    KeyComs.enter_pressed = true;
		    
		}
		if (key == KeyCode.SHIFT){
		    KeyComs.shift_pressed = true;
		    
		}

	    });
    }

    public void setKeyReleased(Scene scene){
	scene.setOnKeyReleased(e -> {
		KeyCode key = e.getCode();
		if (key == KeyCode.RIGHT){
		    KeyComs.right_pressed = false;

		}
		if (key == KeyCode.LEFT){
		    KeyComs.left_pressed = false;

		}
		if (key == KeyCode.UP){
		    KeyComs.up_pressed = false;
		}
		if (key == KeyCode.DOWN){
		    KeyComs.down_pressed = false;
		}
		if (key == KeyCode.Z){
		    KeyComs.z_pressed = false;
		    
		}
		if (key == KeyCode.ENTER){
		    KeyComs.enter_pressed = false;
		    
		}
		if (key == KeyCode.SHIFT){
		    KeyComs.shift_pressed = false;
		    
		}
		
	    });
    }

    public static LinkedList<Tile> readFile(String map_file) throws IOException, ClassNotFoundException{
	ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(map_file));

	LinkedList map = (LinkedList)inStream.readObject();
	return map;
    }

    public static void loadMap(Location location, Pane pane){

	LinkedList<GenericTile> temp = curr_location.getMap();
	for (GenericTile t: temp){
	    pane.getChildren().remove(t.getImageView());
	}

	temp = location.getMap();

	curr_tiles = (LinkedList<GenericTile>)temp.clone();
	for (GenericTile t: temp){
	    pane.getChildren().add(t.getImageView());
	}
	
	curr_location = location;
    }

    private static LinkedList<GenericTile> convertMap(LinkedList<Tile> map){
	LinkedList<GenericTile> temp = new LinkedList<GenericTile>();
	for (Tile t: map){
	    String name = t.toString();
	    GenericTile new_t = new GenericTile(name, t.getX(), t.getY());
	    //System.out.println(name);
	    if (name.equals("Bush.png")){
		//System.out.println(1);
		new_t = new Bush(t.getX(), t.getY());
		//System.out.println(new_t);
		
	    }
	    temp.add(new_t);
	}
	return temp;
    }
    

    public static void loadLocations(){

	File map_dir = new File("maps/");
	String[] maps = map_dir.list();
	int num_maps = maps.length;

	for (String s: maps){
	    LinkedList<Tile> tiles = new LinkedList<Tile>();
		
	    try{
		tiles = readFile("maps/" + s + "/" + s + ".bin");
		
	    }catch (IOException | ClassNotFoundException ex){
		System.out.println(ex.getMessage());
	    }

	  
	    Location new_loc = new Location(convertMap(tiles));
	    if (world.isEmpty()){
		curr_location = new_loc;
		curr_tiles = (LinkedList<GenericTile>) new_loc.getMap().clone();
		loadMap(curr_location, pane);
	    }
	    world.add(new_loc);
	}
	
    }
}
