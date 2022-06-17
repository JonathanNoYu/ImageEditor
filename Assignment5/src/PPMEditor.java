
import Util.ImageUtil;
import controller.ImageController;
import controller.PPMImageController;
import java.awt.Color;
import model.ImageModel;
import model.PPMModel;
import view.ImageTextViewImpl;
import view.ImageView;

/**
 * Run this class's main method, and it will run start up an empty, controller, model and view.
 */
public class PPMEditor {

  /**
   * A basic main method that does nothing with the given program arguments because it calls upon
   * a controller to then ask the user through System.in, to input any commands after this method
   * fully runs. There is room for augmentation and possible improvement later.
   * @param args is a throw away argument that does not matter what it is given just yet
   */
  public static void main(String[] args) {
    ImageModel model = new PPMModel();
    ImageView view = new ImageTextViewImpl(model);
    ImageController controller = new PPMImageController(model, view);
    controller.processImage();
  }
}
