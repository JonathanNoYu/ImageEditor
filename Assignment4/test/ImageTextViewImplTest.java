import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import mocks.MockAppendable;
import model.ImageModel;
import model.ImageModelImpl;
import org.junit.Before;
import org.junit.Test;
import view.ImageTextViewImpl;
import view.ImageView;

/**
 * This is the testing class for image text view. Involved tests are for toString, renderMessage,
 * renderImage and to test the IOExceptions with a mock appendable.
 */
public class ImageTextViewImplTest {

  ImageView view;
  ImageView view2;
  ImageModel model;
  ImageModel model2;
  Appendable append;

  @Before
  public void init() {
    this.append = new StringBuilder();
    this.model = new ImageModelImpl();
    this.view = new ImageTextViewImpl(this.model, this.append);
    this.model2 = new ImageModelImpl("res/gp.ppm", "coolimage");
    this.view2 = new ImageTextViewImpl(this.model2, this.append);

  }

  @Test
  public void testInvalidImageView() {
    try {
      ImageView v1 = new ImageTextViewImpl(null, this.append);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model and output", e.getMessage());
    }
    try {
      ImageView v2 = new ImageTextViewImpl(this.model, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model and output", e.getMessage());
    }
    try {
      ImageView v3 = new ImageTextViewImpl(null, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("There must be a model and output", e.getMessage());
    }
  }

  @Test
  public void testToString() {
    assertEquals("", this.view.toString());
    assertEquals("[r = 255,g = 0,b = 0] [r = 14,g = 0,b = 255] [r = 255,g = 0,b = 0] "
        + "[r = 14,g = 0,b = 255] " + System.lineSeparator()
        + "[r = 14,g = 0,b = 255] [r = 255,g = 0,b = 0] [r = 14,g = 0,b = 255] "
        + "[r = 31,g = 255,b = 0] " + System.lineSeparator()
        + "[r = 255,g = 0,b = 0] [r = 31,g = 255,b = 0] [r = 31,g = 255,b = 0] "
        + "[r = 14,g = 0,b = 255] " + System.lineSeparator()
        + "[r = 14,g = 0,b = 255] [r = 255,g = 0,b = 0] [r = 14,g = 0,b = 255] "
        + "[r = 31,g = 255,b = 0] " + System.lineSeparator(), this.view2.toString());
  }

  @Test
  public void renderImage() {
    assertEquals("", this.append.toString());
    try {
      this.view.renderImage("empty");
      assertEquals("", this.append.toString());
    } catch (IOException e) {
      fail();
    }
    this.init();
    assertEquals("", this.append.toString());
    try {
      this.view2.renderImage("coolimage");
      assertEquals("[r = 255,g = 0,b = 0] [r = 14,g = 0,b = 255] [r = 255,g = 0,b = 0] " +
          "[r = 14,g = 0,b = 255] " + System.lineSeparator() +
          "[r = 14,g = 0,b = 255] [r = 255,g = 0,b = 0] [r = 14,g = 0,b = 255] " +
          "[r = 31,g = 255,b = 0] " + System.lineSeparator() +
          "[r = 255,g = 0,b = 0] [r = 31,g = 255,b = 0] [r = 31,g = 255,b = 0] " +
          "[r = 14,g = 0,b = 255] " + System.lineSeparator() +
          "[r = 14,g = 0,b = 255] [r = 255,g = 0,b = 0] [r = 14,g = 0,b = 255] " +
          "[r = 31,g = 255,b = 0] " + System.lineSeparator(), this.append.toString());
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void renderMessage() {
    StringBuilder log = new StringBuilder();
    ImageView ex1 = new ImageTextViewImpl(new ImageModelImpl(), log);
    try {
      ex1.renderMessage("fun");
      assertEquals("fun" + System.lineSeparator(), log.toString());
    } catch (IOException e) {
      fail();
    }
    try {
      ex1.renderMessage("guy");
      assertEquals("fun" + System.lineSeparator() + "guy" + System.lineSeparator(),
          log.toString());
    } catch (IOException e) {
      fail();
    }
    try {
      ex1.renderMessage(System.lineSeparator() + "mood");
      assertEquals("fun" + System.lineSeparator() + "guy" + System.lineSeparator()
          + System.lineSeparator() + "mood" + System.lineSeparator(), log.toString());
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testIOExceptions() {
    MockAppendable badAppendable = new MockAppendable();
    ImageView v4 = new ImageTextViewImpl(this.model, badAppendable);
    try {
      v4.renderMessage("hi");
      fail("This appendable is corrupted >:(, your transmission failed! :O");
    } catch (IOException e) {
      assertEquals("This appendable is corrupted >:(, your transmission failed! :O",
          e.getMessage());
    }

    try {
      v4.renderImage("res/FakeImage3.ppm");
    } catch (IOException e) {
      assertEquals("This appendable is corrupted >:(, your transmission failed! :O",
          e.getMessage());
    }
  }
}