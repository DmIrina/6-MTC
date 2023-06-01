package task5;

import java.util.logging.Level;
import java.util.logging.Logger;

// управління потоками (очікування, дозвіл на роботу)
public class Manager {
    private boolean permission;
    private boolean stop;
    private int num;
    private int lines;

    private final int charCount;
    private final int lineCount;

    public Manager(int charCount, int lineCount) {
        this.charCount = charCount;
        this.lineCount = lineCount;
    }

    // контролює чергу
    public synchronized boolean getPermission() {
        return permission;
    }

    // контролює, чи всі дані виведено
    public synchronized boolean isStop() {
        return stop;
    }

    public synchronized void waitAndChange(boolean control, char ch) {
        while (getPermission() != control) {  // поки не наша черга
            try {
                // якщо не наша черга - очікуємо,
                // поки від іншого потоку не отримаємо інформацію, що стан обєкту змінився
                // wait() закінчуємо та в циклі йдемо на наступну перевірку
                wait();
            } catch (InterruptedException exception) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
        permission = !permission;
        num++;

        if (!stop) {
            System.out.print(ch);
        }

        if (num == charCount) {
            num = 0;
            System.out.println(lines);
            lines++;
        }

        if (lines == lineCount) {
            stop = true;
        }
        notifyAll();        // повідомляємо інші потоки про зміну стану об'єкту
    }
}