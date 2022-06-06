package ImageController;

/**
 * The Controller for the Image Model which listens for allowed commands. It deals with .ppm files.
 */
public interface ImageController {

  /**
   * Runs the controller which lets the user then input commands to either overwrite the current
   * loaded file, save a file in the storage, make grey scale versions of the loaded file, flip the
   * image horizontally or vertically.
   */
  void processImage();
}
