CREATE DATABASE  IF NOT EXISTS `sfg_dev`;
CREATE DATABASE  IF NOT EXISTS `sfg_prod`;

CREATE USER 'sfg_dev_user'@'localhost' IDENTIFIED BY 'devpassword';
CREATE USER 'sfg_prod_user'@'localhost' IDENTIFIED BY 'prodpassword';
#allow user to connect to mysql from any host
CREATE USER 'sfg_dev_user'@'%' IDENTIFIED BY 'devpassword';
#allow user to connect to mysql from any host
CREATE USER 'sfg_prod_user'@'%' IDENTIFIED BY 'prodpassword';

GRANT SELECT,INSERT,UPDATE,DELETE ON sfg_dev.* TO 'sfg_dev_user'@'localhost';
GRANT SELECT,INSERT,UPDATE,DELETE ON sfg_dev.* TO 'sfg_dev_user'@'%';

GRANT SELECT,INSERT,UPDATE,DELETE ON sfg_prod.* TO 'sfg_prod_user'@'localhost';
GRANT SELECT,INSERT,UPDATE,DELETE ON sfg_prod.* TO 'sfg_prod_user'@'%';

## to view grants for a user
# SHOW GRANTS FOR 'sfg_dev_user';

## to list users
# SELECT User, Host FROM mysql.user