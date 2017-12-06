import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.input.MouseButton;
import javafx.scene.input.KeyCode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.lang.CloneNotSupportedException;
import java.lang.SecurityException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MapMaker extends Application{

    private static boolean deselected = true;
    private static boolean coloring = false;
    
    private static HashMap<Rectangle, Tile> map = new HashMap<Rectangle, Tile>();
    
    private static Tile curr_tile = new Tile();

    private static File tile_f = new File("img/Tiles/");
    private static String[] tiles = tile_f.list();
    private static int num_tiles = tiles.length;
    
    @Override
    public void start(Stage primaryStage){

	Pane pane = new Pane();
	Scene scene = new Scene(pane, 900, 608);

	scene.setOnKeyPressed(e ->{
		if (e.getCode() == KeyCode.Z && !coloring){
		    coloring = true;
		}else if (e.getCode() == KeyCode.Z && coloring){
		    coloring = false;
		}
		
	    });
	

	Cursor default_cursor = pane.getCursor();

	gridSetup(pane); // sets up an empty grid
	tileSetup(pane); // sets up tiles that can be selected
	
	// Allows deselecting of a tile by right-clicking
	pane.setOnMouseClicked(e -> {
		if (e.getButton() == MouseButton.SECONDARY){
		    pane.setCursor(default_cursor);
		    deselected = true;
		    coloring = false;
		    curr_tile = new Tile();
		}
	    });

	

	// Button that fills all white space with grass
	Rectangle fillAll = new Rectangle(25*32 + 30, 540, 32, 32);
	fillAll.setFill(Color.BLACK);
	fillAll.setOnMouseClicked(e -> {
		fillDefault();
		
	    });

	// Button that converts the map into a HashSet,
	// serializes the HashSet, and exits the map maker
	
	Rectangle done = new Rectangle(25*32 + 30, 576, 32, 32);
	done.setFill(Color.BLUE);
	done.setOnMouseClicked(e ->{
		setXY();

		try{
     
		    writeToFile(convertToLinkedList());
		}catch (IOException ex){
		    System.out.println(ex.getMessage());
		}

		Platform.exit();
	    });
	
	pane.getChildren().addAll(fillAll, done);


	primaryStage.setScene(scene);
	primaryStage.show();	
    }

    private static void gridSetup(Pane pane){
	// Tile placement grid;
	for (int row = 0; row < 608/32; row++){ // 19
	    for (int col = 0; col < 800/32; col++){ // 25
		Rectangle rect = new Rectangle(col*32, row*32, 32, 32);
		rect.setFill(Color.WHITE);
		rect.setStroke(Color.BLACK);

		// Hover functions
		/*
		rect.setOnMouseEntered(e ->{
			if (rect.getFill().equals(Color.WHITE)){
			    rect.setFill(Color.ORANGE);
			}
		    });

		
		rect.setOnMouseExited(e -> {
			if (rect.getFill().equals(Color.ORANGE)){
			    rect.setFill(Color.WHITE);
			}
		    });
		
		*/
		// Clicking functions
		rect.setOnMouseEntered(e -> {
			
			if (coloring &&
			    !deselected){
			    
			    rect.setFill(new ImagePattern(((ImageCursor)pane.getCursor()).getImage()));
			    try{
				map.put(rect, curr_tile.clone());
			    }catch(CloneNotSupportedException ex){
				System.out.println(ex.getMessage());
			    }
			    
			    
			}
		    });

		rect.setOnMouseClicked(e ->{
			if (!deselected){
			     rect.setFill(new ImagePattern(((ImageCursor)pane.getCursor()).getImage()));
			    try{
				map.put(rect, curr_tile.clone());
			    }catch(CloneNotSupportedException ex){
				System.out.println(ex.getMessage());
			    }
			    
			    
			}else if (e.getButton() == MouseButton.SECONDARY &&
				  deselected){

			    
			    rect.setFill(Color.WHITE);
			    map.put(rect, new Tile());
			    
			}
			
		    });

		map.put(rect, new Tile());
		pane.getChildren().add(rect);
		
	    }
	}


    }

    private static void tileSetup(Pane pane){
	for (int i = 0; i < num_tiles; i++){
	    Tile tile = new Tile(tiles[i]);
	    
	    ImageView img_v = new ImageView(new Image(tile.getPath()));
	    img_v.setX(25*32 + 30);
	    img_v.setY(i * 32 + i*10);
	    
	    img_v.setFitHeight(32);
	    img_v.setFitWidth(32);
	    
	    img_v.setOnMouseClicked(e -> {
		    pane.setCursor(new ImageCursor(img_v.getImage(), 16, 16));
		    curr_tile = tile;
		    deselected = false;
		});

	    img_v.setOnMouseEntered(e -> {
		    img_v.setFitHeight(40);
		    img_v.setFitWidth(40);
		});

	    img_v.setOnMouseExited(e -> {
		    img_v.setFitHeight(32);
		    img_v.setFitWidth(32);
		});

	    
	    pane.getChildren().add(img_v);
	}
    }

    // helper method for fill-all-white-space button
    private static void fillDefault(){
	for (Rectangle r: map.keySet()){
	    if (map.get(r).getPath().equals("")){
		r.setFill(new ImagePattern(new Image("img/Tiles/Grass.png")));
		map.put(r, new Tile("Grass.png"));
	    }
	}
    }


    // helper method for quit-save button
    // sets the (x,y) cors for each tile in the HashMap
    private static void setXY(){
	for (Rectangle r: map.keySet()){
	    map.get(r).setX(r.getX());
	    map.get(r).setY(r.getY());
	}
    }

    // converts HashSet into a bin
    private static void writeToFile(LinkedList<Tile> final_map) throws IOException{
	
	 File map_dir = new File("maps/");
	 String[] maps = map_dir.list();
	 int num_maps = maps.length;

	 File new_dir = new File("maps/Map" + num_maps);
	 try{
	     new_dir.mkdir();
	     
	 }catch (SecurityException ex){
	     System.out.println(ex.getMessage());
	 }
	
	ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("maps/Map" + num_maps + "/Map" + num_maps + ".bin"));
	outStream.writeObject(final_map);
	
    }

    // converts HashMap<Rectangle, Tile> into LinkedList<Tile>
    private static LinkedList<Tile> convertToLinkedList(){
	LinkedList<Tile> final_map = map.entrySet().stream().map(Map.Entry:: getValue).collect(Collectors.toCollection(LinkedList::new));

	//System.out.println(final_map);
	return final_map;
    }
}
