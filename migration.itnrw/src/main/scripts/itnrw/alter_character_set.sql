-- sys@oracle as sysdba  
-- zeichensatz aendern 

shutdown immediate;
       
startup mount;
       
alter system enable restricted session;
alter system set job_queue_processes=0;
       
alter database open;
       
-- alter database character set internal_use WE8MSWIN1252 ;
alter database national character set internal_use UTF8 ;
       
alter database national character set internal_use AL16UTF16 ;
       
shutdown immediate;
startup;
       
select * from nls_database_parameters where parameter like '%CHARACTERSET%';
       

