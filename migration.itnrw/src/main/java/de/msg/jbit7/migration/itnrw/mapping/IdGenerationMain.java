package de.msg.jbit7.migration.itnrw.mapping;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.ConversionService;

public class IdGenerationMain {

	private static final String XML_APPLICATION_CONFIGURATION = "classpath:beans.xml";

	public static void main(String[] args)  {
		
		final CommandLine cmd = parse(args);
		
		
		final boolean overwrite = cmd.hasOption("f");
		
		final String mandatorAsString = cmd.getOptionValue("m");
		try (final AbstractRefreshableConfigApplicationContext applicationContext = new ClassPathXmlApplicationContext(XML_APPLICATION_CONFIGURATION)) {
			final ConversionService conversionService = applicationContext.getBean(ConversionService.class);
			applicationContext.getBean(AbstractIdGenerationService.class).createIds(conversionService.convert(mandatorAsString, Long.class), overwrite);
		}
	
		System.exit(0);
	}

	private static CommandLine parse(String[] args) {
		final Options options = new Options();
		
		options.addOption("f", false , "Overwrite existing idMapping.");
		options.addOption("h", false , "Print help.");
		options.addOption("m", true, "Mandators id.");
		
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

	private static CommandLine parse(String[] args, final Options options, final CommandLineParser parser) {
			
		try {
			return parser.parse( options, args);
		} catch (ParseException e) {
			printHelpAndExit(options);
		}
		return null;
	}

	private static void printHelpAndExit(final Options options) {
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "generateIds.sh", options );
		
		System.exit(1);
	}

}
