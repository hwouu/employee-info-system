package com.company.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/company";  // DB URL
    private static final String USER = "test";  // DB 사용자 이름
    private static final String PASSWORD = "0000";  // DB 비밀번호

    // 데이터베이스 연결 생성
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // JDBC 드라이버를 통해 데이터베이스 연결
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
        } catch (SQLException e) {
            e.printStackTrace();  // 오류 발생 시 출력
        }
        return connection;  // 연결 객체 반환
    }
}
