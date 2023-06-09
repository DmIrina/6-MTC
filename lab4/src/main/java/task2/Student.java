package task2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student {
    private String name;
    private List<Grade> grades;

    public Student(String name) {
        this.name = name;
        this.grades = Collections.synchronizedList(new ArrayList<>());
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Grade> addGrade(Grade grade) {
        if (grade.getPoints() <= 0 || grade.getPoints() > 100) {
            return null;
        }
        grades.add(grade);
        return new ArrayList<>(grades);
    }

    public List<Grade> getGrades() {
        return grades;
    }
}
