import java.util.Random;

public class Screen extends Render {

    private Render test;

    public Screen(int width, int height) {
        super(width, height);
        Random random = new Random();
        test = new Render(256, 256);
        for(int i = 0; i < 256*256; i++) {
            test.pixels[i] = random.nextInt();
        }
    }

    public void render() {
        draw(test, 0, 0);
    }
}
