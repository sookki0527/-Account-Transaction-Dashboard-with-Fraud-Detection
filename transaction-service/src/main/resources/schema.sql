DROP TABLE IF EXISTS transaction;

CREATE TABLE IF NOT EXISTS transaction (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           amount DECIMAL(10,2),
    date DATETIME,
    user_id VARCHAR(255),
    from_account_id BIGINT,
    to_account_id BIGINT,
    type VARCHAR(20)
    );
