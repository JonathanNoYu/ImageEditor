package ImageCommands.PixelOperations;

import ImageCommands.ImageCommand;
import ImageModel.Pixel;

/**
 * a {@code PixelOperations.BlueGreyscale} represents a greyscale image with the blue component of
 * the image.
 */
public class BlueGreyscale implements ImageCommand {

  @Override
  public Pixel process(Pixel p) {

    return p;
  }
}
