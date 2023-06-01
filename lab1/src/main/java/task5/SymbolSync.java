package task5;

public class SymbolSync implements Runnable {

    private char c;
    private Manager manager;
    boolean controlValue;

    public SymbolSync(char c, Manager manager, boolean control) {
        this.c = c;
        this.controlValue = control;
        this.manager = manager;
    }

    @Override
    public void run() {
        while (true) {
            manager.waitAndChange(controlValue, c);
            if (manager.isStop())
                return;
        }
    }
}
