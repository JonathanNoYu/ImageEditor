package ImageModel;

/**
 * A class that represents a single pixel, it is a simple object with three fields, one for each RBG
 * color and can do basic operations on those three components of a pixel.
 */
public class Pixel {

  private int red;
  private int green;
  private int blue;

  /**
   * The Main and Only constructor for a pixel, where all the components are from 0 to 255.
   * @param r is the value of the red component of this pixel
   * @param g is the value of the green component of this pixel
   * @param b is the value of the blue component of this pixel
   */
  public Pixel(int r, int g, int b) {
    if (r < 0 || g < 0 || b < 0
        || r > 255 || g < 255 || b < 255) { // prim data types can never be null
      throw new IllegalArgumentException("Pixels must have RGB values from 0 to 255");
    }
    this.red = r;
    this.green = g;
    this.blue = b;
  }

  /**
   * Sets the red component to a valid int (0 to 255).
   * @param r is the new red int
   */
  public void setRed(int r) {
    if (r < 0 || r > 255) {
      throw new IllegalArgumentException("Red component can only be from 0 to 255");
    }
    this.red = r;
  }

  /**
   * Sets the Green component to a valid int (0 to 255).
   * @param g is the new green int
   */
  public void setGreen(int g) {
    if (g < 0 || g > 255) {
      throw new IllegalArgumentException("Green component can only be from 0 to 255");
    }
    this.green = g;
  }

  /**
   * Sets the blue component to a valid int (0 to 255).
   * @param b is the new blue int
   */
  public void setBlue(int b) {
    if (b < 0 || b > 255) {
      throw new IllegalArgumentException("Blue component can only be from 0 to 255");
    }
    this.blue = b;
  }

  /**
   * Sets all the components of this pixel to a valid value (0 to 255)
   * @param val is the new int to replace all the components.
   */
  public void setAll(int val) {
    if (val < 0 || val > 255) {
      throw new IllegalArgumentException("Blue component can only be from 0 to 255");
    }
    this.red = val;
    this.green = val;
    this.blue = val;
  }

  /**
   * Gets the red component's value.
   * @return int that is the value of this component
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Gets the green component's value.
   * @return int that is the value of this component
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Gets the blue component's value.
   * @return int that is the value of this component
   */
  public int getBlue() {
    return this.blue;
  }

  /**
   * Calculates the maximum value of this pixel.
   * @return int that is the largest component
   */
  public int value() {
    return Math.max(Math.max(this.red, this.green), this.blue);
  }

  /**
   * Calculates the intensity of this pixel.
   * @return int that is the intensity
   */
  public int intensity() {
    return (this.red + this.green + this.blue) / 3;
  }

  /**
   * Calculates the luma of this pixel.
   * @return int that is the luma
   */
  public int luma() {
   return (int) ((0.2126 * this.red) + (0.7152 * this.green) + (0.0722 * this.blue));
  }
}
