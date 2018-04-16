package de.msg.jbit7.migration.itnrw.mapping.support;

import java.util.HashMap;
import java.util.Map;

import de.msg.jbit7.migration.itnrw.mapping.StartValues;

public class CountersImpl implements Counters {
	
	private long partnerNumber = 0L;
	private long contractNumber = 0L;
	private long processNumber = 0L;
	private long collectiveContractNumber = 0L;
	
	private final long mandator;
	
	private final Map<String,Long> schools = new HashMap<>();
	private final Map<String,Long> offices = new HashMap<>();

	
	
	public CountersImpl(final StartValues startValues) {
		this.partnerNumber = startValues.getPartnerNumber();
		this.contractNumber = startValues.getContractNumber();
		this.processNumber =  startValues.getProcessNumber();
		this.collectiveContractNumber = startValues.getCollectiveContractNumber();
		this.mandator=startValues.getMandator();
	}
	


	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.stamm.support.Counters#nextPartnerNumber()
	 */
	@Override
	public final String nextPartnerNumber() {
		partnerNumber++;
		return "" + partnerNumber;
	}
	
	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.stamm.support.Counters#nextProcessNumber()
	 */
	@Override
	public final long nextProcessNumber() {
		processNumber++;
		return processNumber;
	}
	
	/* (non-Javadoc)
	 * @see de.msg.jbit7.migration.itnrw.stamm.support.Counters#nextContactNumber()
	 */
	@Override
	public final long nextContactNumber() {
		contractNumber++;
		return contractNumber;
	}
	
	
	@Override
	public final long nextCollectiveContractNumberSchool(final String schoolNumber) {
		if(! schools.containsKey(schoolNumber)) {
			schools.put(schoolNumber, nextCollectiveContractNumber());
		}
		return schools.get(schoolNumber);
	}
	
	@Override
	public final long nextCollectiveContractNumberOffice(final String officeNumber) {
		if(! offices.containsKey(officeNumber)) {
			offices.put(officeNumber, nextCollectiveContractNumber());
		}
		return offices.get(officeNumber);
	}
	
	private long nextCollectiveContractNumber() {
		collectiveContractNumber++;
		return collectiveContractNumber;
	}



	@Override
	public long mandator() {
		return mandator;
	}



	@Override
	public StartValues startValues() {
		StartValues startValues = new StartValues();
		startValues.setPartnerNumber(partnerNumber);
		startValues.setContractNumber(contractNumber);
		startValues.setProcessNumber(processNumber);
		startValues.setCollectiveContractNumber(collectiveContractNumber);
		startValues.setMandator(mandator);
		return startValues;
	}

}
