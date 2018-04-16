package de.msg.jbit7.migration.itnrw.stamm;

public class KindInfo {
	private Long beihilfenr;
	private Long lfdKind;
	private String name;
	private String vorname;
	private Long gebDatum;
	private Long parag28Abs2;
	private String zeitstempel;
	private Long kostenerstattung;
	private Long beihilfeberechtigt;
	private String versichert;
	private Long sterbedatum;
	private byte[] lguid;

	public Long getBeihilfenr() {
		return beihilfenr;
	}

	public void setBeihilfenr(Long beihilfenr) {
		this.beihilfenr = beihilfenr;
	}

	public Long getLfdKind() {
		return lfdKind;
	}

	public void setLfdKind(Long lfdKind) {
		this.lfdKind = lfdKind;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public Long getGebDatum() {
		return gebDatum;
	}

	public void setGebDatum(Long gebDatum) {
		this.gebDatum = gebDatum;
	}

	public Long getParag28Abs2() {
		return parag28Abs2;
	}

	public void setParag28Abs2(Long parag28Abs2) {
		this.parag28Abs2 = parag28Abs2;
	}

	public String getZeitstempel() {
		return zeitstempel;
	}

	public void setZeitstempel(String zeitstempel) {
		this.zeitstempel = zeitstempel;
	}

	public Long getKostenerstattung() {
		return kostenerstattung;
	}

	public void setKostenerstattung(Long kostenerstattung) {
		this.kostenerstattung = kostenerstattung;
	}

	public Long getBeihilfeberechtigt() {
		return beihilfeberechtigt;
	}

	public void setBeihilfeberechtigt(Long beihilfeberechtigt) {
		this.beihilfeberechtigt = beihilfeberechtigt;
	}

	public String getVersichert() {
		return versichert;
	}

	public void setVersichert(String versichert) {
		this.versichert = versichert;
	}

	public Long getSterbedatum() {
		return sterbedatum;
	}

	public void setSterbedatum(Long sterbedatum) {
		this.sterbedatum = sterbedatum;
	}

	public byte[] getLguid() {
		return lguid;
	}

	public void setLguid(byte[] lguid) {
		this.lguid = lguid;
	}

}
