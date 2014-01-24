package midiread;

import utils.ByteUtils;
import utils.Constants;

public class TrackChunkHeader {
	byte [] identifier;
	byte [] length;

	public TrackChunkHeader(byte [] identifier, byte [] length){
		this.identifier=identifier;
		this.length=length;
	}

	public int tracklength(){
		return ByteUtils.bytesToInt(length);
	}
	public static int size(){
		return Constants.blTrackChunkHeader+Constants.blTrackChunkLength;
	}
	public void printInfo(){
		System.out.println("Track size: "+tracklength());
	}
}
