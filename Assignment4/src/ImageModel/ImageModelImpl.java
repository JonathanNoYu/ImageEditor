package ImageModel;

import ImageCommands.ImageCommand;
import ImageCommands.ImageOrientation.ImageOrientation;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A Class that stores and represents an implementation of an image editor for .ppm as of right now.
 * It can store only one image at a time and save/output images.
 */
public class ImageModelImpl implements ImageModel {
  private File in; // Current loaded image
  private final Map<String, File> storedFiles; // Stored any files
  private Scanner scanFile;
  private int row;
  private int col;
  private int maxValue;
  private Pixel[][] pixels;

  public ImageModelImpl() {
    storedFiles = new HashMap<>();
  }

  public ImageModelImpl(String path, String imgName) {
    this.loadImage(path, imgName);
    storedFiles = new HashMap<>();
  }

  @Override
  public void loadImage(String path, String imgName) {
    try {
      scanFile = new Scanner(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      System.out.println("File " + imgName + " not found!");
      return;
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (scanFile.hasNextLine()) {
      String s = scanFile.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    scanFile = new Scanner(builder.toString());

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
  public void saveImage(String path, String destName) {
    try {
      File fromStorage = storedFiles.get(destName);
      Scanner readFile = new Scanner(fromStorage);
      DataOutputStream outputFile = new DataOutputStream(new FileOutputStream(path));
      while (readFile.hasNext()) { // while there is stuff in the file
        outputFile.write(readFile.nextByte()); // it writes into the new file being outputted
      }
      outputFile.close();
    } catch (IOException e) {
      System.out.println("File failed to save");
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
  public void processCommand(ImageCommand cmd) {
    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.col; j++) {
        pixels[i][j] = cmd.process(pixels[i][j]);
      }
    }
  }

  @Override
  public void processCommand(ImageOrientation cmd) {
    cmd.inputFile(this.pixels);
    this.storedFiles.put(cmd.outputName(), cmd.output());
  }
}
