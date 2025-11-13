package model;

public class Student {
    private int id;
    private String name;
    private int age;
    private String branch;
    private String email;
    private String phone;

    public Student() {}

    // Constructor used when ADDING a student
    public Student(String name, int age, String branch, String email, String phone) {
        this.name = name;
        this.age = age;
        this.branch = branch;
        this.email = email;
        this.phone = phone;
    }

    // Constructor used when UPDATING a student
    public Student(int id, String name, int age, String branch, String email, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.branch = branch;
        this.email = email;
        this.phone = phone;
    }

    // getters & setters below...

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
