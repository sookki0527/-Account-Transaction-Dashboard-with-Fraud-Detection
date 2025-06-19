ALTER TABLE account AUTO_INCREMENT = 1000;
DELETE FROM account;
INSERT INTO account (user_id, account_type, account_number, balance, currency) VALUES
                                                                                   ('user1', 'Checking', '1234567890', 1200.50, 'USD'),
                                                                                   ('user1', 'Savings',  '9876543210', 3500.00, 'USD'),
                                                                                   ('user2', 'Checking', '1122334455', 200.00,  'USD'),
                                                                                   ('user2', 'Savings',  '5566778899', 1800.00, 'USD'),
                                                                                   ('user3', 'Checking', '1122112211', 750.00, 'USD'),
                                                                                   ('user4', 'Savings',  '6677889900', 9200.00, 'USD');
