package command.ppm;

import command.abstractions.AbstractPPMCommand;
import model.ImageModel;

/**
 * only command for flipping an image. It currently supports only horizontal and
 * vertical flips.
 */
public class PPMFlip extends AbstractPPMCommand {

  /**
   * Main constructor, you must discern which type of flip you are. The type is checked when asked
   * for an output, to reduce a need to always check for available flips in the constructor.
   *
   * @param type is the string name for the type of flip you want
   */
  public PPMFlip(String type, String imgName, String newName, ImageModel model) {
    super(type, imgName, newName, model);
  }

  @Override
  public void process() throws IllegalArgumentException {
    switch (this.type) {
      case "horizontal-flip":
        this.flipHorizontally();
        super.saveOutImage();
        return;
      case "vertical-flip":
        this.flipVertical();
        super.saveOutImage();
        return;
      default:
        throw new IllegalArgumentException("There is no such flip as " + type);
    }
  }

  // Flips the input image horizontally
  protected void flipHorizontally() {
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        super.outImage[row][this.col - 1 - col] = super.image[row][col];
      }
    }
  }

  // Flips the input image vertically
  protected void flipVertical() {
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        super.outImage[this.row - 1 - row][col] = super.image[row][col];
      }
    }
  }
}
