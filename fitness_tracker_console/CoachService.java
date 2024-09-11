package com.example.fitness_tracker;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.*;
public class CoachService {
	public static void register(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        try (Connection connection = DBConnection.getConnection()) {
            String query = "INSERT INTO coach (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Registration successful! You can now log in.");
            }
        } catch (SQLException e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }
    public boolean login(String username, String password) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM coach WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                System.out.println("Login successful! Welcome " + username);
                return true;
            } else {
                System.out.println("Invalid credentials. Please try again.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return false;
        }
    }

    public void logout() {
        System.out.println("Logged out successfully.");
    }


public void createFitnessProgram(Scanner scanner, int coachId) {
    System.out.print("Enter Program Name: ");
    String programName = scanner.nextLine();
    System.out.print("Enter Program Description: ");
    String description = scanner.nextLine();
    System.out.print("Enter Duration (e.g., '4 weeks'): ");
    String duration = scanner.nextLine();
    System.out.print("Enter Category (Men, Women, Children, Senior Citizens): ");
    String category = scanner.nextLine();

    try (Connection connection = DBConnection.getConnection()) {
        String query = "INSERT INTO fitness_program (program_name, description, duration, category, coach_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, programName);
        statement.setString(2, description);
        statement.setString(3, duration);
        statement.setString(4, category);
        statement.setInt(5, coachId);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Fitness program created successfully!");
        }
    } catch (SQLException e) {
        System.out.println("Error creating fitness program: " + e.getMessage());
    }
}

// Read (View) all fitness programs
public void viewFitnessPrograms() {
    try (Connection connection = DBConnection.getConnection()) {
        String query = "SELECT * FROM fitness_program";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id"));
            System.out.println("Program Name: " + resultSet.getString("program_name"));
            System.out.println("Description: " + resultSet.getString("description"));
            System.out.println("Duration: " + resultSet.getString("duration"));
            System.out.println("Category: " + resultSet.getString("category"));
            System.out.println("Coach ID: " + resultSet.getInt("coach_id"));
            System.out.println("------------------------");
        }
    } catch (SQLException e) {
        System.out.println("Error viewing fitness programs: " + e.getMessage());
    }
}

// Update a fitness program
public void updateFitnessProgram(Scanner scanner) {
    System.out.print("Enter the Program ID to update: ");
    int programId = scanner.nextInt();
    scanner.nextLine(); // consume newline
    System.out.print("Enter New Program Name: ");
    String programName = scanner.nextLine();
    System.out.print("Enter New Program Description: ");
    String description = scanner.nextLine();
    System.out.print("Enter New Duration (e.g., '4 weeks'): ");
    String duration = scanner.nextLine();
    System.out.print("Enter New Category (Men, Women, Children, Senior Citizens): ");
    String category = scanner.nextLine();

    try (Connection connection = DBConnection.getConnection()) {
        String query = "UPDATE fitness_program SET program_name = ?, description = ?, duration = ?, category = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, programName);
        statement.setString(2, description);
        statement.setString(3, duration);
        statement.setString(4, category);
        statement.setInt(5, programId);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Fitness program updated successfully!");
        }
    } catch (SQLException e) {
        System.out.println("Error updating fitness program: " + e.getMessage());
    }
}

// Delete a fitness program
public void deleteFitnessProgram(Scanner scanner) {
    System.out.print("Enter the Program ID to delete: ");
    int programId = scanner.nextInt();

    try (Connection connection = DBConnection.getConnection()) {
        String query = "DELETE FROM fitness_program WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, programId);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Fitness program deleted successfully!");
        }
    } catch (SQLException e) {
        System.out.println("Error deleting fitness program: " + e.getMessage());
    }
}
public void createDietPlan(Scanner scanner) {
    System.out.print("Enter Program ID to associate with this diet plan: ");
    int programId = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    System.out.print("Enter Meal Time (Breakfast, Lunch, Dinner, Snack): ");
    String mealTime = scanner.nextLine();
    System.out.print("Enter Food Items (comma-separated): ");
    String foodItems = scanner.nextLine();
    System.out.print("Enter Total Calories: ");
    int calories = scanner.nextInt();

    try (Connection connection = DBConnection.getConnection()) {
        String query = "INSERT INTO diet_plan (program_id, meal_time, food_items, calories) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, programId);
        statement.setString(2, mealTime);
        statement.setString(3, foodItems);
        statement.setInt(4, calories);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Diet plan created successfully!");
        }
    } catch (SQLException e) {
        System.out.println("Error creating diet plan: " + e.getMessage());
    }
}

