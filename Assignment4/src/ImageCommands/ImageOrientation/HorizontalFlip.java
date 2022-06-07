package ImageCommands.ImageOrientation;

import ImageModel.Pixel;
import java.io.File;

/**
 * a {@code ImageCommands.ImageOrientation.ImageOrientation.HorizontalFlip} represents the flipping
 * of an image horizontally.
 */
public class HorizontalFlip implements ImageOrientation {

  Pixel[][] in;
  String fileName;
  File out;

  public HorizontalFlip(String name) {
    this.fileName = name;
    this.out = new File(name);
  }

  @Override
  public Pixel process(Pixel p) {

    return p;
  }

  @Override
  public File output() {
    return this.out;
  }

  @Override
  public void inputFile(Pixel[][] image) {
    this.in = in;
  }

  @Override
  public String outputName() {
    return this.fileName;
  }
}
