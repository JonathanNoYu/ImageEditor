package ImageCommands;

import ImageModel.Pixel;

/**
 * Interface for creating new commands in which we process images.
 */
public interface ImageCommand {

  /**
   * Processes the current three pixels and does some sort of operation(s) on them.
   *
   */
  Pixel process(Pixel p);
}
