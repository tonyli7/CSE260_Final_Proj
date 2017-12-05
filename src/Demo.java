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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Scanner;

public class Demo extends Application {

    public static Location curr_location = new Location();
    //public static LinkedList<GenericTile> curr_tiles = new LinkedList<GenericTile>();
    //public static LinkedList<Monster> curr_mons = new LinkedList<Monster>();
    public static HashSet<Location> world = new HashSet<Location>();
    
    public static Pane pane = new Pane();

    @Override
    public void start(Stage primaryStage) {
	Random rand = new Random();

	//Pane pane = new Pane();
	Player player = new Player("Link", "Link", 400, 300);
	
	
	loadLocations();
	//loadMonsters("maps/Map0/Monsters0");
	pane.getChildren().addAll(player.getWeapon().getImageView(), player.getImageView());


	//animation.play();
	Scene scene = new Scene(pane, 800, 600, Color.BLACK);
	setKeyPressed(scene);
	setKeyReleased(scene);
	

	KeyComs key_coms = new KeyComs(player);
	MonsterComs mon_coms = new MonsterComs(curr_location.getMonsters(), player);
	System.out.println(curr_location.getMonsters().get(0).getSteps());
	key_coms.start();
	mon_coms.start();
	
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
	//System.out.println(temp);
	for (GenericTile t: temp){
	    pane.getChildren().remove(t.getImageView());
	}

	try{
	    curr_location = (Location)location.clone();
	}catch(CloneNotSupportedException ex){
	    System.out.println(ex.getMessage());
	}
	
	temp = curr_location.getMap();
	//System.out.println(temp);
	//curr_tiles = (LinkedList<GenericTile>)temp.clone();
	for (GenericTile t: temp){
	    
	    pane.getChildren().add(t.getImageView());
	}

	LinkedList<Monster> mons = curr_location.getMonsters();
	//curr_mons = (LinkedList<Monster>)mons.clone();
	for (Monster m: mons){
	    pane.getChildren().add(m.getImageView());
	}
	

    }

    private static LinkedList<GenericTile> convertMap(LinkedList<Tile> map){
	LinkedList<GenericTile> temp = new LinkedList<GenericTile>();
	for (Tile t: map){
	    String name = t.toString();
	    GenericTile new_t = new GenericTile(name, t.getX(), t.getY());
	    
	    if (name.equals("Bush.png")){
		
		new_t = new Bush(t.getX(), t.getY());
			
	    }
	    if (name.equals("Block.png") || name.equals("Stump.png")){
		
		new_t = new GenericBlock(name, t.getX(), t.getY());
			
	    }

	    
	    
	    temp.add(new_t);
	}
	return temp;
    }
    

    public static void loadLocations(){

	File map_dir = new File("maps/");
	String[] maps = map_dir.list();
	int num_maps = maps.length;

	for (int i = 0; i < num_maps; i++){
	    String s = maps[i];
	    LinkedList<Tile> tiles = new LinkedList<Tile>();
		
	    try{
		tiles = readFile("maps/" + s + "/" + s + ".bin");
		
	    }catch (IOException | ClassNotFoundException ex){
		System.out.println(ex.getMessage());
	    }

	  
	    Location new_loc = new Location(convertMap(tiles), loadMonsters("maps/" + s + "/Monsters" + i));
	    
	    if (world.isEmpty()){

		try{
		    curr_location = (Location)new_loc.clone();
		}catch(CloneNotSupportedException ex){
		    System.out.println(ex.getMessage());
		}
		//curr_tiles = (LinkedList<GenericTile>)new_loc.getMap().clone();
		loadMap(new_loc, pane);
	    }
	    world.add(new_loc);
	}
    }

    public static LinkedList<Monster> loadMonsters(String file_path){
	LinkedList<Monster> monsters = new LinkedList<Monster>();
	
	File mon_f = new File(file_path);
	Scanner scn = new Scanner(System.in);
	try{
	    scn = new Scanner(mon_f);
	}catch (FileNotFoundException ex){
	    System.out.println(ex.getMessage());
	    return monsters;
	}

	while (scn.hasNextLine()){
	    String[] toks = scn.nextLine().split(" ");
	    Monster monster = new Monster(toks[0], Integer.parseInt(toks[3]), toks[0], Integer.parseInt(toks[1]), Integer.parseInt(toks[2]));
	    monster.setDirPattern(convertDirPattern(toks[4]));
	    
	    monster.setSteps(Integer.parseInt(toks[5]));
	    //System.out.println(monster.getSteps());
	    monsters.add(monster);
	}
	return monsters;
	
    }

    public static ArrayList<Integer> convertDirPattern(String dir_pattern){

	ArrayList<Integer> pattern = new ArrayList<Integer>();

	for (int i = 0; i < dir_pattern.length(); i++){
	    String s = dir_pattern.substring(i, i + 1);
	    if (s.equals("U")){
		pattern.add(Sprite.UP);
	    }
	     if (s.equals("R")){
		pattern.add(Sprite.RIGHT);
	    }
	     if (s.equals("D")){
		pattern.add(Sprite.DOWN);
	    }
	     if (s.equals("L")){
		pattern.add(Sprite.LEFT);
	    }
	}
	return pattern;
	
    }
}
