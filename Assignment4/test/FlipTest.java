import static org.junit.Assert.assertEquals;

import ImageCommands.ImageOrientation.Flip;
import ImageCommands.ImageOrientation.ImageOrientation;
import ImageModel.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * This is the testing class for the Image Orientation command, flip. This command should flip the
 * image either horizontally or vertically.
 */
public class FlipTest {

  Pixel[][] image1;
  Pixel[][] image2;
  ImageOrientation hFlip1;
  ImageOrientation hFlip2;
  ImageOrientation vFlip1;
  ImageOrientation vFlip2;

  @Before
  public void setup() {
    // Pixel[2][4] below, i.e. 2 rows and 4 columns a rectangle
    image1 = new Pixel[][]{new Pixel[]{new Pixel(1, 2, 3),
        new Pixel(4, 5, 6), new Pixel(7, 8, 9),
        new Pixel(10, 11, 12)},
        new Pixel[]{new Pixel(13, 14, 15),
            new Pixel(16, 17, 18), new Pixel(19, 20, 21),
            new Pixel(22, 23, 24)}};
    // Visualization below of original image1
    // (1,2,3)      (4,5,6)      (7,8,9)      (10,11,12)
    // (13,14,15)   (16,17,18)   (19,20,21)   (22,23,24)


    // Pixel[2][2] below i.e. 2 rows and 2 columns a square
    image2 = new Pixel[][]{new Pixel[]{new Pixel(1, 2, 3),
        new Pixel(4, 5, 6)}, new Pixel[]{new Pixel(7, 8, 9),
        new Pixel(10, 11, 12)}};
    // Visualization below of original image2
    // (1,2,3)   (4,5,6)
    // (7,8,9)   (10,11,12)

    hFlip1 = new Flip("horizontal-flip");
    vFlip1 = new Flip("vertical-flip");
    hFlip2 = new Flip("horizontal-flip");
    vFlip2= new Flip("vertical-flip");
  }

  @Test
  public void testInvalidFlips() {
    hFlip1 = new Flip("Fake-flip");
    vFlip1 = new Flip("No-flip");
    hFlip2 = new Flip("Yes-flip");
    vFlip2= new Flip("MaybeSomeTimeOrSomething:)-flip");

    this.hFlip1.inputImage(image1);
    this.vFlip1.inputImage(image1);
    this.hFlip2.inputImage(image2);
    this.vFlip2.inputImage(image2);

    try {
      Pixel[][] hFlip1 = this.hFlip1.outputImage();
    } catch (IllegalArgumentException e) {
      assertEquals("There is no such flip as Fake-flip", e.getMessage());
    }

    try {
      Pixel[][] vFlip1 = this.vFlip1.outputImage();
    } catch (IllegalArgumentException e) {
      assertEquals("There is no such flip as No-flip", e.getMessage());
    }

    try {
      Pixel[][] hFlip2 = this.hFlip2.outputImage();
    } catch (IllegalArgumentException e) {
      assertEquals("There is no such flip as Yes-flip", e.getMessage());
    }

    try {
      Pixel[][] vFlip2 = this.vFlip2.outputImage();
    } catch (IllegalArgumentException e) {
      assertEquals("There is no such flip as MaybeSomeTimeOrSomething:)-flip",
          e.getMessage());
    }
  }

  @Test
  public void testValidCMD() {
    // Tests for the rectangle (image1)

    // Visualization below of HFlip of Image1     V.S.  Visualization below of original image1
    // (10,11,12) (7,8,9)    (4,5,6)    (1,2,3)    || (1,2,3)      (4,5,6)      (7,8,9)   (10,11,12)
    // (22,23,24) (19,20,21) (16,17,18) (13,14,15) || (13,14,15)   (16,17,18)   (19,20,21)(22,23,24)
    this.hFlip1.inputImage(image1);
    Pixel[][] hFlip1 = this.hFlip1.outputImage();
    assertEquals("[r = 10,g = 11,b = 12]", hFlip1[0][0].toString());
    assertEquals("[r = 7,g = 8,b = 9]", hFlip1[0][1].toString());
    assertEquals("[r = 4,g = 5,b = 6]", hFlip1[0][2].toString());
    assertEquals("[r = 1,g = 2,b = 3]", hFlip1[0][3].toString());
    assertEquals("[r = 22,g = 23,b = 24]", hFlip1[1][0].toString());
    assertEquals("[r = 19,g = 20,b = 21]", hFlip1[1][1].toString());
    assertEquals("[r = 16,g = 17,b = 18]", hFlip1[1][2].toString());
    assertEquals("[r = 13,g = 14,b = 15]", hFlip1[1][3].toString());

    // Visualization below of VFlip of Image1     V.S.  Visualization below of original image1
    // (13,14,15) (16,17,18) (19,20,21) (22,23,24) || (1,2,3)      (4,5,6)      (7,8,9)   (10,11,12)
    // (1,2,3)    (4,5,6)    (7,8,9)    (10,11,12) || (13,14,15)   (16,17,18)   (19,20,21)(22,23,24)
    this.vFlip1.inputImage(image1);
    Pixel[][] vFlip1 = this.vFlip1.outputImage();
    assertEquals("[r = 13,g = 14,b = 15]", vFlip1[0][0].toString());
    assertEquals("[r = 16,g = 17,b = 18]", vFlip1[0][1].toString());
    assertEquals("[r = 19,g = 20,b = 21]", vFlip1[0][2].toString());
    assertEquals("[r = 22,g = 23,b = 24]", vFlip1[0][3].toString());
    assertEquals("[r = 1,g = 2,b = 3]", vFlip1[1][0].toString());
    assertEquals("[r = 4,g = 5,b = 6]", vFlip1[1][1].toString());
    assertEquals("[r = 7,g = 8,b = 9]", vFlip1[1][2].toString());
    assertEquals("[r = 10,g = 11,b = 12]", vFlip1[1][3].toString());



    // Tests for the square (image2)

    // Visualization below of HFlip of Image2  V.S.   Visualization below of original image2
    // (4,5,6)      (1,2,3)                     || (1,2,3)   (4,5,6)
    // (10,11,12)   (7,8,9)                     || (7,8,9)   (10,11,12)
    this.hFlip2.inputImage(image2);
    Pixel[][] hFlip2 = this.hFlip2.outputImage();
    assertEquals("[r = 4,g = 5,b = 6]", hFlip2[0][0].toString());
    assertEquals("[r = 1,g = 2,b = 3]", hFlip2[0][1].toString());
    assertEquals("[r = 10,g = 11,b = 12]", hFlip2[1][0].toString());
    assertEquals("[r = 7,g = 8,b = 9]", hFlip2[1][1].toString());

    // Visualization below of VFlip of Image2  V.S.   Visualization below of original image2
    // (7,8,9)   (10,11,12)                     || (1,2,3)   (4,5,6)
    // (1,2,3)   (4,5,6)                        || (7,8,9)   (10,11,12)
    this.vFlip2.inputImage(image2);
    Pixel[][] vFlip2= this.vFlip2.outputImage();
    assertEquals("[r = 7,g = 8,b = 9]", vFlip2[0][0].toString());
    assertEquals("[r = 10,g = 11,b = 12]", vFlip2[0][1].toString());
    assertEquals("[r = 1,g = 2,b = 3]", vFlip2[1][0].toString());
    assertEquals("[r = 4,g = 5,b = 6]", vFlip2[1][1].toString());
  }
}