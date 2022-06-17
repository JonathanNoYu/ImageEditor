import Util.ImageUtil;
import command.Filter;
import command.ImageCommand;
import model.ImageIOModel;
import model.ImageModelV2;
import org.junit.Before;

/**
 * Tests the filter class, which is only made for the ImageModelV2 class, as it uses bufferedImages
 */
public class FilterTest {
  ImageModelV2 model1;
  ImageModelV2 model2;
  ImageModelV2 model3;
  ImageCommand blur1;
  ImageCommand blur2;
  ImageCommand blur3;
  ImageCommand blu4;
  ImageCommand blu5;
  ImageCommand sharpen1;
  ImageCommand sharpen2;
  ImageCommand sharpen3;
  ImageCommand sharpen4;
  ImageCommand sharpen5;

  @Before
  public void setup() {
    ImageUtil.setUpPPM();
    ImageUtil.setUpBufferedImages();
    this.model1 = new ImageIOModel("res/image1.ppm", "img1");
    this.model2 = new ImageIOModel("res/image2.ppm", "img2");
    this.model3 = new ImageIOModel("res/bmp1.bmp", "bmp1");
    this.model3.loadImage("res/jpeg1.jpeg", "jpeg1");
    this.model3.loadImage("res/png1.png", "png1");
    this.blur1 = new Filter("blur", "img1", "BlurImg1", this.model1);
    this.blur2 = new Filter("blur", "img2", "BlurImg2", this.model2);
    this.blur3 = new Filter("blur", "bmp1", "BlurBMP", this.model3);
    this.blu4 = new Filter("blur", "jpeg1", "BlurJPEG", this.model3);
    this.blu5 = new Filter("blur", "png1", "BlurPNG", this.model3);
    this.sharpen1 = new Filter("sharpen", "img1", "SharpenImg1", this.model1);
    this.sharpen2 = new Filter("sharpen", "img2", "SharpenImg2", this.model2);
    this.sharpen3 = new Filter("sharpen", "bmp1", "SharpenBMP", this.model3);
    this.sharpen4 = new Filter("sharpen", "jpeg1", "SharpenJPEG", this.model3);
    this.sharpen5 = new Filter("sharpen", "png1", "SharpenPNG", this.model3);
  }

  public void testInvalidFilters() {

  }
}
