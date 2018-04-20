package de.msg.jbit7.migration.itnrw.partner;

import java.util.Date;

import de.msg.jbit7.migration.itnrw.util.Persistence;

@Persistence(PartnerCore.INSERT_PARTNER)
public class PartnerCore {


	final static String INSERT_PARTNER = "INSERT INTO PARTNERS_CORE (MANDATOR,DATASTATE,PROCESSNR,HISTNR,RPROCESSNR,DOP,DOR,IND,TERMINATIONFLAG,PARTNERS_NR,LEGAL_PERSON,FIRST_NAME,SECOND_NAME,DATE_OF_BIRTH,SEX,NATIONALITY,NATIONALITY_2,NATIONALITY_3,PROFESSION,ACTIVITY_STATE,TITLE,LANGUAGE_CORRESPONDENCE,VIP_FLAG,PARTNER_STATE,FIRST_NAME_CAN,FIRST_NAME_PHON,SECOND_NAME_CAN,SECOND_NAME_PHON,NOTICE,NAME_ADDITION,NAME_ADDITION2,DEFAULT_ADDRESS,DEFAULT_BANK,DEFAULT_COMMUNICATION,MARITAL_STATUS,SOCIAL_INSURANCE_NUMBER,SOCIAL_INSURANCE_NUMBER_SP,PLACE_OF_BIRTH,BIRTH_NAME,EXT_CUSTOMER_NUMBER,NUMBER_CHILDREN,ADVERTISING,REASON_FOR_CHANGE,EMPLOYER,SALUTATION,HEALTH_INSURANCE_NUMBER,CITIZEN_NUMBER,ID_DOCUMENT_TYPE,ID_DOCUMENT_NR,ID_DOCUMENT_ISSUED_DATE,ID_DOCUMENT_EXPIRY_DATE,ID_DOCUMENT_AUTHORITY,ID_DOCUMENT_AUTHORITY_COUNTRY,TENANT,BASIC_TYPE,FIRST_SECONDARY_TYPE,SECOND_SECONDARY_TYPE,CCI_NUMBER,SECTOR,DENOMINATION,PERSONNEL_NR,USERID,NAME_ADDITION3,MANAGEMENT,CANCELLATION,CANCELLATION_DATE,DISPATCH_TYPE,DATE_OF_DEATH,TITLE_OF_NOBILITY,HONORARY_TITLE,NAME_PREFIX,PEP_FLAG,EU_SANCTION_FLAG) VALUES (:mandator,:datastate,:processnr,:histnr,:rprocessnr,:dop,:dor,:ind,:terminationflag,:partnersNr,:legalPerson,:firstName,:secondName,:dateOfBirth,:sex,:nationality,:nationality2,:nationality3,:profession,:activityState,:title,:languageCorrespondence,:vipFlag,:partnerState,:firstNameCan,:firstNamePhon,:secondNameCan,:secondNamePhon,:notice,:nameAddition,:nameAddition2,:defaultAddress,:defaultBank,:defaultCommunication,:maritalStatus,:socialInsuranceNumber,:socialInsuranceNumberSp,:placeOfBirth,:birthName,:extCustomerNumber,:numberChildren,:advertising,:reasonForChange,:employer,:salutation,:healthInsuranceNumber,:citizenNumber,:idDocumentType,:idDocumentNr,:idDocumentIssuedDate,:idDocumentExpiryDate,:idDocumentAuthority,:idDocumentAuthorityCountry,:tenant,:basicType,:firstSecondaryType,:secondSecondaryType,:cciNumber,:sector,:denomination,:personnelNr,:userid,:nameAddition3,:management,:cancellation,:cancellationDate,:dispatchType,:dateOfDeath,:titleOfNobility,:honoraryTitle,:namePrefix,:pepFlag,:euSanctionFlag)";
	private Long mandator;
	   private String datastate;
	   private Long processnr;
	   private Long histnr;
	   private Long rprocessnr;
	   private Date dop;
	   private Date dor;
	   private Date ind;
	   private Long terminationflag;
	   private String partnersNr;
	   private Long legalPerson;
	   private String firstName;
	   private String secondName;
	   private Date dateOfBirth;
	   private Long sex;
	   private String nationality;
	   private String nationality2;
	   private String nationality3;
	   private Long profession;
	   private String activityState;
	   private String title;
	   private String languageCorrespondence;
	   private Long vipFlag;
	   private Long partnerState;
	   private String firstNameCan;
	   private String firstNamePhon;
	   private String secondNameCan;
	   private String secondNamePhon;
	   private String notice;
	   private String nameAddition;
	   private String nameAddition2;
	   private String defaultAddress;
	   private String defaultBank;
	   private String defaultCommunication;
	   private Long maritalStatus;
	   private String socialInsuranceNumber;
	   private String socialInsuranceNumberSp;
	   private String placeOfBirth;
	   private String birthName;
	   private String extCustomerNumber;
	   private Long numberChildren;
	   private Long advertising;
	   private Long reasonForChange;
	   private String employer;
	   private Long salutation;
	   private String healthInsuranceNumber;
	   private String citizenNumber;
	   private Long idDocumentType;
	   private String idDocumentNr;
	   private Date idDocumentIssuedDate;
	   private Date idDocumentExpiryDate;
	   private String idDocumentAuthority;
	   private String idDocumentAuthorityCountry;
	   private Long tenant;
	   private Long basicType;
	   private Long firstSecondaryType;
	   private Long secondSecondaryType;
	   private String cciNumber;
	   private String sector;
	   private String denomination;
	   private String personnelNr;
	   private String userid;
	   private String nameAddition3;
	   private String management;
	   private String cancellation;
	   private Date cancellationDate;
	   private Long dispatchType;
	   private Date dateOfDeath;
	   private Long titleOfNobility;
	   private Long honoraryTitle;
	   private String namePrefix;
	   private Long pepFlag;
	   private Long euSanctionFlag;

