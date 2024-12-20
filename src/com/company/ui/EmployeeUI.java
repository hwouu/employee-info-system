package com.company.ui;

import com.company.db.EmployeeSearch;
import com.company.model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeUI extends JFrame {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private List<JTextField> searchField;
    private JComboBox groupSalaryComboBox;
    private JButton searchButton, resetButton, deleteButton, conditionDeleteButton, addButton, editButton,
            groupSalaryButton, groupNumButton;
    // 체크박스들
    private JCheckBox fnameCheckBox, minitCheckBox, lnameCheckBox, ssnCheckBox, bdateCheckBox,
            addressCheckBox, sexCheckBox, salaryCheckBox, superSsnCheckBox, dnoCheckBox;

    public EmployeeUI() {
        setTitle("Employee Information System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 테이블 생성
        String[] columnNames = {"First Name", "Middle Initial", "Last Name", "SSN", "Birth Date",
                "Address", "Sex", "Salary", "Supervisor SSN", "Department Name"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // 현재 컬럼의 이름을 가져옴
                String columnName = getColumnName(columnIndex);
                // Salary 컬럼인 경우에만 Double 반환
                if (columnName.equals("Salary")) {
                    return Double.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 모든 셀을 편집 불가능하게 설정
            }
        };

        employeeTable = new JTable(tableModel);


        employeeTable.setAutoCreateRowSorter(true);

        // 각 컬럼의 선호하는 너비 설정
        employeeTable.getColumnModel().getColumn(0).setPreferredWidth(100); // First Name
        employeeTable.getColumnModel().getColumn(1).setPreferredWidth(50);  // Middle Initial
        employeeTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Last Name
        employeeTable.getColumnModel().getColumn(3).setPreferredWidth(100); // SSN
        employeeTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Birth Date
        employeeTable.getColumnModel().getColumn(5).setPreferredWidth(150); // Address
        employeeTable.getColumnModel().getColumn(6).setPreferredWidth(50);  // Sex
        employeeTable.getColumnModel().getColumn(7).setPreferredWidth(100); // Salary
        employeeTable.getColumnModel().getColumn(8).setPreferredWidth(100); // Supervisor SSN
        employeeTable.getColumnModel().getColumn(9).setPreferredWidth(150); // Department Name

        // 테이블 설정
        employeeTable.setFillsViewportHeight(true);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // 기존 코드 계속
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        ActionListener checkBoxListener = e -> loadEmployeeData();

        // 검색 필드 리스트 (우측)
        JPanel searchListPanel = new JPanel();
        searchListPanel.setLayout(new BoxLayout(searchListPanel, BoxLayout.Y_AXIS));
        JButton addQueryButton = new JButton("추가하기");

        searchListPanel.add(addQueryButton);
        searchListPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 간격 추가
        searchListPanel.add(searchButton = new JButton("검색하기"));
        add(searchListPanel, BorderLayout.EAST);

        // 그룹별 평균 급여 검색
        JPanel groupSalaryPanel = new JPanel();
        groupSalaryPanel.setLayout(new FlowLayout());
        groupSalaryComboBox = new JComboBox<>(new String[] {
                "Sex", "Super_ssn", "Dname"
        });
        groupSalaryButton = new JButton("평균 급여 검색");
        groupSalaryPanel.add(new JLabel("그룹별 평균 급여"));
        groupNumButton = new JButton("그룹별 직원 수 검색");
        groupSalaryPanel.add(groupSalaryComboBox);
        groupSalaryPanel.add(groupSalaryButton);
        groupSalaryPanel.add(groupNumButton);

        add(groupSalaryPanel, BorderLayout.NORTH);

        // 체크박스 패널 생성 (왼쪽)
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new GridLayout(0, 1)); // 세로로 체크박스 배치

        // EmployeeUI.java 체크박스 생성 부분 수정

        fnameCheckBox = new JCheckBox("First Name", true);
        fnameCheckBox.addActionListener(checkBoxListener);

        minitCheckBox = new JCheckBox("Middle Initial", true);
        minitCheckBox.addActionListener(checkBoxListener);

        lnameCheckBox = new JCheckBox("Last Name", true);
        lnameCheckBox.addActionListener(checkBoxListener);

        ssnCheckBox = new JCheckBox("SSN", true);
        ssnCheckBox.addActionListener(checkBoxListener);

        bdateCheckBox = new JCheckBox("Birth Date", true);
        bdateCheckBox.addActionListener(checkBoxListener);

        addressCheckBox = new JCheckBox("Address", true);
        addressCheckBox.addActionListener(checkBoxListener);

        sexCheckBox = new JCheckBox("Sex", true);
        sexCheckBox.addActionListener(checkBoxListener);

        salaryCheckBox = new JCheckBox("Salary", true);
        salaryCheckBox.addActionListener(checkBoxListener);

        superSsnCheckBox = new JCheckBox("Supervisor SSN", true);
        superSsnCheckBox.addActionListener(checkBoxListener);

        dnoCheckBox = new JCheckBox("Department Name", true);
        dnoCheckBox.addActionListener(checkBoxListener);

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
        buttonPanel.setLayout(new BorderLayout()); // 버튼을 양쪽 및 가운데로 배치
        resetButton = new JButton("초기화");
        addButton = new JButton("새 직원 추가");
        deleteButton = new JButton("선택된 직원 삭제");
        conditionDeleteButton = new JButton("조건에 맞는 직원 삭제"); // 조건 삭제 버튼 추가
        editButton = new JButton("직원 수정");

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // 왼쪽 정렬
        leftPanel.add(resetButton);
        buttonPanel.add(leftPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 가운데 정렬
        centerPanel.add(addButton);
        centerPanel.add(editButton);
        buttonPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // 오른쪽 정렬
        rightPanel.add(deleteButton);
        rightPanel.add(conditionDeleteButton); // 조건 삭제 버튼 추가
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        add(buttonPanel, BorderLayout.SOUTH);

        // 초기화 버튼 이벤트
        resetButton.addActionListener(e -> loadEmployeeData());

        groupSalaryButton.addActionListener(e -> showAverageSalaryDialog());
        groupNumButton.addActionListener(e -> showGroupNumber());

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
                        JOptionPane.YES_NO_OPTION);

                // '예'를 선택한 경우에만 삭제 진행
                if (confirmation == JOptionPane.YES_OPTION) {
                    EmployeeSearch.deleteEmployee(ssn);
                    loadEmployeeData(); // 데이터 재로드
                }
            } else {
                JOptionPane.showMessageDialog(null, "삭제할 직원을 선택해주세요.");
            }
        });

        // 조건에 맞는 직원 삭제 버튼
        conditionDeleteButton.addActionListener(e -> {
            String conditionsInput = JOptionPane.showInputDialog(this,
                    "삭제 조건을 입력하세요 (예: SSN=123456789, Salary>50000):");

            if (conditionsInput == null || conditionsInput.trim().isEmpty()) {
                // 조건이 입력되지 않았거나 사용자가 취소를 눌렀을 경우 바로 종료
                return;
            }

            // 이후의 삭제 로직 계속 진행
            String employeeData = EmployeeSearch.getEmployeeDataByConditions(conditionsInput);

            if (employeeData != null && !employeeData.isEmpty()) {
                JTextArea textArea = new JTextArea(15, 30);
                textArea.setText("삭제할 직원 정보:\n" + employeeData);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);
                JScrollPane scrollPane1 = new JScrollPane(textArea);

                int confirm = JOptionPane.showConfirmDialog(this, scrollPane1, "삭제 확인", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    int affectedRows = EmployeeSearch.deleteEmployeeByConditions(conditionsInput);

                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(this, "조건에 맞는 직원이 성공적으로 삭제되었습니다.");
                    } else {
                        JOptionPane.showMessageDialog(this, "조건에 맞는 직원을 찾을 수 없습니다.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "삭제 작업이 취소되었습니다.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "조건에 맞는 직원을 찾을 수 없습니다.");
            }

            loadEmployeeData(); // 데이터 재로드
        });

        // 직원 수정 버튼 클릭 시 대화상자 표시
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // SSN 입력
                String ssn = JOptionPane.showInputDialog(EmployeeUI.this, "수정할 직원의 SSN을 입력하세요:");
                if (ssn == null || ssn.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(EmployeeUI.this, "SSN을 입력하지 않았습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 테이블에서 SSN으로 직원 찾기
                int targetRow = -1;
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    if (ssn.equals(tableModel.getValueAt(row, 3))) { // 3번 컬럼이 SSN임
                        targetRow = row;
                        break;
                    }
                }

                // SSN에 해당하는 직원이 없는 경우
                if (targetRow == -1) {
                    JOptionPane.showMessageDialog(EmployeeUI.this, "해당 SSN을 가진 직원을 찾을 수 없습니다.", "오류",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 수정할 항목 선택
                String[] fields = { "Address", "Salary", "Super_Ssn", "Dno" };
                String selectedField = (String) JOptionPane.showInputDialog(
                        EmployeeUI.this,
                        "수정할 항목을 선택하세요:",
                        "항목 선택",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        fields,
                        fields[0]);

                if (selectedField != null) {
                    // 새 값 입력
                    String newValue = JOptionPane.showInputDialog(EmployeeUI.this, selectedField + "의 새 값을 입력하세요:");
                    if (newValue != null && !newValue.trim().isEmpty()) {
                        int affectedRow = EmployeeSearch.updateEmployeeInDatabase(ssn, selectedField, newValue);
                        if (affectedRow > 0) {
                            JOptionPane.showMessageDialog(EmployeeUI.this, "직원 정보가 업데이트되었습니다.");
                        }
                        // 선택된 항목에 맞게 값을 업데이트

                        switch (selectedField) {
                            case "Address":
                                tableModel.setValueAt(newValue, targetRow, 5);
                                break;
                            case "Salary":
                                tableModel.setValueAt(newValue, targetRow, 7);
                                break;
                            case "Super_Ssn":
                                tableModel.setValueAt(newValue, targetRow, 8);
                                break;
                            case "Dno":
                                tableModel.setValueAt(newValue, targetRow, 9);
                                break;

                        }
                    } else {
                        JOptionPane.showMessageDialog(EmployeeUI.this, "새 값을 입력하지 않았습니다.", "오류",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // 직원 추가 버튼 이벤트 - 새 창 띄우기
        addButton.addActionListener(e -> openAddEmployeeDialog());

        // 검색 버튼 클릭 이벤트
        searchButton.addActionListener(e -> {
            List<List<String>> conditions = new ArrayList<>();
            for (Component component : searchListPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel searchPanel = (JPanel) component;
                    JComboBox<String> searchColumnComboBox = (JComboBox<String>) searchPanel.getComponent(0);
                    JTextField searchField = (JTextField) searchPanel.getComponent(1);

                    String searchColumn = searchColumnComboBox.getSelectedItem().toString();
                    String searchValue = searchField.getText();

                    if (!searchValue.isEmpty()) {
                        List<String> condition = new ArrayList<>();
                        condition.add(searchColumn);
                        condition.add(searchValue);
                        conditions.add(condition);
                    }
                }
            }
            List<Employee> employees = EmployeeSearch.searchEmployees(conditions);

            tableModel.setRowCount(0); // 테이블 초기화
            for (Employee employee : employees) {
                List<Object> rowData = new ArrayList<>();

                if (fnameCheckBox.isSelected())
                    rowData.add(employee.getFirstName());
                if (minitCheckBox.isSelected())
                    rowData.add(employee.getMiddleInitial());
                if (lnameCheckBox.isSelected())
                    rowData.add(employee.getLastName());
                if (ssnCheckBox.isSelected())
                    rowData.add(employee.getSsn());
                if (bdateCheckBox.isSelected())
                    rowData.add(employee.getBirthDate());
                if (addressCheckBox.isSelected())
                    rowData.add(employee.getAddress());
                if (sexCheckBox.isSelected())
                    rowData.add(employee.getSex());
                if (salaryCheckBox.isSelected())
                    rowData.add(employee.getSalary());
                if (superSsnCheckBox.isSelected())
                    rowData.add(employee.getSupervisorSsn());
                if (dnoCheckBox.isSelected())
                    rowData.add(employee.getDepartmentName()); // 부서 이름 출력

                tableModel.addRow(rowData.toArray());
            }
        });

        // 조건 추가 버튼 클릭 이벤트
        addQueryButton.addActionListener(e -> {
            JTextField searchField = new JTextField(15);
            JButton removeButton = new JButton("삭제");
            JComboBox<String> searchColumnComboBox = new JComboBox<>(new String[] {
                    "Fname", "Minit", "Lname", "Ssn", "Bdate", "Address", "Sex", "Salary", "Super_ssn",
                    "Department Name"
            });

            JPanel searchPanel = new JPanel();
            searchPanel.add(searchColumnComboBox);
            searchPanel.add(searchField);
            searchPanel.add(removeButton);

            searchListPanel.add(searchPanel);
            searchListPanel.revalidate();
            searchListPanel.repaint();

            removeButton.addActionListener(e1 -> {
                searchListPanel.remove(searchPanel);
                searchListPanel.revalidate();
                searchListPanel.repaint();
            });
        });

        // 직원 정보 로드
        loadEmployeeData();

        // UI 표시
        setVisible(true); // JFrame을 화면에 표시
    }

    // 그룹별 평균 급여
    private void showAverageSalaryDialog() {
        String group = groupSalaryComboBox.getSelectedItem().toString();
        Map<String, Double> avgSalaries = EmployeeSearch.getAverageSalaryByGroup(group);

        StringBuilder resultText = new StringBuilder();
        for (Map.Entry<String, Double> entry : avgSalaries.entrySet()) {
            resultText.append(entry.getKey()).append(" : ")
                    .append(String.format("%.2f", entry.getValue())).append("\n");
        }

        JOptionPane.showMessageDialog(this, resultText.toString(), "그룹별 평균 급여", JOptionPane.INFORMATION_MESSAGE);
    }
    // 그룹별 직원수
    private void showGroupNumber() {
        String group = groupSalaryComboBox.getSelectedItem().toString();
        Map<String, Integer> avgNumber = EmployeeSearch.getEmployeeCountByGroup(group);

        StringBuilder resultText = new StringBuilder();
        for (Map.Entry<String, Integer> entry : avgNumber.entrySet()) {
            resultText.append(entry.getKey()).append(" : ")
                    .append(String.format("%d", entry.getValue())).append("\n");
        }

        JOptionPane.showMessageDialog(this, resultText.toString(), "그룹별 직원 수", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadEmployeeData() {
        List<Employee> employees = EmployeeSearch.getAllEmployees();

        // 표시할 컬럼 목록 동적 업데이트
        List<String> visibleColumns = new ArrayList<>();
        if (fnameCheckBox.isSelected())
            visibleColumns.add("First Name");
        if (minitCheckBox.isSelected())
            visibleColumns.add("Middle Initial");
        if (lnameCheckBox.isSelected())
            visibleColumns.add("Last Name");
        if (ssnCheckBox.isSelected())
            visibleColumns.add("SSN");
        if (bdateCheckBox.isSelected())
            visibleColumns.add("Birth Date");
        if (addressCheckBox.isSelected())
            visibleColumns.add("Address");
        if (sexCheckBox.isSelected())
            visibleColumns.add("Sex");
        if (salaryCheckBox.isSelected())
            visibleColumns.add("Salary");
        if (superSsnCheckBox.isSelected())
            visibleColumns.add("Supervisor SSN");
        if (dnoCheckBox.isSelected())
            visibleColumns.add("Department Name");

        // 테이블 모델의 컬럼 업데이트
        tableModel.setColumnIdentifiers(visibleColumns.toArray());

        tableModel.setRowCount(0); // 테이블 초기화

        // 데이터 로드
        for (Employee employee : employees) {
            List<Object> rowData = new ArrayList<>();

            if (fnameCheckBox.isSelected())
                rowData.add(employee.getFirstName());
            if (minitCheckBox.isSelected())
                rowData.add(employee.getMiddleInitial());
            if (lnameCheckBox.isSelected())
                rowData.add(employee.getLastName());
            if (ssnCheckBox.isSelected())
                rowData.add(employee.getSsn());
            if (bdateCheckBox.isSelected())
                rowData.add(employee.getBirthDate());
            if (addressCheckBox.isSelected())
                rowData.add(employee.getAddress());
            if (sexCheckBox.isSelected())
                rowData.add(employee.getSex());
            if (salaryCheckBox.isSelected())
                rowData.add(employee.getSalary());
            if (superSsnCheckBox.isSelected())
                rowData.add(employee.getSupervisorSsn());
            if (dnoCheckBox.isSelected())
                rowData.add(employee.getDepartmentName());

            tableModel.addRow(rowData.toArray());
        }
    }

    // 직원 추가 창을 여는 메서드
    private void openAddEmployeeDialog() {
        JDialog dialog = new JDialog(this, "새 직원 추가", true);
        dialog.setSize(400, 600);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 패딩 추가
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
        gbc.gridwidth = 2; // 버튼이 전체 너비를 차지하게 설정
        gbc.anchor = GridBagConstraints.CENTER; // 중앙 정렬
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
            Employee newEmployee = new Employee(fname, minit, lname, ssn, bdate, address, sex,
                    Double.parseDouble(salary), superSsn, Integer.parseInt(dno));
            boolean addResult = EmployeeSearch.addEmployee(newEmployee);

            // 추가된 직원 정보를 메인 테이블에 반영
            loadEmployeeData();

            // 추가 성공 시 창 닫기
            if (addResult) {
                dialog.dispose();
            }
            System.out.println("Employee added: " + addResult);
        });

        dialog.setVisible(true);
    }

}
