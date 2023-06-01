package task3;

public class Grade {
    private int points;
    private String grade;
    private String teacherName;

    public Grade(int points, String teacherName) {
        this.points = points;
        this.grade = setGrade(points);
        this.teacherName = teacherName;
    }

    public int getPoints() {
        return this.points;
    }

    public String getGrade() {
        return this.grade;
    }

    private String setGrade(int points) {
        if (points >= 95) {
             return "A";
        } else if (points >= 85) {
            return "B";
        } else if (points >= 75) {
            return "C";
        } else if (points >= 65) {
            return "D";
        } else if (points >= 60) {
            return "E";
        } else {
            return "F";
        }
    }

    @Override
    public String toString() {
        return "(" + points + ", " + grade + ", " + teacherName + ")";
    }
}
