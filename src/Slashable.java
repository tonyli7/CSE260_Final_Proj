import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.LinkedList;
public interface Slashable{

    public <T> void slashed(Player player, LinkedList<T> list, Pane pane);
    public double getX();
    public double getY();
    public double getWidth();
    public double getHeight();
    public ImageView getImageView();
}
