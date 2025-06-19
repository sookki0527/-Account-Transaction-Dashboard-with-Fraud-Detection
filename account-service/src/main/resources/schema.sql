CREATE TABLE IF NOT EXISTS account (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       user_id VARCHAR(255),
    account_type VARCHAR(50),
    account_number VARCHAR(20),
    balance DECIMAL(10,2),
    currency VARCHAR(10)
    );
