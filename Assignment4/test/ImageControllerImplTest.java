import static org.junit.Assert.assertEquals;

import ImageController.ImageController;
import ImageController.ImageControllerImpl;
import ImageModel.ImageModel;
import ImageModel.ImageModelImpl;
import ImageView.ImageView;
import Mocks.MockImageModel;
import Mocks.MockImageView;
import java.io.StringReader;
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

  @Test
  public void testInputsWithMocks() {
    StringBuilder viewLog1 = new StringBuilder();
    StringBuilder modelLog1 = new StringBuilder();
    StringReader inputs = new StringReader("");
    MockImageModel mockModel1 = new MockImageModel(modelLog1);
    MockImageView mockView1 = new MockImageView(mockModel1, viewLog1);
    this.controller1 = new ImageControllerImpl(mockModel1, mockView1);

  }
}