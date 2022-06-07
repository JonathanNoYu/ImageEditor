package ImageCommands.ImageOrientation;

import ImageCommands.ImageCommand;
import ImageModel.Pixel;
import java.io.File;

/**
 * An Interface for ImageCommands that specifically orient an image differently. AKA moving position
 * of pixels in an image.
 */
public interface ImageOrientation extends ImageCommand {

  /**
   * The Image Orientation Implementations will orient or change the positions of the pixels, we
   * need to output the pixel is some way, therefore this method outputs the augmented file.
   *
   * @return File that has all the changes made
   */
  File output();

  /**
   * InputFile is meant to give the command the Image it needs to then rearrange. This is to make it
   * so the model can give its image when it needs to run this command, instead of needing the
   * controller to have the file on hand.
   *
   * @param image is the image represented by a 2D array of pixels
   */
  void inputFile(Pixel[][] image);

  /**
   * OutputName is used to get the name of the file, so that it can be stored later in the model.
   * Since the model does not necessarily have access to the name we need this method.
   *
   * @return String that represents the name the output file goes by
   */
  String outputName();
}
