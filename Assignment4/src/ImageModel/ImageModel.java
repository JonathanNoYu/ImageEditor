package ImageModel;

import ImageCommands.ImageCommand;
import ImageCommands.ImageOrientation.ImageOrientation;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * An interface that represents the model of an image modifier.
 * This model will include the files, their storage and the ability to manipulate the data from the
 * files.
 *
 */
public interface ImageModel {

  /**
   * Loads the selected image into the model, and is meant to be used to store an image to then use.
   *
   * @param path is the path the image comes from
   * @param imgName is the name the user wants to call back the file/image they loaded
   */
  public void loadImage(String path, String imgName);

  /**
   * Saves/outputs an image to the specified path. This is used, so you can have may copy of the
   * augmented image, but they are not automatically saved. So this method is used to output and
   * therefore save the image.
   *
   * @param path is the path the image is going to save to
   * @param destName is the name for the file being saved
   */
  public void saveImage(String path, String destName);

  /**
   * Gets a specific pixel if the pixel is on the board.
   *
   * @param row is the row of the pixel we want
   * @param col is the col of the pixel we want
   * @return
   */
  public Pixel getPixel(int row, int col);

  /**
   * Gets the total columns of the loaded image.
   * @return int that is the columns of the loaded image.
   */
  public int getCol();

  /**
   * Gets the total rows of the loaded image.
   * @return int that is the rows of the loaded image
   */
  public int getRow();

  /**
   * Processes a generic ImageCommand that is given.
   * This command is made to execute on a given pixel.
   *
   * @param cmd is the image command given to execute
   */
  public void processCommand(ImageCommand cmd, String useImage, String filePath) throws IOException;

  /**
   * Processes a generic Image Orientation type of command that is given.
   * This command is made to execute with a given file as an input, as it orients the image.
   *
   * @param cmd is the image orientation command given to execute
   */
  public void processCommand(ImageOrientation cmd, String useImage, String filePath) throws IOException;
}
