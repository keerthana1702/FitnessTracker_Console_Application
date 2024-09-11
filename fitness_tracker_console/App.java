package com.example.fitness_tracker;

import java.util.Scanner;

public class App {
 private static CoachService coachService = new CoachService();
 private static ClientService clientService = new ClientService();
 private static boolean isCoachLoggedIn = false;
 private static boolean isClientLoggedIn = false;
 private static int loggedInCoachId = -1; 
 private static int loggedInClientId = -1; 

 public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);
     int choice;

     while (true) {
         if (!isCoachLoggedIn && !isClientLoggedIn) {
             System.out.println("\n--- Fitness Tracker Application ---");
             System.out.println("1. Register as Coach");
             System.out.println("2. Login as Coach");
             System.out.println("3. Register as Client");
             System.out.println("4. Login as Client");
             System.out.println("5. Exit");
             System.out.print("Enter your choice: ");
             choice = scanner.nextInt();
             scanner.nextLine();
             switch (choice) {
                 
                 case 1:
                     //coach register
                     CoachService.register(scanner);
                     break;
                 case 2:
                	 //coach login
                     System.out.print("Enter Username: ");
                     String coachUsername = scanner.nextLine();
                     System.out.print("Enter Password: ");
                     String coachPassword = scanner.nextLine();
                     isCoachLoggedIn = coachService.login(coachUsername, coachPassword);
                     if (isCoachLoggedIn) {
                         loggedInCoachId = 1; 
                     }
                     break;
                 case 3:
                     // Client Register
                     clientService.register(scanner);
                     break;
                 case 4:
                     // Client Login 
                     isClientLoggedIn = clientService.login(scanner);
                     if (isClientLoggedIn) {
                         loggedInClientId = 1;
                     }
                     break;
                 case 5:
                     // Exit Option
                     System.out.println("Exiting the application.");
                     scanner.close();
                     return;  // Exit the application
                 default:
                     System.out.println("Invalid choice. Please try again.");
             }
         } else if (isCoachLoggedIn) {
             // Menu for Coach
             System.out.println("\n--- Fitness Tracker Application (Coach Logged In) ---");
             System.out.println("1. Create Fitness Program");
             System.out.println("2. View All Fitness Programs");
             System.out.println("3. Update Fitness Program");
             System.out.println("4. Delete Fitness Program");
             System.out.println("5. Create Diet Plan for a Program");
             System.out.println("6. View Diet Plans for a Program");
             System.out.println("7. Update Diet Plan");
             System.out.println("8. Delete Diet Plan");
             System.out.println("9. View Applications for Fitness Programs");
             System.out.println("10. Accept/Reject Applications");
             System.out.println("11. Logout");
             System.out.print("Enter your choice: ");
             choice = scanner.nextInt();
             scanner.nextLine(); 

             switch (choice) {
                 case 1:
                     // Create Fitness Program
                     coachService.createFitnessProgram(scanner, loggedInCoachId);
                     break;
                 case 2:
                     // View All Fitness Programs
                     coachService.viewFitnessPrograms();
                     break;
                 case 3:
                     // Update Fitness Program
                     coachService.updateFitnessProgram(scanner);
                     break;
                 case 4:
                     // Delete Fitness Program
                     coachService.deleteFitnessProgram(scanner);
                     break;
                 case 5:
                     // Create Diet Plan
                     coachService.createDietPlan(scanner);
                     break;
                 case 6:
                     // View Diet Plans
                     coachService.viewDietPlans(scanner);
                     break;
                 case 7:
                     // Update Diet Plan
                     coachService.updateDietPlan(scanner);
                     break;
                 case 8:
                     // Delete Diet Plan
                     coachService.deleteDietPlan(scanner);
                     break;
                 case 9:
                     // View Applications for Fitness Programs
                     coachService.viewApplicationsForPrograms();
                     break;
                 case 10:
                     // Accept/Reject Applications
                     coachService.updateApplicationStatus(scanner);
                     break;
                 case 11:
                     // Logout Option
                     coachService.logout();
                     isCoachLoggedIn = false;  
                     loggedInCoachId = -1;  
                     break;
                 default:
                     System.out.println("Invalid choice. Please try again.");
             }
         } else if (isClientLoggedIn) {
             //  Menu for Client
             System.out.println("\n--- Fitness Tracker Application (Client Logged In) ---");
             System.out.println("1. View Fitness Programs");
             System.out.println("2. View Program Details");
             System.out.println("3. Check Qualification for a Program and Apply");
             System.out.println("4. Logout");
             System.out.print("Enter your choice: ");
             choice = scanner.nextInt();
             scanner.nextLine(); 
             switch (choice) {
                 case 1:
                     // View Fitness Programs
                     clientService.viewFitnessPrograms();
                     break;
                 case 2:
                     // View Program Details
                     clientService.viewProgramDetails(scanner);
                     break;
                 case 3:
                     // Check Qualification and Apply for a Program
                     clientService.applyToProgram(scanner, loggedInClientId);
                     break;
                 case 4:
                     // Logout Option for Client
                     clientService.logout();
                     isClientLoggedIn = false;  
                     loggedInClientId = -1;  
                     break;
                 default:
                     System.out.println("Invalid choice. Please try again.");
             }
         }
     }
 }


}
