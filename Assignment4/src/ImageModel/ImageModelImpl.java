package ImageModel;

import ImageCommands.ImageCommand;
import ImageCommands.ImageOrientation.ImageOrientation;
import java.io.DataOutputStream;
import java.io.File;
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

  private File in; // Current loaded image
  private final Map<String, Pixel[][]> storedImage; // Stored any files
  private Scanner scanFile;
  private int row;
  private int col;
  private int maxValue;
  private Pixel[][] pixels;

  public ImageModelImpl() {
    storedImage = new HashMap<>();
  }

  public ImageModelImpl(String path, String imgName) {
    this.loadImage(path, imgName);
    this.fileName = imgName;
    storedImage = new HashMap<>();
  }

  @Override
  public void loadImage(String path, String imgName) {
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
        fileContents.append(s + System.lineSeparator());
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
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        int r = scanFile.nextInt(); // gets the red component value
        int g = scanFile.nextInt(); // gets the green component value
        int b = scanFile.nextInt();// gets the blue component value
        pixels[i][j] = new Pixel(r, g, b);
      }
    }
  }

  @Override
  public void saveImage(String path, String  imageName, String destName) {
    Pixel[][] fromStorage;
    if (imageName.equals(this.fileName)) {
      fromStorage = this.pixels;
    } else {
      for (Entry imgName: this.storedImage.entrySet()) {
        if (imgName.equals(imageName)) {
          fromStorage = storedImage.get(imgName);
        }
      }
      throw new IllegalArgumentException("There is no image name as " + destName);
    }
    try {
      DataOutputStream output = new DataOutputStream(new FileOutputStream(path));
      String startingData = "P3" + System.lineSeparator() + this.row
          + " " + this.col + System.lineSeparator() + this.maxValue + System.lineSeparator();
      output.write(startingData.getBytes(StandardCharsets.UTF_8)); // adds initial data
      for (int row = 0; row < this.row; row++) {
        for (int col = 0; col < this.col; col++) { // while there is stuff in the file
          Pixel currPixel = fromStorage[row][col];
          output.write((Integer.toString(currPixel.getRed()) + System.lineSeparator())
                  .getBytes(StandardCharsets.UTF_8));
          output.write((Integer.toString(currPixel.getGreen()) + System.lineSeparator())
              .getBytes(StandardCharsets.UTF_8));
          output.write((Integer.toString(currPixel.getBlue()) + System.lineSeparator())
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
  public void processCommand(ImageCommand cmd, String image, String alis) throws IOException {
    // Needs to change this method, so we can use any image in the storage...
    Pixel[][] newImage = new Pixel[this.row][this.col];
    for (int row = 0; row < this.row; row++) {
      for (int col = 0; col < this.col; col++) {
        newImage[row][col] = cmd.process(pixels[row][col]);// makes a new pixel with the cmd changes
      }
    }
    this.storedImage.put(alis, newImage);
  }

  @Override
  public void processCommand(ImageOrientation cmd, String image, String destName) {
    cmd.inputFile(this.pixels);
    this.storedImage.put(destName, cmd.output());
  }
}
