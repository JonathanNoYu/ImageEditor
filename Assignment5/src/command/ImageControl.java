package command;

import model.ImageModel;

/**
 * This is the image command to deal with input and output of the images. It is special compared
 * to the other commands as it handles adding an image, where if you see is the abstract classes
 * they assume the image is in the storage.
 */
public class ImageControl implements ImageCommand {
  private final String type;
  private final String pathOrImgName;
  private final String destName;
  private final ImageModel model;


  public ImageControl(String type, String pathOrImgName, String destName, ImageModel model) {
    if(model == null) {
      throw new IllegalArgumentException("Your model must exits");
    }
    if (pathOrImgName == null || destName == null) {
      throw new IllegalArgumentException("You need a path and image name (destination name)");
    }
    this.type = type;
    this.pathOrImgName = pathOrImgName;
    this.destName = destName;
    this.model = model;
  }
  @Override
  public void process() throws IllegalArgumentException {
    switch (this.type) {
      case "load":
        this.model.loadImage(pathOrImgName, destName);
        return;
      case "save":
        this.model.saveImage(pathOrImgName, destName);
        return;
      default:
        throw new IllegalArgumentException("There is no image control command of " + this.type);
    }
  }
}
