import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class SimpleGPUTest extends Canvas implements Runnable {

    private JFrame frame;
    private boolean running = false;

    public SimpleGPUTest() {
        frame = new JFrame("Java GPU Test");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        this.createBufferStrategy(2);
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        BufferStrategy bs = this.getBufferStrategy();

        while (running) {
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();

            // Clear screen
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Draw animated shapes
            g.setColor(Color.CYAN);
            g.fillOval((int)(Math.random() * 800), (int)(Math.random() * 600), 50, 50);

            bs.show();
            g.dispose();

            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SimpleGPUTest test = new SimpleGPUTest();
        test.start();
    }
}
