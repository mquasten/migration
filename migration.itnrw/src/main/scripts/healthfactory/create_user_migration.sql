-- create migration user and grant permissions to it
-- the healthfactory  dababase user 
ALTER SESSION  SET "_ORACLE_SCRIPT"=true; 

-- DROP  USER MIGRATION CASCADE ;

CREATE USER MIGRATION IDENTIFIED BY migration ;
GRANT CONNECT, RESOURCE TO migration;

ALTER USER "MIGRATION" QUOTA UNLIMITED ON USERS;

