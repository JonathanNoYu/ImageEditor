import static org.junit.Assert.assertEquals;

import Util.ImageUtil;
import command.ImageControl;
import java.awt.Color;
import java.awt.image.BufferedImage;
import model.ImageIOModel;
import model.PPMModel;
import org.junit.Before;
import org.junit.Test;

public class ImageControlTest {

  PPMModel model1;
  PPMModel model2;
  ImageIOModel model3;
  ImageControl load1;
  ImageControl save1;
  ImageControl load2;
  ImageControl save2;
  ImageControl load3;
  ImageControl save3;
  ImageControl load4;
  ImageControl save4;
  ImageControl load5;
  ImageControl save5;

  @Before
  public void setUp() {
    ImageUtil.setUpPPM();
    ImageUtil.setUpBufferedImages();
    this.model1 = new PPMModel();
    this.model2 = new PPMModel();
    this.model3 = new ImageIOModel();
    this.load1 = new ImageControl("load", "res/image1.ppm", "img1", this.model1);
    this.save1 = new ImageControl("save", "res/image1Save.ppm", "img1", this.model1);
    this.load2 = new ImageControl("load", "res/image2.ppm", "img2", this.model2);
    this.save2 = new ImageControl("save", "res/image2Save.ppm", "img2", this.model2);
    this.load3 = new ImageControl("load", "res/bmp1.bmp", "bmp1", this.model3);
    this.save3 = new ImageControl("save", "res/bmp1Save.bmp", "bmp1", this.model3);
    this.load4 = new ImageControl("load", "res/jpeg1.jpeg", "jpeg1", this.model3);
    this.save4 = new ImageControl("save", "res/jpeg1Save.jpeg", "jpeg1", this.model3);
    this.load5 = new ImageControl("load", "res/png1.png", "png1", this.model3);
    this.save5 = new ImageControl("save", "res/png1Save.png", "png1", this.model3);
    this.load1.process();
    this.save1.process();
    this.load2.process();
    this.save2.process();
    this.load3.process();
    this.save3.process();
    this.load4.process();
    this.save4.process();
    this.load5.process();
    this.save5.process();
  }

  @Test
  public void testLoadAndSave() {
    Color[][] img1 = this.model1.getPPMImage("img1");
    BufferedImage png1 = this.model3.getImage("png1");

    // Each image starts from 1 in its red component and adds one to the next component and so on
    // 2 by 4 -> Image1 and png1
    int component = 1;
    for (int row = 0; row < 2; row++) {
      for (int col = 0; col < 4; col++) {
        Color ppmPixel = img1[row][col];
        Color pngPixel = new Color(png1.getRGB(row, col));
        // Red
        assertEquals(component, ppmPixel.getRed());
        assertEquals(component, pngPixel.getRed());
        // Green
        component += 1;
        assertEquals(component, ppmPixel.getGreen());
        assertEquals(component, pngPixel.getGreen());
        component += 1;
        // Blue
        assertEquals(component, ppmPixel.getBlue());
        assertEquals(component, pngPixel.getBlue());
        component += 1;
      }
    }

    // Rests the component back to 1
    // 2 by 2 -> Image2 and BMP
    component = 1;
    Color[][] img2 = this.model2.getPPMImage("img2");
    BufferedImage bmp1 = this.model3.getImage("bmp1");
    for (int row = 0; row < 2; row++) {
      for (int col = 0; col < 2; col++) {
        Color pixel = img2[row][col];
        Color bmpPixel = new Color(bmp1.getRGB(row, col));
        assertEquals(component, pixel.getRed());
        assertEquals(component, bmpPixel.getRed());
        component += 1;
        assertEquals(component, pixel.getGreen());
        assertEquals(component, bmpPixel.getGreen());
        component += 1;
        assertEquals(component, pixel.getBlue());
        assertEquals(component, bmpPixel.getBlue());
        component += 1;
      }
    }

    // JPEG is not consistent and not 100% accurate with its pixels so
    // It is a 2 by 3
    BufferedImage jpeg1 = this.model3.getImage("jpeg1");
    assertEquals(new Color(3, 4, 6), new Color(jpeg1.getRGB(0,0)));
    assertEquals(new Color(6, 7, 9), new Color(jpeg1.getRGB(0,1)));
    assertEquals(new Color(8, 9, 11), new Color(jpeg1.getRGB(0,2)));
    assertEquals(new Color(9, 10, 12), new Color(jpeg1.getRGB(1,0)));
    assertEquals(new Color(11, 12, 14), new Color(jpeg1.getRGB(1,1)));
    assertEquals(new Color(14, 15, 17), new Color(jpeg1.getRGB(1,2)));
  }
}
