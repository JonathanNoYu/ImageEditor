package ImageCommands.PixelOperations;

import ImageCommands.ImageCommand;
import ImageModel.Pixel;

public class Lighting implements ImageCommand {

  private final String type;
  private final int increment;

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
