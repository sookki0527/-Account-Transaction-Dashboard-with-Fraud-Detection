package org.example.generator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Random;

@Component
public class RandomDataGenerator {

    private final JdbcTemplate jdbcTemplate;
    private final Random random;
    String[] dates = {
            "2025-05-01 02:30:00",
            "2025-06-01 10:30:00",
            "2025-07-01 14:45:00",
            "2025-08-01 08:15:00",
            "2025-09-01 18:00:00"
    };

    public RandomDataGenerator(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.random = new Random();
    }

    public void generateRandomData(int n) {
        for (int i = 0; i < n; i++) {
            double amount = 100 + (500 - 100) * random.nextDouble();
            String date = dates[random.nextInt(dates.length)];
            int fromAccountId = random.nextInt(10) + 1000;  // Random from_account_id between 1000 and 1010
            int toAccountId = random.nextInt(10) + 1010;  // Random to_account_id between 1010 and 1020
            String type = random.nextBoolean() ? "TRANSFER" : random.nextBoolean() ? "WITHDRAW" : "DEPOSIT";  // Random type
            String userId = "user1";  // Random user_id using a simple number (e.g., user1, user2, ...)
            double randomChance = random.nextDouble();
            if (randomChance < 0.05) {
                amount = 1000 + (random.nextDouble() * 10000);
                fromAccountId = random.nextInt(20000) + 10000;
                toAccountId = random.nextInt(20000) + 10000;
            } else if (randomChance < 0.1) {
                amount = (random.nextDouble() * 10);
                fromAccountId = random.nextInt(100);
                toAccountId = random.nextInt(200) + 100;
            }

            String sql = "INSERT INTO transaction (amount, date, from_account_id, to_account_id, type, user_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            jdbcTemplate.update(sql, amount, date, fromAccountId, toAccountId, type, userId);
        }
    }
}
