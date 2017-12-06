import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Pikachu{

    private static final int PONG = 0;
    private static final int BULLET = 0;
    private static final int FIELD = 0;
    
    
    private int health;
    private boolean stunned;
    private ImageView img_v;
    private double x_pos;
    private double y_pos;
    private double height;
    private double width;

    private Image[] pong_imgs;
    private Image[] pong_attack_imgs;

    private Image[] bullet_imgs;
    private Image[] bullet_attack_imgs;

    private Image[] field_imgs;
    private Image[] field_attack_imgs;

    private Image[] intro;

    public Pikachu(){
    }

    
    
    
}
