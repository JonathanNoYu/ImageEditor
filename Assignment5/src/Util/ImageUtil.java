package Util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.imageio.ImageIO;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static void readPPM(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int r = sc.nextInt(); // gets the red component value
        int g = sc.nextInt(); // gets the green component value
        int b = sc.nextInt();// gets the blue component value
        System.out.println("Color of pixel (" + i + "," + j + "): " + r + "," + g + "," + b);
      }
    }
  }

  /**
   * Reads a file supported by ImageIO
   *
   * @param filePath is the path of the file
   * @throws FileNotFoundException if the file does not exist or path is not supported
   */
  public static void readUsingImageIO(String filePath) throws FileNotFoundException {
    BufferedImage img = new BufferedImage(0,0,BufferedImage.TYPE_INT_RGB);
    try {
      img = ImageIO.read(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filePath + " not found!");
    } catch (IOException e) {
      System.out.println("File " + filePath + " is not a supported image");
    }
    for (int row = 0; row < img.getWidth(); row++) {
      for (int col = 0; col < img.getHeight(); col++) {
        Color pixel = new Color(img.getRGB(row, col));
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();
        System.out.println("Color of pixel (" + row + "," + col + "): " + r + "," + g + "," + b);
      }
    }
  }

  /**
   * Converts a 2D PPM color array into a bufferedImage
   *
   * @param ppm is the ppm array we want to convert
   * @return a bufferedImage of that ppm
   */
  public static BufferedImage ppmToBuffered(Color[][] ppm) {
    int width = ppm.length;
    int height = ppm[0].length;
    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int row = 0; row < width; row++) {
      for (int col = 0; col < height; col++) {
        newImage.setRGB(row, col, ppm[row][col].getRGB());
      }
    }
    return newImage;
  }

  // Converts a 2D color array into a bufferedImage
  public static BufferedImage ppmFileToBuffered(String path) throws IllegalArgumentException {
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    StringBuilder fileInfo = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        fileInfo.append(s + System.lineSeparator());
      }
    }
    sc = new Scanner(fileInfo.toString());
    String token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("This is not a ppm file");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    sc.nextInt(); // throws away the max value
    Color[][] ppmImg = new Color[width][height];
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int r = sc.nextInt(); // gets the red component value
        int g = sc.nextInt(); // gets the green component value
        int b = sc.nextInt();// gets the blue component value
        ppmImg[i][j] = new Color(r, g, b);
      }
    }
    return ppmToBuffered(ppmImg);
  }

  public static Color[][] BufferedToPPM(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    Color[][] ppmImage = new Color[width][height];
    for (int row = 0; row < width; row++) {
      for (int col = 0; col < height; col++) {
        ppmImage[row][col] = new Color(image.getRGB(row, col));
      }
    }
    return ppmImage;
  }

  /**
   * Saves a ppmImage as a file at the path if possible
   *
   * @param path  is the path where the image should be saved at
   * @param image is the 2D Color array of the image
   */
  public static void savePPMImage(String path, Color[][] image) throws IllegalArgumentException {
    int width = image[0].length;
    int height = image.length;
    int maxValue = 0;
    StringBuilder ImageAsString = new StringBuilder();
    for (Color[] colors : image) {
      for (Color pixel : colors) {
        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();
        if (maxValue < 255 && (maxValue < r || maxValue < g || maxValue < b)) {
          maxValue = ColorUtil.value(pixel);
        }
        ImageAsString.append(r).append(" ").append(g).append(" ").append(b).append(" ");
      }
      ImageAsString.append(System.lineSeparator());
    }
    // Tries to save the file.
    try {
      DataOutputStream output = new DataOutputStream(new FileOutputStream(path));
      String startingData = "P3" + System.lineSeparator() + height
          + " " + width + System.lineSeparator() + maxValue
          + System.lineSeparator();
      output.write(startingData.getBytes(StandardCharsets.UTF_8)); // adds initial data
      output.write(ImageAsString.toString().getBytes(StandardCharsets.UTF_8));
      output.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("File failed to save, Path or Image does not exist");
    }
  }

  /**
   * Makes two images that can be used to test upon. One is a square and one is a rectangle.
   */
  public static void setUpPPM() {
    // Util.ColorUtil[2][4] below, i.e. 2 rows and 4 columns a rectangle
    Color[][] image1 = new Color[][]{new Color[]{new Color(1, 2, 3),
        new Color(4, 5, 6), new Color(7, 8, 9),
        new Color(10, 11, 12)},
        new Color[]{new Color(13, 14, 15),
            new Color(16, 17, 18), new Color(19, 20, 21),
            new Color(22, 23, 24)}};
    // Visualization below of original image1
    // (1,2,3)      (4,5,6)      (7,8,9)      (10,11,12)
    // (13,14,15)   (16,17,18)   (19,20,21)   (22,23,24)

    // Util.ColorUtil[2][2] below i.e. 2 rows and 2 columns a square
    Color[][] image2 = new Color[][]{new Color[]{new Color(1, 2, 3),
        new Color(4, 5, 6)}, new Color[]{new Color(7, 8, 9),
        new Color(10, 11, 12)}};
    // Visualization below of original image2
    // (1,2,3)   (4,5,6)
    // (7,8,9)   (10,11,12)

    savePPMImage("res/image1.ppm", image1);
    savePPMImage("res/image2.ppm", image2);
  }

  /**
   *
   */
  public static void setUpBufferedImages() {
    BufferedImage bmp = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB); // BMP is a 2 by 2
    BufferedImage jpeg = new BufferedImage(2, 3, BufferedImage.TYPE_INT_RGB); // JPG is a 2 by 3
    BufferedImage png = new BufferedImage(2, 4, BufferedImage.TYPE_INT_RGB); // PNG is a 2 by 4
    File bmpFile = new File("res/bmp1.bmp");
    File jpegFile = new File("res/jpeg1.jpeg");
    File pngFile = new File("res/png1.png");
    Color p1 = new Color(1, 2, 3);
    Color p2 = new Color(4, 5, 6);
    Color p3 = new Color(7, 8, 9);
    Color p4 = new Color(10, 11, 12);
    Color p5 = new Color(13, 14, 15);
    Color p6 = new Color(16, 17, 18);
    Color p7 = new Color(19, 20, 21);
    Color p8 = new Color(22, 23, 24);
    bmp.setRGB(0, 0, p1.getRGB());
    bmp.setRGB(0, 1, p2.getRGB());
    bmp.setRGB(1, 0, p3.getRGB());
    bmp.setRGB(1, 1, p4.getRGB());

    jpeg.setRGB(0, 0, p1.getRGB());
    jpeg.setRGB(0, 1, p2.getRGB());
    jpeg.setRGB(0, 2, p3.getRGB());
    jpeg.setRGB(1, 0, p4.getRGB());
    jpeg.setRGB(1, 1, p5.getRGB());
    jpeg.setRGB(1, 2, p6.getRGB());


    png.setRGB(0, 0, p1.getRGB());
    png.setRGB(0, 1, p2.getRGB());
    png.setRGB(0, 2, p3.getRGB());
    png.setRGB(0, 3, p4.getRGB());
    png.setRGB(1, 0, p5.getRGB());
    png.setRGB(1, 1, p6.getRGB());
    png.setRGB(1, 2, p7.getRGB());
    png.setRGB(1, 3, p8.getRGB());

    try {
      ImageIO.write(bmp, "bmp", bmpFile);
      ImageIO.write(jpeg, "jpeg", jpegFile);
      ImageIO.write(png, "png", pngFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Demo main that runs a ppm file.
   *
   * @param args is the string array with the name of the file you want to read
   */
  public static void main(String[] args) {
    String filename;

    if (args.length > 0) {
      filename = args[1];
    } else {
      filename = "sample.ppm";
    }

    switch (args[0]) {
      case "ppm":
        ImageUtil.readPPM(filename);
        return;
      case "png":
      case "jpg":
      case "bmp":
        try {
          readUsingImageIO(filename);
        } catch (FileNotFoundException e) {
          System.out.println(e.getMessage());
        }
        return;
    }
  }
}

