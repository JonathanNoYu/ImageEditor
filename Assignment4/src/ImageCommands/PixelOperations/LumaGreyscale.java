package ImageCommands.PixelOperations;

import ImageCommands.ImageCommand;
import ImageModel.Pixel;

/**
 * @code PixelOperations.LumaGreyscale} represents the brightness of the greyscale image.
 */
public class LumaGreyscale implements ImageCommand {

  @Override
  public Pixel process(Pixel p) {

    return p;
  }
}
