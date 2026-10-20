package com.torion.dao;

import com.torion.model.AllocationEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AllocationDao {

    public void createTable() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS allocations (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    student_name TEXT NOT NULL,
                    building TEXT NOT NULL,
                    room_no TEXT NOT NULL,
                    monthly_rent REAL NOT NULL
                )
                """;
        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement()) {
            st.execute(sql);
        }
    }

    public void insert(AllocationEntity e) throws SQLException {
        String sql = "INSERT INTO allocations (student_name, building, room_no, monthly_rent) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, e.studentName());
            ps.setString(2, e.building());
            ps.setString(3, e.roomNo());
            ps.setDouble(4, e.monthlyRent());
            ps.executeUpdate();
        }
    }

    public List<AllocationEntity> findAll() throws SQLException {
        List<AllocationEntity> list = new ArrayList<>();
        String sql = "SELECT id, student_name, building, room_no, monthly_rent "
                + "FROM allocations ORDER BY id";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new AllocationEntity(
                        rs.getInt("id"),
                        rs.getString("student_name"),
                        rs.getString("building"),
                        rs.getString("room_no"),
                        rs.getDouble("monthly_rent")));
            }
        }
        return list;
    }
}