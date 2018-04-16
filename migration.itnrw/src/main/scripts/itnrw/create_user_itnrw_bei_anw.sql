-- create migration user and grant permissions to it
-- the healthfactory  dababase user 
ALTER SESSION  SET "_ORACLE_SCRIPT"=true; 

-- DROP  USER ITNRW CASCADE ;

CREATE USER  beihanw IDENTIFIED BY beihanw ;
-- GRANT CONNECT, RESOURCE TO beihilfe;

GRANT CONNECT, RESOURCE TO beihanw;



-- ALTER USER beihanw QUOTA UNLIMITED ON USERS;

-- ALTER USER beihanw QUOTA UNLIMITED ON BEIHDAT default tablespace BEIHDAT;


