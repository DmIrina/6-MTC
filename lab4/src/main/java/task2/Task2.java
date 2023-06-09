package task2;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Task2 {
    private static final int assistantCount = 3;

    public static void main(String[] args) {
        Instant start = Instant.now();
        ArrayList<Group> groups = initGroups();

        Journal journal1 = new Journal(groups);
        Teacher lector = new Teacher(journal1, "l");
        ArrayList<Teacher> assistants = new ArrayList<>();

        for (int i = 1; i < assistantCount + 1; i++) {
            assistants.add(new Teacher(journal1, "a" + i));
        }

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

        System.out.println("Час 4 Threads = " + Duration.between(start, Instant.now()).toMillis() + " ms");


        start = Instant.now();
        Journal journal2 = new Journal(groups);

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        for (Group group : journal2.getGroups()) {
            TeacherTask lectorTask = new TeacherTask(journal2, "l", group.getStudents(), group);
            ArrayList<TeacherTask> assistantsTasks = new ArrayList<>();

            for (int i = 1; i < assistantCount + 1; i++) {
                assistantsTasks.add(new TeacherTask(journal2, "a" + i, group.getStudents(), group));
            }

            pool.submit(lectorTask);
            assistantsTasks.forEach(pool::submit);
        }

        pool.shutdown();

        System.out.println("Час ForkJoin = " + Duration.between(start, Instant.now()).toMillis() + " ms");

        start = Instant.now();
        Journal journal3 = new Journal(groups);
        Teacher singleTeacher = new Teacher(journal3, "L");
        Thread teacherThread = new Thread(singleTeacher);
        teacherThread.start();
        try {
            teacherThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Час Thread = " + Duration.between(start, Instant.now()).toMillis() + " ms");
        journal3.printJournal();
    }

    public static ArrayList<Group> initGroups() {
        Group it01 = new Group("it-01", new ArrayList<>(List.of(
                new Student("s1"),
                new Student("s2"),
                new Student("s3"),
                new Student("s4"),
                new Student("s5"),
                new Student("s21"),
                new Student("s22"),
                new Student("s23"),
                new Student("s24"),
                new Student("s25"),
                new Student("s121"),
                new Student("s122"),
                new Student("s123"),
                new Student("s124"),
                new Student("s125"),
                new Student("s221"),
                new Student("s222"),
                new Student("s223"),
                new Student("s224"),
                new Student("s225"))));

        Group it02 = new Group("it-02", new ArrayList<>(List.of(
                new Student("s6"),
                new Student("s7"),
                new Student("s8"),
                new Student("s9"),
                new Student("s10"),
                new Student("s16"),
                new Student("s17"),
                new Student("s18"),
                new Student("s19"),
                new Student("s110"),
                new Student("s26"),
                new Student("s27"),
                new Student("s28"),
                new Student("s29"),
                new Student("s210"),
                new Student("s36"),
                new Student("s37"),
                new Student("s38"),
                new Student("s39"),
                new Student("s310"))));

        Group it03 = new Group("it-03", new ArrayList<>(List.of(
                new Student("s11"),
                new Student("s12"),
                new Student("s13"),
                new Student("s14"),
                new Student("s15"),
                new Student("s16"),
                new Student("s17"),
                new Student("s111"),
                new Student("s112"),
                new Student("s113"),
                new Student("s114"),
                new Student("s115"),
                new Student("s116"),
                new Student("s117"),
                new Student("s211"),
                new Student("s212"),
                new Student("s213"),
                new Student("s214"),
                new Student("s215"),
                new Student("s216"),
                new Student("s217"),
                new Student("s311"),
                new Student("s312"),
                new Student("s313"),
                new Student("s314"),
                new Student("s315"),
                new Student("s316"),
                new Student("s317"),
                new Student("s311"),
                new Student("s312"),
                new Student("s313"),
                new Student("s314"),
                new Student("s315"),
                new Student("s316"),
                new Student("s317"))));


        return new ArrayList<>(List.of(it01, it02, it03));
    }
}
