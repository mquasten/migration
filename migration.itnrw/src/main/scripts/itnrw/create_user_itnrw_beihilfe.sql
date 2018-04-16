-- create migration user and grant permissions to it
-- the healthfactory  dababase user 
ALTER SESSION  SET "_ORACLE_SCRIPT"=true; 

-- DROP  USER ITNRW CASCADE ;

CREATE USER beihilfe IDENTIFIED BY beihilfe ;
-- GRANT CONNECT, RESOURCE TO beihilfe;

GRANT CONNECT, RESOURCE, CREATE VIEW TO beihilfe;



--ALTER USER beihilfe QUOTA UNLIMITED ON USERS;


ALTER USER beihilfe QUOTA UNLIMITED ON BEIHDAT default tablespace BEIHDAT;
