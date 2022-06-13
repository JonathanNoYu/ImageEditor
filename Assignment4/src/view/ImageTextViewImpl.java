package view;

import java.io.IOException;
import model.ImageModel;

/**
 * This class represents the view of the image editor. Currently, there is no real use for this view
 * but, it exists as principle and as a fill in for a newer more user-friendly and useful image
 * view.
 */
public class ImageTextViewImpl implements ImageView {

  private final ImageModel model;
  private final Appendable out;

  /**
   * Default Constructor that makes the output System.out. This is made to make a ImageTextView
   * without needing to set up an Appendable output.
   *
   * @param model is the model this view is displaying
   */
  public ImageTextViewImpl(ImageModel model) {
    this(model, System.out);
  }

  /**
   * Convenience Constructor for testing and more detailed construction of ImageTextView.
   *
   * @param model is the model this view is displaying
   * @param out   is the Appendable output this view is outputting to
   */
  public ImageTextViewImpl(ImageModel model, Appendable out) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("There must be a model and output");
    }
    this.model = model;
    this.out = out;
  }

  @Override
  public String toString() {
    StringBuilder textImage = new StringBuilder();
    for (int row = 0; row < this.model.getRow(); row++) {
      for (int col = 0; col < this.model.getCol(); col++) {
        textImage.append(this.model.getPixel(row, col).toString());
        textImage.append(" ");
      }
      textImage.append(System.lineSeparator());
    }
    return textImage.toString();
  }

  @Override
  public void renderImage(String imageName) throws IOException {
    this.out.append(this.toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.out.append(message + System.lineSeparator());
  }
}
