package de.msg.jbit7.migration.itnrw.partner;

import java.util.Date;

import de.msg.jbit7.migration.itnrw.util.Persistence;
@Persistence(Communication.INSERT_COMMUNICATION)
public class Communication {

	
	static final String INSERT_COMMUNICATION = "INSERT INTO COMMUNICATION (MANDATOR,DATASTATE,PROCESSNR,HISTNR,RPROCESSNR,DOP,DOR,IND,TERMINATIONFLAG,PARTNERS_NR,COMMUNICATION_NR,COMMUNICATION_TYPE,VALUE,REASON_FOR_CHANGE,AVAILABILITY,USERID,OUTDATED,USAGE_AGREEMENT) VALUES (:mandator,:datastate,:processnr,:histnr,:rprocessnr,:dop,:dor,:ind,:terminationflag,:partnersNr,:communicationNr,:communicationType,:value,:reasonForChange,:availability,:userid,:outdated,:usageAgreement)";

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
	private String communicationNr;
	private Long communicationType;
	private String value;
	private Long reasonForChange;
	private String availability;
	private String userid;
	private Long outdated;
	private Long usageAgreement;

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

	public String getCommunicationNr() {
		return communicationNr;
	}

	public void setCommunicationNr(String communicationNr) {
		this.communicationNr = communicationNr;
	}

	public Long getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(Long communicationType) {
		this.communicationType = communicationType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getReasonForChange() {
		return reasonForChange;
	}

	public void setReasonForChange(Long reasonForChange) {
		this.reasonForChange = reasonForChange;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Long getOutdated() {
		return outdated;
	}

	public void setOutdated(Long outdated) {
		this.outdated = outdated;
	}

	public Long getUsageAgreement() {
		return usageAgreement;
	}

	public void setUsageAgreement(Long usageAgreement) {
		this.usageAgreement = usageAgreement;
	}

}
