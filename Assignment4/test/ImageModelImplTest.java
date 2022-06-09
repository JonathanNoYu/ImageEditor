import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ImageCommands.ImageOrientation.Flip;
import ImageCommands.PixelOperations.Greyscale;
import ImageCommands.PixelOperations.Lighting;
import ImageModel.ImageModel;
import ImageModel.ImageModelImpl;
import ImageModel.Pixel;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * This testing the model for our image processor. Notes: Test the load, save, get pixels and
 * processing commands
 */
public class ImageModelImplTest {

  ImageModel model1;
  ImageModel model2;

  ImageModel model3;
  ImageModel model4;


  /**
   * Helper test method to check equivalence of two loaded images in two separate models.
   *
   * @param m1 is the first model
   * @param m2 is the second model
   */
  public void sameLoadedImage(ImageModel m1, ImageModel m2) {
    // Testing if the load method is the same as loading it through the constructor just in case,
    for (int row = 0; row < m1.getRow(); row++) {
      for (int col = 0; col < m2.getCol(); col++) {
        assertEquals(m1.getPixel(row, col), m2.getPixel(row, col));
      }
    }
  }

  @Test
  @Before
  public void testLoadImage() {
    this.model1 = new ImageModelImpl("images/FakeImage.ppm", "Fake-Image"); // Square
    assertEquals(5, this.model1.getRow());
    assertEquals(5, this.model1.getCol());
    this.model2 = new ImageModelImpl("images/FakeImage2.ppm", "Fake2");// Square image
    assertEquals(3, this.model2.getRow());
    assertEquals(3, this.model2.getCol());
    this.model3 = new ImageModelImpl(); // defaults row and columns to 0
    assertEquals(0, this.model3.getRow());
    assertEquals(0, this.model3.getCol());
    this.model3.loadImage("images/FakeImage2.ppm", "Fake2"); // Square image

    // Checks if model2 and model3 are the same, they load the image differently.
    this.sameLoadedImage(this.model2, this.model3);

    this.model4 = new ImageModelImpl("images/FakeImage3.ppm", "Fake3"); // Rectangular image
    assertEquals(4, this.model4.getRow());
    assertEquals(2, this.model4.getCol());
  }

  /**
   * Checks only for if the loads are null. If the file does not exist it is automatically outputted
   * to the System.out.
   */
  @Test
  public void testInvalidLoadsAndSaves() {
    this.model1 = new ImageModelImpl();

    try {
      this.model1.loadImage(null, "N/A");
    } catch (IllegalArgumentException e) {
      assertEquals("You must have an image path and image name", e.getMessage());
    }

    try {
      this.model1.loadImage("non-Existent-File/Doesn'tExits.stopIt", null);
    } catch (IllegalArgumentException e) {
      assertEquals("You must have an image path and image name", e.getMessage());
    }

    try {
      this.model2.loadImage(null, "N/A");
    } catch (IllegalArgumentException e) {
      assertEquals("You must have an image path and image name", e.getMessage());
    }

    try {
      this.model3.loadImage("non-Existent-File/Doesn'tExits.stopIt", null);
    } catch (IllegalArgumentException e) {
      assertEquals("You must have an image path and image name", e.getMessage());
    }
  }

  @Test
  public void testGetPixel() {
    assertEquals(0, this.model1.getPixel(0, 0).getRed());
    assertEquals(0, this.model1.getPixel(0, 0).getGreen());
    assertEquals(0, this.model1.getPixel(0, 0).getBlue());
    assertEquals(7, this.model1.getPixel(1, 2).getRed());
    assertEquals(8, this.model1.getPixel(1, 3).getGreen());
    assertEquals(9, this.model1.getPixel(1, 4).getBlue());
    assertEquals(11, this.model1.getPixel(2, 1).getRed());
    assertEquals(11, this.model1.getPixel(2, 1).getGreen());
    assertEquals(11, this.model1.getPixel(2, 1).getBlue());
    assertEquals(15, this.model1.getPixel(3, 0).getRed());
    assertEquals(15, this.model1.getPixel(3, 0).getGreen());
    assertEquals(15, this.model1.getPixel(3, 0).getBlue());
    assertEquals(255, this.model1.getPixel(4, 4).getRed());
    assertEquals(255, this.model1.getPixel(4, 4).getGreen());
    assertEquals(255, this.model1.getPixel(4, 4).getBlue());
  }

