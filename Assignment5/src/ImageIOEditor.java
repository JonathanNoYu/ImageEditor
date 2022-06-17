import controller.ImageController;
import controller.ImageIOController;
import model.ImageIOModel;
import model.ImageModelV2;
import view.ImageTextViewImpl;
import view.ImageView;

/**
 * Image editor that uses ImageIO and can convert ppm images into jpg, png, bmp and any image files
 * that ImageIO can write.
 */
public class ImageIOEditor {

  /**
   * Main method that runs an empty ImageIO MVC image editor
   *
   * @param args does not matter as it is never used.
   */
  public static void main(String[] args) {
    ImageModelV2 model = new ImageIOModel();
    ImageView view = new ImageTextViewImpl(model);
    ImageController controller = new ImageIOController(model, view);
    controller.processImage();
  }
}
