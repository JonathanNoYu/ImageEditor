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
  public void testAllWithMocks() {
    StringBuilder viewLog1 = new StringBuilder();
    StringBuilder modelLog1 = new StringBuilder();
    StringReader inputs1 = new StringReader("exit");
    StringBuilder output1 = new StringBuilder();
    MockImageModel mockModel1 = new MockImageModel(modelLog1);
    MockImageView mockView1 = new MockImageView(mockModel1, viewLog1, output1);
    this.controller1 = new ImageControllerImpl(mockModel1, mockView1, inputs1);
    this.controller1.processImage();

    // Testing the starting and ending message
    String instructions = "To load an image, please enter "
        + "\'load <THE IMAGE PATH> <THE IMAGE ALIAS>\'"
        + "The alis is the name we will refer to the image by. \n"
        + "Once an image is loaded you may augment it through greyscale, brightening and "
        + "darkening it but also flipping it horizontally or vertically\n"
        + "The loaded image would not be modified. \n"
        + "To save an image, please enter \'save <THE DESIRED IMAGE PATH> <THE IMAGE ALIAS>\'";
    String endMessage = "Thanks for using our Image editor!";
    String[] outputArray1 = output1.toString().split(System.lineSeparator());
    assertEquals(instructions, outputArray1[0]);
    assertEquals(endMessage, outputArray1[1]);

    // Checking the inputs to the model and view
    String desiredModelLog1 = "";
    String desiredViewLog1 = "renderMessage Method Called Msg: To" + System.lineSeparator()
        + "renderMessage Method Called Msg: Thanks" + System.lineSeparator();
    assertEquals(desiredModelLog1, modelLog1.toString());
    assertEquals(desiredViewLog1, viewLog1.toString());
  }
}