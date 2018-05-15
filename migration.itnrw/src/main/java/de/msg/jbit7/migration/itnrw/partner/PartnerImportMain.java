package de.msg.jbit7.migration.itnrw.partner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.ConversionService;

public class PartnerImportMain {
	
	private static final String XML_APPLICATION_CONFIGURATION = "classpath:beans.xml";
	
	public static void main(final String[] args)  {
		final CommandLine commandLine = parse(args);
		
		final boolean overwrite = commandLine.hasOption("f");
		final String mandatorAsString = commandLine.getOptionValue("m");
		try (final AbstractRefreshableConfigApplicationContext applicationContext = new ClassPathXmlApplicationContext(XML_APPLICATION_CONFIGURATION)) {
			final ConversionService conversionService = applicationContext.getBean(ConversionService.class);
			final PartnerService partnerService = applicationContext.getBean(PartnerService.class);
			final Long mandator = conversionService.convert(mandatorAsString, Long.class);
			partnerService.importPartners(mandator, overwrite);
			
			final PartnerFamilyService partnerFamilyService = applicationContext.getBean(PartnerFamilyService.class);
			partnerFamilyService.createPartners(mandator); 
			
			System.out.println("... finished.");
			//applicationContext.getBean(IdGenerationService.class).createIds(conversionService.convert(mandatorAsString, Long.class), overwrite, migUser);
		}
		
	}
	
	private static CommandLine parse(String[] args) {
		final Options options = new Options();
		options.addOption("m", true, "Mandators id.");
		options.addOption("f", false , "Delete existing Partners.");
		options.addOption("h", false , "Print help.");
		
		final CommandLineParser parser = new DefaultParser();
		
		final CommandLine cmd = parse(args, options, parser);
		
		if( !cmd.hasOption("m")) {
			System.err.println("Mandator is required");
			printHelpAndExit(options);
		}
		
		if(cmd.hasOption("h")) {
			printHelpAndExit(options);
		}
		
		return cmd;
	}
	
	private static void printHelpAndExit(final Options options) {
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "importPartners.sh", options );
		
		System.exit(1);
	}
	
	private static CommandLine parse(String[] args, final Options options, final CommandLineParser parser) {
		
		try {
			return parser.parse( options, args);
		} catch (ParseException e) {
			printHelpAndExit(options);
		}
		return null;
	}


}
