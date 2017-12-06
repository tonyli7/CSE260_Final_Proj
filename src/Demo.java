import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.animation.KeyFrame;
import javafx.scene.input.KeyCode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;

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
import java.util.HashMap;
import java.util.Scanner;

public class Demo extends Application {

    public static Location curr_location = new Location();
    public static ArrayList<Location> world = new ArrayList<Location>();
    public static Pane pane = new Pane();
    public static MyScreen splash_screen = new MyScreen(loadScreen("screens/Splash", 3), 0);
    public static MyScreen menu_screen = new MyScreen(loadScreen("screens/Menu", 3), 0);
    public static MyScreen over_screen = new MyScreen(loadScreen("screens/Over", 2), 0);
    
    public static Rectangle dmg_rect = new Rectangle(0, 0, 800, 600);
    public static Scene scene;
    
    public static final int SPLASH = 0;
    public static final int SAVES = 1;
    public static final int GAME = 2;
    public static final int MENU = 3;
    public static final int HELP = 4;
    public static final int OVER = 5;
    public static int GAME_STATE;

    @Override
    public void start(Stage primaryStage) {
	//Pane pane = new Pane();
	Player player = new Player("Link", "Link", 400, 300);
	
	//newGame(player);
	scene = new Scene(splash_screen.getPane(), 800, 608, Color.BLACK);

	GAME_STATE = SPLASH;
	//scene.setRoot(splash_pane);
	setKeyPressed(scene);
	setKeyReleased(scene);
	

	KeyComs key_coms = new KeyComs(player);
	MonsterComs mon_coms = new MonsterComs(player);
	
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

	LinkedList<GenericTile> temp;
	
	if (curr_location.getName() != null){
	    temp = curr_location.getMap();
	    
	    for (GenericTile t: temp){
		pane.getChildren().remove(t.getImageView());
	    }

	    LinkedList<Monster> mons = curr_location.getMonsters();
	    for (Monster m: mons){
		pane.getChildren().remove(m.getImageView());
	    }
	    
	}
	try{
	    curr_location = (Location)location.clone();
	}catch(CloneNotSupportedException ex){
	    System.out.println(ex.getMessage());
	}
	
	temp = curr_location.getMap();

	for (GenericTile t: temp){
	    ImageView v = t.getImageView();
	    v.setOnMouseClicked(e -> {
		    System.out.println(v.getX() + " , " + v.getY());
		});
	    pane.getChildren().add(v);
	    v.toBack();
	}

	LinkedList<Monster> mons = curr_location.getMonsters();
	
	for (Monster m: mons){
	    pane.getChildren().add(m.getImageView());
	}

	curr_location.setLinks(location.getLinks());
	

    }

    private static LinkedList<GenericTile> convertMap(LinkedList<Tile> map){
	LinkedList<GenericTile> temp = new LinkedList<GenericTile>();
	for (Tile t: map){
	    String name = t.getName();
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

	  
	    Location new_loc = new Location(convertMap(tiles), loadMonsters("maps/" + s + "/Monsters" + i), s);
	    
	   
	    world.add(new_loc);
	}

	for (int i = 0; i < num_maps; i++){
	    String s = maps[i];

	    File links_f = new File("maps/" + s + "/Links" + i);

	    Scanner scn = new Scanner(System.in);
	    try{
		scn = new Scanner(links_f);
	    }catch (FileNotFoundException ex){
		System.out.println(ex.getMessage());
		
	    }
	    

	    HashMap<Cors, Location> links = new HashMap<Cors, Location>();
	    
	    while(scn.hasNextLine()){
		String[] toks = scn.nextLine().split(" ");
		

		links.put(new Cors(Integer.parseInt(toks[1]), Integer.parseInt(toks[2])) , getLocation(toks[0]));
		
	    }

	    getLocation(s).setLinks(links);
	    
	}
	loadMap(getLocation("Map0"), pane);
	
	
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

    public static void loadPlayer(Player player, Pane pane){
	pane.getChildren().addAll(player.getWeapon().getImageView(),
				  player.getImageView());


	ImageView[] hearts = player.getHearts();
	for(int i = 0; i < hearts.length; i++){
	    pane.getChildren().add(hearts[i]);
	    
	}
    }

    public static Location getLocation(String name){
	Location temp = new Location();
	for (int i = 0; i < world.size(); i++){
	    temp = world.get(i);
	    if (temp.getName().equals(name)){
		return temp;
	    }
	}
	return temp;
    }

    public static MyButton[] loadScreen(String screen_path, int num_buttons){
	File screen_f = new File(screen_path);

	Scanner scn = new Scanner(System.in);
	try{
	    scn = new Scanner(screen_f);
	}catch (FileNotFoundException ex){
	    System.out.println(ex.getMessage());
	}

	MyButton[] buttons = new MyButton[num_buttons];
	int i = 0;
	while (scn.hasNextLine()){
	    String[] toks = scn.nextLine().split(",");
	    buttons[i] = new MyButton(toks[0], Integer.parseInt(toks[1]), 325, i*100 + 200);
	    i++;    
	}
	return buttons;
    }

    public static MyScreen getScreen(int state){
	if (GAME_STATE == SPLASH){
	    return splash_screen;
	}
	if (GAME_STATE == MENU){
	    return menu_screen;
	}
	if (GAME_STATE == OVER){
	    return over_screen;
	}
	return splash_screen;
    }

    public static void newGame(Player player){
	pane.getChildren().clear();
	loadLocations();
	loadPlayer(player, pane);
	
	RadialGradient rg = new RadialGradient(0, .1, 400, 300, 500, false, CycleMethod.NO_CYCLE, new Stop(0, Color.TRANSPARENT), new Stop(1, Color.RED));
	
	dmg_rect.setFill(rg);
	dmg_rect.setOpacity(.4);
	pane.getChildren().add(dmg_rect);
	dmg_rect.setVisible(false);
    }
}
