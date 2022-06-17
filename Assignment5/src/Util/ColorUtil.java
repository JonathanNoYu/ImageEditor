package Util;

import java.awt.Color;

/**
 * A class that represents a single pixel, it is a simple object with three fields, one for each RBG
 * color and can do basic operations on those three components of a pixel.
 */
public class ColorUtil {

  /**
   * Calculates the maximum value of this pixel.
   *
   * @param color is the pixel we want to find the max value of
   * @return int that is the largest component
   */
  public static int value(Color color) {
    return Math.max(Math.max(color.getRed(), color.getGreen()), color.getBlue());
  }

  /**
   * Calculates the intensity of this pixel.
   *
   * @param color is the pixel we want to find the intensity of
   * @return int that is the intensity
   */
  public static int intensity(Color color) {
    return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
  }

  /**
   * Calculates the luma of this pixel.
   *
   * @param color is the pixel we want to find the luma of
   * @return int that is the luma
   */
  public static int luma(Color color) {
    return (int) ((0.2126 * color.getRed())
        + (0.7152 * color.getGreen())
        + (0.0722 * color.getBlue()));
  }

  /**
   * Creates a string from a color in a more readable way
   *
   * @param color is the color we want to get the string of
   * @return
   */
  public static String toString(Color color) {
    StringBuilder string = new StringBuilder();
    string.append("[r = ").append(color.getRed());
    string.append(",g = ").append(color.getGreen());
    string.append(",b = ").append(color.getBlue());
    string.append("]");
    return string.toString();
  }

  /**
   * Checks if the number is within 0 to 255, the valid range for a color component when making
   * a new color.
   * @param num
   * @return
   */
  public static int checkNum(int num) {
    if (num < 0) {
      return 0;
    }
    return Math.min(num, 255);
  }
}
