package com.company.ui;

import com.company.db.EmployeeSearch;
import com.company.model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeUI extends JFrame {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> searchColumnComboBox;
    private JTextField firstNameField, middleInitialField, lastNameField, ssnField, birthDateField, addressField, sexField, salaryField, superSsnField, deptField;
    private JButton searchButton, deleteButton, addButton;

    public EmployeeUI() {
        setTitle("Employee Information System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 테이블 생성
        String[] columnNames = {"First Name", "Middle Initial", "Last Name", "SSN", "Birth Date", "Address", "Sex", "Salary", "Supervisor SSN", "Department No."};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // 검색 필드와 버튼 패널
        JPanel searchPanel = new JPanel();
        searchColumnComboBox = new JComboBox<>(new String[]{"Fname", "Lname", "Ssn", "Sex", "Dno"});
        searchField = new JTextField(10);
        searchButton = new JButton("검색");
        searchPanel.add(new JLabel("검색 범위"));
        searchPanel.add(searchColumnComboBox);
        searchPanel.add(new JLabel("검색 내용"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // 직원 정보 추가 패널
        JPanel addPanel = new JPanel(new GridLayout(6, 4));
        firstNameField = new JTextField(10);
        middleInitialField = new JTextField(1);
        lastNameField = new JTextField(10);
        ssnField = new JTextField(9);
        birthDateField = new JTextField(10);
        addressField = new JTextField(15);
        sexField = new JTextField(1);
        salaryField = new JTextField(8);
        superSsnField = new JTextField(9);
        deptField = new JTextField(3);
        addButton = new JButton("직원 추가");

        addPanel.add(new JLabel("First Name:"));
        addPanel.add(firstNameField);
        addPanel.add(new JLabel("Middle Initial:"));
        addPanel.add(middleInitialField);
        addPanel.add(new JLabel("Last Name:"));
        addPanel.add(lastNameField);
        addPanel.add(new JLabel("SSN:"));
        addPanel.add(ssnField);
        addPanel.add(new JLabel("Birth Date:"));
        addPanel.add(birthDateField);
        addPanel.add(new JLabel("Address:"));
        addPanel.add(addressField);
        addPanel.add(new JLabel("Sex:"));
        addPanel.add(sexField);
        addPanel.add(new JLabel("Salary:"));
        addPanel.add(salaryField);
        addPanel.add(new JLabel("Supervisor SSN:"));
        addPanel.add(superSsnField);
        addPanel.add(new JLabel("Dept No:"));
        addPanel.add(deptField);
        addPanel.add(addButton);

        add(addPanel, BorderLayout.SOUTH);

        // 직원 삭제 버튼
        deleteButton = new JButton("선택된 직원 삭제");
        add(deleteButton, BorderLayout.EAST);

        // 데이터 로드
        loadEmployeeData();

        // 검색 버튼 클릭 이벤트
        searchButton.addActionListener(e -> {
            String column = searchColumnComboBox.getSelectedItem().toString(); // 검색할 열
            String value = searchField.getText(); // 검색할 값
            List<Employee> employees = EmployeeSearch.searchEmployees(column, value);
            tableModel.setRowCount(0); // 테이블 초기화
            for (Employee employee : employees) {
                Object[] rowData = {
                        employee.getFirstName(),
                        employee.getMiddleInitial(),
                        employee.getLastName(),
                        employee.getSsn(),
                        employee.getBirthDate(),
                        employee.getAddress(),
                        employee.getSex(),
                        employee.getSalary(),
                        employee.getSupervisorSsn(),
                        employee.getDepartmentNumber()
                };
                tableModel.addRow(rowData);
            }
        });

        // 직원 추가 버튼 클릭 이벤트
        addButton.addActionListener(e -> {
            Employee employee = new Employee(
                    firstNameField.getText(),
                    middleInitialField.getText(),
                    lastNameField.getText(),
                    ssnField.getText(),
                    birthDateField.getText(),
                    addressField.getText(),
                    sexField.getText(),
                    Double.parseDouble(salaryField.getText()),
                    superSsnField.getText(),
                    Integer.parseInt(deptField.getText())
            );
            EmployeeSearch.addEmployee(employee);
            loadEmployeeData(); // 데이터 재로드
        });

        // 삭제 버튼 클릭 이벤트
        deleteButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow(); // 선택된 행 가져오기
            if (selectedRow >= 0) {
                String ssn = tableModel.getValueAt(selectedRow, 3).toString(); // 선택된 행의 SSN
                EmployeeSearch.deleteEmployee(ssn);
                loadEmployeeData(); // 데이터 재로드
            } else {
                JOptionPane.showMessageDialog(null, "삭제할 직원을 선택해주세요.");
            }
        });

        setVisible(true);
    }

    // 직원 정보 테이블 로드
    private void loadEmployeeData() {
        List<Employee> employees = EmployeeSearch.getAllEmployees();
        tableModel.setRowCount(0); // 테이블 초기화
        for (Employee employee : employees) {
            Object[] rowData = {
                    employee.getFirstName(),
                    employee.getMiddleInitial(),
                    employee.getLastName(),
                    employee.getSsn(),
                    employee.getBirthDate(),
                    employee.getAddress(),
                    employee.getSex(),
                    employee.getSalary(),
                    employee.getSupervisorSsn(),
                    employee.getDepartmentNumber()
            };
            tableModel.addRow(rowData);
        }
    }
}
