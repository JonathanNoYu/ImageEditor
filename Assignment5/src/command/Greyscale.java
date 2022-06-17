package command;

import Util.ColorUtil;
import command.abstractions.AbstractImageIOCommand;
import java.awt.Color;
import model.ImageModelV2;

/**
 * a{@Greyscale code} represents the class the holds all commands that can convert a pixel to grey
 * in different ways. This ImageCommand is only able to use BufferedImages as their images.
 */
public class Greyscale extends AbstractImageIOCommand {

  /**
   * Main Constructor, must give a type of greyscale to then run. It allows non-supported greyscale
   * because it is later check in the process switch. Additionally, this class is only called by the
   * controller and implementors who know of it.
   *
   * @param type is the type of greyscale you want.
   */
  public Greyscale(String type, String imgName, String newName, ImageModelV2 model) {
    super(type, imgName, newName, model);
  }

  @Override
  public void process() throws IllegalArgumentException {
    for (int row = 0; row < super.row; row++) {
      for (int col = 0; col < super.col; col++) {
        Color pixel = new Color(super.image.getRGB(row, col));
        Color newPixel = this.setColor(this.newColor(pixel));
        super.outImage.setRGB(row, col, newPixel.getRGB());
      }
    }
    super.saveOutImage();
  }

  // Gets the int value for the new Color value greyscale for this pixel
  private int newColor(Color c) {
    switch (super.type) {
      case "value-component":
        return ColorUtil.value(c);
      case "intensity-component":
        return ColorUtil.intensity(c);
      case "luma-component":
        return ColorUtil.luma(c);
      case "red-component":
        return c.getRed();
      case "green-component":
        return c.getGreen();
      case "blue-component":
        return c.getBlue();
      default:
        throw new IllegalArgumentException("There is no greyscale of " + this.type);
    }
  }

  // Sets all the color components to one value.
  private Color setColor(int val) {
    return new Color(val, val, val);
  }
}
