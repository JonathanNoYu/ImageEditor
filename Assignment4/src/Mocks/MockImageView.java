package Mocks;

import ImageModel.ImageModel;
import ImageView.ImageView;

/**
 * The Mock Class for a MarbleSolitaireView. It gives back dummy outputs that hold no meaning, but
 * it also records each input and puts it in the log to check if inputs are correctly given.
 */
public class MockImageView implements ImageView {

  private final StringBuilder output;
  private final StringBuilder log;

  /**
   * Constructor for a Mock Image View. Although the state is given it is never really used as the
   * outputs are not meant to really be used with the state. However, it is asked to keep the
   * familiarity in the creation of a view and to see if the state is called as if this was a normal
   * view.
   *
   * @param state  is the mock game
   * @param output is the outputs for the view
   */
  public MockImageView(ImageModel state, StringBuilder log, StringBuilder output) {
    this.log = log;
    this.output = output;
  }

  @Override
  public String toString() {
    log.append("toString Method Called");
    log.append(System.lineSeparator());
    return "LOADED-IMAGE";
  }

  @Override
  public void renderImage(String imageName) {
    log.append("renderImage Method Called With String: ");
    log.append(imageName);
    log.append(System.lineSeparator());
    this.output.append("Image: ");
    this.output.append(imageName);
    this.output.append(System.lineSeparator());
  }

  @Override
  public void renderMessage(String message) {
    String shortMessage = message.split(" ")[0];
    log.append("renderMessage Method Called Msg: ");
    log.append(shortMessage);
    log.append(System.lineSeparator());
    this.output.append(message);
    this.output.append(System.lineSeparator());
  }
}
