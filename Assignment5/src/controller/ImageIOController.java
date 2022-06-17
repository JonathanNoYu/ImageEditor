package controller;

import command.ColorTransform;
import command.Filter;
import command.Flip;
import command.Greyscale;
import command.ImageCommand;
import command.ImageControl;
import command.Lighting;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;
import model.ImageModelV2;
import view.ImageView;

/**
 * The implementation of the controller which does what PPMController does however it changes most
 * images into bufferedImages so we deal with more popular image files, such as JPG, PNG and BMP.
 */
public class ImageIOController implements ImageController {

  private final ImageModelV2 model;
  private final ImageView view;
  private final Scanner scanFromInput;
  private final Map<String, Function<Scanner, ImageCommand>> commandMap = new HashMap<>();

  /**
   * Main Default constructor for the controller. There must be a view and a model. the input is set
   * to System.in as a default input.
   *
   * @param model is the model processor
   * @param view  is the view of the model
   */
  public ImageIOController(ImageModelV2 model, ImageView view) {
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
  public ImageIOController(ImageModelV2 model, ImageView view, Readable input) {
    if (model == null || view == null || input == null) {
      throw new IllegalArgumentException("There must be a model, view and input");
    }
    this.model = model;
    this.view = view;
    this.scanFromInput = new Scanner(input);
    this.commandMap.put("load", (s) -> {
      return new ImageControl("load", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("save", (s) -> {
      return new ImageControl("save", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("brighten", (s) -> {
      return new Lighting(this.scanInt(s), "brighten", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("darken", (s) -> {
      return new Lighting(this.scanInt(s), "darken", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("vertical-flip", (s) -> {
      return new Flip("vertical-flip", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("horizontal-flip", (s) -> {
      return new Flip("horizontal-flip", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("value-component", (s) -> {
      return new Greyscale("value-component", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("red-component", (s) -> {
      return new Greyscale("red-component", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("green-component", (s) -> {
      return new Greyscale("green-component", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("blue-component", (s) -> {
      return new Greyscale("blue-component", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("intensity-component", (s) -> {
      return new Greyscale("intensity-component", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("luma-component", (s) -> {
      return new Greyscale("luma-component", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("blur", (s) -> {
      return new Filter("blur", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("sharpen", (s) -> {
      return new Filter("sharpen", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("greyscale", (s) -> {
      return new ColorTransform("greyscale", this.scanString(s),
          this.scanString(s), this.model);
    });
    this.commandMap.put("sepiaTone", (s) -> {
      return new ColorTransform("sepiaTone", this.scanString(s),
          this.scanString(s), this.model);
    });
  }

  @Override
  public void processImage() throws IllegalStateException {
    this.instructions();
    while (scanFromInput.hasNext()) {
      String inputFromUser; // scanned inputs
      inputFromUser = this.scanString(scanFromInput);
      if (inputFromUser.equals("exit")) {
        this.renderMessage("Thanks for using our Image editor!");
        return;
      } else {
        ImageCommand command;
        Function<Scanner, ImageCommand> cmd = this.commandMap
            .getOrDefault(inputFromUser, null);
        if (cmd != null) {
          try {
            command = cmd.apply(scanFromInput);
            command.process();
          } catch (IllegalArgumentException e) {
            this.renderMessage(e.getMessage());
          }
        } else {
          this.renderMessage("Unknown command %s" + inputFromUser);
        }
      }
    }
    throw new IllegalStateException("Ran out of Inputs");
  }

  // Provides instructions
  private void instructions() {
    try {
      this.view.renderMessage("This is our ppm image editor! \n"
          + "Below are the allowed commands: (type them out with only one space in between each"
          + " word press enter separately)\n"
          + "\tload <Image Path> <Image Alias>\n"
          + "\tsave <Image Path> <Image Alias>\n"
          + "\tbrighten <Increment> <Image Alias To Be Modified> <Image Alias>\n"
          + "\tdarken   <Increment> <Image Alias To Be Modified> <Image Alias>\n"
          + "\thorizontal-flip      <Image Alias To Be Modified> <Image Alias>\n"
          + "\tvertical-flip        <Image Alias To Be Modified> <Image Alias>\n"
          + "\tvalue-component      <Image Alias To Be Modified> <Image Alias>\n"
          + "\tintensity-component  <Image Alias To Be Modified> <Image Alias>\n"
          + "\tluma-component       <Image Alias To Be Modified> <Image Alias>\n"
          + "\tred-component        <Image Alias To Be Modified> <Image Alias>\n"
          + "\tgreen-component      <Image Alias To Be Modified> <Image Alias>\n"
          + "\tblue-component       <Image Alias To Be Modified> <Image Alias>\n"
          + "\tblur                 <Image Alias To Be Modified> <Image Alias>\n"
          + "\tsharpen              <Image Alias To Be Modified> <Image Alias>\n"
          + "\tgreyscale            <Image Alias To Be Modified> <Image Alias>\n"
          + "\tsepiaTone            <Image Alias To Be Modified> <Image Alias>\n"
          + "\texit \n"
          + "Key Notes: (examples are below this in the short Script)\n"
          + "\t   <Image Path> is the path in which the file exits, this also includes the file "
          + "name and the file type.\n"
          + "\t   <Image Alias> is the name of the image that you had loaded/modified and gave it "
          + "a new name to reference it by.\n"
          + "\t   <Image Alias To Be Modified> is the image's alias that you want to modify again "
          + "or modify for the first time.\n"
          + "\t   <Increment> is a value to increase a pixel's entire value, valid numbers are from"
          + " 0 to 255.\n"
          + "\t   exit is the only way to stop the program.\n"
          + "\t   Lastly, The loaded image would not be modified.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Message failed to render");
    }
  }

  // Renders a simple message throws an error if transmission has failed.
  private void renderMessage(String message) {
    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Message failed to render");
    }
  }

  // Will be used in newer version of the controllers throws an error if transmission has failed.
  private void renderImage(String imageName) {
    try {
      this.view.renderImage(imageName);
    } catch (IOException e) {
      throw new IllegalStateException("Image not found");
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
