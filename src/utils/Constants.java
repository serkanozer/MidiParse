package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

public class Constants {
	// bl = byte length of
	public static final short blHeaderChunkHeader = 4;
	public static final short blHeaderChunkHeaderLength = 4;
	public static final short blHeaderChunkFormat = 2;
	public static final short blHeaderChunkNumberOfTrackChunks = 2;
	public static final short blHeaderChunkDivision = 2;
	public static final short blHeaderChunk = blHeaderChunkHeader
			+ blHeaderChunkHeaderLength + blHeaderChunkFormat
			+ blHeaderChunkNumberOfTrackChunks + blHeaderChunkDivision;
	
	public static final short blTrackChunkHeader = 4;
	public static final short blTrackChunkLength = 4;
	public static final short blTrackHeaderChunk = blTrackChunkHeader + blTrackChunkLength;
	public static final short blMetaEventType = 1;
	public static final short blEventType = 1;
	public static final String noteNamesWithSharps[] = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#",
			"A", "A#", "B" };
	public static final String noteNamesWithFlats[] = { "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab",
		"A", "Bb", "B" };
	public static final short clockSignalsPerClick = 24;
	public static final int MICROSECONDS_PER_MINUTE = 60000000;
	public static final Map<Integer, String> eventMap;
	static {
		eventMap = readDat("midispecs.dat");
	}
	public static final Map<Integer, String> realSysExMap;
	static {
		realSysExMap=readDat("SysExReal.dat");
	}
	public static final Map<Integer, String> nonRealSysExMap;
	static {
		nonRealSysExMap=readDat("SysExNonReal.dat");
	}
	public static final Map<Integer, String> manifacturersMap;
	static {
		manifacturersMap=readDat("Manifacturers.dat");
	}
	public static final Map<Integer, String> programChangesMap;
	static {
		programChangesMap=readDat("ProgramChange.dat");
	}
	public static Map<Integer, String> readDat(String filename) {
		FileInputStream saveFile = null;
		try {
			saveFile = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ObjectInputStream save = new ObjectInputStream(saveFile);

			@SuppressWarnings("unchecked")
			Map<Integer, String> specMap = (Map<Integer, String>) save
					.readObject();

			save.close();
			return specMap;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
