package Mocks;

import ImageCommands.ImageCommand;
import ImageCommands.ImageOrientation.ImageOrientation;
import ImageModel.ImageModel;
import ImageModel.Pixel;
import java.io.IOException;

/**
 * Mock Image Model class to test inputs from the controller. All method calls are documented in the
 * log which is specifically a string builder.
 */
public class MockImageModel implements ImageModel {

  private final StringBuilder log;

  /**
   * Default Constructor with only the log, this is so we can use the log outside the Mock.
   *
   * @param log is the log for the Mock
   */
  public MockImageModel(StringBuilder log) {
    this.log = log;
  }

  /**
   * Default Constructor with only the log, this is so we can use the log outside the Mock.
   *
   * @param path    is the path of the desired image to be loaded
   * @param imgName is the alis of the image to be loaded, that it will be referenced as
   * @param log     is the log for the Mock
   */
  public MockImageModel(String path, String imgName, StringBuilder log) {
    this.log = log;
    this.log.append("Load Image at Path: " + path + " Alis:" + imgName + System.lineSeparator());
  }

  @Override
  public void loadImage(String path, String imgName) {
    this.log.append("Load Image at Path: " + path + " Alis: " + imgName + System.lineSeparator());
  }

  @Override
  public void saveImage(String path, String imageName) {
    this.log.append(
        "Save Image at Path: " + path + " Alis: " + imageName + System.lineSeparator());
  }

  @Override
  public Pixel getPixel(int row, int col) {
    this.log.append("getPixel Method Called With row: " + row + " col: " + col
        + System.lineSeparator());
    return new Pixel(0, 0, 0);
  }

  @Override
  public int getCol() {
    this.log.append("getCol Method Called" + System.lineSeparator());
    return 0;
  }

  @Override
  public int getRow() {
    this.log.append("getRow Method Called" + System.lineSeparator());
    return 0;
  }

  @Override
  public int getMaxValue(String image) {
    this.log.append("getMaxValue Method Called with Image: " + image + System.lineSeparator());
    return 0;
  }

  @Override
  public Pixel[][] getImage(String ImageName) {
    this.log.append("getImage Method called with Image: " + ImageName + System.lineSeparator());
    return new Pixel[0][0];
  }

  @Override
  public void processCommand(ImageCommand cmd, String newImageName, String filePath) {
    this.log.append("processCommand Method Called with ImageCommand cmd: " + cmd
        + " newImageName: " + newImageName + " filePath: " + filePath + System.lineSeparator());
  }

  @Override
  public void processCommand(ImageOrientation cmd, String newImageName, String filePath) {
    this.log.append("processCommand Method Called with ImagineOrientation cmd: " + cmd
        + " newImageName: " + newImageName + " filePath: " + filePath + System.lineSeparator());
  }
}
