package de.msg.jbit7.migration.itnrw.mapping;

public class IdMapping {
	private Long beihilfenr;
	private String partnerNr;
	private Long contractNumber;
	private Long processNumber;
	private String marriagePartnerNr;
	private String[] childrenPartnerNr;
	private Long[] childrenNr;
	

	private Long[] collectiveContractNumbers;

	private String schulnummer;
	private String dienststelle;
	
	private Long mandator; 
	
	private String migrationUser;

	public Long getBeihilfenr() {
		return beihilfenr;
	}

	public void setBeihilfenr(Long beihilfenr) {
		this.beihilfenr = beihilfenr;
	}

	public String getPartnerNr() {
		return partnerNr;
	}

	public void setPartnerNr(String partnerNr) {
		this.partnerNr = partnerNr;
	}

	public Long getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(Long contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Long getProcessNumber() {
		return processNumber;
	}

	public void setProcessNumber(Long processNumber) {
		this.processNumber = processNumber;
	}

	public String getMarriagePartnerNr() {
		return marriagePartnerNr;
	}

	public void setMarriagePartnerNr(String marriagePartnerNr) {
		this.marriagePartnerNr = marriagePartnerNr;
	}

	public String[] getChildrenPartnerNr() {
		return childrenPartnerNr;
	}

	public void setChildrenPartnerNr(String[] childrenPartnerNr) {
		this.childrenPartnerNr = childrenPartnerNr;
	}

	

	public String getSchulnummer() {
		return schulnummer;
	}

	public void setSchulnummer(String schulnummer) {
		this.schulnummer = schulnummer;
	}

	public String getDienststelle() {
		return dienststelle;
	}

	public void setDienststelle(String dienststelle) {
		this.dienststelle = dienststelle;
	}
	
	public Long[] getCollectiveContractNumbers() {
		return collectiveContractNumbers;
	}

	public void setCollectiveContractNumbers(Long[] collectiveContractNumbers) {
		this.collectiveContractNumbers = collectiveContractNumbers;
	}
	
	public Long[] getChildrenNr() {
		return childrenNr;
	}

	public void setChildrenNr(Long[] childrenNr) {
		this.childrenNr = childrenNr;
	}

	public Long getMandator() {
		return mandator;
	}

	public void setMandator(Long mandator) {
		this.mandator = mandator;
	}
	
	public String getMigrationUser() {
		return migrationUser;
	}

	public void setMigrationUser(String migrationUser) {
		this.migrationUser = migrationUser;
	}

	
}
