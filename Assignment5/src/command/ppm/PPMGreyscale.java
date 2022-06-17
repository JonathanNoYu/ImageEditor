package command.ppm;

import Util.ColorUtil;
import command.abstractions.AbstractPPMCommand;
import java.awt.Color;
import model.ImageModel;

/**
 * a{@Greyscale code} represents the class the holds all commands that can convert a pixel to grey
 * in different ways. PPM compatible image command only.
 */
public class PPMGreyscale extends AbstractPPMCommand {

  /**
   * Main Constructor, must give a type of greyscale to then run. It allows non-supported greyscale
   * because it is later check in the process switch. Additionally, this class is only called by the
   * controller and implementors who know of it.
   *
   * @param type is the type of greyscale you want.
   */
  public PPMGreyscale(String type, String imgName, String newName, ImageModel model) {
    super(type, imgName, newName, model);
  }

  @Override
  public void process() throws IllegalArgumentException {
    for (int row = 0; row < super.row; row++) {
      for (int col = 0; col < super.col; col++) {
        super.outImage[row][col] = this.setColor(this.newColor(super.image[row][col]));
      }
    }
    super.saveOutImage();
  }

  // Gets the int value for the new Color value greyscale for this pixel
  protected int newColor(Color c) {
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
  protected Color setColor(int val) {
    return new Color(val, val, val);
  }
}
