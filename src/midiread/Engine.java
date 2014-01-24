package midiread;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import utils.ByteUtils;
import utils.Constants;

public class Engine {
	static int current;
	public static byte b[];
	private static HeaderChunk header;
	private static TrackChunk tracks[];

	public static void main(String args[]) {


		getMIDIFile(args[0]);
		getMIDIHeader();
		getTrackChunks();
		for(int i=0; i<tracks.length; i++)
			tracks[i].printInfo();

	}
	public static void getMIDIFile(String filename){
		RandomAccessFile f;
		try {
			f = new RandomAccessFile(filename, "r");
			b = new byte[(int)f.length()];
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
		header.printHeaderInfo();
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
