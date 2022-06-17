package imageio;

import static org.junit.Assert.assertEquals;

import Util.ImageUtil;
import controller.ImageIOController;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.StringReader;
import mocks.MockImageIOModel;
import mocks.MockImageView;
import model.ImageIOModel;
import model.ImageModelV2;
import org.junit.Before;
import org.junit.Test;
import view.ImageTextViewImpl;
import view.ImageView;

public class ImageIOControllerTest {
  ImageIOController controller1;
  ImageIOController controller2;
  ImageIOController controller3;
  ImageView view1;
  ImageView view2;
  ImageView view3;
  ImageModelV2 model1;
  ImageModelV2 model2;
  ImageModelV2 model3;
  StringReader in1;
  StringReader in2;
  StringReader in3;
  StringBuilder out1;
  StringBuilder out2;
  StringBuilder out3;

  @Before
  public void setUp(){
    ImageUtil.setUpBufferedImages();
    this.model1 = new ImageIOModel("res/bmp1.bmp", "bmp1");
    this.model2 = new ImageIOModel("res/jpeg1.jpeg", "jpeg1");
    this.model3 = new ImageIOModel(); // "res/png1.png", "png1"
    this.in1 = new StringReader("exit");
    this.in2 = new StringReader("exit");
    this.in3 = new StringReader("exit");
    this.out1 = new StringBuilder();
    this.out2 = new StringBuilder();
    this.out3 = new StringBuilder();
    this.view1 = new ImageTextViewImpl(this.model1, this.out1);
    this.view2 = new ImageTextViewImpl(this.model2, this.out2);
    this.view3 = new ImageTextViewImpl(this.model3, this.out3);
    this.controller1 = new ImageIOController(this.model1, this.view1, this.in1);
    this.controller2 = new ImageIOController(this.model2, this.view2, this.in2);
    this.controller3 = new ImageIOController(this.model3, this.view3, this.in3);
  }

