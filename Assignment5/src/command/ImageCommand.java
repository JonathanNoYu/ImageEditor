package command;

/**
 * Interface for creating new commands in which we process images. Each image Command should take
 * in a ImageModel as a parameter in its constructor. It should also take in the Image alias to
 * know which image is being changed.
 */
public interface ImageCommand {

  /**
   * Processes the current image using some sort of modification through helpers or through just
   * this method. It should change the model given.
   * @throws IllegalArgumentException when the command runs into some kind of error
   * Ex: Colors are null, Missing images, etc.
   */
  void process() throws IllegalArgumentException;
}
