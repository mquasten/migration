-- create migration user and grant permissions to it
-- the healthfactory  dababase user 
ALTER SESSION  SET "_ORACLE_SCRIPT"=true; 

-- DROP  USER ITNRW CASCADE ;

CREATE USER admbeih IDENTIFIED BY admbeih ;
-- GRANT CONNECT, RESOURCE TO beihilfe;

GRANT CONNECT, RESOURCE TO admbeih;



-- ALTER USER admbeih QUOTA UNLIMITED ON USERS;

ALTER USER admbeih QUOTA UNLIMITED ON ADM_BEIHDAT default tablespace ADM_BEIHDAT;