// Read (View) all diet plans for a specific fitness program
public void viewDietPlans(Scanner scanner) {
    System.out.print("Enter Program ID to view associated diet plans: ");
    int programId = scanner.nextInt();

    try (Connection connection = DBConnection.getConnection()) {
        String query = "SELECT * FROM diet_plan WHERE program_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, programId);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.println("Diet Plan ID: " + resultSet.getInt("id"));
            System.out.println("Meal Time: " + resultSet.getString("meal_time"));
            System.out.println("Food Items: " + resultSet.getString("food_items"));
            System.out.println("Calories: " + resultSet.getInt("calories"));
            System.out.println("------------------------");
        }
    } catch (SQLException e) {
        System.out.println("Error viewing diet plans: " + e.getMessage());
    }
}

// Update a diet plan
public void updateDietPlan(Scanner scanner) {
    System.out.print("Enter Diet Plan ID to update: ");
    int dietPlanId = scanner.nextInt();
    scanner.nextLine(); 

    System.out.print("Enter New Meal Time (Breakfast, Lunch, Dinner, Snack): ");
    String mealTime = scanner.nextLine();
    System.out.print("Enter New Food Items (comma-separated): ");
    String foodItems = scanner.nextLine();
    System.out.print("Enter New Total Calories: ");
    int calories = scanner.nextInt();

    try (Connection connection = DBConnection.getConnection()) {
        String query = "UPDATE diet_plan SET meal_time = ?, food_items = ?, calories = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, mealTime);
        statement.setString(2, foodItems);
        statement.setInt(3, calories);
        statement.setInt(4, dietPlanId);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Diet plan updated successfully!");
        }
    } catch (SQLException e) {
        System.out.println("Error updating diet plan: " + e.getMessage());
    }
}

// Delete a diet plan
public void deleteDietPlan(Scanner scanner) {
    System.out.print("Enter Diet Plan ID to delete: ");
    int dietPlanId = scanner.nextInt();

    try (Connection connection = DBConnection.getConnection()) {
        String query = "DELETE FROM diet_plan WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, dietPlanId);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Diet plan deleted successfully!");
        }
    } catch (SQLException e) {
        System.out.println("Error deleting diet plan: " + e.getMessage());
    }
}
public void viewApplicationsForPrograms() {
    try (Connection connection = DBConnection.getConnection()) {
        String query = "SELECT a.id, u.username, f.program_name, a.application_date, a.status " +
                       "FROM applications a " +
                       "JOIN users u ON a.user_id = u.id " +
                       "JOIN fitness_program f ON a.program_id = f.id";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            System.out.println("Application ID: " + resultSet.getInt("id"));
            System.out.println("User: " + resultSet.getString("username"));
            System.out.println("Program: " + resultSet.getString("program_name"));
            System.out.println("Application Date: " + resultSet.getDate("application_date"));
            System.out.println("Status: " + resultSet.getString("status"));
            System.out.println("------------------------");
        }
    } catch (SQLException e) {
        System.out.println("Error viewing applications: " + e.getMessage());
    }
}


// Accept or reject an application
public void updateApplicationStatus(Scanner scanner) {
    System.out.print("Enter Application ID to update: ");
    int applicationId = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    System.out.print("Enter new status (Accepted/Rejected): ");
    String status = scanner.nextLine();

    if (!status.equalsIgnoreCase("Accepted") && !status.equalsIgnoreCase("Rejected")) {
        System.out.println("Invalid status. Please enter 'Accepted' or 'Rejected'.");
        return;
    }

    try (Connection connection = DBConnection.getConnection()) {
        String query = "UPDATE applications SET status = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, status);
        statement.setInt(2, applicationId);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Application status updated to " + status + " successfully!");
        } else {
            System.out.println("No application found with the provided ID.");
        }
    } catch (SQLException e) {
        System.out.println("Error updating application status: " + e.getMessage());
    }
}

}
