package com.example.fitness_tracker;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CoachServiceTest {

    private CoachService coachService;

    @BeforeEach
    void setUp() {
        coachService = new CoachService();
    }

    @Test
    void testCreateFitnessProgramSuccess() throws SQLException {
        
        String userInput = "Yoga\nGreat for flexibility\n4 weeks\nGeneral\n18-40\nBeginner\n199.99\n";
        Scanner scanner = new Scanner(userInput);

        assertDoesNotThrow(() -> coachService.createFitnessProgram(scanner, 1));  
    }

    @Test
    void testViewApplicationsForPrograms() {
        assertDoesNotThrow(() -> coachService.viewApplicationsForPrograms());
    }

    @Test
    void testAcceptOrRejectApplication() throws SQLException {
        
        String userInput = "1\nAccepted\n";  
        Scanner scanner = new Scanner(userInput);

        assertDoesNotThrow(() -> coachService.updateApplicationStatus(scanner));
    }
    @Test
    void testCreateProgramWithNegativePrice() {
        String userInput = "Yoga\nGreat for flexibility\n4 weeks\nGeneral\n18-40\nBeginner\n-199.99\n";  
        Scanner scanner = new Scanner(userInput);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> coachService.createFitnessProgram(scanner, 1));
        assertEquals("Price cannot be negative", exception.getMessage());
    }
    
    @Test
    void testCreateProgramWithEmptyName() {
        String userInput = "\nGreat for flexibility\n4 weeks\nGeneral\n18-40\nBeginner\n199.99\n";  
        Scanner scanner = new Scanner(userInput);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> coachService.createFitnessProgram(scanner, 1));
        assertEquals("Program name cannot be empty", exception.getMessage());
    }

    @Test
    void testViewApplicationsForProgramWithNoApplications() {
        
        assertDoesNotThrow(() -> {
            coachService.viewApplicationsForPrograms();
        });
    }

    @Test
    void testAcceptApplicationWithInvalidStatus() {
        String userInput = "1\nInvalidStatus\n";  
        Scanner scanner = new Scanner(userInput);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> coachService.updateApplicationStatus(scanner));
        assertEquals("Invalid status. Please enter 'Accepted' or 'Rejected'.", exception.getMessage());
    }
}
