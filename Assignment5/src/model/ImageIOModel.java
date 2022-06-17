package model;

import Util.ColorUtil;
import Util.ImageUtil;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;

/**
 * This Image Model handles images that the ImageIO class handles. This includes JPG, PNG and BMP.
 * This version of the image model will also support ppm files by using composition. Additionally,
 * this model will prioritize a bufferedImage over using a 2D color array.
 */
public class ImageIOModel implements ImageModelV2 {

  private String fileName;
  private BufferedImage loadedImg;
  private final Map<String, BufferedImage> storedImg;

  public ImageIOModel() {
    this.storedImg = new HashMap<>();
  }

  public ImageIOModel(String path, String imgName) {
    if (path == null || imgName == null) {
      throw new IllegalArgumentException("Your path and imgName must exist");
    }
    this.storedImg = new HashMap<>();
    this.fileName = imgName;
    this.loadImage(path, imgName);
  }

  @Override
  public void loadImage(String path, String imgName) throws IllegalArgumentException {
    BufferedImage img;
    // This has to be here since I can load a model with nothing then ask to load null Strings
    if (path == null || imgName == null) {
      throw new IllegalArgumentException("You must have an image path and image name");
    }
    String fileType = this.getFileType(path);
    if (fileType.equals("ppm")) {
      img = ImageUtil.ppmFileToBuffered(path);
    } else {
      try {
        img = ImageIO.read(new FileInputStream(path));
        this.fileName = imgName;
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File " + imgName + " not found!");
      } catch (IOException e) {
        throw new IllegalArgumentException("File " + imgName + " is not a supported image");
      }
    }
    this.loadedImg = img;
    this.storedImg.put(imgName, img);
  }

  @Override
  public void saveImage(String path, String imageName) throws IllegalArgumentException {
    String fileType = this.getFileType(path);
    if (fileType.equals("ppm")) {
      Color[][] img = ImageUtil.BufferedToPPM(this.getImage(imageName));
      ImageUtil.savePPMImage(path, img);
      return;
    }
    BufferedImage img = this.storedImg.get(imageName);
    try {
      ImageIO.write(img, fileType, new File(path));
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private String getFileType(String path) throws IllegalArgumentException {
    try {
      return path.split("\\.")[1];
    } catch (ArrayIndexOutOfBoundsException e){
      throw new IllegalArgumentException("The path " + path + " does not exist");
    }
  }

  public int getRow() {
    try {
      return this.loadedImg.getWidth();
    } catch (NullPointerException e) {
      return 0;
    }
  }

  public int getCol() {
    try {
      return this.loadedImg.getHeight();
    } catch (NullPointerException e) {
      return 0;
    }
  }

  @Override
  public int getMaxValue(String image) {
    BufferedImage img = this.getImage(image);
    int maxValue = 0;
    for (int row = 0; row < img.getWidth(); row++) {
      for (int col = 0; col < img.getHeight(); col++) {
        Color currPixel = new Color(img.getRGB(row, col));
        int r = currPixel.getRed();
        int g = currPixel.getGreen();
        int b = currPixel.getBlue();
        if(maxValue == 255) {
          return maxValue;
        }
        if (maxValue < 255 && (maxValue < r || maxValue < g || maxValue < b)) {
          maxValue = ColorUtil.value(currPixel);
        }
      }
    }
    return maxValue;
  }

  public Color getPixel(int row, int col) {
    return new Color(this.loadedImg.getRGB(row, col));
  }

  @Override
  public void putInStorage(String name, BufferedImage image) {
    this.storedImg.put(name, image);
  }

  @Override
  public BufferedImage getImage(String imageName) throws IllegalArgumentException {
    if (imageName.equals(this.fileName)) {
      return this.loadedImg;
    } else {
      for (Entry<String, BufferedImage> entry : this.storedImg.entrySet()) {
        if (entry.getKey().equals(imageName)) {
          return storedImg.get(imageName);
        }
      }
      throw new IllegalArgumentException("There is no image name as " + imageName);
    }
  }

  @Override
  public Color[][] getPPMImage(String imageName) {
    return ImageUtil.BufferedToPPM(this.getImage(imageName));
  }

  @Override
  public void putInStorage(String name, Color[][] pixels) {
    this.putInStorage(name, ImageUtil.ppmToBuffered(pixels));
  }
}
