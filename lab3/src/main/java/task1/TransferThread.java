package task1;

public class TransferThread extends Thread {
    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;
    private static final int REPLICATIONS = 1000;

    public TransferThread(Bank b, int from, int max) {
        this.bank = b;
        this.fromAccount = from;
        this.maxAmount = max;
    }

    public void run() {
 //        try {
            while (!interrupted()) {
                for (int i = 0; i < REPLICATIONS; i++) {
                    int toAccount = (int) (bank.size() * Math.random());
                    int amount = (int) (500 * maxAmount * Math.random() / REPLICATIONS);
                    // варіант 1:
                    //this.bank.lockTransfer(fromAccount, toAccount, amount);

                    // варіант 2:
                    //this.bank.syncStatementTransfer(fromAccount, toAccount, amount);

                    // варіант 3:
                    // this.bank.tryLockTransfer(fromAccount, toAccount, amount);

                    // варінт 4
                     this.bank.syncMethodTransfer(fromAccount, toAccount, amount);

                    // Thread.sleep(1);
                }
            }
//        } catch (InterruptedException ignored) {
//        }
    }
}
