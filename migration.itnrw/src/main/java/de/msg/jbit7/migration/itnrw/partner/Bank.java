package de.msg.jbit7.migration.itnrw.partner;

import java.util.Date;

import de.msg.jbit7.migration.itnrw.util.Persistence;

@Persistence(Bank.INSERT_BANK)
public class Bank {

	static final String  INSERT_BANK = "INSERT INTO BANK (MANDATOR,DATASTATE,PROCESSNR,HISTNR,RPROCESSNR,DOP,DOR,IND,TERMINATIONFLAG,PARTNERS_NR,BANK_NR,IBAN,BIC,BANK_CODE,ACCOUNT_NUMBER,BANK_NAME,ACCOUNT_HOLDER,COUNTRY,OUTDATED,BANK_DISTRICT,DISTRICT_BANK_CODE,CREDIT_CARD_COMPANY,CREDIT_CARD_NUMBER,CREDIT_CARD_EXPIRY,CREDIT_CARD_SECURITY_CODE,REASON_FOR_CHANGE,CREDIT_CARD_TYPE,ACCOUNT_TYPE,BANK_STATE,USERID,POSTCODE,TOWN,STREET_AND_NO,CURRENCY_OF_ACCOUNT) VALUES (:mandator,:datastate,:processnr,:histnr,:rprocessnr,:dop,:dor,:ind,:terminationflag,:partnersNr,:bankNr,:iban,:bic,:bankCode,:accountNumber,:bankName,:accountHolder,:country,:outdated,:bankDistrict,:districtBankCode,:creditCardCompany,:creditCardNumber,:creditCardExpiry,:creditCardSecurityCode,:reasonForChange,:creditCardType,:accountType,:bankState,:userid,:postcode,:town,:streetAndNo,:currencyOfAccount)";

	
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
	private String bankNr;
	private String iban;
	private String bic;
	private String bankCode;
	private String accountNumber;
	private String bankName;
	private String accountHolder;
	private String country;
	private Long outdated;
	private String bankDistrict;
	private String districtBankCode;
	private String creditCardCompany;
	private String creditCardNumber;
	private Date creditCardExpiry;
	private String creditCardSecurityCode;
	private Long reasonForChange;
	private String creditCardType;
	private Long accountType;
	private Long bankState;
	private String userid;
	private String postcode;
	private String town;
	private String streetAndNo;
	private String currencyOfAccount;

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

	public String getBankNr() {
		return bankNr;
	}

	public void setBankNr(String bankNr) {
		this.bankNr = bankNr;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getOutdated() {
		return outdated;
	}

	public void setOutdated(Long outdated) {
		this.outdated = outdated;
	}

	public String getBankDistrict() {
		return bankDistrict;
	}

	public void setBankDistrict(String bankDistrict) {
		this.bankDistrict = bankDistrict;
	}

	public String getDistrictBankCode() {
		return districtBankCode;
	}

	public void setDistrictBankCode(String districtBankCode) {
		this.districtBankCode = districtBankCode;
	}

	public String getCreditCardCompany() {
		return creditCardCompany;
	}

	public void setCreditCardCompany(String creditCardCompany) {
		this.creditCardCompany = creditCardCompany;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public Date getCreditCardExpiry() {
		return creditCardExpiry;
	}

	public void setCreditCardExpiry(Date creditCardExpiry) {
		this.creditCardExpiry = creditCardExpiry;
	}

	public String getCreditCardSecurityCode() {
		return creditCardSecurityCode;
	}

	public void setCreditCardSecurityCode(String creditCardSecurityCode) {
		this.creditCardSecurityCode = creditCardSecurityCode;
	}

	public Long getReasonForChange() {
		return reasonForChange;
	}

	public void setReasonForChange(Long reasonForChange) {
		this.reasonForChange = reasonForChange;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public Long getAccountType() {
		return accountType;
	}

	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}

	public Long getBankState() {
		return bankState;
	}

	public void setBankState(Long bankState) {
		this.bankState = bankState;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getStreetAndNo() {
		return streetAndNo;
	}

	public void setStreetAndNo(String streetAndNo) {
		this.streetAndNo = streetAndNo;
	}

	public String getCurrencyOfAccount() {
		return currencyOfAccount;
	}

	public void setCurrencyOfAccount(String currencyOfAccount) {
		this.currencyOfAccount = currencyOfAccount;
	}

}
