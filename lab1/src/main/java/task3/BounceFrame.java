package task3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;

    public int BLUE_COUNT = 1;
    public int RED_COUNT = 1;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.canvas = new BallCanvas();

        System.out.println("In Frame Thread name = " + Thread.currentThread().getName());

        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");
        buttonStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int x = 0;
                int y = 0;
                if (Math.random() < 0.5) {
                    x = new Random().nextInt(canvas.getWidth());
                } else {
                    y = new Random().nextInt(canvas.getHeight());
                }

                for (int i = 0; i < RED_COUNT; i++) {
                    Ball redBall = new Ball(canvas, Thread.MAX_PRIORITY, x, y);
                    canvas.add(redBall);
                    BallThread redThread = new BallThread(redBall);
                    redThread.setPriority(Thread.MAX_PRIORITY);
                    redThread.start();
                    // System.out.println("RED: thread name = " + redThread.getName());
                }

                for (int i = 0; i < BLUE_COUNT; i++) {
                    Ball blueBall = new Ball(canvas, Thread.MIN_PRIORITY, x, y);
                    canvas.add(blueBall);
                    BallThread blueThread = new BallThread(blueBall);
                    blueThread.setPriority(Thread.MIN_PRIORITY);
                    blueThread.start();
                    // System.out.println("BLUE: thread name = " + blueThread.getName());
                }
            }
        });
        buttonStop.addActionListener(e -> System.exit(0));

        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
