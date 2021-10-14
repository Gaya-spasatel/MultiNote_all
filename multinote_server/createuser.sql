CREATE USER 'multinote'@'localhost' IDENTIFIED BY 'multinotepassword';
GRANT CREATE, SELECT, INSERT, UPDATE, DELETE ON multinote.* TO 'multinote'@'localhost';
FLUSH PRIVILEGES;