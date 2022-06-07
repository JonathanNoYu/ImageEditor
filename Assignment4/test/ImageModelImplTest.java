import ImageCommands.PixelOperations.Greyscale;
import ImageCommands.PixelOperations.ValueGreyscale;
import ImageModel.ImageModelImpl;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

/**
 * This testing the model for our image processor.
 * Notes: Test the load, save, get pixels and processing commands
 */
public class ImageModelImplTest {
  ImageModelImpl model1;
  ImageModelImpl model2;

  @Test
  @Before
  public void testLoadImage() {
    model1 = new ImageModelImpl("images/FakeImage.ppm", "Fake-Image");
    assertEquals(5, this.model1.getRow());
    assertEquals(5, this.model1.getCol());
  }

  @Test
  public void testSaveImage() {
    try {
      this.model1.processCommand(new Greyscale("value-component"), "FakeImage","FakeImage-Output1");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    model1.saveImage("images/FakeImage-Output1.ppm", "Fake-Image","FakeImage-Output1");
  }

  @Test
  public void testGetPixel() {
    assertEquals(0, this.model1.getPixel(0,0).getRed());
    assertEquals(0, this.model1.getPixel(0,0).getGreen());
    assertEquals(0, this.model1.getPixel(0,0).getBlue());
    assertEquals(7, this.model1.getPixel(1,2).getRed());
    assertEquals(8, this.model1.getPixel(1,3).getGreen());
    assertEquals(9, this.model1.getPixel(1,4).getBlue());
    assertEquals(11, this.model1.getPixel(2,1).getRed());
    assertEquals(11, this.model1.getPixel(2,1).getGreen());
    assertEquals(11, this.model1.getPixel(2,1).getBlue());
    assertEquals(15, this.model1.getPixel(3,0).getRed());
    assertEquals(15, this.model1.getPixel(3,0).getGreen());
    assertEquals(15, this.model1.getPixel(3,0).getBlue());
    assertEquals(255, this.model1.getPixel(4,4).getRed());
    assertEquals(255, this.model1.getPixel(4,4).getGreen());
    assertEquals(255, this.model1.getPixel(4,4).getBlue());
  }

  @Test
  public void processCommand() {
    try {
      this.model1.processCommand(new ValueGreyscale(), "FakeImage","FakeImage-Output1");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    // Loaded pixels should not change
    assertEquals(0, this.model1.getPixel(0,0).getRed());
    assertEquals(0, this.model1.getPixel(0,0).getGreen());
    assertEquals(0, this.model1.getPixel(0,0).getBlue());
    assertEquals(7, this.model1.getPixel(1,2).getRed());
    assertEquals(8, this.model1.getPixel(1,3).getGreen());
    assertEquals(9, this.model1.getPixel(1,4).getBlue());
    assertEquals(11, this.model1.getPixel(2,1).getRed());
    assertEquals(11, this.model1.getPixel(2,1).getGreen());
    assertEquals(11, this.model1.getPixel(2,1).getBlue());
    assertEquals(15, this.model1.getPixel(3,0).getRed());
    assertEquals(15, this.model1.getPixel(3,0).getGreen());
    assertEquals(15, this.model1.getPixel(3,0).getBlue());
    assertEquals(255, this.model1.getPixel(4,4).getRed());
    assertEquals(255, this.model1.getPixel(4,4).getGreen());
    assertEquals(255, this.model1.getPixel(4,4).getBlue());
  }

  public void testProcessCommand() {

  }
}