	   public Long getMandator() {
			return mandator;
		}
		public void setMandator(Long mandator) {
			this.mandator = mandator;
		}
		public String getDatastate() {
			return datastate;
		}
		public void setDatastate(String datastate) {
			this.datastate = datastate;
		}
		public Long getProcessnr() {
			return processnr;
		}
		public void setProcessnr(Long processnr) {
			this.processnr = processnr;
		}
		public Long getHistnr() {
			return histnr;
		}
		public void setHistnr(Long histnr) {
			this.histnr = histnr;
		}
		public Long getRprocessnr() {
			return rprocessnr;
		}
		public void setRprocessnr(Long rprocessnr) {
			this.rprocessnr = rprocessnr;
		}
		public Date getDop() {
			return dop;
		}
		public void setDop(Date dop) {
			this.dop = dop;
		}
		public Date getDor() {
			return dor;
		}
		public void setDor(Date dor) {
			this.dor = dor;
		}
		public Date getInd() {
			return ind;
		}
		public void setInd(Date ind) {
			this.ind = ind;
		}
		public Long getTerminationflag() {
			return terminationflag;
		}
		public void setTerminationflag(Long terminationflag) {
			this.terminationflag = terminationflag;
		}
		public String getPartnersNr() {
			return partnersNr;
		}
		public void setPartnersNr(String partnersNr) {
			this.partnersNr = partnersNr;
		}
		public Long getLegalPerson() {
			return legalPerson;
		}
		public void setLegalPerson(Long legalPerson) {
			this.legalPerson = legalPerson;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getSecondName() {
			return secondName;
		}
		public void setSecondName(String secondName) {
			this.secondName = secondName;
		}
		public Date getDateOfBirth() {
			return dateOfBirth;
		}
		public void setDateOfBirth(Date dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}
		public Long getSex() {
			return sex;
		}
		public void setSex(Long sex) {
			this.sex = sex;
		}
		public String getNationality() {
			return nationality;
		}
		public void setNationality(String nationality) {
			this.nationality = nationality;
		}
		public String getNationality2() {
			return nationality2;
		}
		public void setNationality2(String nationality2) {
			this.nationality2 = nationality2;
		}
		public String getNationality3() {
			return nationality3;
		}
		public void setNationality3(String nationality3) {
			this.nationality3 = nationality3;
		}
		public Long getProfession() {
			return profession;
		}
		public void setProfession(Long profession) {
			this.profession = profession;
		}
		public String getActivityState() {
			return activityState;
		}
		public void setActivityState(String activityState) {
			this.activityState = activityState;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getLanguageCorrespondence() {
			return languageCorrespondence;
		}
		public void setLanguageCorrespondence(String languageCorrespondence) {
			this.languageCorrespondence = languageCorrespondence;
		}
		public Long getVipFlag() {
			return vipFlag;
		}
		public void setVipFlag(Long vipFlag) {
			this.vipFlag = vipFlag;
		}
		public Long getPartnerState() {
			return partnerState;
		}
		public void setPartnerState(Long partnerState) {
			this.partnerState = partnerState;
		}
		public String getFirstNameCan() {
			return firstNameCan;
		}
		public void setFirstNameCan(String firstNameCan) {
			this.firstNameCan = firstNameCan;
		}
		public String getFirstNamePhon() {
			return firstNamePhon;
		}
		public void setFirstNamePhon(String firstNamePhon) {
			this.firstNamePhon = firstNamePhon;
		}
		public String getSecondNameCan() {
			return secondNameCan;
		}
		public void setSecondNameCan(String secondNameCan) {
			this.secondNameCan = secondNameCan;
		}
		public String getSecondNamePhon() {
			return secondNamePhon;
		}
		public void setSecondNamePhon(String secondNamePhon) {
			this.secondNamePhon = secondNamePhon;
		}
		public String getNotice() {
			return notice;
		}
		public void setNotice(String notice) {
			this.notice = notice;
		}
		public String getNameAddition() {
			return nameAddition;
		}
		public void setNameAddition(String nameAddition) {
			this.nameAddition = nameAddition;
		}
		public String getNameAddition2() {
			return nameAddition2;
		}
		public void setNameAddition2(String nameAddition2) {
			this.nameAddition2 = nameAddition2;
		}
		public String getDefaultAddress() {
			return defaultAddress;
		}
		public void setDefaultAddress(String defaultAddress) {
			this.defaultAddress = defaultAddress;
		}
		public String getDefaultBank() {
			return defaultBank;
		}
		public void setDefaultBank(String defaultBank) {
			this.defaultBank = defaultBank;
		}
		public String getDefaultCommunication() {
			return defaultCommunication;
		}
		public void setDefaultCommunication(String defaultCommunication) {
			this.defaultCommunication = defaultCommunication;
		}
		public Long getMaritalStatus() {
			return maritalStatus;
		}
		public void setMaritalStatus(Long maritalStatus) {
			this.maritalStatus = maritalStatus;
		}
		public String getSocialInsuranceNumber() {
			return socialInsuranceNumber;
		}
		public void setSocialInsuranceNumber(String socialInsuranceNumber) {
			this.socialInsuranceNumber = socialInsuranceNumber;
		}
		public String getSocialInsuranceNumberSp() {
			return socialInsuranceNumberSp;
		}
		public void setSocialInsuranceNumberSp(String socialInsuranceNumberSp) {
			this.socialInsuranceNumberSp = socialInsuranceNumberSp;
		}
		public String getPlaceOfBirth() {
			return placeOfBirth;
		}
		public void setPlaceOfBirth(String placeOfBirth) {
			this.placeOfBirth = placeOfBirth;
		}
		public String getBirthName() {
			return birthName;
		}
		public void setBirthName(String birthName) {
			this.birthName = birthName;
		}
		public String getExtCustomerNumber() {
			return extCustomerNumber;
		}
		public void setExtCustomerNumber(String extCustomerNumber) {
			this.extCustomerNumber = extCustomerNumber;
		}
		public Long getNumberChildren() {
			return numberChildren;
		}
		public void setNumberChildren(Long numberChildren) {
			this.numberChildren = numberChildren;
		}
		public Long getAdvertising() {
			return advertising;
		}
		public void setAdvertising(Long advertising) {
			this.advertising = advertising;
		}
		public Long getReasonForChange() {
			return reasonForChange;
		}
		public void setReasonForChange(Long reasonForChange) {
			this.reasonForChange = reasonForChange;
		}
		public String getEmployer() {
			return employer;
		}
		public void setEmployer(String employer) {
			this.employer = employer;
		}
		public Long getSalutation() {
			return salutation;
		}
		public void setSalutation(Long salutation) {
			this.salutation = salutation;
		}
		public String getHealthInsuranceNumber() {
			return healthInsuranceNumber;
		}
		public void setHealthInsuranceNumber(String healthInsuranceNumber) {
			this.healthInsuranceNumber = healthInsuranceNumber;
		}
		public String getCitizenNumber() {
			return citizenNumber;
		}
		public void setCitizenNumber(String citizenNumber) {
			this.citizenNumber = citizenNumber;
		}
		public Long getIdDocumentType() {
			return idDocumentType;
		}
		public void setIdDocumentType(Long idDocumentType) {
			this.idDocumentType = idDocumentType;
		}
		public String getIdDocumentNr() {
			return idDocumentNr;
		}
		public void setIdDocumentNr(String idDocumentNr) {
			this.idDocumentNr = idDocumentNr;
		}
		public Date getIdDocumentIssuedDate() {
			return idDocumentIssuedDate;
		}
		public void setIdDocumentIssuedDate(Date idDocumentIssuedDate) {
			this.idDocumentIssuedDate = idDocumentIssuedDate;
		}
		public Date getIdDocumentExpiryDate() {
			return idDocumentExpiryDate;
		}
		public void setIdDocumentExpiryDate(Date idDocumentExpiryDate) {
			this.idDocumentExpiryDate = idDocumentExpiryDate;
		}
		public String getIdDocumentAuthority() {
			return idDocumentAuthority;
		}
		public void setIdDocumentAuthority(String idDocumentAuthority) {
			this.idDocumentAuthority = idDocumentAuthority;
		}
		public String getIdDocumentAuthorityCountry() {
			return idDocumentAuthorityCountry;
		}
		public void setIdDocumentAuthorityCountry(String idDocumentAuthorityCountry) {
			this.idDocumentAuthorityCountry = idDocumentAuthorityCountry;
		}
		public Long getTenant() {
			return tenant;
		}
		public void setTenant(Long tenant) {
			this.tenant = tenant;
		}
		public Long getBasicType() {
			return basicType;
		}
		public void setBasicType(Long basicType) {
			this.basicType = basicType;
		}
		public Long getFirstSecondaryType() {
			return firstSecondaryType;
		}
		public void setFirstSecondaryType(Long firstSecondaryType) {
			this.firstSecondaryType = firstSecondaryType;
		}
		public Long getSecondSecondaryType() {
			return secondSecondaryType;
		}
		public void setSecondSecondaryType(Long secondSecondaryType) {
			this.secondSecondaryType = secondSecondaryType;
		}
		public String getCciNumber() {
			return cciNumber;
		}
		public void setCciNumber(String cciNumber) {
			this.cciNumber = cciNumber;
		}
		public String getSector() {
			return sector;
		}
		public void setSector(String sector) {
			this.sector = sector;
		}
		public String getDenomination() {
			return denomination;
		}
		public void setDenomination(String denomination) {
			this.denomination = denomination;
		}
		public String getPersonnelNr() {
			return personnelNr;
		}
		public void setPersonnelNr(String personnelNr) {
			this.personnelNr = personnelNr;
		}
		public String getUserid() {
			return userid;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}
		public String getNameAddition3() {
			return nameAddition3;
		}
		public void setNameAddition3(String nameAddition3) {
			this.nameAddition3 = nameAddition3;
		}
		public String getManagement() {
			return management;
		}
		public void setManagement(String management) {
			this.management = management;
		}
		public String getCancellation() {
			return cancellation;
		}
		public void setCancellation(String cancellation) {
			this.cancellation = cancellation;
		}
		public Date getCancellationDate() {
			return cancellationDate;
		}
		public void setCancellationDate(Date cancellationDate) {
			this.cancellationDate = cancellationDate;
		}
		public Long getDispatchType() {
			return dispatchType;
		}
		public void setDispatchType(Long dispatchType) {
			this.dispatchType = dispatchType;
		}
		public Date getDateOfDeath() {
			return dateOfDeath;
		}
		public void setDateOfDeath(Date dateOfDeath) {
			this.dateOfDeath = dateOfDeath;
		}
		public Long getTitleOfNobility() {
			return titleOfNobility;
		}
		public void setTitleOfNobility(Long titleOfNobility) {
			this.titleOfNobility = titleOfNobility;
		}
		public Long getHonoraryTitle() {
			return honoraryTitle;
		}
		public void setHonoraryTitle(Long honoraryTitle) {
			this.honoraryTitle = honoraryTitle;
		}
		public String getNamePrefix() {
			return namePrefix;
		}
		public void setNamePrefix(String namePrefix) {
			this.namePrefix = namePrefix;
		}
		public Long getPepFlag() {
			return pepFlag;
		}
		public void setPepFlag(Long pepFlag) {
			this.pepFlag = pepFlag;
		}
		public Long getEuSanctionFlag() {
			return euSanctionFlag;
		}
		public void setEuSanctionFlag(Long euSanctionFlag) {
			this.euSanctionFlag = euSanctionFlag;
		}
	   
}
