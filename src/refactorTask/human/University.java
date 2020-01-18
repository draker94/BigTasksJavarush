package refactorTask.human;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class University {

    private String name;
    private int age;
    private List<Student> students = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public University(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student getStudentWithAverageGrade(double averageGrade) {
        for (Student e : students) {
            if (e.getAverageGrade() == averageGrade) {
                return e;
            }
        }
        return null;
    }

    public Student getStudentWithMaxAverageGrade() {
        Student studentWithMaxAverageGrade = students.get(0);
        for (Student e : students) {
            if(e.getAverageGrade() > studentWithMaxAverageGrade.getAverageGrade()) {
                studentWithMaxAverageGrade = e;
            }
        }
        return studentWithMaxAverageGrade;
    }

    public Student getStudentWithMinAverageGrade() {
        Student studentWithMinAverageGradeAndExpel = students.get(0);
        for (Student e : students) {
            if(e.getAverageGrade() < studentWithMinAverageGradeAndExpel.getAverageGrade()) {
                studentWithMinAverageGradeAndExpel = e;
            }
        }
        return studentWithMinAverageGradeAndExpel;
    }

    public void expel(Student student) {
        students.remove(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}