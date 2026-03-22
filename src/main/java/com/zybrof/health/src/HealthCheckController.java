package com.zybrof.health.src;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for application health monitoring.
 * It provides an endpoint to check the service status and database connectivity.
 */
@RestController
public class HealthCheckController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Health check endpoint.
     * Checks if the application is running and if it can execute a simple query on the database.
     *
     * @return A status message indicating the health of the system.
     */
    @GetMapping("/health")
    public String healthCheck() {
        try {
            // Execute a simple query to verify database connectivity
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            if (result != null && result == 1) {
                return "UP: System is running and database is accessible.";
            } else {
                return "DOWN: Database connectivity issue.";
            }
        } catch (Exception e) {
            return "DOWN: Error during health check: " + e.getMessage();
        }
    }
}
