package command;

import Util.ColorUtil;
import command.abstractions.AbstractImageIOCommand;
import java.awt.Color;
import model.ImageModelV2;

/**
 * Image command that filters images, used to sharpen and blur and works only bufferedImages
 */
public class ColorTransform extends AbstractImageIOCommand {
  private final double[][] matrix;
  /**
   * Main Constructor for the Abstract Image Commands that converts all ppm files into a buffered
   * images as to centralize the images into one data type.
   *
   * @param type    is the specific type of modification one would want to do on an image
   * @param imgName is the current image name that would be modified
   * @param newName is the name of the new image being made
   * @param model   is the model that stores all the images.
   */
  public ColorTransform(String type, String imgName, String newName, ImageModelV2 model) {
    super(type, imgName, newName, model);
    switch (super.type) {
      case "greyscale":
        this.matrix = new double[][]{
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722}};
        return;
      case "sepiaTone":
        this.matrix = new double[][]{
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}};
        return;
      default:
        throw new IllegalArgumentException("There is no color transform as " + super.type);
    }
  }

  @Override
  public void process() {
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        Color newPixel = this.colorTransform(new Color(super.image.getRGB(row,col)));
        super.outImage.setRGB(row,col, newPixel.getRGB());
      }
    }
    super.saveOutImage();
  }

  public Color colorTransform(Color c) {
    int r = c.getRed();
    int g = c.getGreen();
    int b = c.getBlue();
    int newR = (int) ((this.matrix[0][1] * r) + (this.matrix[0][1] * g) + (this.matrix[0][2] * b));
    int newG = (int) ((this.matrix[1][1] * r) + (this.matrix[1][1] * g) + (this.matrix[1][2] * b));
    int newB = (int) ((this.matrix[2][1] * r) + (this.matrix[2][1] * g) + (this.matrix[2][2] * b));
    return new Color(ColorUtil.checkNum(newR), ColorUtil.checkNum(newG), ColorUtil.checkNum(newB));
  }
}
