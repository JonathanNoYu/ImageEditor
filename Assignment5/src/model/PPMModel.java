package model;

import Util.ColorUtil;
import java.awt.Color;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * A Class that stores and represents an implementation of an image editor for .ppm as of right now.
 * It can store only one image at a time and save/output images.
 */
public class PPMModel implements ImageModel {

  private String fileName;
  private final Map<String, Color[][]> storedImage; // Stored any Image as its 2D Pixel Array
  private int row; // loaded Image's row
  // INVARIANT: row is always 0 or greater because a given P3 has an image that must have one pixel
  //  at (0,0)
  private int col; // loaded Image's col
  // INVARIANT: col is always 0 or greater because a given P3 has an image that must have one pixel
  //  at (0,0)
  private int maxValue; // loaded Image's Maximum Value
  // maxValue: col is always 0 or greater because a given ppm image must have a pixel that is valid
  // aka a pixel with three component that is from 0 to 255.
  private Color[][] pixels; // loaded Image's 2D Pixel Array

  /**
   * Default Constructor used to create an empty ImageModel Processor without any image loaded. This
   * is mostly used as we can not guess what image a user wants to load.
   */
  public PPMModel() {
    storedImage = new HashMap<>();
  }

  /**
   * Constructor that immediately loads the given image with the name. Use mostly for testing.
   *
   * @param path is the string path of where the image is located
   * @param alis is the name the image is going to be referred by in this model
   */
  public PPMModel(String path, String alis) {
    if (path == null || alis == null) {
      throw new IllegalArgumentException("You must have an image path and image name");
    }
    this.storedImage = new HashMap<>();
    this.loadImage(path, alis);
  }

  @Override
  public void loadImage(String path, String imgName) throws IllegalArgumentException {
    // This has to be here since I can load a model with nothing then ask to load null Strings
    if (path == null || imgName == null) {
      throw new IllegalArgumentException("You must have an image path and image name");
    }
    Scanner scanFile;
    try {
      scanFile = new Scanner(new FileInputStream(path));
      this.fileName = imgName;
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + imgName + " not found!");
    }

    StringBuilder fileContents = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (scanFile.hasNextLine()) {
      String s = scanFile.nextLine();
      if (s.charAt(0) != '#') {
        fileContents.append(s);
        fileContents.append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    scanFile = new Scanner(fileContents.toString());

    String token;

    token = scanFile.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
      return;
    }
    this.col = scanFile.nextInt();
    this.row = scanFile.nextInt();
    this.maxValue = scanFile.nextInt();
    this.pixels = new Color[row][col];
    Color[][] storedPixels = new Color[row][col];
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        int r = scanFile.nextInt(); // gets the red component value
        int g = scanFile.nextInt(); // gets the green component value
        int b = scanFile.nextInt();// gets the blue component value
        this.pixels[row][col] = new Color(r, g, b);
        storedPixels[row][col] = new Color(r, g, b);
      }
    }
    this.storedImage.put(this.fileName, storedPixels); // Stores the load Image for convince.
  }

  @Override
  public void saveImage(String path, String imageName) throws IllegalArgumentException {
    Color[][] fromStorage = this.getPPMImage(imageName);
    try {
      DataOutputStream output = new DataOutputStream(new FileOutputStream(path));
      String startingData = "P3" + System.lineSeparator() + this.col
          + " " + this.row + System.lineSeparator() + this.getMaxValue(imageName)
          + System.lineSeparator();
      output.write(startingData.getBytes(StandardCharsets.UTF_8)); // adds initial data
      for (int row = 0; row < this.row; row++) {
        for (int col = 0; col < this.col; col++) { // while there is stuff in the file
          Color currPixel = fromStorage[row][col];
          output.write((currPixel.getRed() + System.lineSeparator())
              .getBytes(StandardCharsets.UTF_8));
          output.write((currPixel.getGreen() + System.lineSeparator())
              .getBytes(StandardCharsets.UTF_8));
          output.write((currPixel.getBlue() + System.lineSeparator())
              .getBytes(StandardCharsets.UTF_8));
          // it writes into the new file being outputted
        }
      }
      output.close();
    } catch (IOException e) {
      System.out.println("File failed to save, Path or Image does not exist");
    }
  }

  @Override
  public Color getPixel(int row, int col) {
    return this.pixels[row][col];
  }

  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public int getCol() {
    return this.col;
  }

  @Override
  public int getMaxValue(String image) {
    if (image.equals(this.fileName)) {
      return this.maxValue;
    } else {
      Color[][] imagePixels = this.getPPMImage(image);
      int max = 0;
      for (int row = 0; row < this.row; row++) {
        for (int col = 0; col < this.col; col++) {
          int maxValue = ColorUtil.value(imagePixels[row][col]);
          if (maxValue > max) {
            max = maxValue;
          }
        }
      }
      return max;
    }
  }

  @Override
  public Color[][] getPPMImage(String imageName) throws IllegalArgumentException {
    if (imageName.equals(this.fileName)) {
      return this.pixels;
    } else {
      for (Entry<String, Color[][]> entry : this.storedImage.entrySet()) {
        if (entry.getKey().equals(imageName)) {
          return storedImage.get(imageName);
        }
      }
      throw new IllegalArgumentException("There is no image name as " + imageName);
    }
  }

  @Override
  public void putInStorage(String name, Color[][] pixels) {
    this.storedImage.put(name, pixels);
  }
}
