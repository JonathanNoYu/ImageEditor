package command;

import model.Pixel;

/**
 * Interface for creating new commands in which we process images, specifcally at the pixel leve.
 * Other interfaces that may expand on the methods and add to it to create a change to the image on
 * more than just a pixel at a time. Additionally, the convention we are using is, we make a copy
 * of the pixel or input Image before we augment and output said pixel or image.
 */
public interface ImageCommand {

  /**
   * Processes the current three pixels and does some sort of operation(s) on them.
   * In Image commands that extend and augment the whole image, this method may be obsolete but is
   * kept as it may be able to help combine multiple commands at once.
   */
  Pixel process(Pixel p);

  /**
   * Overrides toString to give a more readable command name when asked to reference what it is.
   * Mainly used for testing however can be useful later on.
   * @return
   */
  String toString();

  /**
   * cmdType is to call what type of interface the command is implementing, so an Image Orientation
   * would return orientation rather than the more general Image Command. This is to quick fix to
   * not correctly using dynamic dispatch. In later update this would be simplified, however I
   * found this mistake too late to "publish" this assignment with the new patch.
   * @return
   */
  String cmdType();
}
