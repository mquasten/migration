-- create mapping user and grant permissions to it
-- the mappingdababase user 
ALTER SESSION  SET "_ORACLE_SCRIPT"=true; 

-- DROP  USER MAPPING CASCADE ;

CREATE USER MAPPING IDENTIFIED BY mapping;
GRANT CONNECT, RESOURCE, CREATE PUBLIC SYNONYM TO MAPPING;

ALTER USER "MAPPING" QUOTA UNLIMITED ON USERS;

