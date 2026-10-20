package com.torion.ui;

import com.torion.dao.AllocationDao;
import com.torion.model.AllocationEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AllocationMenu {
    private final AllocationDao dao = new AllocationDao();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Enter choice: ");
            switch (sc.nextLine().trim()) {
                case "1" -> addAllocation();
                case "2" -> viewAllocations();
                case "3" -> {
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    private void printMenu() {
        String line = "=".repeat(52);
        System.out.println("\n" + line);
        System.out.println("        STUDENT DORMITORY BED ALLOCATION");
        System.out.println(line);
        System.out.println("[1] Add New Allocation");
        System.out.println("[2] View All Allocations");
        System.out.println("[3] Exit System");
        System.out.println(line);
    }

    private void addAllocation() {
        System.out.println("\n--- ADD NEW BED ALLOCATION ---");
        try {
            System.out.print("Enter Student Name: ");
            String name = sc.nextLine().trim();
            System.out.print("Enter Building Name: ");
            String building = sc.nextLine().trim();
            System.out.print("Enter Room Number: ");
            String room = sc.nextLine().trim();
            System.out.print("Enter Monthly Rent: ");
            double rent = Double.parseDouble(sc.nextLine().trim());

            if (name.isEmpty() || building.isEmpty() || room.isEmpty()) {
                System.out.println("ERROR: Fields cannot be empty. Record not saved.");
                return;
            }
            if (rent < 0) {
                System.out.println("ERROR: Rent cannot be negative. Record not saved.");
                return;
            }

            dao.insert(new AllocationEntity(0, name, building, room, rent));
            System.out.println("\nSUCCESS: Dorm bed allocated successfully!");
        } catch (NumberFormatException ex) {
            System.out.println("ERROR: Invalid rent amount. Record not saved.");
        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        }
    }

    private void viewAllocations() {
        try {
            List<AllocationEntity> records = dao.findAll();
            String eq = "=".repeat(84);
            String border = "+----+--------------------+------------+--------------+----------------+";

            System.out.println("\n" + eq);
            System.out.println("                         DORMITORY BED ALLOCATIONS");
            System.out.println(eq);
            System.out.println(border);
            System.out.printf("| %-2s | %-18s | %-10s | %-12s | %-14s |%n",
                    "ID", "STUDENT NAME", "BUILDING", "ROOM NO", "MONTHLY RENT");
            System.out.println(border);
            for (AllocationEntity e : records) {
                System.out.printf("| %-2d | %-18s | %-10s | %-12s | %-14.2f |%n",
                        e.id(), e.studentName(), e.building(), e.roomNo(), e.monthlyRent());
            }
            System.out.println(border);
            System.out.println("Total Records Displayed: " + records.size());
        } catch (SQLException ex) {
            System.out.println("Database error: " + ex.getMessage());
        }
    }
}