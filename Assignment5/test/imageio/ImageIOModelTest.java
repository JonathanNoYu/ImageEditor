package imageio;

import static org.junit.Assert.assertEquals;

import Util.ImageUtil;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import model.ImageIOModel;
import model.ImageModelV2;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the newer ImageIOModel, that uses ImageIO and uses different image formats,
 * such as PNG, JPEG, BMP, etc.
 */
public class ImageIOModelTest {

  ImageModelV2 model1;
  ImageModelV2 model2;
  ImageModelV2 model3;

  @Before
  public void setUp() {
    ImageUtil.setUpPPM();
    ImageUtil.setUpBufferedImages();
    this.model1 = new ImageIOModel();
    this.model2 = new ImageIOModel();
    this.model3 = new ImageIOModel();
  }

  @Test
  public void testInvalidLoads() {
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
      this.model1.loadImage("hahaNotEvenaPath", "pathNotFound");
    } catch (IllegalArgumentException e) {
      assertEquals("The path hahaNotEvenaPath does not exist", e.getMessage());
    }

    try {
      this.model1.loadImage("notRealPath.notAFileType", "pathNotFound");
    } catch (IllegalArgumentException e) {
      assertEquals("File pathNotFound not found!", e.getMessage());
    }
  }

  @Test
  public void testValidLoadsAndGetPixels() {
    // Models are originally empty
    try {
      this.model1.getImage("bmp1");
    } catch (IllegalArgumentException e) {
      assertEquals("There is no image name as bmp1", e.getMessage());
    }
    try {
      this.model2.getImage("jpeg1");
    } catch (IllegalArgumentException e) {
      assertEquals("There is no image name as jpeg1", e.getMessage());
    }
    try {
      this.model3.getImage("png1");
    } catch (IllegalArgumentException e) {
      assertEquals("There is no image name as png1", e.getMessage());
    }

    this.model1.loadImage("res/bmp1.bmp", "bmp1");
    assertEquals(new Color(1, 2, 3), this.model1.getPixel(0, 0));
    assertEquals(new Color(4, 5, 6), this.model1.getPixel(0, 1));
    assertEquals(new Color(7, 8, 9), this.model1.getPixel(1, 0));
    assertEquals(new Color(10, 11, 12), this.model1.getPixel(1, 1));

    this.model2.loadImage("res/jpeg1.jpeg", "jpeg1");
    assertEquals(new Color(3, 4, 6), this.model2.getPixel(0, 0));
    assertEquals(new Color(6, 7, 9), this.model2.getPixel(0, 1));
    assertEquals(new Color(8, 9, 11), this.model2.getPixel(0, 2));
    assertEquals(new Color(9, 10, 12), this.model2.getPixel(1, 0));
    assertEquals(new Color(11, 12, 14), this.model2.getPixel(1, 1));
    assertEquals(new Color(14, 15, 17), this.model2.getPixel(1, 2));

    this.model3.loadImage("res/png1.png", "png1");
    assertEquals(new Color(1, 2, 3), this.model3.getPixel(0, 0));
    assertEquals(new Color(4, 5, 6), this.model3.getPixel(0, 1));
    assertEquals(new Color(7, 8, 9), this.model3.getPixel(0, 2));
    assertEquals(new Color(10, 11, 12), this.model3.getPixel(0, 3));
    assertEquals(new Color(13, 14, 15), this.model3.getPixel(1, 0));
    assertEquals(new Color(16, 17, 18), this.model3.getPixel(1, 1));
    assertEquals(new Color(19, 20, 21), this.model3.getPixel(1, 2));
    assertEquals(new Color(22, 23, 24), this.model3.getPixel(1, 3));
  }

  @Test
  public void testValidSaves() {
    this.model1.loadImage("res/bmp1.bmp", "bmp1");
    assertEquals(2, this.model1.getRow());
    assertEquals(2, this.model1.getCol());
    this.model2.loadImage("res/jpeg1.jpeg", "jpeg1");
    assertEquals(2, this.model2.getRow());
    assertEquals(3, this.model2.getCol());
    this.model3.loadImage("res/png1.png", "png1");
    assertEquals(2, this.model3.getRow());
    assertEquals(4, this.model3.getCol());

    this.model1.saveImage("res/bmp1Save.bmp", "bmp1");
    this.model2.saveImage("res/jpeg1Save.jpeg", "jpeg1");
    this.model3.saveImage("res/png1Save.png", "png1");

    this.model1.loadImage("res/Image1.ppm", "img1");
    assertEquals(2, this.model1.getRow());
    assertEquals(4, this.model1.getCol());
    this.model1.saveImage("res/Image1Save2.ppm", "img1");
    try {
      ImageUtil.readUsingImageIO("res/bmp1Save.bmp");
      ImageUtil.readUsingImageIO("res/jpeg1Save.jpeg");
      ImageUtil.readUsingImageIO("res/png1Save.png");
      ImageUtil.readPPM("res/Image1Save2.ppm");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Test for all the buffered Image outputs/input methods.
   */
  @Test
  public void testBufferedImage() {
    this.model1.loadImage("res/Image1.ppm", "img1");
    this.model1.loadImage("res/bmp1.bmp", "bmp1");
    this.model2.loadImage("res/jpeg1.jpeg", "jpeg1");
    this.model3.loadImage("res/png1.png", "png1");

    BufferedImage img1 = this.model1.getImage("img1"); // PPM are always Converted to
    BufferedImage bmp1 = this.model1.getImage("bmp1"); // Buffered Images
    BufferedImage jpeg1 = this.model2.getImage("jpeg1");
    BufferedImage png1 = this.model3.getImage("png1");

    this.model3.putInStorage("img1", img1);
    this.model2.putInStorage("bmp1", bmp1);
    this.model1.putInStorage("jpeg1", jpeg1);
    this.model1.putInStorage("png1", png1);

    // They all have a row of 2
    for (int row = 0; row < 2; row++) {
      for (int col = 0; col < png1.getHeight(); col++) {
        assertEquals(img1.getRGB(row, col), this.model3.getImage("img1"));
        assertEquals(bmp1.getRGB(row, col), this.model2.getImage("bmp1"));
        assertEquals(jpeg1.getRGB(row, col), this.model1.getImage("jpeg1"));
        assertEquals(png1.getRGB(row, col), this.model1.getImage("png1"));
      }
    }

  }

  /**
   * Test for all the PPM Image outputs/input methods.
   */
  @Test
  public void testPPM() {
    this.model1.loadImage("res/Image1.ppm", "img1");
    this.model1.loadImage("res/bmp1.bmp", "bmp1");
    this.model2.loadImage("res/jpeg1.jpeg", "jpeg1");
    this.model3.loadImage("res/png1.png", "png1");

    Color[][] img1 = this.model1.getPPMImage("img1");
    Color[][] bmp1 = this.model1.getPPMImage("bmp1");
    Color[][] jpeg1 = this.model2.getPPMImage("jpeg1");
    Color[][] png1 = this.model3.getPPMImage("png1");

    this.model3.putInStorage("img1", img1);
    this.model2.putInStorage("bmp1", bmp1);
    this.model1.putInStorage("jpeg1", jpeg1);
    this.model1.putInStorage("png1", png1);

    assertEquals(img1, this.model3.getPPMImage("img1"));
    assertEquals(bmp1, this.model2.getPPMImage("bmp1"));
    assertEquals(jpeg1, this.model1.getPPMImage("jpeg1"));
    assertEquals(png1, this.model1.getPPMImage("png1"));
  }
}
