package model;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * This is the section version of the image model interface that will deal with bufferedImages.
 * It will also include ppm file capabilities by converting ppm images into bufferedImages.
 * You should note the new imageModel does not have a maxValue method for a bufferedImage.
 */
public interface ImageModelV2 extends ImageModel {

  /**
   * Gets the image if the image is either loaded or stored from prior modification.
   *
   * @param imageName is the name of the Image that we want to get
   * @return a BufferedImage that is desired
   * @throws IllegalArgumentException when the image is not in the storage or is not loaded
   */
  BufferedImage getImage(String imageName) throws IllegalArgumentException;

  /**
   * Puts a bufferedImage inside the storage of this new model.
   *
   * @param name is the alias of the image
   * @param image is the image itself
   */
  void putInStorage(String name, BufferedImage image);
}
