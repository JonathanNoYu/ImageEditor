package ImageController;

import ImageCommands.ImageCommand;
import ImageCommands.ImageOrientation.HorizontalFlip;
import ImageCommands.ImageOrientation.VerticalFlip;
import ImageCommands.PixelOperations.Brighten;
import ImageModel.ImageModel;
import ImageView.ImageView;
import java.io.File;
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
  private final Scanner scanFromInput = new Scanner(System.in);
  private ImageCommand cmd; // current command in use
  private final Map<String, ImageCommand> commandMap = new HashMap<>();

  public ImageControllerImpl(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void processImage() {
    while (!exit) {
      String inputFromUser = this.scanString(scanFromInput);
      String destName = "";
      switch (inputFromUser) {
        case "load":
          String loadPath = this.scanString(scanFromInput);
          destName = this.scanString(scanFromInput);
          break;
        case "save":
          String savePath = scanFromInput.next();
          destName = scanFromInput.next();
          break;
        case "brighten":
          cmd = new Brighten();
          break;
        case "vertical-flip":
//          cmd = new VerticalFlip(this.in, destName);
          break;
        case "horizontal-flip":
//          cmd = new HorizontalFlip(this.in, destName);
          break;
        case "red-component":
        case "green-component":
        case "blue-component":
        case "value-component":
        case "intensity-component":
        case "luma-component":
        case "exit":
          exit = true;
        default:
          System.out.println("Unknown command %s" + inputFromUser);
          cmd = null;
          break;
      }
//      if (cmd != null) {
//        File tempFile = new File(destName);
//        for (int i = 0; i < height; i++) {
//          for (int j = 0; j < width; j++) {
//            int r = scanFile.nextInt(); // gets the red component value
//            int g = scanFile.nextInt(); // gets the green component value
//            int b = scanFile.nextInt();// gets the blue component value
//            int[] changedPixel = cmd.process(r, g, b); // execute the command
//          }
//        }
//        tempOut.put(destName, tempFile);
//        cmd = null;
//      }
    }
  }

  private String scanString(Scanner sc) {
    try {
      return sc.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException(e.getCause());
    }
  }
}
