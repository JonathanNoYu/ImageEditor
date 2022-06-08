package ImageView;

import ImageModel.ImageModel;
import java.io.IOException;

public class ImageViewImpl implements ImageView {
  private final ImageModel model;
  private final Appendable out;

  public ImageViewImpl(ImageModel model) {
    this(model, System.out);
  }

  public ImageViewImpl(ImageModel model, Appendable out) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("There must be a model and output");
    }
    this.model = model;
    this.out = out;
  }

  @Override
  public String toString() {
    return "";
  }

  @Override
  public void renderImage(String imageName) throws IOException {

  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.out.append(message + System.lineSeparator());
  }
}
