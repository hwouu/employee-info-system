package com.company.db;

import com.company.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeSearch {

    // 모든 직원 정보 조회
    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM EMPLOYEE";

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
        String query = "SELECT * FROM EMPLOYEE WHERE " + column + " = ?";

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
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    // 직원 삭제
    public static void deleteEmployee(String ssn) {
        String query = "DELETE FROM EMPLOYEE WHERE Ssn = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, ssn);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Employee deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 조건에 맞는 직원 삭제
    public static int deleteEmployeeByConditions(String conditionsInput) {
        if (conditionsInput != null && !conditionsInput.trim().isEmpty()) {
            String[] conditionPairs = conditionsInput.split(",");

            StringBuilder queryBuilder = new StringBuilder("DELETE FROM EMPLOYEE WHERE ");
            List<String> values = new ArrayList<>();

            for (int i = 0; i < conditionPairs.length; i++) {
                String condition = conditionPairs[i].trim();
                String operator = null;

                // 비교 연산자 찾기
                if (condition.contains(">=")) {
                    operator = ">=";
                } else if (condition.contains("<=")) {
                    operator = "<=";
                } else if (condition.contains(">")) {
                    operator = ">";
                } else if (condition.contains("<")) {
                    operator = "<";
                } else if (condition.contains("!=")) {
                    operator = "!=";
                } else if (condition.contains("=")) {
                    operator = "=";
                }

                // 연산자가 없는 경우 오류 처리
                if (operator == null) {
                    return -1; // 잘못된 입력 형식
                }

                // 연산자를 기준으로 필드와 값 분리
                String[] fieldValue = condition.split(operator);
                if (fieldValue.length == 2) {
                    String field = fieldValue[0].trim();
                    String value = fieldValue[1].trim();

                    values.add(value);

                    queryBuilder.append(field).append(" ").append(operator).append(" ?");
                    if (i < conditionPairs.length - 1) {
                        queryBuilder.append(" AND ");
                    }
                } else {
                    return -1; // 잘못된 입력 형식
                }
            }

            String query = queryBuilder.toString();

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = connection.prepareStatement(query)) {

                for (int i = 0; i < values.size(); i++) {
                    pstmt.setString(i + 1, values.get(i));
                }

                int affectedRows = pstmt.executeUpdate();
                return affectedRows;

            } catch (SQLException ex) {
                ex.printStackTrace();
                return -1;
            }
        } else {
            return -1;
        }
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
