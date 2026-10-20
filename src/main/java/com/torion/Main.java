package com.torion;

import com.torion.dao.AllocationDao;
import com.torion.ui.AllocationMenu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            new AllocationDao().createTable();
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
            return;
        }
        new AllocationMenu().start();
    }
}