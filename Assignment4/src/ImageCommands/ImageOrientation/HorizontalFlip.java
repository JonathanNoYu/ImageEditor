package ImageCommands.ImageOrientation;

import ImageModel.Pixel;

/**
 * a {@code ImageCommands.ImageOrientation.ImageOrientation.HorizontalFlip} represents the flipping
 * of an image horizontally.
 */
public class HorizontalFlip implements ImageOrientation {

  Pixel[][] in;
  String fileName;
  Pixel[][] out;

  public HorizontalFlip(String name) {
    this.fileName = name;
  }

  @Override
  public Pixel process(Pixel p) {
    return p;
  }

  @Override
  public Pixel[][] output() {
    return this.out;
  }

  @Override
  public void inputFile(Pixel[][] image) {
    if (image == null) {
      throw new IllegalArgumentException("Image is missing, please load an image");
    }
    this.in = in;
    this.out = new Pixel[in.length][in[0].length];
  }
}
