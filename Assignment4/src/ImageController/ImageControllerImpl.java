package ImageController;

import ImageCommands.ImageCommand;
import ImageCommands.ImageOrientation.HorizontalFlip;
import ImageCommands.ImageOrientation.VerticalFlip;
import ImageCommands.PixelOperations.Brighten;
import ImageCommands.PixelOperations.Greyscale;
import ImageModel.ImageModel;
import ImageView.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The implementation of the controller which lets the user asks any allow commands or the user
 * enters exit to quit the program.
 */
public class ImageControllerImpl implements ImageController {

  private final ImageModel model;
  private final ImageView view;
  private boolean exit = false; // when it is true we end the program
  private final Scanner scanFromInput;
  private ImageCommand cmd; // current command in use
  private final Map<String, ImageCommand> commandMap = new HashMap<>();

  /**
   * Main Default constructor for the controller. There must be a view and a model.
   * the input is set to System.in as a default input.
   *
   * @param model is the model processor
   * @param view is the view of the model
   */
  public ImageControllerImpl(ImageModel model, ImageView view) {
    this(model, view, System.in);
  }

  /**
   * Constructor for the controller. You must have a defined view, model and input.
   * This is convent for testing the controller.
   *
   * @param model is the model processor
   * @param view is the view of the model
   * @param input is the inputs the user wants to give to the controller
   */
  public ImageControllerImpl(ImageModel model, ImageView view, InputStream input) {
    if (model == null || view == null || input == null) {
      throw new IllegalArgumentException("There must be a model, view and input");
    }
    this.model = model;
    this.view = view;
    this.scanFromInput = new Scanner(input);
  }

  @Override
  public void processImage() throws IllegalStateException {
    while (!exit) {
      String inputFromUser = this.scanString(scanFromInput);
      String pathOrImageName = null;
      String destName = null;
      switch (inputFromUser) {
        case "load":
          pathOrImageName = this.scanString(scanFromInput);
          destName = this.scanString(scanFromInput);
          break;
        case "save":
          pathOrImageName = this.scanString(scanFromInput);
          destName = this.scanString(scanFromInput);
          this.model.saveImage(pathOrImageName, destName);
          break;
        case "brighten":
          cmd = new Brighten(this.scanInt(scanFromInput));
          pathOrImageName = this.scanString(scanFromInput);
          destName = this.scanString(scanFromInput);
          break;
        case "vertical-flip":
          pathOrImageName = this.scanString(scanFromInput);
          destName = this.scanString(scanFromInput);
          cmd = new VerticalFlip();
          break;
        case "horizontal-flip":
          pathOrImageName = this.scanFromInput.next();
          destName = this.scanFromInput.next();
          cmd = new HorizontalFlip();
          break;
        case "red-component":
          pathOrImageName = this.scanFromInput.next();
          destName = this.scanFromInput.next();
          cmd = new Greyscale("red-component");
          break;
        case "green-component":
          pathOrImageName = this.scanFromInput.next();
          destName = this.scanFromInput.next();
          cmd = new Greyscale("green-component");
          break;
        case "blue-component":
          pathOrImageName = this.scanFromInput.next();
          destName = this.scanFromInput.next();
          cmd = new Greyscale("blue-component");
          break;
        case "value-component":
          pathOrImageName = this.scanFromInput.next();
          destName = this.scanFromInput.next();
          cmd = new Greyscale("value-component");
          break;
        case "intensity-component":
          pathOrImageName = this.scanFromInput.next();
          destName = this.scanFromInput.next();
          cmd = new Greyscale("intensity-component");
          break;
        case "luma-component":
          pathOrImageName = this.scanFromInput.next();
          destName = this.scanFromInput.next();
          cmd = new Greyscale("luma-component");
          break;
        case "exit":
          exit = true;
          this.renderMessage("Thanks for using our Image editor!");
          return;
        default:
          this.renderMessage("Unknown command %s" + inputFromUser);
          cmd = null;
          break;
      }
      if (cmd != null) {
        try {
          this.model.processCommand(cmd, pathOrImageName, destName);
        } catch (IOException e) {
          throw new IllegalStateException(e.getMessage());
        }
      }
    }
  }

  // Renders a simple message throws an error if transmission has failed.
  private void renderMessage(String message) {
    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  // Will be used in newer version of the controllers throws an error if transmission has failed.
  private void renderImage(String imageName) {
    try {
      this.view.renderImage(imageName);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  // Scans for String input throws an error if transmission has failed.
  private String scanString(Scanner sc) {
    try {
      return sc.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException(e.getCause());
    }
  }

  // Scans for an Integer input throws an error if transmission has failed.
  private int scanInt(Scanner sc) {
    try {
      return sc.nextInt();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException(e.getCause());
    }
  }
}
