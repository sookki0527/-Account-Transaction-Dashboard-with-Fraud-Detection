create database IF NOT EXISTS transaction_db;
create database IF NOT EXISTS notification_db;
create database IF NOT EXISTS account_db;
CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY 'passwordForMySQL8.1';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
