package com.example.gpa_calculator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    private static final String DB_URL = "jdbc:sqlite:gpa_database.db";

    public DatabaseHandler() {
        createTable();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS gpa_history (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "student_name TEXT NOT NULL," +
                "gpa REAL NOT NULL," +
                "timestamp TEXT NOT NULL" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertRecord(GpaRecord record) {
        String sql = "INSERT INTO gpa_history(student_name, gpa, timestamp) VALUES(?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, record.getStudentName());
            pstmt.setDouble(2, record.getGpa());
            pstmt.setString(3, record.getTimestamp());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<GpaRecord> getAllRecords() {
        List<GpaRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM gpa_history ORDER BY id DESC";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                records.add(new GpaRecord(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getDouble("gpa"),
                        rs.getString("timestamp")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return records;
    }

    public void updateRecord(GpaRecord record) {
        String sql = "UPDATE gpa_history SET student_name = ?, gpa = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, record.getStudentName());
            pstmt.setDouble(2, record.getGpa());
            pstmt.setInt(3, record.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteRecord(int id) {
        String sql = "DELETE FROM gpa_history WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
