package com.company.model;

public class Employee {
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String ssn;
    private String birthDate;
    private String address;
    private String sex;
    private double salary;
    private String supervisorSsn;
    private int departmentNumber;
    private String departmentName;


    // 생성자
    public Employee(String firstName, String middleInitial, String lastName, String ssn, String birthDate,
                    String address, String sex, double salary, String supervisorSsn, int departmentNumber) {
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.ssn = ssn;
        this.birthDate = birthDate;
        this.address = address;
        this.sex = sex;
        this.salary = salary;
        this.supervisorSsn = supervisorSsn;
        this.departmentNumber = departmentNumber;
    }

    // Getter 및 Setter
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getSupervisorSsn() {
        return supervisorSsn;
    }

    public void setSupervisorSsn(String supervisorSsn) {
        this.supervisorSsn = supervisorSsn;
    }

    public int getDepartmentNumber() {
        return departmentNumber;
    }

    public void setDepartmentNumber(int departmentNumber) {
        this.departmentNumber = departmentNumber;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
