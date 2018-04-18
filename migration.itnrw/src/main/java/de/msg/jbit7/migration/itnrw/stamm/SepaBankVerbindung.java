package de.msg.jbit7.migration.itnrw.stamm;

public class SepaBankVerbindung {

	private Long beihilfenr;

	private String typ;
	private Long lfdnr;
	private Long version;
	private String kontoinhaber;
	private String iban;
	private String bic;
	private String nameBank;
	private String verwendungszweck;
	private Long istPruefungNotwendig;
	private Long istGeloescht;
	private String letzteAenderung;
	private String benutzer;

	public Long getBeihilfenr() {
		return beihilfenr;
	}

	public void setBeihilfenr(Long beihilfenr) {
		this.beihilfenr = beihilfenr;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public Long getLfdnr() {
		return lfdnr;
	}

	public void setLfdnr(Long lfdnr) {
		this.lfdnr = lfdnr;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getKontoinhaber() {
		return kontoinhaber;
	}

	public void setKontoinhaber(String kontoinhaber) {
		this.kontoinhaber = kontoinhaber;
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

	public String getNameBank() {
		return nameBank;
	}

	public void setNameBank(String nameBank) {
		this.nameBank = nameBank;
	}

	public String getVerwendungszweck() {
		return verwendungszweck;
	}

	public void setVerwendungszweck(String verwendungszweck) {
		this.verwendungszweck = verwendungszweck;
	}

	public Long getIstPruefungNotwendig() {
		return istPruefungNotwendig;
	}

	public void setIstPruefungNotwendig(Long istPruefungNotwendig) {
		this.istPruefungNotwendig = istPruefungNotwendig;
	}

	public Long getIstGeloescht() {
		return istGeloescht;
	}

	public void setIstGeloescht(Long istGeloescht) {
		this.istGeloescht = istGeloescht;
	}

	public String getLetzteAenderung() {
		return letzteAenderung;
	}

	public void setLetzteAenderung(String letzteAenderung) {
		this.letzteAenderung = letzteAenderung;
	}

	public String getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(String benutzer) {
		this.benutzer = benutzer;
	}

}
