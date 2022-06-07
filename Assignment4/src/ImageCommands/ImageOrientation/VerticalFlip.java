package ImageCommands.ImageOrientation;

import ImageModel.Pixel;
import java.io.File;

/**
 * a{@code ImageCommands.ImageOrientation.ImageOrientation.VerticalFlip} represents the class that
 * will flip image vertically.
 */
public class VerticalFlip implements ImageOrientation {

  Pixel[][] in;
  String fileName;
  File out;

  public VerticalFlip(String name) {
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