  @Test
  public void testInvalidControllers() {
    try {
      this.controller1 = new ImageIOController(null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new ImageIOController(null, null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new ImageIOController(this.model1, null, this.in1);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new ImageIOController(null, this.view1, this.in1);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }

    try {
      this.controller1 = new ImageIOController(this.model1, this.view1, null);
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model, view and input", e.getMessage());
    }
  }

  @Test
  public void testStartingEndMessage() {
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
        + "\tblur                 <Image Alias To Be Modified> <Image Alias>\n"
        + "\tsharpen              <Image Alias To Be Modified> <Image Alias>\n"
        + "\tgreyscale            <Image Alias To Be Modified> <Image Alias>\n"
        + "\tsepiaTone            <Image Alias To Be Modified> <Image Alias>\n"
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
  }

  @Test
  public void testValidInputsWithMocks() {
    // Normal start and exit
    StringBuilder viewLog1 = new StringBuilder();
    StringBuilder modelLog1 = new StringBuilder();
    ImageModelV2 mockModel1 = new MockImageIOModel(modelLog1);
    ImageView mockView1 = new MockImageView(viewLog1, this.out1);
    this.controller1 = new ImageIOController(mockModel1, mockView1, this.in1);
    this.controller1.processImage();

    assertEquals("renderMessage Method Called Msg: This" + System.lineSeparator()
        + "renderMessage Method Called Msg: Thanks" + System.lineSeparator(), viewLog1.toString());
    assertEquals("", modelLog1.toString());

    // Tests all commands
    this.in2 = new StringReader("load res/FakeImage.ppm Fake-Image "
        + "load res/bmp1.bmp bmp1 "
        + "load res/jpeg1.jpeg jpeg1 "
        + "load res/png1.png png1 "
        + "brighten 10 Fake-Image Bright-Fake-Image "
        + "darken 10 Fake-Image Darken-Fake-Image "
        + "horizontal-flip jpeg1 HFlip-jpeg1 "
        + "vertical-flip Fake-Image VFlip-Fake-Image "
        + "value-component Fake-Image VGrey-Fake-Image "
        + "intensity-component Fake-Image IGrey-Fake-Image "
        + "luma-component png1 LGrey-png1 "
        + "red-component Fake-Image RGrey-Fake-Image "
        + "green-component Fake-Image GGrey-Fake-Image "
        + "blue-component Fake-Image BGrey-Fake-Image "
        + "blur bmp1 Blur-bmp1 "
        + "sharpen Fake-Image Sharpen-Fake-Image "
        + "greyscale bmp1 Grey-bmp1 "
        + "sepiaTone Fake-Image Sepia-Tone-Image "
        + "save res/BrightFakeImage.ppm Bright-Fake-Image "
        + "save res/Blur-bmp1.bmp Blur-bmp1 "
        + "save res/LGrey-png1.png LGrey-png1 "
        + "exit");
    this.out2 = new StringBuilder();
    StringBuilder viewLog2 = new StringBuilder();
    StringBuilder modelLog2 = new StringBuilder();
    ImageModelV2 mockModel2 = new MockImageIOModel(modelLog2);
    ImageView mockView2 = new MockImageView(viewLog2, this.out2);
    this.controller2 = new ImageIOController(mockModel2, mockView2, this.in2);
    this.controller2.processImage();
    assertEquals("renderMessage Method Called Msg: This" + System.lineSeparator()
        + "renderMessage Method Called Msg: Thanks" + System.lineSeparator(), viewLog2.toString());
    assertEquals("loadImage-> Path: res/FakeImage.ppm imgName: Fake-Image"
        + System.lineSeparator()
        + "loadImage-> Path: res/bmp1.bmp imgName: bmp1" + System.lineSeparator()
        + "loadImage-> Path: res/jpeg1.jpeg imgName: jpeg1" + System.lineSeparator()
        + "loadImage-> Path: res/png1.png imgName: png1" + System.lineSeparator()
        + "getImage-> ImageName: Fake-Image" + System.lineSeparator()
        + "putInStorage-> Name: Bright-Fake-Image Buffered Image"  + System.lineSeparator()
        + "getImage-> ImageName: Fake-Image" + System.lineSeparator()
        + "putInStorage-> Name: Darken-Fake-Image Buffered Image" + System.lineSeparator()
        + "getImage-> ImageName: jpeg1" + System.lineSeparator()
        + "putInStorage-> Name: HFlip-jpeg1 Buffered Image" + System.lineSeparator()
        + "getImage-> ImageName: Fake-Image" + System.lineSeparator()
        + "putInStorage-> Name: VFlip-Fake-Image Buffered Image" + System.lineSeparator()
        + "getImage-> ImageName: Fake-Image" + System.lineSeparator()
        + "putInStorage-> Name: VGrey-Fake-Image Buffered Image" + System.lineSeparator()
        + "getImage-> ImageName: Fake-Image" + System.lineSeparator()
        + "putInStorage-> Name: IGrey-Fake-Image Buffered Image" + System.lineSeparator()
        + "getImage-> ImageName: png1" + System.lineSeparator()
        + "putInStorage-> Name: LGrey-png1 Buffered Image" + System.lineSeparator()
        + "getImage-> ImageName: Fake-Image" + System.lineSeparator()
        + "putInStorage-> Name: RGrey-Fake-Image Buffered Image" + System.lineSeparator()
        + "getImage-> ImageName: Fake-Image" + System.lineSeparator()
        + "putInStorage-> Name: GGrey-Fake-Image Buffered Image" + System.lineSeparator()
        + "getImage-> ImageName: Fake-Image" + System.lineSeparator()
        + "putInStorage-> Name: BGrey-Fake-Image Buffered Image" + System.lineSeparator()
        + "getImage-> ImageName: bmp1" + System.lineSeparator()
        + "putInStorage-> Name: Blur-bmp1 Buffered Image" + System.lineSeparator()
        + "getImage-> ImageName: Fake-Image" + System.lineSeparator()
        + "putInStorage-> Name: Sharpen-Fake-Image Buffered Image" + System.lineSeparator()
        + "getImage-> ImageName: bmp1" + System.lineSeparator()
        + "putInStorage-> Name: Grey-bmp1 Buffered Image" + System.lineSeparator()
        + "getImage-> ImageName: Fake-Image" + System.lineSeparator()
        + "putInStorage-> Name: Sepia-Tone-Image Buffered Image" + System.lineSeparator()
        + "saveImage-> Path: res/BrightFakeImage.ppm imgName: Bright-Fake-Image"
        + System.lineSeparator()
        + "saveImage-> Path: res/Blur-bmp1.bmp imgName: Blur-bmp1"
        + System.lineSeparator()
        + "saveImage-> Path: res/LGrey-png1.png imgName: LGrey-png1"
        + System.lineSeparator(), modelLog2.toString());
  }

  /**
   * Takes roughly 1 Minutes to run fully
   */
  @Test
  public void testAllCommands() {
    this.in3 = new StringReader("load res/bmp1.bmp bmp1 "
        + "load res/jpeg1.jpeg jpeg1 "
        + "load res/png1.png png1 "
        + "brighten 50 bmp1 BrightBMP "
        + "darken 50 jpeg1 DarkenJPEG "
        + "horizontal-flip jpeg1 HFlipJPEG "
        + "vertical-flip png1 VFlipPNG "
        + "value-component bmp1 VGreyBMP "
        + "intensity-component jpeg1 IGreyJPEG "
        + "luma-component png1 LGreyPNG "
        + "red-component bmp1 RGreyBMP "
        + "green-component jpeg1 GGreyJPEG "
        + "blue-component png1 BGreyPNG "
        + "blur bmp1 BlurBMP "
        + "sharpen jpeg1 SharpenJPEG "
        + "save res/BrightBMP.bmp BrightBMP "
        + "save res/DarkenJPEG.jpeg DarkenJPEG "
        + "save res/HFlipJPEG.jpeg HFlipJPEG "
        + "save res/VFlipPNG.png VFlipPNG "
        + "save res/VGreyBMP.bmp VGreyBMP "
        + "save res/IGreyJPEG.jpeg IGreyJPEG "
        + "save res/LGreyPNG.png LGreyPNG "
        + "save res/RGreyBMP.bmp RGreyBMP "
        + "save res/GGreyJPEG.jpeg GGreyJPEG "
        + "save res/BGreyPNG.png BGreyPNG "
        + "save res/BlurBMP.bmp BlurBMP "
        + "save res/SharpenJPEG.jpeg SharpenJPEG "
        + "exit");
    this.controller3 = new ImageIOController(this.model3,this.view3, this.in3);
    this.controller3.processImage();
  }

  /**
   * The images are correctly changed, This is mostly for checking the image manually with our eyes.
   * The main purpose is to see if all the command do run and no error is found.
   *
   */
  @Test
  public void testSmolImage() {
    this.in2 = new StringReader("load res/smol.jpeg smol "
        + "brighten 50 smol BrightSmol "
        + "darken 50 smol DarkenSmol "
        + "horizontal-flip smol HFlipSmol "
        + "vertical-flip smol VFlipSmol "
        + "value-component smol VGreySmol "
        + "intensity-component smol IGreySmol "
        + "luma-component smol LGreySmol "
        + "red-component smol RGreySmol "
        + "green-component smol GGreySmol "
        + "blue-component smol BGreySmol "
        + "blur smol BlurSmol "
        + "sharpen smol SharpenSmol "
        + "greyscale smol GreySmol "
        + "sepiaTone smol SepiaSmol "
        + "save res/BrightSmol.bmp BrightSmol "
        + "save res/DarkenSmol.jpeg DarkenSmol "
        + "save res/HFlipSmol.png HFlipSmol "
        + "save res/VFlipSmol.png VFlipSmol "
        + "save res/VGreySmol.bmp VGreySmol "
        + "save res/IGreySmol.bmp IGreySmol "
        + "save res/LGreySmol.png LGreySmol "
        + "save res/RGreySmol.bmp RGreySmol "
        + "save res/GGreySmol.jpeg GGreySmol "
        + "save res/BGreySmol.png BGreySmol "
        + "save res/BlurSmol.bmp BlurSmol "
        + "save res/SharpenSmol.jpeg SharpenSmol "
        + "save res/GreySmol.png GreySmol "
        + "save res/SepiaSmol.png SepiaSmol "
        + "exit");
    this.controller2 = new ImageIOController(this.model2,this.view2, this.in2);
    this.controller2.processImage();
  }
}
