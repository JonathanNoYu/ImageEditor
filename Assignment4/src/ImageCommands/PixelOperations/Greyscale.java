package ImageCommands.PixelOperations;

import ImageCommands.ImageCommand;
import ImageModel.Pixel;

/**
 * a{@Greyscale code} represents the class the holds all commands that can convert a pixel to grey
 * in different ways.
 */
public class Greyscale implements ImageCommand {
  String typeGreyscale;
  /**
   *
   * @param typeGreyscale
   */
  public Greyscale(String typeGreyscale) {
    this.typeGreyscale = typeGreyscale;
  }

  @Override
  public Pixel process(Pixel p) {
    switch(this.typeGreyscale) {
      case "value-component" :
        this.valueGreyscale(p);
        break;
      case "intensity-component":
        this.intensity(p);
        break;
      case "luma-component":
        this.luma(p);
        break;
      case "red-greyscale":
        this.redGreyscale(p);
        break;
      case "green-greyscale":
        this.greenGreyscale(p);
        break;
      case "blue-greyscale":
        this.blueGreyscale(p);
        break;
    }
    return p;
  }

  /**
   * using the value the RGB is set to the maximum of all three values.
   * @param p
   * @return the changed Pixel
   */
  public Pixel valueGreyscale(Pixel p) {
    p.setBlue(p.value());
    p.setGreen(p.value());
    p.setRed(p.value());
    return p;
  }

  /**
   * using the intensity the RGB is set to the average of all three values.
   * @param p is the pixel we want to modify
   * @return the changed Pixel
   */
  public Pixel intensity(Pixel p) {
    p.setBlue(p.intensity());
    p.setGreen(p.intensity());
    p.setBlue(p.intensity());
    return p;
  }

  /**
   * using the luma the RGB is set to the weighted sum of all three values.
   * @param p is the pixel we want to modify
   * @return the changed Pixel
   */
  public Pixel luma(Pixel p) {
    p.setBlue(p.luma());
    p.setRed(p.luma());
    p.setGreen(p.luma());
    return p;
  }

  /**
   * converts the pixel to grey by setting all RGB values to blue value.
   * @param p is the pixel we want to modify
   * @return the changed Pixel
   */
  public Pixel blueGreyscale(Pixel p) {
    p.setGreen(p.getBlue());
    p.setRed(p.getBlue());
    return p;
  }

  /**
   * converts the pixel to grey by setting all RGB values to green value.
   * @param p is the pixel we want to modify
   * @return the changed Pixel
   */
  public Pixel greenGreyscale(Pixel p) {
    p.setBlue(p.getGreen());
    p.setRed(p.getGreen());
    return p;
  }

  /**
   * converts the pixel to grey by setting all RGB values to red value.
   * @param p is the pixel we want to modify
   * @return the changed Pixel
   */
  public Pixel redGreyscale(Pixel p) {
    p.setGreen(p.getRed());
    p.setBlue(p.getRed());
    return p;
  }

}
