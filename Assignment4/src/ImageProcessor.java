import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ImageProcessor {

  public static void main(String[] args) {
    boolean exit = false; // when it is true we end the program
    File in; // Current loaded image
    Map<String, File> tempOut = new HashMap<>(); // Stored changes made to the in file
    ImageCommand cmd; // current command in use
    Scanner scanFromInput = new Scanner(System.in);
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "sample.ppm";
    }

    while(!exit) {
      String inputFromUser = scanFromInput.next();
      String destName;
      switch(inputFromUser) {
        case "load":
        case "save" :
          try {
            File fromStorage = tempOut.get(destName);
            Scanner readFile = new Scanner(fromStorage);
            DataOutputStream outputFile = new DataOutputStream(new FileOutputStream(destName));
            while (readFile.hasNext()) { // while there is stuff in the file
              outputFile.write(readFile.nextByte()); // it writes into the new file being outputted
            }
          } catch (IOException e) {
            System.out.println("File failed to save");
          }

        case "brighten" :
        case "vertical-flip" :
        case "horizontal-flip" :
        case "red-component" :
        case "green-component" :
        case "blue-component" :
        case "value-component" :
        case "intensity-component" :
        case "luma-component" :
        default :
          System.out.println(String.format("Unknown command %s", inputFromUser));
          cmd = null;
          break;
      }
      if (cmd != null) {
        File tempFile = new File(destName);
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            int r = sc.nextInt(); // gets the red component value
            int g = sc.nextInt(); // gets the green component value
            int b = sc.nextInt();// gets the blue component value
            int[] changedPixel = cmd.process(r,g,b); // execute the command
          }
        }
        tempOut.put(destName, tempFile);
        cmd = null;
      }
  }
  }
}
