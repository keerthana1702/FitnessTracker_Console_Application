package com.example.fitness_tracker;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;
import java.time.LocalDate;
public class ClientService {

 // Register new client
 public void register(Scanner scanner) {
     System.out.print("Enter Username: ");
     String username = scanner.nextLine();
     System.out.print("Enter Password: ");
     String password = scanner.nextLine();
     System.out.print("Enter Email: ");
     String email = scanner.nextLine();

     try (Connection connection = DBConnection.getConnection()) {
         String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
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

 // Login as a client
 public boolean login(Scanner scanner) {
     System.out.print("Enter Username: ");
     String username = scanner.nextLine();
     System.out.print("Enter Password: ");
     String password = scanner.nextLine();

     try (Connection connection = DBConnection.getConnection()) {
         String query = "SELECT * FROM users WHERE username = ? AND password = ?";
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

 // Logout the client
 public void logout() {
     System.out.println("Logged out successfully.");
 }
 public void viewFitnessPrograms() {
     try (Connection connection = DBConnection.getConnection()) {
         String query = "SELECT id, program_name, description, duration, category FROM fitness_program";
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query);

         System.out.println("\n--- List of Fitness Programs ---");
         while (resultSet.next()) {
             System.out.println("Program ID: " + resultSet.getInt("id"));
             System.out.println("Program Name: " + resultSet.getString("program_name"));
             System.out.println("Description: " + resultSet.getString("description"));
             System.out.println("Duration: " + resultSet.getString("duration"));
             System.out.println("Category: " + resultSet.getString("category"));
             System.out.println("------------------------------");
         }
     } catch (SQLException e) {
         System.out.println("Error fetching fitness programs: " + e.getMessage());
     }
 }
 public void viewProgramDetails(Scanner scanner) {
     System.out.print("Enter the Program ID to see the details: ");
     int programId = scanner.nextInt();
     scanner.nextLine(); 

     try (Connection connection = DBConnection.getConnection()) {
         String query = "SELECT fp.program_name, fp.description, fp.age_limit, fp.min_qualification, fp.price, fp.duration, c.username AS coach_name, c.email AS coach_email " +
                        "FROM fitness_program fp " +
                        "JOIN coach c ON fp.coach_id = c.id " +
                        "WHERE fp.id = ?";
         PreparedStatement statement = connection.prepareStatement(query);
         statement.setInt(1, programId);

         ResultSet resultSet = statement.executeQuery();

         if (resultSet.next()) {
             System.out.println("\n--- Program Details ---");
             System.out.println("Program Name: " + resultSet.getString("program_name"));
             System.out.println("Description: " + resultSet.getString("description"));
             System.out.println("Age Limit: " + resultSet.getString("age_limit"));
             System.out.println("Minimum Qualification: " + resultSet.getString("min_qualification"));
             System.out.println("Price: $" + resultSet.getDouble("price"));
             System.out.println("Duration: " + resultSet.getString("duration"));
             System.out.println("Coach Name: " + resultSet.getString("coach_name"));
             System.out.println("Coach Email: " + resultSet.getString("coach_email"));
             System.out.println("------------------------------");
         } else {
             System.out.println("No program found with the provided ID.");
         }
     } catch (SQLException e) {
         System.out.println("Error fetching program details: " + e.getMessage());
     }
 }
 private double calculateBMI(double weight, double height) {
     return weight / (height * height);  // BMI formula
 }
 public boolean checkQualification(Scanner scanner, int programId) {
     // Get client details
     System.out.print("Enter your Age: ");
     int age = scanner.nextInt();
     System.out.print("Enter your Weight (in kg): ");
     double weight = scanner.nextDouble();
     System.out.print("Enter your Height (in meters): ");
     double height = scanner.nextDouble();
     scanner.nextLine(); 

     // Calculate BMI
     double bmi = calculateBMI(weight, height);
     System.out.println("Your BMI is: " + String.format("%.2f", bmi));

     try (Connection connection = DBConnection.getConnection()) {
         String query = "SELECT age_limit, min_qualification FROM fitness_program WHERE id = ?";
         PreparedStatement statement = connection.prepareStatement(query);
         statement.setInt(1, programId);

         ResultSet resultSet = statement.executeQuery();

         if (resultSet.next()) {
             String ageLimit = resultSet.getString("age_limit");
             String minQualification = resultSet.getString("min_qualification");

             // Parse age limit range
             String[] ageRange = ageLimit.split("-");
             int minAge = Integer.parseInt(ageRange[0].trim());
             int maxAge = Integer.parseInt(ageRange[1].replaceAll("[^0-9]", "").trim());

             // Check if client qualifies
             if (age >= minAge && age <= maxAge) {
                 if ((minQualification.equalsIgnoreCase("Beginner") && bmi < 25) ||
                     (minQualification.equalsIgnoreCase("Intermediate") && bmi >= 25 && bmi < 30) ||
                     (minQualification.equalsIgnoreCase("Advanced") && bmi >= 30)) {
                     System.out.println("You qualify for this program!");
                     return true;
                 } else {
                     System.out.println("You do not meet the minimum qualification for this program based on BMI.");
                 }
             } else {
                 System.out.println("You do not meet the age requirements for this program.");
             }
         } else {
             System.out.println("No program found with the provided ID.");
         }
     } catch (SQLException e) {
         System.out.println("Error fetching program details: " + e.getMessage());
     }
     return false;
 }

 public void applyToProgram(Scanner scanner, int clientId) {
     System.out.print("Enter the Program ID to apply: ");
     int programId = scanner.nextInt();
     scanner.nextLine();

     // Check if client qualifies for the program
     if (!checkQualification(scanner, programId)) {
         System.out.println("You do not qualify for this program. Application cannot proceed.");
         return;
     }

     try (Connection connection = DBConnection.getConnection()) {
         // Check if the client has already applied for this program
         String checkQuery = "SELECT * FROM applications WHERE user_id = ? AND program_id = ?";
         PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
         checkStatement.setInt(1, clientId);
         checkStatement.setInt(2, programId);
         ResultSet checkResult = checkStatement.executeQuery();

         if (checkResult.next()) {
             System.out.println("You have already applied for this program.");
             return;
         }

         // Apply for the program
         String query = "INSERT INTO applications (user_id, program_id, application_date, status) VALUES (?, ?, ?, ?)";
         PreparedStatement statement = connection.prepareStatement(query);
         statement.setInt(1, clientId);
         statement.setInt(2, programId);
         statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
         statement.setString(4, "Pending");

         int rowsInserted = statement.executeUpdate();
         if (rowsInserted > 0) {
             System.out.println("Application submitted successfully! Your application is pending review.");
         }
     } catch (SQLException e) {
         System.out.println("Error while applying for the program: " + e.getMessage());
     }
 }



 
}
