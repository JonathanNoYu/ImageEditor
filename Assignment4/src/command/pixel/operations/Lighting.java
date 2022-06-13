package command.pixel.operations;

import command.ImageCommand;
import model.Pixel;

/**
 * This class represents the lighting manipulation command for a pixel. This class like the other
 * commands check for the valid type when processing a pixel.
 */
public class Lighting implements ImageCommand {

  private final String type;
  private final int increment;

  /**
   * Main constructor, it checks only for the increment validity as the type is checked elsewhere.
   *
   * @param increment is the value you want to increase the brightness or darken a pixel.
   * @param type      is the type of light, such as brightening a pixel or darken one.
   */
  public Lighting(int increment, String type) {
    if (increment < 0 || increment > 255) {
      throw new IllegalArgumentException("Valid increments is 0 to 255");
    }
    this.increment = increment;
    this.type = type;
  }

  @Override
  public Pixel process(Pixel p) {
    switch (this.type) {
      case "brighten":
        return this.brighten(p);
      case "darken":
        return this.darken(p);
      default:
        throw new IllegalArgumentException("There is no greyscale of " + this.type);
    }
  }

  // Brightens the image by the given increment if possible.
  private Pixel brighten(Pixel p) {
    if (p.getBlue() + this.increment < 256
        && p.getBlue() + this.increment > 0) {
      p.setBlue(p.getBlue() + this.increment);
    }
    if (p.getGreen() + this.increment < 256
        && p.getGreen() + this.increment > 0) {
      p.setGreen(p.getGreen() + this.increment);
    }
    if (p.getRed() + this.increment < 256
        && p.getRed() + this.increment > 0) {
      p.setRed(p.getRed() + this.increment);
    }
    return p;
  }

  // Darkens the image by the given increment if possible.
  private Pixel darken(Pixel p) {
    if ((p.getBlue() - this.increment) > 0
        && (p.getBlue() - this.increment) < 256) {
      p.setBlue(p.getBlue() - this.increment);
    }
    if ((p.getGreen() - this.increment) > 0
        && (p.getGreen() - this.increment) < 256) {
      p.setGreen(p.getGreen() - this.increment);
    }
    if ((p.getRed() - this.increment) > 0
        && (p.getRed() - this.increment) < 256) {
      p.setRed(p.getRed() - this.increment);
    }
    return p;
  }

  @Override
  public String toString() {
    return this.type;
  }

  @Override
  public String cmdType() {
    return "ImagineCommand";
  }
}
