package ImageCommands;

/**
 * Interface for creating new commands in which we process images.
 */
public interface ImageCommand {

  /**
   * Processes the current three pixels and does some sort of operation(s) on them.
   * The inputs may vary as you might need to
   */
  int[] process(int... inputs);
}
