import javafx.scene.image.ImageView;
public interface Collideable{

    public double getX();
    public double getY();
    public double getWidth();
    public double getHeight();
    public void collided(Collideable c, int dir);
    public ImageView getImageView();
}
