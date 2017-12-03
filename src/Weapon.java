import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import java.io.File;

public class Weapon{

    private ImageView img_v;
    private Rotate rotation;
    public Weapon(){

	Image img = new Image("img/Link/Items/Equips/sword.png");
	img_v = new ImageView(img);
	img_v.setFitHeight(img.getHeight() * 1.8);
	img_v.setFitWidth(img.getWidth());
	
	rotation = new Rotate();
	img_v.getTransforms().add(rotation);
	rotation.setAngle(90);
	
    }

    public ImageView getImageView(){
	return img_v;
    }

    public Rotate getRotation(){
	return rotation;
    }

    public void setX(double new_x){
	img_v.setX(new_x);
    }

    public void setY(double new_y){
	img_v.setY(new_y);
    }

    public void display(boolean isVisible){
	img_v.setVisible(isVisible);
    }
}
