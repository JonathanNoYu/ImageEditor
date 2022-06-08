package ImageCommands.ImageOrientation;

import ImageModel.Pixel;

/**
 * a{@code ImageCommands.ImageOrientation.ImageOrientation.VerticalFlip} represents the class that
 * will flip image vertically.
 */
public class VerticalFlip implements ImageOrientation {

  Pixel[][] in;
  Pixel[][] out;

  public VerticalFlip() {
    // There is no need for arguments give as the model will handle it.
  }

  @Override
  public Pixel process(Pixel p) {
    return p;
  }

  @Override
  public Pixel[][] outputImage() {
    for (int row = 0; row < in.length; row++) {
      for (int col = 0; col < in[0].length; col++) {
        this.out[row][col] = this.in[row][in[0].length - 1 - col].copy();
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
    this.out = new Pixel[in.length][in[0].length];
  }
}
