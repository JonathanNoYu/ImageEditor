package ImageCommands.PixelOperations;

import ImageCommands.ImageCommand;
import ImageModel.Pixel;

/**
 * a {@code PixelOperations.Brighten} represents the brightening of an image.
 */
public class Brighten implements ImageCommand {
  int increment;

  public Brighten(int increment) {
    if (increment < 0 || increment > 255) {
      throw new IllegalArgumentException("Valid increments is 0 to 255");
    }
    this.increment = increment;
  }

  @Override
  public Pixel process(Pixel p) {

    return p;
  }
}
