package ImageModel;

import ImageCommands.ImageCommand;
import ImageCommands.ImageOrientation.ImageOrientation;
import java.io.IOException;

/**
 * An interface that represents the model of an image modifier. This model will include the files,
 * their storage and the ability to manipulate the data from the files.
 */
public interface ImageModel {

  /**
   * Loads the selected image into the model, and is meant to be used to store an image to then
   * use.
   *
   * @param path    is the path the image comes from
   * @param imgName is the name the user wants to call back the file/image they loaded
   * @throws IllegalArgumentException if the path or imageName does not exit or is invalid
   */
  void loadImage(String path, String imgName) throws IllegalArgumentException;

  /**
   * Saves/outputs an image to the specified path. This is used, so you can have may copy of the
   * augmented image, but they are not automatically saved. So this method is used to output and
   * therefore save the image.
   *
   * @param path     is the path the image is going to save to
   * @param imageName is the name of the image that is wanting to be saved
   * @throws IllegalArgumentException if the path or imageName does not exit or is invalid
   */
  void saveImage(String path, String  imageName) throws IllegalArgumentException;

  /**
   * Gets a specific pixel if the pixel is on the board.
   *
   * @param row is the row of the pixel we want
   * @param col is the col of the pixel we want
   * @return a Pixel at the given row and column
   */
  Pixel getPixel(int row, int col);

  /**
   * Gets the total columns of pixels in the loaded image.
   *
   * @return int that is the columns of the loaded image.
   */
  int getCol();

  /**
   * Gets the total rows of pixels in the loaded image.
   *
   * @return int that is the rows of the loaded image
   */
  int getRow();

  /**
   * This method returns the max value of all the pixels in the image specified.
   * @param image is the name of the image you want to get the max value of
   * @return the integer for the maximum value
   */
  int getMaxValue(String image);

  /**
   * Gets the image if the image is either loaded or stored from prior modification.
   * @param ImageName is the name of the Image that we want to get
   * @return a 2D Pixel Array representing the image
   * @throws IllegalArgumentException when the image is not in the storage or is not loaded
   */
  Pixel[][] getImage(String ImageName) throws IllegalArgumentException;

  /**
   * Processes a generic ImageCommand that is given. This command is made to execute on a given
   * pixel.
   *
   * @param cmd is the image command given to execute
   */
  void processCommand(ImageCommand cmd, String newImageName, String filePath) throws IOException;

  /**
   * Processes a generic Image Orientation type of command that is given. This command is made to
   * execute with a given file as an input, as it orients the image. Side note, this will be changed
   * because dynamic dispatch can be used. However currently the top priority was to create
   * something simple and therefore this seemed simple and straightforward.
   *
   * @param cmd is the image orientation command given to execute
   */
  void processCommand(ImageOrientation cmd, String newImageName, String filePath)
      throws IOException;
}
