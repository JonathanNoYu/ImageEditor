package ImageCommands.ImageOrientation;

import ImageModel.Pixel;

public class Flip implements ImageOrientation{
  private int row;
  private int col;
  private final String type;
  private Pixel[][] in;
  private Pixel[][] out;

  public Flip(String type) {
    this.type = type;
  }

  @Override
  public Pixel process(Pixel p) {
    return p;
  }

  @Override
  public Pixel[][] outputImage() {
    switch(this.type) {
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

  // Flips the input image veritcally
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
