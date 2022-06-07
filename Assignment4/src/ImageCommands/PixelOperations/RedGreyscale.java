package ImageCommands.PixelOperations;

import ImageCommands.ImageCommand;
import ImageModel.Pixel;

/**
 * a {@code ImageCommands.PixelOperations.RedGreyscale} represents the greyscale image with the red
 * component.
 */
public class RedGreyscale implements ImageCommand {

  @Override
  public Pixel process(Pixel p) {

    return p;
  }
}
