package com.expensetracker;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // A class to represent an Expense
    static class Expense {
        String category;
        double amount;

        public Expense(String category, double amount) {
            this.category = category;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Category: " + category + ", Amount: $" + amount;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Expense> expenses = loadExpenses(); // Load expenses from file
        double totalExpenses = calculateTotalExpenses(expenses);
        double budget = 0.0;

        System.out.println("Welcome to the Expense Tracker!");

        while (true) {
            System.out.println("\n=== Menu ===");
            System.out.println("1. Set Budget");
            System.out.println("2. Add Expense");
            System.out.println("3. View Expenses");
            System.out.println("4. Calculate Total Expenses");
            System.out.println("5. Save and Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline character

            if (choice == 1) {
                // Set the budget
                System.out.print("Enter your budget: ");
                budget = scanner.nextDouble();
                System.out.println("Budget set to: $" + budget);

            } else if (choice == 2) {
                // Add a new expense
                System.out.print("Enter expense category (e.g., Food, Rent): ");
                String category = scanner.nextLine();
                System.out.print("Enter expense amount: ");
                double amount = scanner.nextDouble();
                expenses.add(new Expense(category, amount));
                totalExpenses += amount;
                System.out.println("Expense added successfully!");

                // Check if total expenses exceed the budget
                if (budget > 0 && totalExpenses > budget) {
                    System.out.println("Warning: You have exceeded your budget!");
                }

            } else if (choice == 3) {
                // View all expenses
                System.out.println("\nYour Expenses:");
                if (expenses.isEmpty()) {
                    System.out.println("No expenses recorded yet!");
                } else {
                    for (Expense expense : expenses) {
                        System.out.println(expense);
                    }
                }

            } else if (choice == 4) {
                // Calculate total expenses
                System.out.println("Total Expenses: $" + totalExpenses);

            } else if (choice == 5) {
                // Save expenses to file and exit
                saveExpenses(expenses);
                System.out.println("Expenses saved. Goodbye!");
                break;

            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // Save expenses to a file
    private static void saveExpenses(ArrayList<Expense> expenses) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("expenses.txt"))) {
            for (Expense expense : expenses) {
                writer.println(expense.category + "," + expense.amount);
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    // Load expenses from a file
    private static ArrayList<Expense> loadExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("expenses.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String category = parts[0];
                double amount = Double.parseDouble(parts[1]);
                expenses.add(new Expense(category, amount));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous expenses found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
        return expenses;
    }

    // Calculate the total expenses
    private static double calculateTotalExpenses(ArrayList<Expense> expenses) {
        double total = 0.0;
        for (Expense expense : expenses) {
            total += expense.amount;
        }
        return total;
    }
}

