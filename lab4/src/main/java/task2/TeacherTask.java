package task2;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.RecursiveTask;

public class TeacherTask extends RecursiveTask<Boolean> {
    private final Journal journal;
    private final String teacherName;
    private long threshold = 10;
    private ArrayList<Student> students;
    private Group group;

    public TeacherTask(Journal journal, String teacherName) {
        this.journal = journal;
        this.teacherName = teacherName;
    }

    public TeacherTask(Journal journal, String teacherName, ArrayList<Student> students, Group group) {
        this.journal = journal;
        this.teacherName = teacherName;
        this.group = group;
        this.students = students;
    }


    @Override
    protected Boolean compute() {
        Random random = new Random();

            if (students.size() <= threshold) {
                for (Student student : students) {
                    int points = random.nextInt(101);
                    journal.addGrade(group, student, points, teacherName);
                }
            } else {
                int split = students.size() / 2;
                TeacherTask t1 = new TeacherTask(
                        journal,
                        teacherName,
                        new ArrayList<>(group.getStudents().subList(0, split)),
                        group);

                TeacherTask t2 = new TeacherTask(
                        journal,
                        teacherName,
                        new ArrayList<>(group.getStudents().subList(split, group.getStudents().size())),
                        group);

                t1.fork();
                t2.fork();
                t1.join();
                t2.join();
            }

        return true;
    }
}
