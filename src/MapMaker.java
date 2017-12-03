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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.lang.CloneNotSupportedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MapMaker extends Application{

    private static boolean deselected = true;
    private static HashMap<Rectangle, Tile> map = new HashMap<Rectangle, Tile>();
    
    private static Tile curr_tile = new Tile();

    private static File tile_f = new File("img/Tiles/Outside/");
    private static String[] tiles = tile_f.list();
    private static int num_tiles = tiles.length;
    
    @Override
    public void start(Stage primaryStage){

	Pane pane = new Pane();
	

	Cursor default_cursor = pane.getCursor();

	gridSetup(pane); // sets up an empty grid
	tileSetup(pane); // sets up tiles that can be selected
	// changes the cursor based on selected tile
	pane.setOnMouseClicked(e -> {
		if (e.getButton() == MouseButton.SECONDARY){
		    pane.setCursor(default_cursor);
		    deselected = true;
		    curr_tile = new Tile();
		}
	    });

	


	Rectangle fillAll = new Rectangle(25*32 + 30, 540, 32, 32);
	fillAll.setFill(Color.BLACK);
	fillAll.setOnMouseClicked(e -> {
		fillDefault();
		
	    });

	Rectangle done = new Rectangle(25*32 + 30, 576, 32, 32);
	done.setFill(Color.BLUE);
	done.setOnMouseClicked(e ->{
		setXY();

		try{
		    writeToFile(convertToHashSet());
		}catch (IOException ex){
		    System.out.println(ex.getMessage());
		}

		Platform.exit();
	    });
	
	pane.getChildren().addAll(fillAll, done);
	Scene scene = new Scene(pane, 900, 608);

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

		// Clicking functions
		rect.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY &&
			    !deselected &&
			    !(rect.getFill() instanceof ImagePattern)){
			    
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
	    Tile tile = new Tile(tiles[i], "Outside");
	    
	    ImageView img_v = new ImageView(new Image(tile.getPath()));
	    img_v.setX(25*32 + 30);
	    img_v.setY(i * 32 + i*20);
	    
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
    
    public static void fillDefault(){
	for (Rectangle r: map.keySet()){
	    if (map.get(r).getPath().equals("")){
		r.setFill(new ImagePattern(new Image("img/Tiles/Outside/Grass.png")));
		map.put(r, new Tile("Grass.png","Outside"));
	    }
	}
    }

    public static void setXY(){
	for (Rectangle r: map.keySet()){
	    map.get(r).setX(r.getX());
	    map.get(r).setY(r.getY());
	}
    }

    public static void writeToFile(HashSet<Tile> final_map) throws IOException{
	ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("Map1.bin"));
	outStream.writeObject(final_map);
	
    }

    public static HashSet<Tile> convertToHashSet(){
	HashSet<Tile> final_map = map.entrySet().stream().map(Map.Entry:: getValue).collect(Collectors.toCollection(HashSet:: new));

	//System.out.println(final_map);
	return final_map;
    }
   
}
