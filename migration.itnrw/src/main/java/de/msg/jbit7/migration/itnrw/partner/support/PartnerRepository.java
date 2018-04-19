package de.msg.jbit7.migration.itnrw.partner.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import de.msg.jbit7.migration.itnrw.partner.Address;
import de.msg.jbit7.migration.itnrw.partner.PMContract;
import de.msg.jbit7.migration.itnrw.partner.PartnerCore;
import de.msg.jbit7.migration.itnrw.util.BeanUtil;

@Repository
public class PartnerRepository {

	private static final String MANDATOR_NAME = "mandator";
	private static final String DELETE_CONTRACT_BY_MANDATOR_SQL = String
			.format("DELETE FROM PM_CONTRACT WHERE MANDATOR = :%s", MANDATOR_NAME);
	private static final String DELETE_PARTNER_BY_MANDATOR_SQL = String
			.format("DELETE FROM PARTNERS_CORE WHERE MANDATOR = :%s", MANDATOR_NAME);
	
	private static final String DELETE_ADDRESS_BY_MANDATOR_SQL = String
			.format("DELETE FROM ADDRESS WHERE MANDATOR = :%s", MANDATOR_NAME);
	private final NamedParameterJdbcOperations namedParameterJdbcOperations;

	final String INSERT_PM_CONTRACT = "INSERT INTO PM_CONTRACT (MANDATOR,DATASTATE,PROCESSNR,HISTNR,RPROCESSNR,DOP,DOR,IND,TERMINATIONFLAG,PRIONR,CONTRACT_NUMBER,REASON_FOR_CHANGE,BEGIN_OF_CONTRACT,TERMINATION_DATE,RISK_CARRIER,POLICY_NUMBER,MEMBER_OF_STAFF,COLLECTIVE_CONTRACT_NUMBER,INTERNAL_NUMBER_COLL_CONTRACT,POLICY_CONFIRMATION_FLAG,CONTRACT_TYPE,POSTING_TEXT_1,POSTING_TEXT_2,POSTING_TEXT_3) VALUES (:mandator,:datastate,:processnr,:histnr,:rprocessnr,:dop,:dor,:ind,:terminationflag,:prionr,:contractNumber,:reasonForChange,:beginOfContract,:terminationDate,:riskCarrier,:policyNumber,:memberOfStaff,:collectiveContractNumber,:internalNumberCollContract,:policyConfirmationFlag,:contractType,:postingText1,:postingText2,:postingText3)";

	final String INSERT_PARTNER = "INSERT INTO PARTNERS_CORE (MANDATOR,DATASTATE,PROCESSNR,HISTNR,RPROCESSNR,DOP,DOR,IND,TERMINATIONFLAG,PARTNERS_NR,LEGAL_PERSON,FIRST_NAME,SECOND_NAME,DATE_OF_BIRTH,SEX,NATIONALITY,NATIONALITY_2,NATIONALITY_3,PROFESSION,ACTIVITY_STATE,TITLE,LANGUAGE_CORRESPONDENCE,VIP_FLAG,PARTNER_STATE,FIRST_NAME_CAN,FIRST_NAME_PHON,SECOND_NAME_CAN,SECOND_NAME_PHON,NOTICE,NAME_ADDITION,NAME_ADDITION2,DEFAULT_ADDRESS,DEFAULT_BANK,DEFAULT_COMMUNICATION,MARITAL_STATUS,SOCIAL_INSURANCE_NUMBER,SOCIAL_INSURANCE_NUMBER_SP,PLACE_OF_BIRTH,BIRTH_NAME,EXT_CUSTOMER_NUMBER,NUMBER_CHILDREN,ADVERTISING,REASON_FOR_CHANGE,EMPLOYER,SALUTATION,HEALTH_INSURANCE_NUMBER,CITIZEN_NUMBER,ID_DOCUMENT_TYPE,ID_DOCUMENT_NR,ID_DOCUMENT_ISSUED_DATE,ID_DOCUMENT_EXPIRY_DATE,ID_DOCUMENT_AUTHORITY,ID_DOCUMENT_AUTHORITY_COUNTRY,TENANT,BASIC_TYPE,FIRST_SECONDARY_TYPE,SECOND_SECONDARY_TYPE,CCI_NUMBER,SECTOR,DENOMINATION,PERSONNEL_NR,USERID,NAME_ADDITION3,MANAGEMENT,CANCELLATION,CANCELLATION_DATE,DISPATCH_TYPE,DATE_OF_DEATH,TITLE_OF_NOBILITY,HONORARY_TITLE,NAME_PREFIX,PEP_FLAG,EU_SANCTION_FLAG) VALUES (:mandator,:datastate,:processnr,:histnr,:rprocessnr,:dop,:dor,:ind,:terminationflag,:partnersNr,:legalPerson,:firstName,:secondName,:dateOfBirth,:sex,:nationality,:nationality2,:nationality3,:profession,:activityState,:title,:languageCorrespondence,:vipFlag,:partnerState,:firstNameCan,:firstNamePhon,:secondNameCan,:secondNamePhon,:notice,:nameAddition,:nameAddition2,:defaultAddress,:defaultBank,:defaultCommunication,:maritalStatus,:socialInsuranceNumber,:socialInsuranceNumberSp,:placeOfBirth,:birthName,:extCustomerNumber,:numberChildren,:advertising,:reasonForChange,:employer,:salutation,:healthInsuranceNumber,:citizenNumber,:idDocumentType,:idDocumentNr,:idDocumentIssuedDate,:idDocumentExpiryDate,:idDocumentAuthority,:idDocumentAuthorityCountry,:tenant,:basicType,:firstSecondaryType,:secondSecondaryType,:cciNumber,:sector,:denomination,:personnelNr,:userid,:nameAddition3,:management,:cancellation,:cancellationDate,:dispatchType,:dateOfDeath,:titleOfNobility,:honoraryTitle,:namePrefix,:pepFlag,:euSanctionFlag)";

