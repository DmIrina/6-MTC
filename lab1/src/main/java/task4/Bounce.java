package task4;

import javax.swing.*;

public class Bounce {
    public static void main(String[] args) {
        BounceFrame frame = new BounceFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        System.out.println("Технології паралельних обчислень\nКомп'ютерний практикум № 1");
        System.out.println("Task 4");
        System.out.println("Thread name = " + Thread.currentThread().getName());
    }
}