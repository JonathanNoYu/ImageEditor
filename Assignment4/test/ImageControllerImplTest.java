import static org.junit.Assert.assertEquals;
import ImageController.ImageControllerImpl;
import ImageController.ImageController;
import ImageModel.ImageModelImpl;
import ImageModel.ImageModel;
import ImageView.ImageView;
import org.junit.Test;

public class ImageControllerImplTest {
  ImageController controller1;
  ImageController controller2;
  ImageController controller3;
  ImageController controller4;
  ImageView view1;
  ImageView view2;
  ImageModel model1;
  ImageModel model2;

  @Test
  public void processImage() {
  }

  @Test
  public void testInvalidControllers() {
    this.model1 = new ImageModelImpl("images/FakeImage.ppm", "Fake-Image");

    try {
      this.controller1 = new ImageControllerImpl(null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new ImageControllerImpl(this.model1, null);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }
  }
}