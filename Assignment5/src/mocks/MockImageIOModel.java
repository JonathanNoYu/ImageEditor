package mocks;

import java.awt.Color;
import java.awt.image.BufferedImage;
import model.ImageModelV2;

/**
 * Mock model for the image model that deals with ImageIO/BufferedImages instead of 2D color arrays.
 */
public class MockImageIOModel implements ImageModelV2 {
  private final StringBuilder log;

  /**
   * This is the main constructor that will give in a log that is not null and is only append to.
   * Since there is no way to read the log other than having the log be a reference there is no
   * automatically made new log.
   * @param log is the need log to append messages to
   */
  public MockImageIOModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void loadImage(String path, String imgName) throws IllegalArgumentException {
    this.log.append("loadImage-> Path: " + path + " imgName: " + imgName + System.lineSeparator());
  }

  @Override
  public void saveImage(String path, String imageName) throws IllegalArgumentException {
    this.log.append("saveImage-> Path: " + path + " imgName: " + imageName
        + System.lineSeparator());
  }

  @Override
  public Color getPixel(int row, int col) {
    this.log.append("getPixel-> row: " + row + " col: " + col + System.lineSeparator());
    return new Color(0,0,0);
  }

  @Override
  public int getCol() {
    this.log.append("getCol" + System.lineSeparator());
    return 1;
  }

  @Override
  public int getRow() {
    this.log.append("getRow" + System.lineSeparator());
    return 1;
  }

  @Override
  public int getMaxValue(String image) {
    this.log.append("getMaxValue-> Image: " + image + System.lineSeparator());
    return 1;
  }

  @Override
  public Color[][] getPPMImage(String imageName) throws IllegalArgumentException {
    this.log.append("getPPMImage-> Image: " + imageName + System.lineSeparator());
    return new Color[1][1];
  }

  @Override
  public void putInStorage(String name, Color[][] pixels) {
    this.log.append("putInStorage-> Name: " + name + " PPM Image"+ System.lineSeparator());
  }

  @Override
  public BufferedImage getImage(String imageName) throws IllegalArgumentException {
    this.log.append("getImage-> ImageName: " + imageName +System.lineSeparator());
    return new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
  }

  @Override
  public void putInStorage(String name, BufferedImage image) {
    this.log.append("putInStorage-> Name: " + name + " Buffered Image" +System.lineSeparator());
  }
}
