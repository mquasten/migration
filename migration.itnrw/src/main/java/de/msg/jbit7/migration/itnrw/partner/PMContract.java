package de.msg.jbit7.migration.itnrw.partner;

import java.util.Date;

import de.msg.jbit7.migration.itnrw.util.Persistence;

@Persistence(PMContract.INSERT_PM_CONTRACT)
public class PMContract {
	final static String INSERT_PM_CONTRACT = "INSERT INTO PM_CONTRACT (MANDATOR,DATASTATE,PROCESSNR,HISTNR,RPROCESSNR,DOP,DOR,IND,TERMINATIONFLAG,PRIONR,CONTRACT_NUMBER,REASON_FOR_CHANGE,BEGIN_OF_CONTRACT,TERMINATION_DATE,RISK_CARRIER,POLICY_NUMBER,MEMBER_OF_STAFF,COLLECTIVE_CONTRACT_NUMBER,INTERNAL_NUMBER_COLL_CONTRACT,POLICY_CONFIRMATION_FLAG,CONTRACT_TYPE,POSTING_TEXT_1,POSTING_TEXT_2,POSTING_TEXT_3) VALUES (:mandator,:datastate,:processnr,:histnr,:rprocessnr,:dop,:dor,:ind,:terminationflag,:prionr,:contractNumber,:reasonForChange,:beginOfContract,:terminationDate,:riskCarrier,:policyNumber,:memberOfStaff,:collectiveContractNumber,:internalNumberCollContract,:policyConfirmationFlag,:contractType,:postingText1,:postingText2,:postingText3)";
	private String datastate;
	private Long processnr;
	private Long histnr;
	private Long rprocessnr;
	private Date dop;
	private Date dor;
	private Date ind;
	private Long terminationflag;
	private Long prionr;
	private Long contractNumber;
	private Long reasonForChange;
	private Date beginOfContract;
	private Date terminationDate;
	private Long riskCarrier;
	private String policyNumber;
	private Long memberOfStaff;
	private Long collectiveContractNumber;
	private String internalNumberCollContract;
	private Long policyConfirmationFlag;
	private Long contractType;
	private String postingText1;
	private String postingText2;
	private String postingText3;

	private Long mandator;

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

	public Long getPrionr() {
		return prionr;
	}

	public void setPrionr(Long prionr) {
		this.prionr = prionr;
	}

	public Long getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(Long contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Long getReasonForChange() {
		return reasonForChange;
	}

	public void setReasonForChange(Long reasonForChange) {
		this.reasonForChange = reasonForChange;
	}

	public Date getBeginOfContract() {
		return beginOfContract;
	}

	public void setBeginOfContract(Date beginOfContract) {
		this.beginOfContract = beginOfContract;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public Long getRiskCarrier() {
		return riskCarrier;
	}

	public void setRiskCarrier(Long riskCarrier) {
		this.riskCarrier = riskCarrier;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public Long getMemberOfStaff() {
		return memberOfStaff;
	}

	public void setMemberOfStaff(Long memberOfStaff) {
		this.memberOfStaff = memberOfStaff;
	}

	public Long getCollectiveContractNumber() {
		return collectiveContractNumber;
	}

	public void setCollectiveContractNumber(Long collectiveContractNumber) {
		this.collectiveContractNumber = collectiveContractNumber;
	}

	public String getInternalNumberCollContract() {
		return internalNumberCollContract;
	}

	public void setInternalNumberCollContract(String internalNumberCollContract) {
		this.internalNumberCollContract = internalNumberCollContract;
	}

	public Long getPolicyConfirmationFlag() {
		return policyConfirmationFlag;
	}

	public void setPolicyConfirmationFlag(Long policyConfirmationFlag) {
		this.policyConfirmationFlag = policyConfirmationFlag;
	}

	public Long getContractType() {
		return contractType;
	}

	public void setContractType(Long contractType) {
		this.contractType = contractType;
	}

	public String getPostingText1() {
		return postingText1;
	}

	public void setPostingText1(String postingText1) {
		this.postingText1 = postingText1;
	}

	public String getPostingText2() {
		return postingText2;
	}

	public void setPostingText2(String postingText2) {
		this.postingText2 = postingText2;
	}

	public String getPostingText3() {
		return postingText3;
	}

	public void setPostingText3(String postingText3) {
		this.postingText3 = postingText3;
	}

}
