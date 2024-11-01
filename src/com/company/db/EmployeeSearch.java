package com.company.db;

import com.company.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class EmployeeSearch {

    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT e.*, d.Dname AS departmentName " +
                "FROM EMPLOYEE e " +
                "JOIN DEPARTMENT d ON e.Dno = d.Dnumber";  // JOIN 추가

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
                // 부서명을 Employee 객체에 설정
                employee.setDepartmentName(resultSet.getString("departmentName"));  // 이 부분을 추가
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
                        resultSet.getInt("Dno"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    // 그룹별 평균 급여
    public static Map<String, Double> getAverageSalaryByGroup(String groupByColumn) {
        Map<String, Double> averageSalaries = new HashMap<>();
        String query = "SELECT " + groupByColumn + ", AVG(Salary) AS avg_salary FROM EMPLOYEE GROUP BY " + groupByColumn;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String group = resultSet.getString(groupByColumn);
                double averageSalary = resultSet.getDouble("avg_salary");
                averageSalaries.put(group, averageSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return averageSalaries;
    }


    // 데이터를 조회하여 조건에 맞는 직원 정보를 반환하는 함수
    public static String getEmployeeDataByConditions(String conditionsInput) {
        if (conditionsInput != null && !conditionsInput.trim().isEmpty()) {
            String[] conditionPairs = conditionsInput.split(",");
            StringBuilder selectQueryBuilder = new StringBuilder("SELECT * FROM EMPLOYEE WHERE ");
            List<String> values = new ArrayList<>();

            for (int i = 0; i < conditionPairs.length; i++) {
                String condition = conditionPairs[i].trim();
                String operator = null;

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

                if (operator == null) {
                    return null;
                }

                String[] fieldValue = condition.split(operator);
                if (fieldValue.length == 2) {
                    String field = fieldValue[0].trim();
                    String value = fieldValue[1].trim();

                    values.add(value);

                    selectQueryBuilder.append(field).append(" ").append(operator).append(" ?");
                    if (i < conditionPairs.length - 1) {
                        selectQueryBuilder.append(" AND ");
                    }
                } else {
                    return null;
                }
            }

            String selectQuery = selectQueryBuilder.toString();
            StringBuilder employeeData = new StringBuilder();

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement selectPstmt = connection.prepareStatement(selectQuery)) {

                for (int i = 0; i < values.size(); i++) {
                    selectPstmt.setString(i + 1, values.get(i));
                }

                ResultSet resultSet = selectPstmt.executeQuery();

                if (resultSet.next()) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    do {
                        for (int i = 1; i <= columnCount; i++) {
                            employeeData.append(metaData.getColumnName(i)).append(": ").append(resultSet.getString(i)).append("\n");
                        }
                        employeeData.append("\n");
                    } while (resultSet.next());

                    return employeeData.toString();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    // 조건에 맞는 직원 데이터를 삭제하는 함수
    public static int deleteEmployeeByConditions(String conditionsInput) {
        if (conditionsInput != null && !conditionsInput.trim().isEmpty()) {
            String[] conditionPairs = conditionsInput.split(",");
            StringBuilder queryBuilder = new StringBuilder("DELETE FROM EMPLOYEE WHERE ");
            List<String> values = new ArrayList<>();

            for (int i = 0; i < conditionPairs.length; i++) {
                String condition = conditionPairs[i].trim();
                String operator = null;

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

                if (operator == null) {
                    return -1;
                }

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
                    return -1;
                }
            }

            String deleteQuery = queryBuilder.toString();

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement deletePstmt = connection.prepareStatement(deleteQuery)) {

                for (int i = 0; i < values.size(); i++) {
                    deletePstmt.setString(i + 1, values.get(i));
                }

                return deletePstmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
                return -1;
            }
        } else {
            return -1;
        }
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



    // 직원 추가
    // EmployeeSearch.java 파일의 addEmployee 메서드에서

    public static boolean addEmployee(Employee employee) {
        String query = "INSERT INTO EMPLOYEE (Fname, Minit, Lname, Ssn, Bdate, Address, Sex, Salary, Super_ssn, Dno) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            // 날짜 형식 확인
            if (!isValidDate(employee.getBirthDate())) {
                throw new ParseException("유효하지 않은 날짜 형식입니다. YYYY-MM-DD 형식으로 입력하세요.", 0);
            }

            // 성별 유효성 검사
            if (!employee.getSex().equals("F") && !employee.getSex().equals("M")) {
                showErrorPopup("성별은 'F' 또는 'M'만 가능합니다.");
                return false;
            }

            // 급여 유효성 검사
            if (employee.getSalary() <= 0) {
                showErrorPopup("급여는 양수여야 합니다.");
                return false;
            }

            // SupervisorSSN 유효성 검사
            if (employee.getSupervisorSsn() != null && !employee.getSupervisorSsn().isEmpty()) {
                if (!isValidSupervisorSsn(employee.getSupervisorSsn())) {
                    showErrorPopup("유효하지 않은 상사 주민등록번호입니다. 해당 상사가 존재하지 않습니다.");
                    return false;
                }
            }

            // 부서 번호 유효성 검사
            if (!isValidDepartmentNumber(employee.getDepartmentNumber())) {
                showErrorPopup("유효하지 않은 부서 번호입니다. 올바른 부서 번호를 입력하세요.");
                return false;
            }

            // 직원 정보 데이터베이스에 추가
            pstmt.setString(1, employee.getFirstName());
            pstmt.setString(2, employee.getMiddleInitial());
            pstmt.setString(3, employee.getLastName());
            pstmt.setString(4, employee.getSsn());
            pstmt.setString(5, employee.getBirthDate());
            pstmt.setString(6, employee.getAddress());
            pstmt.setString(7, employee.getSex());
            pstmt.setDouble(8, employee.getSalary());
            pstmt.setString(9, employee.getSupervisorSsn().isEmpty() ? null : employee.getSupervisorSsn());
            pstmt.setInt(10, employee.getDepartmentNumber());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "직원이 성공적으로 추가되었습니다.");
            }
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            if (e.getMessage().contains("PRIMARY")) {
                showErrorPopup("중복된 주민등록번호가 존재합니다. 다른 번호를 입력하세요.");
            } else if (e.getMessage().contains("foreign key constraint")) {
                showErrorPopup("유효하지 않은 부서 번호 또는 상사 주민등록번호입니다.");
            }
            return false;
        } catch (ParseException e) {
            showErrorPopup("유효하지 않은 날짜 형식입니다. YYYY-MM-DD 형식으로 입력하세요.");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorPopup("데이터베이스 오류가 발생했습니다. 다시 시도하세요.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            showErrorPopup("예상치 못한 오류가 발생했습니다. 오류를 확인하세요.");
            return false;
        }
    }




    // 상사 주민등록번호 유효성 검사
    private static boolean isValidSupervisorSsn(String supervisorSsn) {
        if (supervisorSsn == null || supervisorSsn.isEmpty()) {
            return true; // 상사가 없을 경우 (optional)
        }
        String query = "SELECT COUNT(*) FROM EMPLOYEE WHERE Ssn = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, supervisorSsn);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 부서 번호 유효성 검사
    public static boolean isValidDepartmentNumber(int dno) {
        System.out.println("Checking department number: " + dno); // 부서 번호 출력

        String query = "SELECT * FROM DEPARTMENT WHERE Dnumber = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, dno);
            ResultSet rs = pstmt.executeQuery();

            boolean exists = rs.next();
            System.out.println("Department exists: " + exists); // 부서 존재 여부 출력

            return exists; // 결과가 존재하면 true 반환
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    // 오류 팝업 메시지 생성
    private static void showErrorPopup(String message) {
        // 비모달 JDialog 창 생성
        JDialog dialog = new JDialog();
        dialog.setTitle("오류");
        dialog.setModal(true); // 모달로 설정하여 다른 창을 열 때 창을 닫을 때까지 대기

        // 오류 메시지와 확인 버튼을 포함한 패널 생성
        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        JButton button = new JButton("확인");

        // 확인 버튼 클릭 시 창 닫기
        button.addActionListener(e -> dialog.dispose());

        panel.add(label);
        panel.add(button);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }


    // 날짜 형식 확인 메서드
    private static boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
