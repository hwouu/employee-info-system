package com.company.ui;

import com.company.db.EmployeeSearch;
import com.company.model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeUI extends JFrame {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> searchColumnComboBox;
    private JButton searchButton, resetButton, deleteButton, addButton;

    // 체크박스들
    private JCheckBox fnameCheckBox, minitCheckBox, lnameCheckBox, ssnCheckBox, bdateCheckBox,
            addressCheckBox, sexCheckBox, salaryCheckBox, superSsnCheckBox, dnoCheckBox;

    public EmployeeUI() {
        setTitle("Employee Information System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 테이블 생성
        String[] columnNames = {"First Name", "Middle Initial", "Last Name", "SSN", "Birth Date", "Address", "Sex", "Salary", "Supervisor SSN", "Department No."};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // 검색 필드와 버튼 패널 (상단)
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout()); // 컴포넌트를 한 줄로 배치
        searchColumnComboBox = new JComboBox<>(new String[]{
                "Fname", "Minit", "Lname", "Ssn", "Bdate", "Address", "Sex", "Salary", "Super_ssn", "Dno"
        });
        searchField = new JTextField(10);
        searchButton = new JButton("검색");
        searchPanel.add(new JLabel("검색 범위"));
        searchPanel.add(searchColumnComboBox);
        searchPanel.add(new JLabel("검색 내용"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // 체크박스 패널 생성 (왼쪽)
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new GridLayout(0, 1)); // 세로로 체크박스 배치
        fnameCheckBox = new JCheckBox("First Name", true);
        minitCheckBox = new JCheckBox("Middle Initial", true);
        lnameCheckBox = new JCheckBox("Last Name", true);
        ssnCheckBox = new JCheckBox("SSN", true);
        bdateCheckBox = new JCheckBox("Birth Date", true);
        addressCheckBox = new JCheckBox("Address", true);
        sexCheckBox = new JCheckBox("Sex", true);
        salaryCheckBox = new JCheckBox("Salary", true);
        superSsnCheckBox = new JCheckBox("Supervisor SSN", true);
        dnoCheckBox = new JCheckBox("Dept No", true);

        checkBoxPanel.add(fnameCheckBox);
        checkBoxPanel.add(minitCheckBox);
        checkBoxPanel.add(lnameCheckBox);
        checkBoxPanel.add(ssnCheckBox);
        checkBoxPanel.add(bdateCheckBox);
        checkBoxPanel.add(addressCheckBox);
        checkBoxPanel.add(sexCheckBox);
        checkBoxPanel.add(salaryCheckBox);
        checkBoxPanel.add(superSsnCheckBox);
        checkBoxPanel.add(dnoCheckBox);

        add(checkBoxPanel, BorderLayout.WEST);

        // 하단에 초기화, 삭제, 추가 버튼 패널 추가
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());  // 버튼을 한 줄로 배치
        resetButton = new JButton("초기화");
        deleteButton = new JButton("선택된 직원 삭제");
        buttonPanel.add(resetButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 초기화 버튼 이벤트
        resetButton.addActionListener(e -> loadEmployeeData());

        // 직원 삭제 버튼 이벤트
        deleteButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                String ssn = tableModel.getValueAt(selectedRow, 3).toString();
                EmployeeSearch.deleteEmployee(ssn);
                loadEmployeeData(); // 데이터 재로드
            } else {
                JOptionPane.showMessageDialog(null, "삭제할 직원을 선택해주세요.");
            }
        });

        // 검색 버튼 클릭 이벤트
        searchButton.addActionListener(e -> {
            String column = searchColumnComboBox.getSelectedItem().toString(); // 검색할 열
            String value = searchField.getText(); // 검색할 값
            List<Employee> employees = EmployeeSearch.searchEmployees(column, value);
            tableModel.setRowCount(0); // 테이블 초기화
            for (Employee employee : employees) {
                List<Object> rowData = new ArrayList<>();

                if (fnameCheckBox.isSelected()) rowData.add(employee.getFirstName());
                if (minitCheckBox.isSelected()) rowData.add(employee.getMiddleInitial());
                if (lnameCheckBox.isSelected()) rowData.add(employee.getLastName());
                if (ssnCheckBox.isSelected()) rowData.add(employee.getSsn());
                if (bdateCheckBox.isSelected()) rowData.add(employee.getBirthDate());
                if (addressCheckBox.isSelected()) rowData.add(employee.getAddress());
                if (sexCheckBox.isSelected()) rowData.add(employee.getSex());
                if (salaryCheckBox.isSelected()) rowData.add(employee.getSalary());
                if (superSsnCheckBox.isSelected()) rowData.add(employee.getSupervisorSsn());
                if (dnoCheckBox.isSelected()) rowData.add(employee.getDepartmentNumber());

                tableModel.addRow(rowData.toArray());
            }
        });

        // 직원 정보 로드
        loadEmployeeData();

        // UI 표시
        setVisible(true);  // JFrame을 화면에 표시
    }

    // 직원 정보 테이블 로드
    private void loadEmployeeData() {
        List<Employee> employees = EmployeeSearch.getAllEmployees();
        tableModel.setRowCount(0);  // 테이블 초기화

        for (Employee employee : employees) {
            List<Object> rowData = new ArrayList<>();

            if (fnameCheckBox.isSelected()) rowData.add(employee.getFirstName());
            if (minitCheckBox.isSelected()) rowData.add(employee.getMiddleInitial());
            if (lnameCheckBox.isSelected()) rowData.add(employee.getLastName());
            if (ssnCheckBox.isSelected()) rowData.add(employee.getSsn());
            if (bdateCheckBox.isSelected()) rowData.add(employee.getBirthDate());
            if (addressCheckBox.isSelected()) rowData.add(employee.getAddress());
            if (sexCheckBox.isSelected()) rowData.add(employee.getSex());
            if (salaryCheckBox.isSelected()) rowData.add(employee.getSalary());
            if (superSsnCheckBox.isSelected()) rowData.add(employee.getSupervisorSsn());
            if (dnoCheckBox.isSelected()) rowData.add(employee.getDepartmentNumber());

            tableModel.addRow(rowData.toArray());
        }
    }
}
