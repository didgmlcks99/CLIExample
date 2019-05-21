package edu.handong.csee.java.examples;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Runner {
	
	Boolean fullpath;
	String path;
	boolean verbose;
	boolean help;

	public static void main(String[] args) {

		Runner myRunner = new Runner();
		myRunner.run(args);

	}

	private void run(String[] args) {
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
			
			int count=0;
			File dir = new File(path);
			if(fullpath) {
				System.out.println("fullpath is " + fullpath + ", you turned on -f option, it will print fullpath name!");
				
				for(File file:dir.listFiles()) {
					try {
						System.out.println(file.getCanonicalPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count++;
				}
			}else {
				// path is required (necessary) data so no need to have a branch.
				System.out.println("You provided \"" + path + "\" as the value of the option p");
				
				for(File file:dir.listFiles()) {
						System.out.println(file.getPath());
						count++;
				}
			}
			
			
			if(verbose) {
				System.out.println("There was " + count + " numbers of files detected!");
				System.out.println("Your program is terminated. (This message is shown because you turned on -v option!)");
			}
		}
	}

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);
			
			path = cmd.getOptionValue("p"); 
			fullpath = cmd.hasOption("f");
			verbose = cmd.hasOption("v");
			help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	// Definition Stage
	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("p").longOpt("path")
				.desc("Set a path of a directory or a file to display")
				.hasArg()
				.argName("Path name to display")
				.required()
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("f").longOpt("fullpath")
				.desc("Set a fullpath of a directory or a file to display")
				//.hasArg()
				.argName("FullPath name to display")
				//.required()
				.build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("v").longOpt("verbose")
				.desc("Display detailed messages!")
				//.hasArg()     // this option is intended not to have an option value but just an option
				.argName("verbose option")
				//.required() // this is an optional option. So disabled required().
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
		        .desc("Help")
		        .build());

		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "CLI test program";
		String footer ="\nPlease report issues at https://github.com/lifove/CLIExample/issues";
		formatter.printHelp("CLIExample", header, options, footer, true);
	}

}