import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class Demo extends Application {
    private int x = 0;
    @Override
    public void start(Stage primaryStage) {
	Pane pane = new Pane();
	
	Image[] right_imgs = new Image[7];
	for(int i = 0; i < 7; i++){
	    right_imgs[i] = new Image("img/Link/MovingRight/" + i + ".png");
	}
	
	ImageView imageView = new ImageView(right_imgs[x]);
	imageView.setFitHeight(34.5);
	imageView.setFitWidth(24);
	
	pane.getChildren().add(imageView);

	Scene scene = new Scene(pane, 800, 600);

	scene.setOnKeyPressed(e -> {
		KeyCode key = e.getCode();
		if (key == KeyCode.RIGHT){
		    increment();
		    imageView.setImage(right_imgs[x % 7]);
		    imageView.setX(3*x);
		    System.out.println(x);
		}
	    });
	
	primaryStage.setScene(scene); primaryStage.show();
	primaryStage.show();

    }

    public void increment(){
	x++;
    }

}
