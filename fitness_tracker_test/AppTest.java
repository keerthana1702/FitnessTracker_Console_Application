package com.example.fitness_tracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;



class AppTest {

    @Test
    void testMainMethod() {
        
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
    @Test
    void testMainWithoutExceptions() {
        
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
    
}