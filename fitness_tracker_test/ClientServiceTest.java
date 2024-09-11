package com.example.fitness_tracker;

import org.junit.jupiter.api.*;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {

 private ClientService clientService;

 @BeforeEach
 void setUp() {
     clientService = new ClientService();
 }

 @Test
 void testRegisterSuccess() throws SQLException {
     
     String userInput = "testUser\npassword123\ntestuser@example.com\n";
     Scanner scanner = new Scanner(userInput);
     
     assertDoesNotThrow(() -> clientService.register(scanner));
 }

 @Test
 void testRegisterFailureDuplicateUser() throws SQLException {
    
     String userInput1 = "duplicateUser\npassword123\nduplicateuser@example.com\n";
     Scanner scanner1 = new Scanner(userInput1);
     clientService.register(scanner1);
     
     
     String userInput2 = "duplicateUser\npassword123\nduplicateuser@example.com\n";
     Scanner scanner2 = new Scanner(userInput2);
     
     Exception exception = assertThrows(SQLException.class, () -> clientService.register(scanner2));
     assertTrue(exception.getMessage().contains("Duplicate entry"));
 }

 @Test
 void testLoginSuccess() throws SQLException {
     
     String userInput = "testUser\npassword123\n";
     Scanner scanner = new Scanner(userInput);

     boolean isLoggedIn = clientService.login(scanner);
     assertTrue(isLoggedIn);
 }

 @Test
 void testLoginFailure() throws SQLException {
     
     String userInput = "nonExistentUser\nwrongPassword\n";
     Scanner scanner = new Scanner(userInput);

     boolean isLoggedIn = clientService.login(scanner);
     assertFalse(isLoggedIn);
 }

 @Test
 void testViewFitnessPrograms() {
     assertDoesNotThrow(() -> clientService.viewFitnessPrograms());
 }

 @Test
 void testApplyToProgramSuccess() throws SQLException {
    
     String userInput = "1\n"; 
     Scanner scanner = new Scanner(userInput);

     assertDoesNotThrow(() -> clientService.applyToProgram(scanner, 1));
 }
 @Test
 void testRegisterWithEmptyUsername() {
     String userInput = "\npassword123\ntest@example.com\n";  
     Scanner scanner = new Scanner(userInput);

     Exception exception = assertThrows(IllegalArgumentException.class, () -> clientService.register(scanner));
     assertEquals("Username cannot be empty", exception.getMessage());
 }

 @Test
 void testRegisterWithInvalidEmail() {
     String userInput = "testUser\npassword123\ninvalidEmail\n";
     Scanner scanner = new Scanner(userInput);

     Exception exception = assertThrows(IllegalArgumentException.class, () -> clientService.register(scanner));
     assertEquals("Invalid email format", exception.getMessage());
 }

 @Test
 void testLoginWithNullUsername() {
     String userInput = "\npassword123\n"; 
     Scanner scanner = new Scanner(userInput);

     Exception exception = assertThrows(IllegalArgumentException.class, () -> clientService.login(scanner));
     assertEquals("Username cannot be null or empty", exception.getMessage());
 }

 @Test
 void testApplyToNonExistentProgram() {
     String userInput = "999\n";  
     Scanner scanner = new Scanner(userInput);

     Exception exception = assertThrows(SQLException.class, () -> clientService.applyToProgram(scanner, 1));
     assertEquals("Program not found", exception.getMessage());
 }

}
