package ImageCommands.PixelOperations;

import ImageCommands.ImageCommand;
import ImageModel.Pixel;

/**
 * a{@code PixelOperations.ValueGreyscale} represents the value of the greyscale.
 */
public class ValueGreyscale implements ImageCommand {

  @Override
  public Pixel process(Pixel p) {
    p.setAll(p.value());
    return p;
  }
}
