public class Render3D extends Render {

    public Render3D(int width, int height) {
        super(width, height);
    }

    public void floor() {
        for (int y = 0; y < height; y++) {
            double ceiling = (y - height / 2.0) / height;

            double z = 8 / ceiling;

            for (int x = 0; x < width; x++) {
                double depth = (x - width / 2.0) / height;
                depth *= z;
                int xx = (int) (depth) & 15;
                int yy = (int) (z) & 15;
                pixels[x + y * width] = (xx * 16) | (yy * 16) << 8;

            }
        }
    }
}
