package com.example.productcrud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseMigrationConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrationConfig.class);

    @Bean
    CommandLineRunner migrateProfileImageUrlColumn(DataSource dataSource) {
        return args -> {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            try {
                String checkSql = "SELECT data_type FROM information_schema.columns " +
                        "WHERE table_name = 'users' AND column_name = 'profile_image_url'";
                
                String currentType;
                try {
                    currentType = jdbcTemplate.queryForObject(checkSql, String.class);
                } catch (Exception e) {
                    logger.warn("Could not determine column type: {}", e.getMessage());
                    return;
                }
                logger.info("Current profile_image_url column type: {}", currentType);
                
                if ("text".equals(currentType)) {
                    logger.info("profile_image_url column is already TEXT, skipping migration");
                    return;
                }
                
                logger.info("Migrating profile_image_url from {} to TEXT...", currentType);
                
                // Try ALTER TABLE first
                try {
                    if ("oid".equals(currentType)) {
                        // For OID (Large Object), need to cast properly
                        jdbcTemplate.execute("ALTER TABLE users ALTER COLUMN profile_image_url TYPE TEXT USING ''");
                    } else {
                        jdbcTemplate.execute("ALTER TABLE users ALTER COLUMN profile_image_url TYPE TEXT");
                    }
                    logger.info("Successfully migrated profile_image_url to TEXT via ALTER TABLE");
                } catch (Exception alterEx) {
                    logger.warn("ALTER TABLE failed: {}, trying alternative approach", alterEx.getMessage());
                    // Alternative: backup data, drop and recreate column
                    try {
                        jdbcTemplate.execute("ALTER TABLE users ADD COLUMN profile_image_url_new TEXT DEFAULT ''");
                        jdbcTemplate.execute("UPDATE users SET profile_image_url_new = COALESCE(profile_image_url::TEXT, '') WHERE profile_image_url IS NOT NULL");
                        jdbcTemplate.execute("ALTER TABLE users DROP COLUMN profile_image_url");
                        jdbcTemplate.execute("ALTER TABLE users RENAME COLUMN profile_image_url_new TO profile_image_url");
                        logger.info("Successfully migrated profile_image_url to TEXT via column replacement");
                    } catch (Exception backupEx) {
                        logger.error("Both migration approaches failed: {}", backupEx.getMessage());
                    }
                }
            } catch (Exception e) {
                logger.error("Migration failed: {}", e.getMessage());
            }
        };
    }
}