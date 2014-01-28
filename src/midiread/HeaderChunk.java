package midiread;

import java.io.IOException;
import java.io.RandomAccessFile;

import utils.ByteUtils;

public class HeaderChunk {
	static final int headerIdentifierLength=4,
					headerLengthLength=4,
					formatLength=2,
					numOfTrackChunksLength=2,
					divisionLength=2;
	byte [] headerIdentifier;
	byte [] headerLength;
	byte [] format;
	byte [] numOfTrackChunks;
	byte [] division;
	
	
	private void init(){
		headerIdentifier=new byte[headerIdentifierLength];
		headerLength=new byte[headerLengthLength];
		format=new byte[formatLength];
		numOfTrackChunks=new byte[numOfTrackChunksLength];
		division=new byte[divisionLength];
	}
	public void updateSeek(RandomAccessFile rf, long inc) throws IOException{
		
		rf.seek(rf.getFilePointer()+inc);
		
	}
	public HeaderChunk(RandomAccessFile rf){
		
		init();
		try {

			rf.read(headerIdentifier);
			rf.read(headerLength);
			rf.read(format);
			rf.read(numOfTrackChunks);
			rf.read(division);

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public HeaderChunk(byte file[]){
		init();
		int lastpos=0;
		lastpos+=ByteUtils.byteCopy(file, headerIdentifier, lastpos, headerIdentifierLength);	
		lastpos+=ByteUtils.byteCopy(file, headerLength, lastpos, headerLengthLength);
		lastpos+=ByteUtils.byteCopy(file, format, lastpos, formatLength);
		lastpos+=ByteUtils.byteCopy(file, numOfTrackChunks, lastpos, numOfTrackChunksLength);
		lastpos+=ByteUtils.byteCopy(file, division, lastpos, divisionLength);
		
	}


	public int format(){
		return ByteUtils.bytesToShort(this.format);
	}
	public String identifier(){
		return ByteUtils.bytesToString(headerIdentifier);
	}
	public String formatDescription(){
		return MidiFormat.values()[this.format()].getDescription();
	}
	public int numOfTrackChunks(){
		return ByteUtils.bytesToShort(numOfTrackChunks);
	}
	public int division(){
		return ByteUtils.bytesToShort(division);
	}
	public int length(){
		return ByteUtils.bytesToInt(headerLength);
	}
	public int headerTotalLength(){
		return headerIdentifierLength+headerLengthLength+formatLength+numOfTrackChunksLength+divisionLength;
	}
	public void printHeaderInfo(){
		System.out.println("Identifier: "+this.identifier());		
		System.out.println("Length: "+this.length());
		System.out.println("Format: "+ this.formatDescription());
		System.out.println("Number Of Track Chunks "+this.numOfTrackChunks());
		System.out.println("Time Division: "+this.division());
	}
	
}
