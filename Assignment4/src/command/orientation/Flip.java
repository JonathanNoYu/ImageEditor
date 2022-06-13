package command.orientation;

import model.Pixel;

/**
 * This class is the command for flipping an image. It currently supports only horizontal and
 * vertical flips.
 */
public class Flip implements ImageOrientation {

  private int row;
  private int col;
  private final String type;
  private Pixel[][] in;
  private Pixel[][] out;

  /**
   * Main constructor, you must discern which type of flip you are. The type is checked when asked
   * for an output, to reduce a need to always check for available flips in the constructor.
   *
   * @param type is the string name for the type of flip you want
   */
  public Flip(String type) {
    this.type = type;
  }

  @Override
  public Pixel process(Pixel p) {
    return p;
  }

  @Override
  public Pixel[][] outputImage() {
    switch (this.type) {
      case "horizontal-flip":
        return this.flipHorizontally();
      case "vertical-flip":
        return this.flipVertical();
      default:
        throw new IllegalArgumentException("There is no such flip as " + type);
    }
  }

  // Flips the input image horizontally
  private Pixel[][] flipHorizontally() {
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        this.out[row][this.col - 1 - col] = this.in[row][col].copy();
      }
    }
    return this.out;
  }

  // Flips the input image vertically
  private Pixel[][] flipVertical() {
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        this.out[this.row - 1 - row][col] = this.in[row][col].copy();
      }
    }
    return this.out;
  }


  @Override
  public void inputImage(Pixel[][] image) {
    if (image == null) {
      throw new IllegalArgumentException("Image is missing, please load an image");
    }
    this.in = image;
    this.col = image[0].length;
    this.row = image.length;
    this.out = new Pixel[row][col];
  }

  @Override
  public String toString() {
    return this.type;
  }

  @Override
  public String cmdType() {
    return "Orientation";
  }
}
