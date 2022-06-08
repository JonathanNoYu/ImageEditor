package ImageController;

import ImageCommands.ImageCommand;
import ImageCommands.ImageOrientation.HorizontalFlip;
import ImageCommands.ImageOrientation.VerticalFlip;
import ImageCommands.PixelOperations.Brighten;
import ImageCommands.PixelOperations.Greyscale;
import ImageModel.ImageModel;
import ImageView.ImageView;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

/**
 * The implementation of the controller which lets the user asks any allow commands or the user
 * enters exit to quit the program.
 */
public class ImageControllerImpl implements ImageController {

  private final ImageModel model;
  private final ImageView view;
  private boolean exit = false; // when it is true we end the program
  private final Scanner scanFromInput;
  private String pathOrImageName;
  private String destName;
  private final Map<String, Function<Scanner, ImageCommand>> commandMap = new HashMap<>();

  /**
   * Main Default constructor for the controller. There must be a view and a model. the input is set
   * to System.in as a default input.
   *
   * @param model is the model processor
   * @param view  is the view of the model
   */
  public ImageControllerImpl(ImageModel model, ImageView view) {
    this(model, view, new InputStreamReader(System.in));
  }

  /**
   * Constructor for the controller. You must have a defined view, model and input. This is convent
   * for testing the controller.
   *
   * @param model is the model processor
   * @param view  is the view of the model
   * @param input is the inputs the user wants to give to the controller
   */
  public ImageControllerImpl(ImageModel model, ImageView view, Readable input) {
    if (model == null || view == null || input == null) {
      throw new IllegalArgumentException("There must be a model, view and input");
    }
    this.model = model;
    this.view = view;
    this.scanFromInput = new Scanner(input);
    this.commandMap.put("brighten", (s) -> {
      ImageCommand cmd = new Brighten(this.scanInt(s));
      this.setPathAliasCMD(s);
      return cmd;
    });
    this.commandMap.put("vertical-flip", (s) -> {
      this.setPathAliasCMD(s);
      return new VerticalFlip();
    });
    this.commandMap.put("horizontal-flip", (s) -> {
      this.setPathAliasCMD(s);
      return new HorizontalFlip();
    });
    this.commandMap.put("value-component", (s) -> {
      this.setPathAliasCMD(s);
      return new Greyscale("value-component");
    });
    this.commandMap.put("red-component", (s) -> {
      this.setPathAliasCMD(s);
      return new Greyscale("red-component");
    });
    this.commandMap.put("green-component", (s) -> {
      this.setPathAliasCMD(s);
      return new Greyscale("green-component");
    });
    this.commandMap.put("blue-component", (s) -> {
      this.setPathAliasCMD(s);
      return new Greyscale("blue-component");
    });
    this.commandMap.put("intensity-component", (s) -> {
      this.setPathAliasCMD(s);
      return new Greyscale("intensity-component");
    });
    this.commandMap.put("luma-component", (s) -> {
      this.setPathAliasCMD(s);
      return new Greyscale("luma-component");
    });
  }

  @Override
  public void processImage() throws IllegalStateException {
    String inputFromUser; // scanned inputs

    this.instructions();

    while (!exit) {
      inputFromUser = this.scanString(scanFromInput);
      switch (inputFromUser) {
        case "load":
          pathOrImageName = this.scanString(scanFromInput);
          destName = this.scanString(scanFromInput);
          this.model.loadImage(pathOrImageName, destName);
          break;
        case "save":
          pathOrImageName = this.scanString(scanFromInput);
          destName = this.scanString(scanFromInput);
          this.model.saveImage(pathOrImageName, destName);
          break;
        case "exit":
          exit = true;
          this.renderMessage("Thanks for using our Image editor!");
          return;
        default:
          Function<Scanner, ImageCommand> cmd = this.commandMap
              .getOrDefault(inputFromUser, null);
          if (cmd != null) {
            try {
              this.model.processCommand(cmd.apply(scanFromInput),
                  pathOrImageName, destName);
            } catch (IOException e) {
              throw new IllegalStateException(e.getMessage()); // Command fails to work
            }
          } else {
            this.renderMessage("Unknown command %s" + inputFromUser);
          }
          break;
      }
    }
  }


  private void instructions() {
    try {
      this.view.renderMessage("To load an image, please enter "
          + "'load <THE IMAGE PATH> <THE IMAGE ALIAS>'"
          + "The alis is the name we will refer to the image by. \n"
          + "Once an image is loaded you may augment it through greyscale, brightening and "
          + "darkening it but also flipping it horizontally or vertically\n"
          + "The loaded image would not be modified. \n"
          + "To save an image, please enter 'save <THE DESIRED IMAGE PATH> <THE IMAGE ALIAS>'");
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
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

  // Helper used to ask for a path or image name and then an destinationName/alias
  private void setPathAliasCMD(Scanner sc) {
    pathOrImageName = this.scanString(sc);
    destName = this.scanString(sc);
  }
}
