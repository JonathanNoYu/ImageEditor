package ImageView;

import java.io.File;
import java.io.IOException;

/**
 * The Interface for ImageView, For now only includes override of toString,
 * rendering a file and rendering a message.
 */
public interface ImageView {

  /**
   * Override ToString and should show some part of the image so that there can be some references
   * to any changes or confirm there is some sort of
   * @return String that represents the model
   */
  String toString();

  /**
   * This method is meant to render files but more specifically images
   * @param file the file that is meant to be rendered
   * @throws IOException if there is a failed transmission
   */
  void renderFile(File file) throws IOException;

  /**
   * This method renders a message to the view.
   * @param message is the message that someone would want to view
   * @throws IOException when the message fails to run.
   */
  void renderMessage(String message) throws IOException;
}
