package model;

import java.awt.Color;

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
   * @param path      is the path the image is going to save to
   * @param imageName is the name of the image that is wanting to be saved
   * @throws IllegalArgumentException if the path or imageName does not exit or is invalid
   */
  void saveImage(String path, String imageName) throws IllegalArgumentException;

  /**
   * Gets a specific pixel if the pixel is on the board.
   *
   * @param row is the row of the pixel we want
   * @param col is the col of the pixel we want
   * @return a Color at the given row and column
   */
  Color getPixel(int row, int col);

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
   * Given an image name it will retrieve and find the maximum value for a component in the whole
   * image.
   *
   * @param image is the name of the image you want to get the max value of
   * @return the integer for the maximum value
   */
  int getMaxValue(String image);

  /**
   * Gets the image if the image is either loaded or stored from prior modification.
   *
   * @param imageName is the name of the Image that we want to get
   * @return a 2D Util.ColorUtil Array representing the image
   * @throws IllegalArgumentException when the image is not in the storage or is not loaded
   */
  Color[][] getPPMImage(String imageName) throws IllegalArgumentException;

  /**
   * Puts a given image in the form of a 2D array of colors and stores the image for later use.
   * @param name is the name the image will be referred to
   * @param pixels is the 2D Array of colors aka the pixels of the image.
   */
  void putInStorage(String name, Color[][] pixels);
}
