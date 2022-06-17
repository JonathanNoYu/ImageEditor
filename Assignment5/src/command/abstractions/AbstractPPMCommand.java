package command.abstractions;

import command.ImageCommand;
import java.awt.Color;
import model.ImageModel;

/**
 * Abstracted class for the most ppm image commands, as they all need a model, image they are
 * working on and a way to save the image. Therefore, this class is made to reduce the duplicate
 * code. This class also only deals with ppm files.
 */
public abstract class AbstractPPMCommand implements ImageCommand {

  protected final String newName;
  protected final String imgName;
  protected final ImageModel model;
  protected final String type;
  protected Color[][] image;
  protected Color[][] outImage;
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
  public AbstractPPMCommand(String type, String imgName, String newName, ImageModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model must exist");
    }
    if (imgName == null || newName == null || type == null) {
      throw new IllegalArgumentException("You must have an image name, new image name and type");
    }
    this.newName = newName;
    this.imgName = imgName;
    this.model = model;
    this.type = type;
    Color[][] img = this.model.getPPMImage(imgName);
    this.image = img;
    this.row = img.length;
    this.col = img[0].length;
    this.outImage = new Color[this.row][this.col];
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
   * @return 2D array of colors that is the output image
   */
  public Color[][] outputImage() {
    return this.outImage;
  }
}

