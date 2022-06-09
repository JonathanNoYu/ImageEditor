import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ImageCommands.PixelOperations.Greyscale;
import ImageModel.Pixel;

/**
 * Tests all the methods in the Greyscale class.
 */
public class GreyscaleTest {

  Pixel p1;
  Pixel p2;
  Pixel p3;
  Pixel p4;
  Pixel p5;

  @Before
  public void init() {
    // black
    this.p1 = new Pixel(0, 0, 0);
    // purple
    this.p2 = new Pixel(123, 3, 252);
    // blue
    this.p3 = new Pixel(76, 126, 212);
    // green
    this.p4 = new Pixel(45, 171, 7);
    // white
    this.p5 = new Pixel(255, 255, 255);
  }

  @Test
  public void valueGreyscale() {
    init();
    Greyscale valueGreyscale = new Greyscale("value-component");
    assertEquals(new Pixel(0,0,0),valueGreyscale.valueGreyscale(p1));
    assertEquals(0, this.p1.value());
    assertEquals(new Pixel(252,252,252),valueGreyscale.valueGreyscale(p2));
    assertEquals(252, this.p2.value());
    assertEquals(new Pixel(212,212,212),valueGreyscale.valueGreyscale(p3));
    assertEquals(212, this.p3.value());
    assertEquals(new Pixel(171,171,171),valueGreyscale.valueGreyscale(p4));
    assertEquals(171, this.p4.value());
    assertEquals(new Pixel(255,255,255),valueGreyscale.valueGreyscale(p5));
    assertEquals(255, this.p5.value());
  }


  @Test
  public void blueGreyscale() {
    init();
    Greyscale blueGreyscale = new Greyscale("blue-component");
    blueGreyscale.blueGreyscale(p1);
    assertEquals(new Pixel(0,0,0), p1);
    blueGreyscale.blueGreyscale(p2);
    assertEquals(new Pixel(252,252,252), p2);
    blueGreyscale.blueGreyscale(p3);
    assertEquals(new Pixel(212,212,212), p3);
    blueGreyscale.blueGreyscale(p4);
    assertEquals(new Pixel(7,7,7), p4);
    blueGreyscale.blueGreyscale(p5);
    assertEquals(new Pixel(255,255,255), p5);

  }

  @Test
  public void greenGreyscale() {
    init();
    Greyscale greenGreyscale = new Greyscale("green-component");
    greenGreyscale.greenGreyscale(p1);
    assertEquals(new Pixel(0,0,0), p1);
    greenGreyscale.greenGreyscale(p2);
    assertEquals(new Pixel(3,3,3), p2);
    greenGreyscale.greenGreyscale(p3);
    assertEquals(new Pixel(126,126,126), p3);
    greenGreyscale.greenGreyscale(p4);
    assertEquals(new Pixel(171,171,171), p4);
    greenGreyscale.greenGreyscale(p5);
    assertEquals(new Pixel(255,255,255), p5);
  }

  @Test
  public void redGreyscale() {
    init();
    Greyscale redGreyscale = new Greyscale("red-component");
    redGreyscale.redGreyscale(p1);
    assertEquals(new Pixel(0,0,0), p1);
    redGreyscale.redGreyscale(p2);
    assertEquals(new Pixel(123,123,123), p2);
    redGreyscale.redGreyscale(p3);
    assertEquals(new Pixel(76,76,76), p3);
    redGreyscale.redGreyscale(p4);
    assertEquals(new Pixel(45,45,45), p4);
    redGreyscale.redGreyscale(p5);
    assertEquals(new Pixel(255,255,255), p5);


  }

  @org.junit.Test
  public void intensity() {
    init();
    Greyscale intensity = new Greyscale("intensity-component");
    intensity.intensity(p1);
    assertEquals(new Pixel(0,0,0),p1);
    intensity.intensity(p2);
    assertEquals(new Pixel(126,126,126),p2);
    intensity.intensity(p3);
    assertEquals(new Pixel(138,138,138),p3);
    intensity.intensity(p4);
    assertEquals(new Pixel(74,74,74),p4);
    intensity.intensity(p5);
    assertEquals(new Pixel(255,255,255),p5);

  }

  @org.junit.Test
  public void luma() {
    init();
    Greyscale luma = new Greyscale("luma-component");
    luma.luma(p1);
    assertEquals(new Pixel(0,0,0),p1);
    luma.luma(p2);
    assertEquals(new Pixel(46,46,46),p2);
    luma.luma(p3);
    assertEquals(new Pixel(121,121,121),p3);
    luma.luma(p4);
    assertEquals(new Pixel(132,132,132),p4);
    luma.luma(p5);
    assertEquals(new Pixel(254,254,254),p5);


  }
  @Test
  public void testToString() {
    init();
    assertEquals("[r = 0,g = 0,b = 0]", p1.toString());
    Greyscale redGreyscale = new Greyscale("red-component");
    redGreyscale.redGreyscale(p2);
    assertEquals("[r = 123,g = 123,b = 123]", this.p2.toString());
    Greyscale greenGreyscale = new Greyscale("green-component");
    greenGreyscale.greenGreyscale(p3);
    assertEquals("[r = 126,g = 126,b = 126]", this.p3.toString());
    Greyscale blueGreyscale = new Greyscale("blue-component");
    blueGreyscale.blueGreyscale(p4);
    assertEquals("[r = 7,g = 7,b = 7]", this.p4.toString());
    Greyscale luma = new Greyscale("luma-component");
    luma.luma(p5);
    assertEquals("[r = 254,g = 254,b = 254]", this.p5.toString());
  }

}