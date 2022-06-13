import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import model.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests all methods in the pixel class.
 */
public class PixelTest {

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
  public void invalidRGB() {
    try {
      Pixel p = new Pixel(256, 123, 45);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //for invalid Red -> over 255
    }
    try {
      Pixel p = new Pixel(169, 256, 69);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //for invalid Green -> over 255
    }
    try {
      Pixel p = new Pixel(200, 221, 269);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //for invalid Blue -> over 255
    }
    try {
      Pixel p = new Pixel(300, 256, 69);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //for invalid Red and Green -> over 255
    }
    try {
      Pixel p = new Pixel(-1, 123, 129);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //for invalid Red -> under 0
    }
    try {
      Pixel p = new Pixel(145, -3, 89);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //for invalid Green -> under 0
    }
    try {
      Pixel p = new Pixel(234, 159, -2);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //for invalid Blue-> under 0
    }
    try {
      Pixel p = new Pixel(-4, 159, -2);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //for invalid Red and Blue-> under 0
    }
    try {
      Pixel p = new Pixel(-4, -5, -2);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      //for invalid Red, Green and Blue-> under 0
    }
  }

  @org.junit.Test
  public void testCopy() {
    this.p1.setRed(12);
    this.p1.setGreen(1);
    this.p1.setBlue(42);

    this.p2 = this.p1.copy();

    assertEquals(this.p2, this.p1);
    assertEquals(this.p2.getRed(), this.p1.getRed());
    assertEquals(this.p2.getGreen(), this.p1.getGreen());
    assertEquals(this.p2.getBlue(), this.p1.getBlue());
    assertEquals(12, this.p2.getRed());
    assertEquals(1, this.p2.getGreen());
    assertEquals(42, this.p2.getBlue());

    this.p3.setRed(220);
    this.p3.setGreen(6);
    this.p3.setBlue(0);

    this.p4 = this.p3.copy();

    assertEquals(this.p4, this.p3);
    assertEquals(this.p4.getRed(), this.p3.getRed());
    assertEquals(this.p4.getGreen(), this.p3.getGreen());
    assertEquals(this.p4.getBlue(), this.p3.getBlue());
    assertEquals(220, this.p4.getRed());
    assertEquals(6, this.p4.getGreen());
    assertEquals(0, this.p4.getBlue());
  }

  @org.junit.Test
  public void setRed() {
    assertEquals(0, this.p1.getRed());
    this.p1.setRed(76);
    assertEquals(76, this.p1.getRed());

    assertEquals(123, this.p2.getRed());
    this.p2.setRed(67);
    assertEquals(67, this.p2.getRed());

    assertEquals(76, this.p3.getRed());
    this.p3.setRed(45);
    assertEquals(45, this.p3.getRed());

    assertEquals(45, this.p4.getRed());
    this.p4.setRed(93);
    assertEquals(93, this.p4.getRed());

    assertEquals(255, this.p5.getRed());
    this.p5.setRed(78);
    assertEquals(78, this.p5.getRed());
  }

  @org.junit.Test
  public void setGreen() {
    assertEquals(0, this.p1.getGreen());
    this.p1.setGreen(76);
    assertEquals(76, this.p1.getGreen());

    assertEquals(3, this.p2.getGreen());
    this.p2.setGreen(67);
    assertEquals(67, this.p2.getGreen());

    assertEquals(126, this.p3.getGreen());
    this.p3.setGreen(45);
    assertEquals(45, this.p3.getGreen());

    assertEquals(171, this.p4.getGreen());
    this.p4.setGreen(93);
    assertEquals(93, this.p4.getGreen());

    assertEquals(255, this.p5.getGreen());
    this.p5.setGreen(78);
    assertEquals(78, this.p5.getGreen());
  }

  @org.junit.Test
  public void setBlue() {
    assertEquals(0, this.p1.getBlue());
    this.p1.setBlue(76);
    assertEquals(76, this.p1.getBlue());

    assertEquals(252, this.p2.getBlue());
    this.p2.setBlue(67);
    assertEquals(67, this.p2.getBlue());

    assertEquals(212, this.p3.getBlue());
    this.p3.setBlue(45);
    assertEquals(45, this.p3.getBlue());

    assertEquals(7, this.p4.getBlue());
    this.p4.setBlue(93);
    assertEquals(93, this.p4.getBlue());

    assertEquals(255, this.p5.getBlue());
    this.p5.setBlue(78);
    assertEquals(78, this.p5.getBlue());
  }

  @org.junit.Test
  public void setAll() {
    assertEquals(0, this.p1.getRed());
    assertEquals(0, this.p1.getGreen());
    assertEquals(0, this.p1.getBlue());
    this.p1.setAll(90);
    assertEquals(90, this.p1.getRed());
    assertEquals(90, this.p1.getGreen());
    assertEquals(90, this.p1.getBlue());

    assertEquals(123, this.p2.getRed());
    assertEquals(3, this.p2.getGreen());
    assertEquals(252, this.p2.getBlue());
    this.p2.setAll(200);
    assertEquals(200, this.p2.getRed());
    assertEquals(200, this.p2.getGreen());
    assertEquals(200, this.p2.getBlue());

    assertEquals(255, this.p5.getRed());
    assertEquals(255, this.p5.getGreen());
    assertEquals(255, this.p5.getBlue());
    this.p5.setAll(69);
    assertEquals(69, this.p5.getRed());
    assertEquals(69, this.p5.getGreen());
    assertEquals(69, this.p5.getBlue());
  }

  @org.junit.Test
  public void value() {
    assertEquals(0, this.p1.value());
    assertEquals(252, this.p2.value());
    assertEquals(212, this.p3.value());
    assertEquals(171, this.p4.value());
    assertEquals(255, this.p5.value());

  }

  @org.junit.Test
  public void intensity() {
    assertEquals(0, this.p1.intensity());
    assertEquals(126, this.p2.intensity());
    assertEquals(138, this.p3.intensity());
    assertEquals(74, this.p4.intensity());
    assertEquals(255, this.p5.intensity());
  }

  @org.junit.Test
  public void luma() {
    assertEquals(0, this.p1.luma());
    assertEquals(46, this.p2.luma());
    assertEquals(121, this.p3.luma());
    assertEquals(132, this.p4.luma());
    assertEquals(254, this.p5.luma());
  }

  @Test
  public void testToString() {
    assertEquals("[r = 0,g = 0,b = 0]", this.p1.toString());
    assertEquals("[r = 123,g = 3,b = 252]", this.p2.toString());
    assertEquals("[r = 76,g = 126,b = 212]", this.p3.toString());
    assertEquals("[r = 45,g = 171,b = 7]", this.p4.toString());
    assertEquals("[r = 255,g = 255,b = 255]", this.p5.toString());
  }
}