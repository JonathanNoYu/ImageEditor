package ppm;

import static org.junit.Assert.assertEquals;

import controller.ImageController;
import controller.PPMImageController;
import java.io.StringReader;
import mocks.MockPPMModel;
import mocks.MockImageView;
import model.ImageModel;
import model.PPMModel;
import org.junit.Test;
import view.ImageTextViewImpl;
import view.ImageView;

/**
 * Testing class of the Image controller. It uses a model mock and a view mock bt
 */
public class PPMImageControllerTest {

  ImageController controller1;
  ImageController controller2;
  ImageView view1;
  ImageModel model1;
  StringReader in1;
  StringBuilder out1;

  @Test
  public void testInvalidControllers() {
    this.model1 = new PPMModel("res/FakeImage.ppm", "Fake-Image");
    this.out1 = new StringBuilder();
    this.view1 = new ImageTextViewImpl(this.model1, out1);
    this.in1 = new StringReader("");

    try {
      this.controller1 = new PPMImageController(null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new PPMImageController(null, null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new PPMImageController(this.model1, null, this.in1);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new PPMImageController(null, this.view1, this.in1);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new PPMImageController(this.model1, this.view1, null);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }
  }

  @Test
  public void testStartingEndMessage() {
    this.model1 = new PPMModel();
    this.out1 = new StringBuilder();
    this.view1 = new ImageTextViewImpl(this.model1, out1);
    this.in1 = new StringReader("exit");
    this.controller1 = new PPMImageController(this.model1, this.view1, this.in1);
    this.controller1.processImage();

    // Testing the starting and ending message
    String instructions = "This is our ppm image editor! \n"
        + "Below are the allowed commands: (type them out with only one space in between each"
        + " word press enter separately)\n"
        + "\tload <Image Path> <Image Alias>\n"
        + "\tsave <Image Path> <Image Alias>\n"
        + "\tbrighten <Increment> <Image Alias To Be Modified> <Image Alias>\n"
        + "\tdarken   <Increment> <Image Alias To Be Modified> <Image Alias>\n"
        + "\thorizontal-flip      <Image Alias To Be Modified> <Image Alias>\n"
        + "\tvertical-flip        <Image Alias To Be Modified> <Image Alias>\n"
        + "\tvalue-component      <Image Alias To Be Modified> <Image Alias>\n"
        + "\tintensity-component  <Image Alias To Be Modified> <Image Alias>\n"
        + "\tluma-component       <Image Alias To Be Modified> <Image Alias>\n"
        + "\tred-component        <Image Alias To Be Modified> <Image Alias>\n"
        + "\tgreen-component      <Image Alias To Be Modified> <Image Alias>\n"
        + "\tblue-component       <Image Alias To Be Modified> <Image Alias>\n"
        + "\texit \n"
        + "Key Notes: (examples are below this in the short Script)\n"
        + "\t   <Image Path> is the path in which the file exits, this also includes the file name"
        + " and the file type.\n"
        + "\t   <Image Alias> is the name of the image that you had loaded/modified and gave it a"
        + " new name to reference it by.\n"
        + "\t   <Image Alias To Be Modified> is the image's alias that you want to modify again or"
        + " modify for the first time.\n"
        + "\t   <Increment> is a value to increase a pixel's entire value, valid numbers are from 0"
        + " to 255.\n"
        + "\t   exit is the only way to stop the program.\n"
        + "\t   Lastly, The loaded image would not be modified.\n";
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
    this.model1 = new PPMModel();
    this.out1 = new StringBuilder();
    this.view1 = new ImageTextViewImpl(this.model1, out1);
    this.in1 = new StringReader("load res/FakeImage3.ppm Fake3 "
        + "save res/FakeImage3Save.ppm Fake3 "
        + "brighten 50 Fake3 Fake3CBrighten50 " // The C after Fake3 stands for controller
        + "darken 50 Fake3 Fake3CDarken50 "     // This is to discern if the controller works
        + "vertical-flip Fake3 Fake3CVFlip "    // With a normal mock and view.
        + "horizontal-flip Fake3 Fake3CHFlip "
        + "value-component Fake3 Fake3CVGrey "
        + "intensity-component Fake3 Fake3CIGrey "
        + "luma-component Fake3 Fake3CLGrey "
        + "red-component Fake3 Fake3CRGrey "
        + "green-component Fake3 Fake3CGGrey "
        + "blue-component Fake3 Fake3CBGrey "
        + "save res/Fake3CBrighten50.ppm Fake3CBrighten50 "
        + "save res/Fake3CDarken50.ppm Fake3CDarken50 "
        + "save res/Fake3CVFlip.ppm Fake3CVFlip "
        + "save res/Fake3CHFlip.ppm Fake3CHFlip "
        + "save res/Fake3CVGrey.ppm Fake3CVGrey "
        + "save res/Fake3CIGrey.ppm Fake3CIGrey "
        + "save res/Fake3CLGrey.ppm Fake3CLGrey "
        + "save res/Fake3CRGrey.ppm Fake3CRGrey "
        + "save res/Fake3CGGrey.ppm Fake3CGGrey "
        + "save res/Fake3CBGrey.ppm Fake3CBGrey "
        + "exit");
    this.controller1 = new PPMImageController(this.model1, this.view1, this.in1);
    this.controller1.processImage();

    String instructions = "This is our ppm image editor! \n"
        + "Below are the allowed commands: (type them out with only one space in between each"
        + " word press enter separately)\n"
        + "\tload <Image Path> <Image Alias>\n"
        + "\tsave <Image Path> <Image Alias>\n"
        + "\tbrighten <Increment> <Image Alias To Be Modified> <Image Alias>\n"
        + "\tdarken   <Increment> <Image Alias To Be Modified> <Image Alias>\n"
        + "\thorizontal-flip      <Image Alias To Be Modified> <Image Alias>\n"
        + "\tvertical-flip        <Image Alias To Be Modified> <Image Alias>\n"
        + "\tvalue-component      <Image Alias To Be Modified> <Image Alias>\n"
        + "\tintensity-component  <Image Alias To Be Modified> <Image Alias>\n"
        + "\tluma-component       <Image Alias To Be Modified> <Image Alias>\n"
        + "\tred-component        <Image Alias To Be Modified> <Image Alias>\n"
        + "\tgreen-component      <Image Alias To Be Modified> <Image Alias>\n"
        + "\tblue-component       <Image Alias To Be Modified> <Image Alias>\n"
        + "\texit \n"
        + "Key Notes: (examples are below this in the short Script)\n"
        + "\t   <Image Path> is the path in which the file exits, this also includes the file name"
        + " and the file type.\n"
        + "\t   <Image Alias> is the name of the image that you had loaded/modified and gave it a"
        + " new name to reference it by.\n"
        + "\t   <Image Alias To Be Modified> is the image's alias that you want to modify again or"
        + " modify for the first time.\n"
        + "\t   <Increment> is a value to increase a pixel's entire value, valid numbers are from 0"
        + " to 255.\n"
        + "\t   exit is the only way to stop the program.\n"
        + "\t   Lastly, The loaded image would not be modified.\n";
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
    MockPPMModel mockModel1 = new MockPPMModel(modelLog1);
    MockImageView mockView1 = new MockImageView(viewLog1, output1);
    this.controller1 = new PPMImageController(mockModel1, mockView1, inputs1);
    this.controller1.processImage();

    // Testing the starting and ending message
    String instructions = "This is our ppm image editor! \n"
        + "Below are the allowed commands: (type them out with only one space in between each"
        + " word press enter separately)\n"
        + "\tload <Image Path> <Image Alias>\n"
        + "\tsave <Image Path> <Image Alias>\n"
        + "\tbrighten <Increment> <Image Alias To Be Modified> <Image Alias>\n"
        + "\tdarken   <Increment> <Image Alias To Be Modified> <Image Alias>\n"
        + "\thorizontal-flip      <Image Alias To Be Modified> <Image Alias>\n"
        + "\tvertical-flip        <Image Alias To Be Modified> <Image Alias>\n"
        + "\tvalue-component      <Image Alias To Be Modified> <Image Alias>\n"
        + "\tintensity-component  <Image Alias To Be Modified> <Image Alias>\n"
        + "\tluma-component       <Image Alias To Be Modified> <Image Alias>\n"
        + "\tred-component        <Image Alias To Be Modified> <Image Alias>\n"
        + "\tgreen-component      <Image Alias To Be Modified> <Image Alias>\n"
        + "\tblue-component       <Image Alias To Be Modified> <Image Alias>\n"
        + "\texit \n"
        + "Key Notes: (examples are below this in the short Script)\n"
        + "\t   <Image Path> is the path in which the file exits, this also includes the file name"
        + " and the file type.\n"
        + "\t   <Image Alias> is the name of the image that you had loaded/modified and gave it a"
        + " new name to reference it by.\n"
        + "\t   <Image Alias To Be Modified> is the image's alias that you want to modify again or"
        + " modify for the first time.\n"
        + "\t   <Increment> is a value to increase a pixel's entire value, valid numbers are from 0"
        + " to 255.\n"
        + "\t   exit is the only way to stop the program.\n"
        + "\t   Lastly, The loaded image would not be modified.\n";
    String endMessage = "Thanks for using our Image editor!";
    String[] outputArray1 = output1.toString().split(System.lineSeparator());
    assertEquals(instructions, outputArray1[0]);
    assertEquals(endMessage, outputArray1[1]);

    // Checking the inputs to the model and view
    String desiredModelLog1 = "";
    String desiredViewLog1 = "renderMessage Method Called Msg: This" + System.lineSeparator()
        + "renderMessage Method Called Msg: Thanks" + System.lineSeparator();
    assertEquals(desiredModelLog1, modelLog1.toString());
    assertEquals(desiredViewLog1, viewLog1.toString());

    //Testing all inputs/commands
    StringBuilder viewLog2 = new StringBuilder();
    StringBuilder modelLog2 = new StringBuilder();
    StringReader inputs2 = new StringReader("load res/FakeImage.ppm Fake-Image "
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
        + "save res/BrightFakeImage.ppm Bright-Fake-Image "
        + "exit");
    StringBuilder output2 = new StringBuilder();
    MockPPMModel mockModel2 = new MockPPMModel(modelLog2);
    MockImageView mockView2 = new MockImageView(viewLog2, output2);
    this.controller2 = new PPMImageController(mockModel2, mockView2, inputs2);
    this.controller2.processImage();

    String[] outputArray2 = output2.toString().split(System.lineSeparator());
    assertEquals(instructions, outputArray2[0]);
    assertEquals(endMessage, outputArray2[1]);

    // Each method is seperated by the System.lineSeparator()
    String desiredModelLog2 = "Load Image at Path: res/FakeImage.ppm Alis: Fake-Image"
        + System.lineSeparator()
        + "getImage called with image: Fake-Image" + System.lineSeparator()
        + "putInStorage Method called with Image: Bright-Fake-Image" + System.lineSeparator()
        + "getImage called with image: Fake-Image" + System.lineSeparator()
        + "putInStorage Method called with Image: Darken-Fake-Image" + System.lineSeparator()
        + "getImage called with image: Fake-Image" + System.lineSeparator()
        + "putInStorage Method called with Image: HFlip-Image" + System.lineSeparator()
        + "getImage called with image: Fake-Image" + System.lineSeparator()
        + "putInStorage Method called with Image: VFlip-Fake-Image" + System.lineSeparator()
        + "getImage called with image: Fake-Image" + System.lineSeparator()
        + "putInStorage Method called with Image: VGrey-Fake-Image" + System.lineSeparator()
        + "getImage called with image: Fake-Image" + System.lineSeparator()
        + "putInStorage Method called with Image: IGrey-Fake-Image" + System.lineSeparator()
        + "getImage called with image: Fake-Image" + System.lineSeparator()
        + "putInStorage Method called with Image: LGrey-Fake-Image" + System.lineSeparator()
        + "getImage called with image: Fake-Image" + System.lineSeparator()
        + "putInStorage Method called with Image: RGrey-Fake-Image" + System.lineSeparator()
        + "getImage called with image: Fake-Image" + System.lineSeparator()
        + "putInStorage Method called with Image: GGrey-Fake-Image" + System.lineSeparator()
        + "getImage called with image: Fake-Image" + System.lineSeparator()
        + "putInStorage Method called with Image: BGrey-Fake-Image" + System.lineSeparator()
        + "Save Image at Path: res/BrightFakeImage.ppm Alis: Bright-Fake-Image"
        + System.lineSeparator();
    String desiredViewLog2 = "renderMessage Method Called Msg: This" + System.lineSeparator()
        + "renderMessage Method Called Msg: Thanks" + System.lineSeparator();
    assertEquals(desiredModelLog2, modelLog2.toString());
    assertEquals(desiredViewLog2, viewLog2.toString()); // the view log does not actually change
    // there is no input to support showing the image in a user-friendly manner therefore it is
    // omitted from the controller inputs. This will most likely differ in newer updates.
  }
}