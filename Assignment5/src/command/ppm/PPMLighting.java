package command.ppm;

import command.abstractions.AbstractPPMCommand;
import java.awt.Color;
import model.ImageModel;

/**
 * Only PPM Compatible ImageCommand. The Class represents the lighting manipulation command for a
 * pixel. This class like the other commands check for the valid type when processing a pixel.
 */
public class PPMLighting extends AbstractPPMCommand {

  private final int increment;

  /**
   * Main constructor, it checks only for the increment validity as the type is checked elsewhere.
   *
   * @param increment is the value you want to increase the brightness or darken a pixel.
   * @param type      is the type of light, such as brightening a pixel or darken one.
   */
  public PPMLighting(int increment, String type, String imgName, String newName, ImageModel model) {
    super(type, imgName, newName, model);
    if (increment < 0 || increment > 255) {
      throw new IllegalArgumentException("Valid increments is 0 to 255");
    }
    switch (this.type) {
      case "brighten":
        this.increment = increment;
        return;
      case "darken":
        this.increment = increment * -1;
        return;
      default:
        throw new IllegalArgumentException("There is no greyscale of " + super.type);
    }
  }

  @Override
  public void process() throws IllegalArgumentException {
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        super.outImage[row][col] = this.newPixel(super.image[row][col]);
      }
    }
    super.saveOutImage();
  }

  protected Color newPixel(Color c) {
    int r = c.getRed();
    int g = c.getGreen();
    int b = c.getBlue();
    if ((b + this.increment) >= 0
        && (b + this.increment) < 256) {
      b += this.increment;
    }
    if ((c.getGreen() + this.increment) >= 0
        && (c.getGreen() + this.increment) < 256) {
      g += this.increment;
    }
    if ((r + this.increment) >= 0
        && (r + this.increment) < 256) {
      r += this.increment;
    }
    return new Color(r, g, b);
  }
}
