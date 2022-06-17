package command;

import Util.ColorUtil;
import command.abstractions.AbstractImageIOCommand;
import java.awt.Color;
import model.ImageModelV2;

/**
 * Image command that filters images, used to sharpen and blur and works only bufferedImages
 */
public class Filter extends AbstractImageIOCommand {

  /**
   * Main Constructor for the Abstract Image Commands that converts all ppm files into a buffered
   * images as to centralize the images into one data type.
   *
   * @param type    is the specific type of modification one would want to do on an image
   * @param imgName is the current image name that would be modified
   * @param newName is the name of the new image being made
   * @param model   is the model that stores all the images.
   */
  public Filter(String type, String imgName, String newName, ImageModelV2 model) {
    super(type, imgName, newName, model);
  }

  @Override
  public void process() throws IllegalArgumentException {
    switch (this.type) {
      case "sharpen":
        this.sharpen();
        super.saveOutImage();
        return;
      case "blur":
        this.blur();
        super.saveOutImage();
        return;
      default:
        throw new IllegalArgumentException("There is no such flip as " + type);
    }
  }

  private void applyMatrix(double[][] matrix, int pRow, int pCol) {
    double red = 0;
    double green = 0;
    double blue = 0;
    int halfLength = matrix.length / 2;
    int length = matrix.length;
    int matrixRow = 0;
    // runs through matrix on that specific pixel
    for (int row = -halfLength + pRow; row < length - halfLength + pRow; row++) {
      int matrixCol = 0;
      for (int col = -halfLength + pCol; col < length - halfLength + pCol; col++) {
        if (row >= 0 && col >= 0 && row < this.row && col < this.col) { // pixel exits
          Color pixel = new Color(super.image.getRGB(row, col));
          red += matrix[matrixRow][matrixCol] * pixel.getRed();
          green += matrix[matrixRow][matrixCol] * pixel.getGreen();
          blue += matrix[matrixRow][matrixCol] * pixel.getBlue();
        }
        matrixCol++;
      }
      matrixRow++;
    }

    red = ColorUtil.checkNum((int) Math.round(red));
    blue = ColorUtil.checkNum((int) Math.round(blue));
    green = ColorUtil.checkNum((int) Math.round(green));
    super.outImage.setRGB(pRow, pCol, new Color((int) red, (int) green, (int) blue).getRGB());
  }

  private void blur() {
    double[][] blur = {
        {0.0625, 0.125, 0.0625},
        {0.125, 0.25, 0.125},
        {0.0625, 0.125, 0.0625}};
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        this.applyMatrix(blur, row, col);
      }
    }
  }

  private void sharpen() {
    double[][] sharpen = {
        {-0.125, -0.125, -0.125, -0.125, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, 0.25, 1, 0.25, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, -0.125, -0.125, -0.125, -0.125}};
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        this.applyMatrix(sharpen, row, col);
      }
    }
  }
}
