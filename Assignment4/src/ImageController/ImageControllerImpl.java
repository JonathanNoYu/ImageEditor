package ImageController;

import ImageCommands.ImageCommand;
import ImageCommands.ImageOrientation.HorizontalFlip;
import ImageCommands.ImageOrientation.VerticalFlip;
import ImageCommands.PixelOperations.Brighten;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The implementation of the controller which lets the user asks any allow commands or the user
 * enters exit to quit the program.
 */
public class ImageControllerImpl implements ImageController {
  private boolean exit = false; // when it is true we end the program
  private File in; // Current loaded image
  private final Map<String, File> tempOut = new HashMap<>(); // Stored changes made to the in file
  private ImageCommand cmd; // current command in use
  private final Scanner scanFromInput = new Scanner(System.in);
  private Scanner scanFile = null;
  private int height = 0;
  private int width = 0;
  private int maxValue = 0;
  private final Map<String, ImageCommand> commandMap = new HashMap<>();

  @Override
  public void processImage() {
    while (!exit) {
      String inputFromUser = this.scanString(scanFromInput);
      String destName = "";
      switch (inputFromUser) {
        case "load":
          String loadPath = this.scanString(scanFromInput);
          destName = this.scanString(scanFromInput);
          try {
            scanFile = new Scanner(new FileInputStream(loadPath));
          } catch (FileNotFoundException e) {
            System.out.println("File " + destName + " not found!");
            return;
          }

          StringBuilder builder = new StringBuilder();
          //read the file line by line, and populate a string. This will throw away any comment lines
          while (scanFile.hasNextLine()) {
            String s = scanFile.nextLine();
            if (s.charAt(0) != '#') {
              builder.append(s + System.lineSeparator());
            }
          }

          //now set up the scanner to read from the string we just built
          scanFile = new Scanner(builder.toString());

          String token;

          token = scanFile.next();
          if (!token.equals("P3")) {
            System.out.println("Invalid PPM file: plain RAW file should begin with P3");
          }
          width = scanFile.nextInt();
          height = scanFile.nextInt();
          maxValue = scanFile.nextInt();
          break;
        case "save":
          try {
            String savePath = scanFromInput.next();
            destName = scanFromInput.next();
            File fromStorage = tempOut.get(destName);
            Scanner readFile = new Scanner(fromStorage);
            DataOutputStream outputFile = new DataOutputStream(new FileOutputStream(savePath));
            while (readFile.hasNext()) { // while there is stuff in the file
              outputFile.write(readFile.nextByte()); // it writes into the new file being outputted
            }
            outputFile.close();
          } catch (IOException e) {
            System.out.println("File failed to save");
          }
          break;
        case "brighten":
          cmd = new Brighten();
          break;
        case "vertical-flip":
          cmd = new VerticalFlip();
          break;
        case "horizontal-flip":
          cmd = new HorizontalFlip();
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
      if (cmd != null) {
        File tempFile = new File(destName);
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            int r = scanFile.nextInt(); // gets the red component value
            int g = scanFile.nextInt(); // gets the green component value
            int b = scanFile.nextInt();// gets the blue component value
            int[] changedPixel = cmd.process(r, g, b); // execute the command
          }
        }
        tempOut.put(destName, tempFile);
        cmd = null;
      }
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
