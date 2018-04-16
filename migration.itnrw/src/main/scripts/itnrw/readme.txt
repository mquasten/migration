Einspielen itnrw DAten in lokale Oracledatenbank (12c, unter windows bei mir)

1) Ändern des Encodings.

sqlplus sys as sysdba
@alter_character_set.sql


2) Anlegen von 2 Tablespaces

sqlpus systen@oracle  (Meine Datenbank heißt oracle)
@create_tablespace.sql

Anlegen der 3 User, Rechte, Quotas

sqlpus systen@oracle
@create_all_itnrw_users.sql


Dann in _Params den Datenbanknamen ändern Zeile:

rem *************************************************
rem 	User-Datenbank
rem *************************************************
set database=oracle

Dump einspielen:

_StartImport


Danach das Encoding auf die Werte der Healthfactory setzen (eine Zeile ändern in alter_character_set.sql);

alter database national character set internal_use AL16UTF16 ;

sqlplus sys as sysdba
@alter_character_set.sql


