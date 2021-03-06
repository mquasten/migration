CREATE TABLE PM_CONTRACT (
MANDATOR	NUMBER,
DATASTATE	NUMBER,
PROCESSNR		NUMBER,
HISTNR		NUMBER,
RPROCESSNR	NUMBER,
DOP		DATE,
DOR		DATE,
IND		DATE,
TERMINATIONFLAG	NUMBER,
PRIONR		NUMBER,
CONTRACT_NUMBER	NUMBER,
REASON_FOR_CHANGE NUMBER,
BEGIN_OF_CONTRACT NUMBER,
TERMINATION_DATE DATE,
RISK_CARRIER NUMBER,
POLICY_NUMBER CHARACTER(50),
MEMBER_OF_STAFF NUMBER,
COLLECTIVE_CONTRACT_NUMBER NUMBER,
INTERNAL_NUMBER_COLL_CONTRACT CHARACTER(50),
POLICY_CONFIRMATION_FLAG NUMBER,
CONTRACT_TYPE NUMBER,
POSTING_TEXT_1 CHARACTER(50),
POSTING_TEXT_2 CHARACTER(50),
POSTING_TEXT_3 CHARACTER(50)
);

CREATE TABLE PARTNERS_CORE (
MANDATOR	NUMBER,							
DATASTATE	NUMBER,						
PROCESSNR	NUMBER,						
HISTNR	NUMBER,						
RPROCESSNR	NUMBER,						
DOP	DATE,						
DOR	DATE,						
IND	DATE,						
TERMINATIONFLAG	NUMBER,					
PARTNERS_NR	NUMBER,			
LEGAL_PERSON	NUMBER,				
FIRST_NAME	VARCHAR2(50),					
SECOND_NAME VARCHAR2(50),				
DATE_OF_BIRTH	DATE,					
SEX	NUMBER,				
NATIONALITY	VARCHAR(2),						
NATIONALITY_2	VARCHAR(2),				
NATIONALITY_3	VARCHAR(2),					
PROFESSION	NUMBER,				
ACTIVITY_STATE VARCHAR(50),				
TITLE		VARCHAR(50),					
LANGUAGE_CORRESPONDENCE		VARCHAR(2),
VIP_FLAG	NUMBER,					
PARTNER_STATE	NUMBER,				
FIRST_NAME_CAN	VARCHAR2(50),					
FIRST_NAME_PHON VARCHAR2(50),					
SECOND_NAME_CAN	VARCHAR2(50),					
SECOND_NAME_PHON	VARCHAR2(50),					
NOTICE	VARCHAR2(500),						
NAME_ADDITION	VARCHAR2(50),					
NAME_ADDITION2	VARCHAR2(50),					
DEFAULT_ADDRESS	NUMBER,				
DEFAULT_BANK	NUMBER,				
DEFAULT_COMMUNICATION	NUMBER,				
MARITAL_STATUS	NUMBER,		
SOCIAL_INSURANCE_NUMBER	VARCHAR(50),						
SOCIAL_INSURANCE_NUMBER_SP	VARCHAR(50),
PLACE_OF_BIRTH	VARCHAR(50),				
BIRTH_NAME	VARCHAR(50),						
EXT_CUSTOMER_NUMBER	VARCHAR(50),
NUMBER_CHILDREN	NUMBER,			
ADVERTISING	NUMBER,					
REASON_FOR_CHANGE	VARCHAR(50),
EMPLOYER	VARCHAR(50),						
SALUTATION	VARCHAR(50),					
HEALTH_INSURANCE_NUMBER	VARCHAR(50),			
CITIZEN_NUMBER	VARCHAR(50),						
ID_DOCUMENT_TYPE	NUMBER,		
ID_DOCUMENT_NR	VARCHAR(50),			
ID_DOCUMENT_ISSUED_DATE	DATE,						
ID_DOCUMENT_EXPIRY_DATE	DATE,				
ID_DOCUMENT_AUTHORITY	VARCHAR(50),		
ID_DOCUMENT_AUTHORITY_COUNTRY	VARCHAR(50),
TENANT	NUMBER,				
BASIC_TYPE	NUMBER,					
FIRST_SECONDARY_TYPE	NUMBER,
SECOND_SECONDARY_TYPE	NUMBER,
CCI_NUMBER	NUMBER,
SECTOR	NUMBER,	
DENOMINATION	VARCHAR(50),
PERSONNEL_NR	VARCHAR(50),
USERID	VARCHAR(50),	
NAME_ADDITION3	VARCHAR(50),
MANAGEMENT	VARCHAR(50),
CANCELLATION	VARCHAR(50),
CANCELLATION_DATE	DATE,
DISPATCH_TYPE	NUMBER,
DATE_OF_DEATH	DATE,
TITLE_OF_NOBILITY	VARCHAR(50),
HONORARY_TITLE	VARCHAR(50),
NAME_PREFIX	VARCHAR(50),
PEP_FLAG	NUMBER,
EU_SANCTION_FLAG	NUMBER
);







