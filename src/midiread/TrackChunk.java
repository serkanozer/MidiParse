package midiread;

import java.util.ArrayList;
import java.util.Arrays;

import utils.ByteUtils;
import utils.Constants;
import Events.MetaEvent;
import Events.MidiEvent;
import Events.SystemCommonEvent;
import Events.SystemExclusiveEvent;
import Events.TrackEvent;

public class TrackChunk {
	private final int start;

	public TrackChunk(byte bytes[], int start) {
		this.start = start;
		this.setBytes(bytes);
		parseEvents(this.start);
	}

	public void parseEvents(int current) {
		setHeader(current);
		int end = start + Constants.blTrackChunkHeader
				+ Constants.blTrackChunkLength + trackChunkHeader.tracklength();
		current = current + Constants.blTrackChunkHeader
				+ Constants.blTrackChunkLength;
		while (current < end) {
			// byte target[] = ByteUtils.byteCopy(bytes, current,
			// Math.min(bytes.length - current, 4));
			long deltaTime = ByteUtils.ReadVarLen(bytes, current);
			current += ByteUtils.longLength(deltaTime);
			byte eventTypeByte = bytes[current];
			current += Constants.blEventType;
			int eventType = ByteUtils.byteToInt(eventTypeByte);
			TrackEvent trackEvent = getTrackEvent(eventType, current);
			current += trackEvent.size();
			trackEvent.setDeltaTime(deltaTime);
			trackEvent.setEventType(eventType);
			trackEvents.add(trackEvent);
		}
	}

	private TrackEvent getTrackEvent(int eventType, int current) {
		if (eventType < 0xC0 || (eventType >= 0xE0 && eventType < 0xF0)) {
			byte parameterFirst = bytes[current];
			current = current + 1;
			byte parameterSecond = bytes[current];
			return new MidiEvent(parameterFirst, parameterSecond);

		} else if ((0xC0 <= eventType) && (eventType < 0xE0)) {
			byte parameterFirst = bytes[current];
			current = current + 1;

			return new MidiEvent(parameterFirst);
		} else if (eventType == 0xFF) {
			byte metaEventType = bytes[current];
			current = current + Constants.blMetaEventType;
			int eventLength = (int) ByteUtils.ReadVarLen(bytes, current);
			current += ByteUtils.longLength(eventLength);
			byte eventData[];
			if (eventLength > 0) {
				eventData = new byte[(int) eventLength];
				ByteUtils
						.byteCopy(bytes, eventData, current, (int) eventLength);
			} else
				eventData = null;
			return new MetaEvent(metaEventType, eventLength, eventData);

		} else if (eventType == 0xF1 || eventType == 0xF3) {
			byte parameterFirst = bytes[current];
			current = current + 1;
			return new SystemCommonEvent(parameterFirst);

		} else if (eventType == 0xF2) {
			byte parameterFirst = bytes[current];
			current = current + 1;
			byte parameterSecond = bytes[current];
			return new SystemCommonEvent(parameterFirst, parameterSecond);

		} else {
			// eventType == 0xF0
			byte firstByte = bytes[current];
			byte manufacturerId[];

			if (firstByte != 0) {
				manufacturerId = ByteUtils.byteCopy(bytes, current, 3);
				current = current + 3;

			} else {
				manufacturerId = new byte[1];
				manufacturerId[0] = firstByte;
				current = current + 1;
			}
			ArrayList<Byte> dataBytes = new ArrayList<Byte>();
			while (bytes[current] != (byte) 0xF7) {
				dataBytes.add(bytes[current]);
				current++;
			}
			dataBytes.add(bytes[current]);
			return new SystemExclusiveEvent(manufacturerId,dataBytes);

		}
	}

	private void setHeader(int current) {

		byte[] identifier = Arrays.copyOfRange(bytes, current, current
				+ Constants.blTrackChunkHeader);
		current = current + Constants.blTrackChunkHeader;
		byte[] length = Arrays.copyOfRange(bytes, current, current
				+ Constants.blTrackChunkLength);
		this.trackChunkHeader = new TrackChunkHeader(identifier, length);
	}

	private TrackChunkHeader trackChunkHeader;
	private ArrayList<TrackEvent> trackEvents = new ArrayList<TrackEvent>();

	public int getSize() {
		return TrackChunkHeader.size() + trackChunkHeader.tracklength();
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void printInfo() {
		trackChunkHeader.printInfo();
		for (TrackEvent trackEvent : trackEvents) {
			trackEvent.printInfo();
		}
	}

	private byte[] bytes;

}
