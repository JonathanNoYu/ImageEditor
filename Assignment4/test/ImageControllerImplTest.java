import static org.junit.Assert.assertEquals;

import ImageController.ImageController;
import ImageController.ImageControllerImpl;
import ImageModel.ImageModel;
import ImageModel.ImageModelImpl;
import ImageView.ImageTextViewImpl;
import ImageView.ImageView;
import Mocks.MockImageModel;
import Mocks.MockImageView;
import java.io.StringReader;
import org.junit.Test;

/**
 * Testing class of the Image controller. It uses a model mock and a view mock bt
 */
public class ImageControllerImplTest {

  ImageController controller1;
  ImageController controller2;
  ImageView view1;
  ImageModel model1;
  StringReader in1;
  StringBuilder out1;

  @Test
  public void testInvalidControllers() {
    this.model1 = new ImageModelImpl("images/FakeImage.ppm", "Fake-Image");
    this.out1 = new StringBuilder();
    this.view1 = new ImageTextViewImpl(this.model1, out1);
    this.in1 = new StringReader("");

    try {
      this.controller1 = new ImageControllerImpl(null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new ImageControllerImpl(null, null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new ImageControllerImpl(this.model1, null, this.in1);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new ImageControllerImpl(null, this.view1, this.in1);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new ImageControllerImpl(this.model1, this.view1, null);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }
  }

  @Test
  public void testStartingEndMessage() {
    this.model1 = new ImageModelImpl();
    this.out1 = new StringBuilder();
    this.view1 = new ImageTextViewImpl(this.model1, out1);
    this.in1 = new StringReader("exit");
    this.controller1 = new ImageControllerImpl(this.model1, this.view1, this.in1);
    this.controller1.processImage();

    // Testing the starting and ending message
    String instructions = "To load an image, please enter "
        + "'load <THE IMAGE PATH> <THE IMAGE ALIAS>'"
        + "The alis is the name we will refer to the image by. \n"
        + "Once an image is loaded you may augment it through greyscale, brightening and "
        + "darkening it but also flipping it horizontally or vertically\n"
        + "The loaded image would not be modified. \n"
        + "To save an image, please enter 'save <THE DESIRED IMAGE PATH> <THE IMAGE ALIAS>'";
    String endMessage = "Thanks for using our Image editor!";
    String[] outputArray1 = out1.toString().split(System.lineSeparator());
    assertEquals(instructions, outputArray1[0]);
    assertEquals(endMessage, outputArray1[1]);
    // There is nothing really to render on the view as the main implementation is augmenting the
    // image and passing commands to do such argumentation.
  }

  @Test
  public void processAllCommands() {
    // Runs a full command run on the FakeImage3
    this.model1 = new ImageModelImpl();
    this.out1 = new StringBuilder();
    this.view1 = new ImageTextViewImpl(this.model1, out1);
    this.in1 = new StringReader("load images/FakeImage3.ppm Fake3 "
        + "save images/FakeImage3Save.ppm Fake3 "
        + "brighten 50 Fake3 Fake3CBrighten50 " // The C after Fake3 stands for controller
        + "darken 50 Fake3 Fake3CDarken50 "   // This is to discern if the controller works
        + "vertical-flip Fake3 Fake3CVFlip "    // With a normal mock and view.
        + "horizontal-flip Fake3 Fake3CHFlip "
        + "value-component Fake3 Fake3CVGrey "
        + "intensity-component Fake3 Fake3CIGrey "
        + "luma-component Fake3 Fake3CLGrey "
        + "red-component Fake3 Fake3CRGrey "
        + "green-component Fake3 Fake3CGGrey "
        + "blue-component Fake3 Fake3CBGrey "
        + "save images/Fake3CBrighten50.ppm Fake3CBrighten50 "
        + "save images/Fake3CDarken50.ppm Fake3CDarken50 "
        + "save images/Fake3CVFlip.ppm Fake3CVFlip "
        + "save images/Fake3CHFlip.ppm Fake3CHFlip "
        + "save images/Fake3CVGrey.ppm Fake3CVGrey "
        + "save images/Fake3CIGrey.ppm Fake3CIGrey "
        + "save images/Fake3CLGrey.ppm Fake3CLGrey "
        + "save images/Fake3CRGrey.ppm Fake3CRGrey "
        + "save images/Fake3CGGrey.ppm Fake3CGGrey "
        + "save images/Fake3CBGrey.ppm Fake3CBGrey "
        + "exit");
    this.controller1 = new ImageControllerImpl(this.model1, this.view1, this.in1);
    this.controller1.processImage();

    String instructions = "To load an image, please enter "
        + "'load <THE IMAGE PATH> <THE IMAGE ALIAS>'"
        + "The alis is the name we will refer to the image by. \n"
        + "Once an image is loaded you may augment it through greyscale, brightening and "
        + "darkening it but also flipping it horizontally or vertically\n"
        + "The loaded image would not be modified. \n"
        + "To save an image, please enter 'save <THE DESIRED IMAGE PATH> <THE IMAGE ALIAS>'";
    String endMessage = "Thanks for using our Image editor!";
    String[] outputArray2 = out1.toString().split(System.lineSeparator());
    assertEquals(instructions, outputArray2[0]);
    assertEquals(endMessage, outputArray2[1]);
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
        + "'load <THE IMAGE PATH> <THE IMAGE ALIAS>'"
        + "The alis is the name we will refer to the image by. \n"
        + "Once an image is loaded you may augment it through greyscale, brightening and "
        + "darkening it but also flipping it horizontally or vertically\n"
        + "The loaded image would not be modified. \n"
        + "To save an image, please enter 'save <THE DESIRED IMAGE PATH> <THE IMAGE ALIAS>'";
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

    //Testing all inputs/commands
    StringBuilder viewLog2 = new StringBuilder();
    StringBuilder modelLog2 = new StringBuilder();
    StringReader inputs2 = new StringReader("load images/FakeImage.ppm Fake-Image "
        + "brighten 10 Fake-Image Bright-Fake-Image "
        + "darken 10 Fake-Image Darken-Fake-Image "
        + "horizontal-flip Fake-Image HFlip-Image "
        + "vertical-flip Fake-Image VFlip-Fake-Image "
        + "value-component Fake-Image VGrey-Fake-Image "
        + "intensity-component Fake-Image IGrey-Fake-Image "
        + "luma-component Fake-Image LGrey-Fake-Image "
        + "red-component Fake-Image RGrey-Fake-Image "
        + "green-component Fake-Image GGrey-Fake-Image "
        + "blue-component Fake-Image BGrey-Fake-Image "
        + "save images/BrightFakeImage.ppm Bright-Fake-Image "
        + "exit");
    StringBuilder output2 = new StringBuilder();
    MockImageModel mockModel2 = new MockImageModel(modelLog2);
    MockImageView mockView2 = new MockImageView(mockModel2, viewLog2, output2);
    this.controller2 = new ImageControllerImpl(mockModel2, mockView2, inputs2);
    this.controller2.processImage();

    String[] outputArray2 = output2.toString().split(System.lineSeparator());
    assertEquals(instructions, outputArray2[0]);
    assertEquals(endMessage, outputArray2[1]);

    // Each method is seperated by the System.lineSeparator()
    String desiredModelLog2 = "Load Image at Path: images/FakeImage.ppm Alis: Fake-Image"
        + System.lineSeparator()
        + "processCommand Method Called with ImageCommand cmd: brighten "
        + "newImageName: Fake-Image filePath: Bright-Fake-Image"
        + System.lineSeparator()
        + "processCommand Method Called with ImageCommand cmd: darken newImageName: "
        + "Fake-Image filePath: Darken-Fake-Image"
        + System.lineSeparator()
        + "processCommand Method Called with ImageCommand cmd: horizontal-flip newImageName: "
        + "Fake-Image filePath: HFlip-Image"
        + System.lineSeparator()
        + "processCommand Method Called with ImageCommand cmd: vertical-flip newImageName: "
        + "Fake-Image filePath: VFlip-Fake-Image"
        + System.lineSeparator()
        + "processCommand Method Called with ImageCommand cmd: value-component newImageName: "
        + "Fake-Image filePath: VGrey-Fake-Image"
        + System.lineSeparator()
        + "processCommand Method Called with ImageCommand cmd: intensity-component newImageName: "
        + "Fake-Image filePath: IGrey-Fake-Image"
        + System.lineSeparator()
        + "processCommand Method Called with ImageCommand cmd: luma-component newImageName: "
        + "Fake-Image filePath: LGrey-Fake-Image"
        + System.lineSeparator()
        + "processCommand Method Called with ImageCommand cmd: red-component newImageName: "
        + "Fake-Image filePath: RGrey-Fake-Image"
        + System.lineSeparator()
        + "processCommand Method Called with ImageCommand cmd: green-component newImageName: "
        + "Fake-Image filePath: GGrey-Fake-Image"
        + System.lineSeparator()
        + "processCommand Method Called with ImageCommand cmd: blue-component newImageName: "
        + "Fake-Image filePath: BGrey-Fake-Image"
        + System.lineSeparator()
        + "Save Image at Path: images/BrightFakeImage.ppm Alis: Bright-Fake-Image"
        + System.lineSeparator();
    String desiredViewLog2 = "renderMessage Method Called Msg: To" + System.lineSeparator()
        + "renderMessage Method Called Msg: Thanks" + System.lineSeparator();
    assertEquals(desiredModelLog2, modelLog2.toString());
    assertEquals(desiredViewLog2, viewLog2.toString()); // the view log does not actually change
    // there is no input to support showing the image in a user-friendly manner therefore it is
    // omitted from the controller inputs. This will most likely differ in newer updates.
  }
}