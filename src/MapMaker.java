import javafx.application.Application;
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
import java.io.File;
public class MapMaker extends Application{

    private static boolean deselected = true;
    
    @Override
    public void start(Stage primaryStage){

	//GridPane map = new GridPane();
	Pane map = new Pane();

	File tile_f = new File("img/Tiles/Outside/");
	String[] tiles = tile_f.list();
	int num_tiles = tiles.length;

	Cursor default_cursor = map.getCursor();
	
	//ImageView mouse_tile = new ImageView("img/Tiles/Outside/Bush.png");
	//boolean deselected = true;
	
	// Tile placement grid;
	for (int row = 0; row < 608/32; row++){ // 19
	    for (int col = 0; col < 800/32; col++){ // 25
		Rectangle rect = new Rectangle(col*32, row*32, 32, 32);
		rect.setFill(Color.WHITE);
		rect.setStroke(Color.BLACK);

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

		rect.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY &&
			    !deselected &&
			    !(rect.getFill() instanceof ImagePattern)){
			    
			    rect.setFill(new ImagePattern(((ImageCursor)map.getCursor()).getImage()));
			    
			}else if (e.getButton() == MouseButton.SECONDARY &&
				  deselected){
			    
			    rect.setFill(Color.WHITE);
			    
			}
			
		    });
		map.getChildren().add(rect);
		
	    }
	}
	
	map.setOnMouseClicked(e -> {
		if (e.getButton() == MouseButton.SECONDARY){
		    map.setCursor(default_cursor);
		    deselected = true;
		    System.out.println(deselected);
		}
	    });
	for (int i = 0; i < num_tiles; i++){
	    Tile tile = new Tile(tiles[i], "Outside");
	    
	    ImageView img_v = new ImageView(new Image(tile.getPath()));
	    img_v.setX(25*32 + 30);
	    img_v.setY(i * 32 + i*20);
	    
	    img_v.setFitHeight(32);
	    img_v.setFitWidth(32);
	    
	    img_v.setOnMouseClicked(e -> {
		    map.setCursor(new ImageCursor(img_v.getImage(), 16, 16));
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

	    
	    map.getChildren().add(img_v);
	}
	
	Scene scene = new Scene(map, 900, 608);

	primaryStage.setScene(scene);
	primaryStage.show();	
    }

    public static void setMouseTile(ImageView mouse_tile, ImageView img_v){
	Image img = img_v.getImage();
	mouse_tile = new ImageView(img);
	mouse_tile.setFitHeight(32);
	mouse_tile.setFitWidth(32);
    }
}
