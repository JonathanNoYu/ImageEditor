package ImageCommands.PixelOperations;

import ImageCommands.ImageCommand;
import ImageModel.Pixel;

/**
 * a{@Greyscale code} represents the class the holds all commands that can convert a pixel to grey
 * in different ways.
 */
public class Greyscale implements ImageCommand {
  private final String type;

  /**
   * Main Constructor, must give a type of greyscale to then run. It allows non-supported greyscale
   * because it is later check in the process switch. Additionally, this class is only called by the
   * controller and implementors who know of it.
   * @param type
   */
  public Greyscale(String type) {
    this.type = type;
  }

  @Override
  public Pixel process(Pixel p) {
    switch(this.type) {
      case "value-component" :
        return this.valueGreyscale(p);
      case "intensity-component":
        return this.intensity(p);
      case "luma-component":
        return this.luma(p);
      case "red-component":
        return this.redGreyscale(p);
      case "green-component":
        return this.greenGreyscale(p);
      case "blue-component":
        return this.blueGreyscale(p);
      default:
        throw new IllegalArgumentException("There is no greyscale of " + this.type);
    }
  }

  /**
   * using the value the RGB is set to the maximum of all three values.
   * @param p
   * @return the changed Pixel
   */
  public Pixel valueGreyscale(Pixel p) {
    int value = p.value();
    p.setBlue(value);
    p.setGreen(value);
    p.setRed(value);
    return p;
  }

  /**
   * using the intensity the RGB is set to the average of all three values.
   * @param p is the pixel we want to modify
   * @return the changed Pixel
   */
  public Pixel intensity(Pixel p) {
    int intensity = p.intensity();
    p.setRed(intensity);
    p.setGreen(intensity);
    p.setBlue(intensity);
    return p;
  }

  /**
   * using the luma the RGB is set to the weighted sum of all three values.
   * @param p is the pixel we want to modify
   * @return the changed Pixel
   */
  public Pixel luma(Pixel p) {
    int luma = p.luma();
    p.setRed(luma);
    p.setBlue(luma);
    p.setGreen(luma);
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

  @Override
  public String toString() {
    return this.type;
  }

  @Override
  public String cmdType() {
    return "ImagineCommand";
  }
}
