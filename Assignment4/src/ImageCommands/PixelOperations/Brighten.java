package ImageCommands.PixelOperations;

import ImageCommands.ImageCommand;
import ImageModel.Pixel;

/**
 * a {@code PixelOperations.Brighten} represents the brightening of an image.
 */
public class Brighten implements ImageCommand {

  @Override
  public Pixel process(Pixel p) {

    return p;
  }
}
