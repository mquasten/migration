package de.msg.jbit7.migration.itnrw.partner;

import java.util.Date;

import de.msg.jbit7.migration.itnrw.util.Persistence;

@Persistence(PartnersRole.INSERT_PARTNERS_ROLE)
public class PartnersRole {

	final static String INSERT_PARTNERS_ROLE = "INSERT INTO PARTNERS_ROLE (MANDATOR,DATASTATE,PROCESSNR,HISTNR,RPROCESSNR,DOP,DOR,IND,TERMINATIONFLAG,ROLE,ORDER_NR_ROLE,LEFT_SIDE,ORDER_NR_LEFT_SIDE,RIGHT_SIDE,ADDRESS_NR,BANK_NR,COMMUNICATION_ROLE_KEY,EXTERN_KEY,ROLE_STATE,RISK_CARRIER) VALUES (:mandator,:datastate,:processnr,:histnr,:rprocessnr,:dop,:dor,:ind,:terminationflag,:role,:orderNrRole,:leftSide,:orderNrLeftSide,:rightSide,:addressNr,:bankNr,:communicationRoleKey,:externKey,:roleState,:riskCarrier)";

	private Long mandator;
	private String datastate;
	private Long processnr;
	private Long histnr;
	private Long rprocessnr;
	private Date dop;
	private Date dor;
	private Date ind;
	private Long terminationflag;
	private String role;
	private Long orderNrRole;
	private String leftSide;
	private String orderNrLeftSide;
	private String rightSide;
	private String addressNr;
	private String bankNr;
	private Long communicationRoleKey;
	private String externKey;
	private Long roleState;
	private Long riskCarrier;

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getOrderNrRole() {
		return orderNrRole;
	}

	public void setOrderNrRole(Long orderNrRole) {
		this.orderNrRole = orderNrRole;
	}

	public String getLeftSide() {
		return leftSide;
	}

	public void setLeftSide(String leftSide) {
		this.leftSide = leftSide;
	}

	public String getOrderNrLeftSide() {
		return orderNrLeftSide;
	}

	public void setOrderNrLeftSide(String orderNrLeftSide) {
		this.orderNrLeftSide = orderNrLeftSide;
	}

	public String getRightSide() {
		return rightSide;
	}

	public void setRightSide(String rightSide) {
		this.rightSide = rightSide;
	}

	public String getAddressNr() {
		return addressNr;
	}

	public void setAddressNr(String addressNr) {
		this.addressNr = addressNr;
	}

	public String getBankNr() {
		return bankNr;
	}

	public void setBankNr(String bankNr) {
		this.bankNr = bankNr;
	}

	public Long getCommunicationRoleKey() {
		return communicationRoleKey;
	}

	public void setCommunicationRoleKey(Long communicationRoleKey) {
		this.communicationRoleKey = communicationRoleKey;
	}

	public String getExternKey() {
		return externKey;
	}

	public void setExternKey(String externKey) {
		this.externKey = externKey;
	}

	public Long getRoleState() {
		return roleState;
	}

	public void setRoleState(Long roleState) {
		this.roleState = roleState;
	}

	public Long getRiskCarrier() {
		return riskCarrier;
	}

	public void setRiskCarrier(Long riskCarrier) {
		this.riskCarrier = riskCarrier;
	}

}
