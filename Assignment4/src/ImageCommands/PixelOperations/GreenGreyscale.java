package ImageCommands.PixelOperations;

import ImageCommands.ImageCommand;
import ImageModel.Pixel;

/**
 * a {@code PixelOperations.GreenGreyscale} represents the greyscale with the green component of the
 * image.
 */
public class GreenGreyscale implements ImageCommand {

  @Override
  public Pixel process(Pixel p) {

    return p;
  }
}
