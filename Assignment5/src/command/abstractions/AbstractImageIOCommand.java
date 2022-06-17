package command.abstractions;

import command.ImageCommand;
import java.awt.image.BufferedImage;
import model.ImageModelV2;

/**
 * Abstracted class for the most image commands, as they all need a model, image they are working on
 * and a way to save the image. Therefore, this class is made to reduce the duplicate code.
 * Additionally, will be updated, so it can run the newest models, which automatically convert ppm
 * files into bufferedImages.
 */
public abstract class AbstractImageIOCommand implements ImageCommand {

  protected final String newName;
  protected final ImageModelV2 model;
  protected final String type;
  protected BufferedImage image;
  protected BufferedImage outImage;
  protected final int row;
  protected final int col;

  /**
   * Main Constructor for the Abstract Image Commands that converts all ppm files into a buffered
   * images as to centralize the images into one data type.
   *
   * @param type    is the specific type of modification one would want to do on an image
   * @param imgName is the current image name that would be modified
   * @param newName is the name of the new image being made
   * @param model   is the model that stores all the images.
   */
  public AbstractImageIOCommand(String type, String imgName, String newName, ImageModelV2 model) {
    if (model == null) {
      throw new IllegalArgumentException("Model must exist");
    }
    if (imgName == null || newName == null || type == null) {
      throw new IllegalArgumentException("You must have an image name, new image name and type");
    }
    this.newName = newName;
    this.model = model;
    this.type = type;
    BufferedImage img = this.model.getImage(imgName);
    this.image = img;
    this.row = img.getWidth();
    this.col = img.getHeight();
    this.outImage = new BufferedImage(row, col, BufferedImage.TYPE_INT_RGB);
  }

  /**
   * Abstracts the process method for the concrete classes to handle.
   */
  public abstract void process() throws IllegalArgumentException;

  // Used to save the outImage, should be done after the image is changed
  protected void saveOutImage() {
    this.model.putInStorage(this.newName, this.outImage);
  }

  /**
   * This outputs the image that is modified.
   *
   * @return BufferedImage that is the output image
   */
  public BufferedImage outputImage() {
    return this.outImage;
  }
}
