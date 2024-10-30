package com.company.db;

import com.company.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeSearch {

    // 모든 직원 정보 조회
    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT e.*, d.Dname AS departmentName " +
                "FROM EMPLOYEE e " +
                "JOIN DEPARTMENT d ON e.Dno = d.Dnumber";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getString("Fname"),
                        resultSet.getString("Minit"),
                        resultSet.getString("Lname"),
                        resultSet.getString("Ssn"),
                        resultSet.getString("Bdate"),
                        resultSet.getString("Address"),
                        resultSet.getString("Sex"),
                        resultSet.getDouble("Salary"),
                        resultSet.getString("Super_ssn"),
                        resultSet.getInt("Dno")
                );
                employee.setDepartmentName(resultSet.getString("departmentName"));  // 부서명 설정
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }


    // 조건에 따른 직원 검색
    public static List<Employee> searchEmployees(String column, String value) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT e.*, d.Dname AS departmentName " +
                "FROM EMPLOYEE e " +
                "JOIN DEPARTMENT d ON e.Dno = d.Dnumber " +
                "WHERE e." + column + " = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, value);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getString("Fname"),
                        resultSet.getString("Minit"),
                        resultSet.getString("Lname"),
                        resultSet.getString("Ssn"),
                        resultSet.getString("Bdate"),
                        resultSet.getString("Address"),
                        resultSet.getString("Sex"),
                        resultSet.getDouble("Salary"),
                        resultSet.getString("Super_ssn"),
                        resultSet.getInt("Dno")
                );
                employee.setDepartmentName(resultSet.getString("departmentName"));  // 부서명 설정
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }


    // 직원 추가
    public static void addEmployee(Employee employee) {
        String query = "INSERT INTO EMPLOYEE (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getMiddleInitial());
            pstmt.setString(3, employee.getLastName());
            pstmt.setString(4, employee.getSsn());
            pstmt.setString(5, employee.getBirthDate());
            pstmt.setString(6, employee.getAddress());
            pstmt.setString(7, employee.getSex());
            pstmt.setDouble(8, employee.getSalary());
            pstmt.setString(9, employee.getSupervisorSsn());
            pstmt.setInt(10, employee.getDepartmentNumber());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Employee added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
