package task2;

import java.util.*;

public final class Journal {
    private final ArrayList<Group> groups;
    private final HashMap<String, HashMap<Student, ArrayList<Grade>>> journal;

    public Journal(ArrayList<Group> groups) {
        this.groups = groups;
        this.journal = new HashMap<>();
        groups.forEach(group -> journal.put(group.getName(), initGroupJournal(group)));
    }

    public ArrayList<Group> getGroups() {
        return this.groups;
    }

    public void addGrade(Group group, Student student, int points, String teacherName) {
        HashMap<Student, ArrayList<Grade>> groupHashmap = journal.get(group.getName());
        groupHashmap.replace(student, student.addGrade(new Grade(points, teacherName)));
        journal.replace(group.getName(), groupHashmap);
    }

    private HashMap<Student, ArrayList<Grade>> initGroupJournal(Group group) {
        HashMap<Student, ArrayList<Grade>> studentGradeHashMap = new HashMap<>();
        group.getStudents().forEach(student -> studentGradeHashMap.put(student, new ArrayList<>()));
        return studentGradeHashMap;
    }

    public void printJournal() {
        List<String> sortedGroupNames = new ArrayList<>(journal.keySet());
        Collections.sort(sortedGroupNames);

        for (String groupName : sortedGroupNames) {
            System.out.printf("Group %s:%n", groupName);

            HashMap<Student, ArrayList<Grade>> group = journal.get(groupName);

            List<Student> sortedStudents = new ArrayList<>(group.keySet());
            sortedStudents.sort(Comparator.comparing(Student::getName));

            for (Student student : sortedStudents) {
                if (student.getGrades() != null && student.getGrades().size() != 0) {
                    System.out.printf(
                            "Student %s, Grades: %s%n",
                            student.getName(),
                            String.join(", ", group.get(student).toString()));
                }
            }
        }
    }
}