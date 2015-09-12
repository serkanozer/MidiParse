package midiread;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import utils.Constants;

public class Engine {
	static int current;
	public static byte b[];
	private static HeaderChunk header;
	private static TrackChunk tracks[];

	public static void main(String args[]) {
		Option help = new Option("help", false, "Prints this help message");
		Option h = new Option("h", false, "Prints header content of midi file");
		Option file = new Option("file", false, "Name of midi file");
		Option t = OptionBuilder.withArgName("track number").hasArg()
				.withDescription("Prints the required track").create("t");
		Option midi = new Option("midi", false, "Prints midi events");
		Option meta = new Option("meta", false, "Prints meta events");
		Option sysEx = new Option("sys", false, "Prints system exclusive events");
		Option note = new Option("note", false,
				"Print note on and note off events");

		Options options = new Options();
		options.addOption(help);
		options.addOption(h);

		options.addOption(t);
		options.addOption(midi);
		options.addOption(meta);
		options.addOption(sysEx);
		options.addOption(note);

		HelpFormatter formatter = new HelpFormatter();
		if (args.length < 1){
			formatter.printHelp("MidiParse .midi", options);
			System.exit(0);	
		}
		CommandLineParser parser = new GnuParser();
		try {
			// parse the command line arguments
			getMIDIFile(args[0]);
			getMIDIHeader();
			getTrackChunks();
			CommandLine line = parser.parse(options, args);
			if (line.hasOption("help"))
				formatter.printHelp("MidiParse .midi", options);
			if (line.hasOption("h"))
				header.printHeaderInfo();
			int optionsSet = 0;
			if (line.hasOption("midi")) {
				optionsSet |= 1;
			}
			if (line.hasOption("meta")) {
				optionsSet |= 2;
			}
			if (line.hasOption("sys")) {
				optionsSet |= 4;
			}
			if (line.hasOption("note")) {
				optionsSet |= 16;
			}
			if (line.hasOption("t")) {
				try {
					int trackNum = Integer.parseInt(line.getOptionValue("t"));
					if (trackNum <= 0)
						System.out.println("Track number should be at least 1");
					else if (trackNum > header.numOfTrackChunks())
						System.out.println("Track number should be at most "
								+ header.numOfTrackChunks());
					else
						tracks[trackNum].printInfo(optionsSet);
				} catch (NumberFormatException ne) {
					System.out.println("Track number should be an integer");
				}

			} else {
				for (int i = 0; i < tracks.length; i++)
					tracks[i].printInfo(optionsSet);
			}
		} catch (ParseException exp) {
			System.out.println("Unexpected exception:" + exp.getMessage());
		}

	}

	public static void getMIDIFile(String filename) {
		RandomAccessFile f;
		try {
			f = new RandomAccessFile(filename, "r");
			b = new byte[(int) f.length()];
			f.read(b);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void getMIDIHeader() {
		header = new HeaderChunk(b);
	}

	public static void getTrackChunks() {
		int numOfTracks = header.numOfTrackChunks();
		tracks = new TrackChunk[numOfTracks];
		int start = Constants.blHeaderChunk;
		for (int i = 0; i < numOfTracks; i++) {
			tracks[i] = new TrackChunk(b, start);
			start += tracks[i].getSize();
		}

	}

}
