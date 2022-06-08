import static org.junit.Assert.assertEquals;

import ImageCommands.ImageOrientation.HorizontalFlip;
import ImageCommands.PixelOperations.Greyscale;
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


  /**
   * Helper test method to check equivalence of two loaded images in two separate models.
   * @param m1 is the first model
   * @param m2 is the second model
   */
  public void sameLoadedImage(ImageModel m1, ImageModel m2) {
    // Testing if the load method is the same as loading it through the constructor just in case,
    for (int row = 0; row < m1.getRow(); row ++) {
      for (int col = 0; col < m2.getCol(); col++) {
        assertEquals(m1.getPixel(row, col), m2.getPixel(row, col));
      }
    }
  }
  @Test
  @Before
  public void testLoadImage() {
    this.model1 = new ImageModelImpl("images/FakeImage.ppm", "Fake-Image");
    assertEquals(5, this.model1.getRow());
    assertEquals(5, this.model1.getCol());
    this.model2 = new ImageModelImpl("images/FakeImage2.ppm", "Fake2");
    assertEquals(3, this.model2.getRow());
    assertEquals(3, this.model2.getCol());
    this.model3 = new ImageModelImpl(); // defaults row and columns to 0
    assertEquals(0, this.model3.getRow());
    assertEquals(0, this.model3.getCol());
    this.model3.loadImage("images/FakeImage2.ppm", "Fake2");

    this.sameLoadedImage(this.model2, this.model3);

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

  @Test
  public void testProcessCommandAndSave() {
    try {
      this.model1.processCommand(new Greyscale("value-component"), "Fake-Image",
          "FakeImage-Output1");
      this.model2.processCommand(new Greyscale("value-component"), "Fake2",
          "FakeImageOutput2-value-greyscale");
      this.model2.processCommand(new HorizontalFlip(), "Fake2",
          "FakeImageOutput2-horizontal-flip");
    } catch (IOException e) {
      throw new RuntimeException(e);
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

    model1.saveImage("images/FakeImage-Output1.ppm", "Fake-Image");
    ImageUtil.readPPM("images/FakeImage-Output1.ppm"); // If this runs the save did it right.

    // checks before any changes
    this.sameLoadedImage(this.model2, this.model3);

    model2.saveImage("images/FakeImageOutput2-value-greyscale.ppm",
        "FakeImageOutput2-value-greyscale");
    ImageUtil.readPPM("images/FakeImageOutput2-value-greyscale.ppm");

    // checks there is no difference in the loaded image, Model3 is the control
    this.sameLoadedImage(this.model2, this.model3);

    model2.saveImage("images/FakeImageOutput2-horizontal-flip.ppm",
        "FakeImageOutput2-horizontal-flip");

    // checks there is no difference in the loaded image, Model3 is the control
    this.sameLoadedImage(this.model2, this.model3);

    ImageUtil.readPPM("images/FakeImageOutput2-horizontal-flip.ppm");
    Pixel[][] FakeImageOutput2 = model2.getImage("FakeImageOutput2-horizontal-flip");
    assertEquals(6, FakeImageOutput2[0][0].getRed());
    assertEquals(7, FakeImageOutput2[0][0].getGreen());
    assertEquals(8, FakeImageOutput2[0][0].getBlue());
    assertEquals(9, FakeImageOutput2[0][1].getRed());
    assertEquals(10, FakeImageOutput2[0][1].getGreen());
    assertEquals(11, FakeImageOutput2[0][1].getBlue());
    assertEquals(12, FakeImageOutput2[0][2].getRed());
    assertEquals(13, FakeImageOutput2[0][2].getGreen());
    assertEquals(14, FakeImageOutput2[0][2].getBlue());
    assertEquals(190, FakeImageOutput2[1][0].getRed());
    assertEquals(100, FakeImageOutput2[1][0].getGreen());
    assertEquals(3, FakeImageOutput2[1][0].getBlue());
    assertEquals(0, FakeImageOutput2[1][1].getRed());
    assertEquals(1, FakeImageOutput2[1][1].getGreen());
    assertEquals(2, FakeImageOutput2[1][1].getBlue());
    assertEquals(3, FakeImageOutput2[1][2].getRed());
    assertEquals(4, FakeImageOutput2[1][2].getGreen());
    assertEquals(5, FakeImageOutput2[1][2].getBlue());
    assertEquals(135, FakeImageOutput2[2][0].getRed());
    assertEquals(55, FakeImageOutput2[2][0].getGreen());
    assertEquals(200, FakeImageOutput2[2][0].getBlue());
    assertEquals(212, FakeImageOutput2[2][1].getRed());
    assertEquals(19, FakeImageOutput2[2][1].getGreen());
    assertEquals(0, FakeImageOutput2[2][1].getBlue());
    assertEquals(58, FakeImageOutput2[2][2].getRed());
    assertEquals(20, FakeImageOutput2[2][2].getGreen());
    assertEquals(90, FakeImageOutput2[2][2].getBlue());
  }
}