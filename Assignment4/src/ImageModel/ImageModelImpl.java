package ImageModel;

import ImageCommands.ImageCommand;
import ImageCommands.ImageOrientation.ImageOrientation;
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
public class ImageModelImpl implements ImageModel {

  private String fileName;
  private final Map<String, Pixel[][]> storedImage; // Stored any Image as it's 2D Pixel Array
  private int row; // loaded Image's row
  private int col; // loaded Image's col
  private int maxValue; // loaded Image's Maximum Value
  private Pixel[][] pixels; // loaded Image's 2D Pixel Array

  /**
   * Default Constructor used to create an empty ImageModel Processor without any image loaded
   */
  public ImageModelImpl() {
    storedImage = new HashMap<>();
  }

  /**
   * Constructor that immediately loads the given image with the name. Use mostly for testing.
   *
   * @param path is the string path of where the image is located
   * @param alis is the name the image is going to be referred by in this model
   */
  public ImageModelImpl(String path, String alis) {
    if (path == null || alis == null) {
      throw new IllegalArgumentException("You must have an image path and image name");
    }
    this.storedImage = new HashMap<>();
    this.loadImage(path, alis);
  }

  @Override
  public void loadImage(String path, String imgName) {
    Scanner scanFile;
    try {
      scanFile = new Scanner(new FileInputStream(path));
      this.fileName = imgName;
    } catch (FileNotFoundException e) {
      System.out.println("File " + imgName + " not found!");
      return;
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
    this.row = scanFile.nextInt();
    this.col = scanFile.nextInt();
    this.maxValue = scanFile.nextInt();
    this.pixels = new Pixel[row][col];
    Pixel[][] storedPixels = new Pixel[row][col];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        int r = scanFile.nextInt(); // gets the red component value
        int g = scanFile.nextInt(); // gets the green component value
        int b = scanFile.nextInt();// gets the blue component value
        this.pixels[i][j] = new Pixel(r, g, b);
        storedPixels[i][j] = new Pixel(r, g, b);
      }
    }

    this.storedImage.put(this.fileName, storedPixels); // Stores the load Image for convince.
  }

  @Override
  public void saveImage(String path, String imageName) {
    Pixel[][] fromStorage = this.getImage(imageName);
    try {
      DataOutputStream output = new DataOutputStream(new FileOutputStream(path));
      String startingData = "P3" + System.lineSeparator() + this.row
          + " " + this.col + System.lineSeparator() + this.getMaxValue(imageName) + System.lineSeparator();
      output.write(startingData.getBytes(StandardCharsets.UTF_8)); // adds initial data
      for (int row = 0; row < this.row; row++) {
        for (int col = 0; col < this.col; col++) { // while there is stuff in the file
          Pixel currPixel = fromStorage[row][col];
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
  public Pixel[][] getImage(String imageName) {
    if (imageName.equals(this.fileName)) {
      return this.pixels;
    } else {
      for (Entry<String, Pixel[][]> entry : this.storedImage.entrySet()) {
        System.out.println(entry.getKey());
        if (entry.getKey().equals(imageName)) {
          return storedImage.get(imageName);
        }
      }
      throw new IllegalArgumentException("There is no image name as " + imageName);
    }
  }

  @Override
  public Pixel getPixel(int row, int col) {
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
      Pixel[][] imagePixels = this.getImage(image);
      int max = 0;
      for (int row = 0; row < this.row; row++) {
        for (int col = 0; col < this.col; col++) {
          int maxValue = imagePixels[row][col].value();
          if (maxValue > max) {
            max = maxValue;
          }
        }
      }
      return max;
    }
  }

  @Override
  public void processCommand(ImageCommand cmd, String image, String alis) throws IOException {
    Pixel[][] newImage = new Pixel[this.row][this.col];
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        newImage[row][col] = cmd.process(this.pixels[row][col].copy());
        // makes a new pixel with the cmd changes
      }
    }
    this.storedImage.put(alis, newImage);
  }

  @Override
  public void processCommand(ImageOrientation cmd, String image, String alis) {
    cmd.inputImage(this.getImage(image));
    this.storedImage.put(alis, cmd.outputImage());
  }
}
