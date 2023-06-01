package task3;

import java.util.Random;

public class Teacher implements Runnable {
    private final Journal journal;
    private final String teacherName;


    public Teacher(Journal journal, String teacherName) {
        this.journal = journal;
        this.teacherName = teacherName;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (Group group : journal.getGroups()) {
            for (Student student : group.getStudents()) {
                int points = random.nextInt(101);
                journal.addGrade(group, student, points, teacherName);
            }
        }
    }
}