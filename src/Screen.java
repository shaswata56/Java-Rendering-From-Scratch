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
        for (int i = 0; i < width * height; i++)
            pixels[i] = 0;
        int anim1 = (int) (Math.sin(System.currentTimeMillis() % 20000.0 / 20000 * Math.PI * 4.0) * 200);
        int anim2 = (int) (Math.cos(System.currentTimeMillis() % 20000.0 / 20000 * Math.PI * 4.0) * 200);
        draw(test, (width - 512) / 2 + anim1, (height - 512) / 2 + anim2);
    }
}
