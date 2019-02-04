import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Display extends Canvas implements Runnable {

    public static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int width = (int) ScreenSize.getWidth();
    public static final int height = (int) ScreenSize.getHeight();
    public static final String title = "Simple Window";

    private Thread thread;
    private boolean running = false;
    private Game game;
    private BufferedImage img;
    private Screen screen;
    private Render render;
    private int[] pixels;

    public Display() {
        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        screen = new Screen(width, height);
        game = new Game();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
    }

    private void start() {
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        if(!running)
            return;
        running = false;
        try{
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void run() {
        int frames = 0;
        double unprocessedSeconds = 0;
        long previousTime = System.nanoTime();
        double secondsPerTick = 1 / 60.0;
        int tickCount = 0;
        boolean ticked = false;
        while (running) {
            long currentTime = System.nanoTime();
            long passedTime = currentTime - previousTime;
            previousTime = currentTime;
            unprocessedSeconds += passedTime / 1000000000.0;

            while (unprocessedSeconds > secondsPerTick) {
                tick();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;
                tickCount++;
                if (tickCount % 60 == 0) {
                    System.out.println(frames + "fps");
                    previousTime += 1000;
                    frames = 0;
                }
            }
            if (ticked) {
                render();
                frames++;
            }
            render();
            frames++;
        }
    }

    private void tick() {
        game.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.render(game);

        for(int i = 0; i < width*height; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, width, height, null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Display game = new Display();
        JFrame frame = new JFrame();
        frame.add(game);
        frame.pack();
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        System.out.println("Running!!");

        game.start();
    }
}
