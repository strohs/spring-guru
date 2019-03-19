CREATE USER 'springstudent'@'localhost' IDENTIFIED BY 'springstudent';
CREATE USER 'springstudent'@'%' IDENTIFIED BY 'springstudent';

GRANT ALL PRIVILEGES ON web_customer_tracker.* TO 'springstudent'@'localhost';
GRANT ALL PRIVILEGES ON web_customer_tracker.* TO 'springstudent'@'%';

