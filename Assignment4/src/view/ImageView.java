package view;

import java.io.IOException;

/**
 * The Interface for ImageView, For now only includes override of toString, rendering a file and
 * rendering a message.
 */
public interface ImageView {

  /**
   * Override ToString and should show some part of the image so that there can be some references
   * to any changes or confirm there is some sort of change.
   *
   * @return String that represents the model
   */
  String toString();

  /**
   * This method is meant to render image give by the 2D array of the pixels of the image. It varies
   * on which implementation of a view you are trying to do but all must represent the image some
   * way, shape or form.
   *
   * @param imageName is the 2D array of the image
   * @throws IOException if there is a failed transmission
   */
  void renderImage(String imageName) throws IOException;

  /**
   * This method simply renders a message to the view.
   *
   * @param message is the message that someone would want to view
   * @throws IOException when the message fails to run.
   */
  void renderMessage(String message) throws IOException;
}
