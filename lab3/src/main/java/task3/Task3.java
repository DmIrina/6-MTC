package task3;

import java.util.ArrayList;
import java.util.List;

public class Task3 {
    private static final int assistantCount = 3;
    private static final int weekN = 2;

    public static void main(String[] args) {

        ArrayList<Group> groups = initGroups();
        Journal journal = new Journal(groups);

        Teacher lector = new Teacher(journal, "l");
        ArrayList<Teacher> assistants = new ArrayList<>();

        for (int i = 1; i < assistantCount + 1; i++) {
            assistants.add(new Teacher(journal, "a" + i));
        }

        for (int j = 0; j < weekN; j++) {
            Thread lectorThread = new Thread(lector);
            ArrayList<Thread> assistantThreads = new ArrayList<>();
            for (Teacher assistant : assistants) {
                assistantThreads.add(new Thread(assistant));
            }

            lectorThread.start();
            for (Thread assistant : assistantThreads) {
                assistant.start();
            }

            try {
                lectorThread.join();
                for (Thread assistant : assistantThreads) {
                    assistant.join();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       journal.printJournal();
    }

    public static ArrayList<Group> initGroups() {
        Group it01 = new Group("it-01", new ArrayList<>(List.of(
                new Student("s1"),
                new Student("s2"),
                new Student("s3"),
                new Student("s4"),
                new Student("s5"))));

        Group it02 = new Group("it-02", new ArrayList<>(List.of(
                new Student("s6"),
                new Student("s7"),
                new Student("s8"),
                new Student("s9"),
                new Student("s10"))));

        Group it03 = new Group("it-03", new ArrayList<>(List.of(
                new Student("s11"),
                new Student("s12"),
                new Student("s13"),
                new Student("s14"),
                new Student("s15"),
                new Student("s16"),
                new Student("s17"))));

        return new ArrayList<>(List.of(it01, it02, it03));
    }
}