	final String INSERT_ADDRESS = "INSERT INTO ADDRESS (MANDATOR,DATASTATE,PROCESSNR,HISTNR,RPROCESSNR,DOP,DOR,IND,TERMINATIONFLAG,PARTNERS_NR,ADDRESS_NR,POSTCODE,CITY1,CITY2,STREET,PO_BOX,COUNTRY,ADDRESS_ADDITION1,ADDRESS_ADDITION2,CITY_CAN,CITY_PHON,OUTDATED,CO_INFORMATION,REASON_FOR_CHANGE,ADDRESS_TYPE,HOUSE_NUMBER,HOUSE_NUMBER_ADDITION,CONTACT,ADDRESS_STATE,VALIDATION_STATE,USERID,PROXIMATE_TOWN,LATITUDE,LONGITUDE) VALUES (:mandator,:datastate,:processnr,:histnr,:rprocessnr,:dop,:dor,:ind,:terminationflag,:partnersNr,:addressNr,:postcode,:city1,:city2,:street,:poBox,:country,:addressAddition1,:addressAddition2,:cityCan,:cityPhon,:outdated,:coInformation,:reasonForChange,:addressType,:houseNumber,:houseNumberAddition,:contact,:addressState,:validationState,:userid,:proximateTown,:latitude,:longitude)";

	
	@Autowired
	PartnerRepository(
			@Qualifier("healthFactoryJDBCTemplate") final NamedParameterJdbcOperations namedParameterJdbcOperations) {
		this.namedParameterJdbcOperations = namedParameterJdbcOperations;
	}

	public final List<PMContract> findContracts(long mandator) {
		final String sql = String.format("SELECT * from PM_CONTRACT where mandator=%s", mandator);
		return namedParameterJdbcOperations.query(sql, new BeanPropertyRowMapper<>(PMContract.class));
	}

	public final void persistContract(final PMContract contract) {
		final Map<String, Object> values = BeanUtil.toMap(contract);
		namedParameterJdbcOperations.update(INSERT_PM_CONTRACT, values);
	}

	public final void cleanMandator(final long mandator) {
		final Map<String, Long> parameters = new HashMap<>();
		parameters.put(MANDATOR_NAME, mandator);
		namedParameterJdbcOperations.update(DELETE_CONTRACT_BY_MANDATOR_SQL, parameters);
		namedParameterJdbcOperations.update(DELETE_PARTNER_BY_MANDATOR_SQL, parameters);
		namedParameterJdbcOperations.update(DELETE_ADDRESS_BY_MANDATOR_SQL, parameters);
	}

	public final void persistPartner(final PartnerCore partnerCore) {
		final Map<String, Object> values = BeanUtil.toMap(partnerCore);
		namedParameterJdbcOperations.update(INSERT_PARTNER, values);

	}

	public final List<PartnerCore> findPartner(final long mandator) {
		final String sql = String.format("SELECT * from PARTNERS_CORE where mandator=%s", mandator);
		return namedParameterJdbcOperations.query(sql, new BeanPropertyRowMapper<>(PartnerCore.class));
	}

	public void persistAddress(final Address address) {
		final Map<String, Object> values = BeanUtil.toMap(address);
		namedParameterJdbcOperations.update(INSERT_ADDRESS, values);
	}
	
	public final List<Address> findAddresses(final long mandator) {
		final String sql = String.format("SELECT * from ADDRESS where mandator=%s", mandator);
		return namedParameterJdbcOperations.query(sql, new BeanPropertyRowMapper<>(Address.class));
	}
	
	

}
