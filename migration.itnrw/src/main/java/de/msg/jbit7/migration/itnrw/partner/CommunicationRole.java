package de.msg.jbit7.migration.itnrw.partner;

import de.msg.jbit7.migration.itnrw.util.Persistence;

@Persistence(CommunicationRole.INSERT_COMMUNICATION_ROLE)
public class CommunicationRole {

	static final String INSERT_COMMUNICATION_ROLE = "INSERT INTO COMMUNICATION_ROLE (MANDATOR,DATASTATE,PROCESSNR,COMMUNICATION_KEY,COMMUNICATION_ROLE_KEY,COMMUNICATION_NR) VALUES (:mandator,:datastate,:processnr,:communicationKey,:communicationRoleKey,:communicationNr)";

	private Long mandator;
	private String datastate;
	private Long processnr;
	private Long communicationKey;
	private Long communicationRoleKey;
	private String communicationNr;

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

	public Long getCommunicationKey() {
		return communicationKey;
	}

	public void setCommunicationKey(Long communicationKey) {
		this.communicationKey = communicationKey;
	}

	public Long getCommunicationRoleKey() {
		return communicationRoleKey;
	}

	public void setCommunicationRoleKey(Long communicationRoleKey) {
		this.communicationRoleKey = communicationRoleKey;
	}

	public String getCommunicationNr() {
		return communicationNr;
	}

	public void setCommunicationNr(String communicationNr) {
		this.communicationNr = communicationNr;
	}

}