package BT_QUAN_LY_SV_VKU;

public class Student {
    private int studentID;
    private String name;
    private int age;
    private String email;
    private float gpa;
    private String password;

    public Student(int studentID, String name, int age, String email, float gpa, String password) {
        this.studentID = studentID;
        this.name = name;
        this.age = age;
        this.email = email;
        this.gpa = gpa;
        this.password = password;
    }

    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public float getGpa() { return gpa; }
    public String getPassword() { return password; }
}