  /**
   * Model1 was the first test just to see if general functionality works, Model2 is the full run of
   * all the commands but on a square image, each image is also checked if they exist, this is to
   * test the save method. Model 3 is meant to be used as a checker for model 2, so see if the
   * loaded image is not altered.
   */
  @Test
  public void testProcessCommandAndSave() {
    try {
      this.model1.processCommand(new Greyscale("value-component"), "Fake-Image",
          "FakeImage-Output1");
      this.model2.processCommand(new Greyscale("value-component"), "Fake2",
          "FakeImageOutput2-value-greyscale");
      this.model2.processCommand(new Flip("horizontal-flip"), "Fake2",
          "FakeImageOutput2-horizontal-flip");
      this.model2.processCommand(new Flip("vertical-flip"), "Fake2",
          "FakeImage2-VFlip");
      this.model2.processCommand(new Greyscale("intensity-component"), "Fake2",
          "FakeImage2-IGrey");
      this.model2.processCommand(new Greyscale("luma-component"), "Fake2",
          "FakeImage2-LGrey");
      this.model2.processCommand(new Greyscale("red-component"), "Fake2",
          "FakeImage2-RGrey");
      this.model2.processCommand(new Greyscale("green-component"), "Fake2",
          "FakeImage2-GGrey");
      this.model2.processCommand(new Greyscale("blue-component"), "Fake2",
          "FakeImage2-BGrey");
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
    // Loaded pixels should not change as they are already greyscale in terms of the max value.
    assertEquals(0, this.model1.getPixel(0, 0).getRed());
    assertEquals(0, this.model1.getPixel(0, 0).getGreen());
    assertEquals(0, this.model1.getPixel(0, 0).getBlue());
    assertEquals(7, this.model1.getPixel(1, 2).getRed());
    assertEquals(8, this.model1.getPixel(1, 3).getGreen());
    assertEquals(9, this.model1.getPixel(1, 4).getBlue());
    assertEquals(11, this.model1.getPixel(2, 1).getRed());
    assertEquals(11, this.model1.getPixel(2, 1).getGreen());
    assertEquals(11, this.model1.getPixel(2, 1).getBlue());
    assertEquals(15, this.model1.getPixel(3, 0).getRed());
    assertEquals(15, this.model1.getPixel(3, 0).getGreen());
    assertEquals(15, this.model1.getPixel(3, 0).getBlue());
    assertEquals(255, this.model1.getPixel(4, 4).getRed());
    assertEquals(255, this.model1.getPixel(4, 4).getGreen());
    assertEquals(255, this.model1.getPixel(4, 4).getBlue());

    this.model1.saveImage("images/FakeImage-Output1.ppm", "Fake-Image");

    // Checks if the File exits and reads it
    ImageUtil.readPPM("images/FakeImage-Output1.ppm");

    // checks before any changes
    this.sameLoadedImage(this.model2, this.model3);

    this.model2.saveImage("images/FakeImageOutput2-value-greyscale.ppm",
        "FakeImageOutput2-value-greyscale");

    // Checks if the File exits and reads it
    ImageUtil.readPPM("images/FakeImageOutput2-value-greyscale.ppm");

    // checks there is no difference in the loaded image, Model3 is the control
    this.sameLoadedImage(this.model2, this.model3);

    this.model2.saveImage("images/FakeImageOutput2-horizontal-flip.ppm",
        "FakeImageOutput2-horizontal-flip");

    // Checks if the File exits and reads it
    ImageUtil.readPPM("images/FakeImageOutput2-horizontal-flip.ppm");
    // checks there is no difference in the loaded image, Model3 is the control
    this.sameLoadedImage(this.model2, this.model3);

    this.model2.saveImage("images/FakeImage2-VFlip.ppm",
        "FakeImage2-VFlip");

    ImageUtil.readPPM("images/FakeImageOutput2-horizontal-flip.ppm");
    Pixel[][] FakeImageOutput2HFlip = model2.getImage("FakeImageOutput2-horizontal-flip");
    assertEquals(58, FakeImageOutput2HFlip[0][0].getRed());
    assertEquals(20, FakeImageOutput2HFlip[0][0].getGreen());
    assertEquals(90, FakeImageOutput2HFlip[0][0].getBlue());
    assertEquals(212, FakeImageOutput2HFlip[0][1].getRed());
    assertEquals(19, FakeImageOutput2HFlip[0][1].getGreen());
    assertEquals(0, FakeImageOutput2HFlip[0][1].getBlue());
    assertEquals(135, FakeImageOutput2HFlip[0][2].getRed());
    assertEquals(55, FakeImageOutput2HFlip[0][2].getGreen());
    assertEquals(200, FakeImageOutput2HFlip[0][2].getBlue());
    assertEquals(3, FakeImageOutput2HFlip[1][0].getRed());
    assertEquals(4, FakeImageOutput2HFlip[1][0].getGreen());
    assertEquals(5, FakeImageOutput2HFlip[1][0].getBlue());
    assertEquals(0, FakeImageOutput2HFlip[1][1].getRed());
    assertEquals(1, FakeImageOutput2HFlip[1][1].getGreen());
    assertEquals(2, FakeImageOutput2HFlip[1][1].getBlue());
    assertEquals(190, FakeImageOutput2HFlip[1][2].getRed());
    assertEquals(100, FakeImageOutput2HFlip[1][2].getGreen());
    assertEquals(3, FakeImageOutput2HFlip[1][2].getBlue());
    assertEquals(12, FakeImageOutput2HFlip[2][0].getRed());
    assertEquals(13, FakeImageOutput2HFlip[2][0].getGreen());
    assertEquals(14, FakeImageOutput2HFlip[2][0].getBlue());
    assertEquals(9, FakeImageOutput2HFlip[2][1].getRed());
    assertEquals(10, FakeImageOutput2HFlip[2][1].getGreen());
    assertEquals(11, FakeImageOutput2HFlip[2][1].getBlue());
    assertEquals(6, FakeImageOutput2HFlip[2][2].getRed());
    assertEquals(7, FakeImageOutput2HFlip[2][2].getGreen());
    assertEquals(8, FakeImageOutput2HFlip[2][2].getBlue());

    ImageUtil.readPPM("images/FakeImage2-VFlip.ppm");
    Pixel[][] FakeImageOutput2VFlip = model2.getImage("FakeImage2-VFlip");
    assertEquals(6, FakeImageOutput2VFlip[0][0].getRed());
    assertEquals(7, FakeImageOutput2VFlip[0][0].getGreen());
    assertEquals(8, FakeImageOutput2VFlip[0][0].getBlue());
    assertEquals(9, FakeImageOutput2VFlip[0][1].getRed());
    assertEquals(10, FakeImageOutput2VFlip[0][1].getGreen());
    assertEquals(11, FakeImageOutput2VFlip[0][1].getBlue());
    assertEquals(12, FakeImageOutput2VFlip[0][2].getRed());
    assertEquals(13, FakeImageOutput2VFlip[0][2].getGreen());
    assertEquals(14, FakeImageOutput2VFlip[0][2].getBlue());
    assertEquals(190, FakeImageOutput2VFlip[1][0].getRed());
    assertEquals(100, FakeImageOutput2VFlip[1][0].getGreen());
    assertEquals(3, FakeImageOutput2VFlip[1][0].getBlue());
    assertEquals(0, FakeImageOutput2VFlip[1][1].getRed());
    assertEquals(1, FakeImageOutput2VFlip[1][1].getGreen());
    assertEquals(2, FakeImageOutput2VFlip[1][1].getBlue());
    assertEquals(3, FakeImageOutput2VFlip[1][2].getRed());
    assertEquals(4, FakeImageOutput2VFlip[1][2].getGreen());
    assertEquals(5, FakeImageOutput2VFlip[1][2].getBlue());
    assertEquals(135, FakeImageOutput2VFlip[2][0].getRed());
    assertEquals(55, FakeImageOutput2VFlip[2][0].getGreen());
    assertEquals(200, FakeImageOutput2VFlip[2][0].getBlue());
    assertEquals(212, FakeImageOutput2VFlip[2][1].getRed());
    assertEquals(19, FakeImageOutput2VFlip[2][1].getGreen());
    assertEquals(0, FakeImageOutput2VFlip[2][1].getBlue());
    assertEquals(58, FakeImageOutput2VFlip[2][2].getRed());
    assertEquals(20, FakeImageOutput2VFlip[2][2].getGreen());
    assertEquals(90, FakeImageOutput2VFlip[2][2].getBlue());

    // Checks if the File exits and reads it
    ImageUtil.readPPM("images/FakeImage2-VFlip.ppm");
    // checks there is no difference in the loaded image, Model3 is the control
    this.sameLoadedImage(this.model2, this.model3);

    this.model2.saveImage("images/FakeImage2-IGrey.ppm",
        "FakeImage2-IGrey");

    // Checks if the File exits and reads it
    ImageUtil.readPPM("images/FakeImage2-IGrey.ppm");
    // checks there is no difference in the loaded image, Model3 is the control
    this.sameLoadedImage(this.model2, this.model3);

    this.model2.saveImage("images/FakeImage2-LGrey.ppm",
        "FakeImage2-LGrey");

    // Checks if the File exits and reads it
    ImageUtil.readPPM("images/FakeImage2-LGrey.ppm");
    // checks there is no difference in the loaded image, Model3 is the control
    this.sameLoadedImage(this.model2, this.model3);

    this.model2.saveImage("images/FakeImage2-RGrey.ppm",
        "FakeImage2-RGrey");

    // Checks if the File exits and reads it
    ImageUtil.readPPM("images/FakeImage2-RGrey.ppm");
    // checks there is no difference in the loaded image, Model3 is the control
    this.sameLoadedImage(this.model2, this.model3);

    this.model2.saveImage("images/FakeImage2-GGrey.ppm",
        "FakeImage2-GGrey");

    // Checks if the File exits and reads it
    ImageUtil.readPPM("images/FakeImage2-GGrey.ppm");
    // checks there is no difference in the loaded image, Model3 is the control
    this.sameLoadedImage(this.model2, this.model3);

    this.model2.saveImage("images/FakeImage2-BGrey.ppm",
        "FakeImage2-BGrey");

    // Checks if the File exits and reads it
    ImageUtil.readPPM("images/FakeImage2-BGrey.ppm");
    // checks there is no difference in the loaded image, Model3 is the control
    this.sameLoadedImage(this.model2, this.model3);
  }

  /**
   * Model4 is meant to check specifically if the flips work correctly. We assume the pixel
   * operations work since they only deal with a single pixel at a time instead of the placement of
   * the pixel which can be misconstrued based on miscalculations. Therefore, this test is made to
   * only check the vertical and horizontal flip commands instead of all the commands.
   */
  @Test
  public void testRectangleFlip() {
    try {
      this.model4.processCommand(new Flip("horizontal-flip"), "Fake3",
          "Fake3HFlip");
      this.model4.processCommand(new Flip("vertical-flip"), "Fake3",
          "Fake3VFlip");
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
    this.model4.saveImage("images/Fake3HFlip.ppm", "Fake3HFlip");
    this.model4.saveImage("images/Fake3VFlip.ppm", "Fake3VFlip");
    Pixel[][] Fake3HFlip = model4.getImage("Fake3HFlip");
    assertEquals("[r = 3,g = 4,b = 5]", Fake3HFlip[0][0].toString());
    assertEquals("[r = 0,g = 1,b = 2]", Fake3HFlip[0][1].toString());
    assertEquals("[r = 9,g = 10,b = 11]", Fake3HFlip[1][0].toString());
    assertEquals("[r = 6,g = 7,b = 8]", Fake3HFlip[1][1].toString());
    assertEquals("[r = 15,g = 16,b = 17]", Fake3HFlip[2][0].toString());
    assertEquals("[r = 12,g = 13,b = 14]", Fake3HFlip[2][1].toString());
    assertEquals("[r = 21,g = 22,b = 23]", Fake3HFlip[3][0].toString());
    assertEquals("[r = 18,g = 19,b = 20]", Fake3HFlip[3][1].toString());

    Pixel[][] Fake3VFlip = model4.getImage("Fake3VFlip");
    assertEquals("[r = 18,g = 19,b = 20]", Fake3VFlip[0][0].toString());
    assertEquals("[r = 21,g = 22,b = 23]", Fake3VFlip[0][1].toString());
    assertEquals("[r = 12,g = 13,b = 14]", Fake3VFlip[1][0].toString());
    assertEquals("[r = 15,g = 16,b = 17]", Fake3VFlip[1][1].toString());
    assertEquals("[r = 6,g = 7,b = 8]", Fake3VFlip[2][0].toString());
    assertEquals("[r = 9,g = 10,b = 11]", Fake3VFlip[2][1].toString());
    assertEquals("[r = 0,g = 1,b = 2]", Fake3VFlip[3][0].toString());
    assertEquals("[r = 3,g = 4,b = 5]", Fake3VFlip[3][1].toString());
  }

  /**
   * This is a double check for all the commands but ran on the koala.ppm. This is, so I can check
   * using koala.ppm if the image is correctly changed. This is for a more simple and easy to read
   * manual test, rather than checking all 1024 by 768 pixels, aka over 2.3 million different
   * integers in the ppm file.
   */
  @Test
  public void testProcessCommandOnKoalaPPM() {
    Boolean allCommandHadRan = false;
    try {
      this.model3.loadImage("images/Koala.ppm", "Koala");
      this.model3.processCommand(new Greyscale("value-component"), "Koala",
          "Koala-VGrey");
      this.model3.processCommand(new Flip("horizontal-flip"), "Koala",
          "Koala-HFlip");
      this.model3.processCommand(new Flip("vertical-flip"), "Koala",
          "Koala-VFlip");
      this.model3.processCommand(new Greyscale("intensity-component"), "Koala",
          "Koala-IGrey");
      this.model3.processCommand(new Greyscale("luma-component"), "Koala",
          "Koala-LGrey");
      this.model3.processCommand(new Greyscale("red-component"), "Koala",
          "Koala-RGrey");
      this.model3.processCommand(new Greyscale("green-component"), "Koala",
          "Koala-GGrey");
      this.model3.processCommand(new Greyscale("blue-component"), "Koala",
          "Koala-BGrey");
      this.model3.processCommand(new Lighting(50,"brighten"), "Koala",
          "Koala-50darken");
      this.model3.processCommand(new Lighting(50, "darken"), "Koala",
          "Koala-50brighten");
      this.model3.saveImage("images/Koala-VGrey.ppm", "Koala-VGrey");
      this.model3.saveImage("images/Koala-HFlip.ppm", "Koala-HFlip");
      this.model3.saveImage("images/Koala-VFlip.ppm", "Koala-VFlip");
      this.model3.saveImage("images/Koala-IGrey.ppm", "Koala-IGrey");
      this.model3.saveImage("images/Koala-LGrey.ppm", "Koala-LGrey");
      this.model3.saveImage("images/Koala-RGrey.ppm", "Koala-RGrey");
      this.model3.saveImage("images/Koala-GGrey.ppm", "Koala-GGrey");
      this.model3.saveImage("images/Koala-BGrey.ppm", "Koala-BGrey");
      this.model3.saveImage("images/Koala-50darken.ppm", "Koala-50darken");
      this.model3.saveImage("images/Koala-50brighten.ppm", "Koala-50brighten");
      allCommandHadRan = true;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertTrue(allCommandHadRan);
  }
}

