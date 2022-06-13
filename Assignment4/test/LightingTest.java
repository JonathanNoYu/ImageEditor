import static org.junit.Assert.assertEquals;

import command.pixel.operations.Lighting;
import model.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests all methods in Lighting class.
 */
public class LightingTest {

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
  public void process() {
    Lighting b1 = new Lighting(20, "brighten");
    b1.process(p1);
    assertEquals("[r = 20,g = 20,b = 20]", this.p1.toString());
    b1.process(p2);
    assertEquals("[r = 143,g = 23,b = 252]", this.p2.toString());
    b1.process(p3);
    assertEquals("[r = 96,g = 146,b = 232]", this.p3.toString());
    init();
    Lighting d1 = new Lighting(20, "darken");
    d1.process(p1);
    assertEquals("[r = 0,g = 0,b = 0]", this.p1.toString());
    d1.process(p2);
    assertEquals("[r = 103,g = 3,b = 232]", this.p2.toString());
    d1.process(p3);
    assertEquals("[r = 56,g = 106,b = 192]", this.p3.toString());
  }

  @Test
  public void testToString() {
    assertEquals("[r = 0,g = 0,b = 0]", this.p1.toString());
    Lighting b1 = new Lighting(20, "brighten");
    b1.process(p2);
    assertEquals("[r = 143,g = 23,b = 252]", this.p2.toString());
    Lighting d1 = new Lighting(20, "darken");
    d1.process(p3);
    assertEquals("[r = 56,g = 106,b = 192]", this.p3.toString());
    assertEquals("[r = 45,g = 171,b = 7]", this.p4.toString());
    assertEquals("[r = 255,g = 255,b = 255]", this.p5.toString());
  }
}