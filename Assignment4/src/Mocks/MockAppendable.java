package Mocks;

import java.io.IOException;

/**
 * A Mock Class for a MockAppendable used to throw IOException Errors. It also records each input
 * and puts it in the log to check if inputs are correctly given.
 */
public class MockAppendable implements Appendable {

  /**
   * Main Constructor of the Mock, which is only used for throw IOException Errors.
   */
  public MockAppendable() {
    // This is meant to only throw IOException Errors.
  }

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("This appendable is corrupted >:(, your transmission failed! :O");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("This appendable is corrupted >:(, your transmission failed! :O");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("This appendable is corrupted >:(, your transmission failed! :O");
  }
}
