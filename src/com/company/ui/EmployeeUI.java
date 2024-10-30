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
        String[] columnNames = {"First Name", "Middle Initial", "Last Name", "SSN", "Birth Date", "Address", "Sex", "Salary", "Supervisor SSN", "Department Name"}; // "Department No." -> "Department Name"
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
        dnoCheckBox = new JCheckBox("Department Name", true);  // "Dept No" -> "Department Name"


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

        // 하단에 초기화, 직원 추가, 삭제 버튼 패널 추가
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());  // 버튼을 양쪽 및 가운데로 배치
        resetButton = new JButton("초기화");
        addButton = new JButton("새 직원 추가");
        deleteButton = new JButton("선택된 직원 삭제");

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));  // 왼쪽 정렬
        leftPanel.add(resetButton);
        buttonPanel.add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  // 가운데 정렬
        centerPanel.add(addButton);
        buttonPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));  // 오른쪽 정렬
        rightPanel.add(deleteButton);
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);

        // 초기화 버튼 이벤트
        resetButton.addActionListener(e -> loadEmployeeData());

        // 직원 삭제 버튼 이벤트
        deleteButton.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                String ssn = tableModel.getValueAt(selectedRow, 3).toString();

                // 확인 대화상자 표시
                int confirmation = JOptionPane.showConfirmDialog(
                        this,
                        "선택한 직원을 삭제하시겠습니까?",
                        "직원 삭제 확인",
                        JOptionPane.YES_NO_OPTION
                );

                // '예'를 선택한 경우에만 삭제 진행
                if (confirmation == JOptionPane.YES_OPTION) {
                    EmployeeSearch.deleteEmployee(ssn);
                    loadEmployeeData(); // 데이터 재로드
                }
            } else {
                JOptionPane.showMessageDialog(null, "삭제할 직원을 선택해주세요.");
            }
        });


        // 직원 추가 버튼 이벤트 - 새 창 띄우기
        addButton.addActionListener(e -> openAddEmployeeDialog());

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
                if (dnoCheckBox.isSelected()) rowData.add(employee.getDepartmentName());  // Department Name 출력

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
            if (dnoCheckBox.isSelected()) rowData.add(employee.getDepartmentName());  // Department Name 출력

            tableModel.addRow(rowData.toArray());
        }
    }

    // 직원 추가 창을 여는 메서드
    private void openAddEmployeeDialog() {
        JDialog dialog = new JDialog(this, "새 직원 추가", true);
        dialog.setSize(400, 600);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);  // 패딩 추가
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 왼쪽 라벨들
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("First Name:"), gbc);

        gbc.gridy++;
        dialog.add(new JLabel("Middle Init:"), gbc);

        gbc.gridy++;
        dialog.add(new JLabel("Last Name:"), gbc);

        gbc.gridy++;
        dialog.add(new JLabel("SSN:"), gbc);

        gbc.gridy++;
        dialog.add(new JLabel("Birth Date:"), gbc);

        gbc.gridy++;
        dialog.add(new JLabel("Address:"), gbc);

        gbc.gridy++;
        dialog.add(new JLabel("Sex:"), gbc);

        gbc.gridy++;
        dialog.add(new JLabel("Salary:"), gbc);

        gbc.gridy++;
        dialog.add(new JLabel("Supervisor SSN:"), gbc);

        gbc.gridy++;
        dialog.add(new JLabel("Dept No:"), gbc);

        // 오른쪽 입력 필드들
        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField fnameField = new JTextField(15);
        dialog.add(fnameField, gbc);

        gbc.gridy++;
        JTextField minitField = new JTextField(15);
        dialog.add(minitField, gbc);

        gbc.gridy++;
        JTextField lnameField = new JTextField(15);
        dialog.add(lnameField, gbc);

        gbc.gridy++;
        JTextField ssnField = new JTextField(15);
        dialog.add(ssnField, gbc);

        gbc.gridy++;
        JTextField bdateField = new JTextField(15);
        dialog.add(bdateField, gbc);

        gbc.gridy++;
        JTextField addressField = new JTextField(15);
        dialog.add(addressField, gbc);

        gbc.gridy++;
        JTextField sexField = new JTextField(15);
        dialog.add(sexField, gbc);

        gbc.gridy++;
        JTextField salaryField = new JTextField(15);
        dialog.add(salaryField, gbc);

        gbc.gridy++;
        JTextField superSsnField = new JTextField(15);
        dialog.add(superSsnField, gbc);

        gbc.gridy++;
        JTextField dnoField = new JTextField(15);
        dialog.add(dnoField, gbc);

        // 추가 버튼을 아래 중앙에 배치
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;  // 버튼이 전체 너비를 차지하게 설정
        gbc.anchor = GridBagConstraints.CENTER;  // 중앙 정렬
        JButton addEmployeeButton = new JButton("추가");
        dialog.add(addEmployeeButton, gbc);

        // 버튼 클릭 이벤트 처리
        addEmployeeButton.addActionListener(e -> {
            String fname = fnameField.getText();
            String minit = minitField.getText();
            String lname = lnameField.getText();
            String ssn = ssnField.getText();
            String bdate = bdateField.getText();
            String address = addressField.getText();
            String sex = sexField.getText();
            String salary = salaryField.getText();
            String superSsn = superSsnField.getText();
            String dno = dnoField.getText();

            // Employee 객체 생성 및 데이터베이스에 추가
            Employee newEmployee = new Employee(fname, minit, lname, ssn, bdate, address, sex, Double.parseDouble(salary), superSsn, Integer.parseInt(dno));
            EmployeeSearch.addEmployee(newEmployee);

            // 추가된 직원 정보를 메인 테이블에 반영
            loadEmployeeData();
            dialog.dispose();  // 창 닫기
        });

        dialog.setVisible(true);
    }

}
