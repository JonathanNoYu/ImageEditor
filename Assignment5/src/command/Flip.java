package command;

import command.abstractions.AbstractImageIOCommand;
import model.ImageModelV2;

/**
 * This class is the command for flipping an image. It currently supports only horizontal and
 * vertical flips. Only works on BufferedImages.
 */
public class Flip extends AbstractImageIOCommand {

  /**
   * Main constructor, you must discern which type of flip you are. The type is checked when asked
   * for an output, to reduce a need to always check for available flips in the constructor.
   *
   * @param type is the string name for the type of flip you want
   */
  public Flip(String type, String imgName, String newName, ImageModelV2 model) {
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

  // Flips the input image vertically
  private void flipVertical () {
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        super.outImage.setRGB(row, this.col - 1 - col, super.image.getRGB(row, col));
      }
    }
  }


  // Flips the input image horizontally
  private void flipHorizontally() {
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        super.outImage.setRGB(this.row - 1 - row, col, super.image.getRGB(row, col));
      }
    }
  }
}
