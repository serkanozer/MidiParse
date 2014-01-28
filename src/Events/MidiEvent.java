package Events;

import utils.ByteUtils;
import utils.Constants;

public class MidiEvent extends TrackEvent {

	public MidiEvent() {

	}

	public MidiEvent(byte parameter1, byte parameter2) {
		this.paramater1 = ByteUtils.byteToInt(parameter1);
		this.paramater2 = ByteUtils.byteToInt(parameter2);
	}
	public MidiEvent(byte parameter1) {
		this.paramater1 = ByteUtils.byteToInt(parameter1);

	}
	int paramater1;
	int paramater2;

	public void printInfo() {
		System.out.println("Midi event");
		super.printInfo();
		MidiEventType midiEventType = getMidiEventType();
		String firstParameterName = null;
		String secondParameterName = null;
		String firstParamaterValue = null;
		String secondParamaterValue = null;
		switch (midiEventType) {
		case NOTEON:
		case NOTEOFF:
			firstParameterName = "Note name";
			firstParamaterValue = getNoteName(paramater1);
			secondParameterName = "Velocity";
			secondParamaterValue = Integer.toString(paramater2);
			break;
		case NOTEAFTERTOUCH:
			firstParameterName = "Note Name";
			firstParamaterValue = getNoteName(paramater1);
			secondParameterName = "Aftertouch amount";
			secondParamaterValue = Integer.toString(paramater2);
			break;
		case CONTROLLER:
			firstParameterName = "Controlller Type";
			firstParamaterValue = Constants.eventMap.get(paramater1);
			secondParameterName = "Value";
			secondParamaterValue = Integer.toString(paramater2);
			break;
		case PROGRAMCHANGE:
			firstParameterName = "Instrument";
			firstParamaterValue = Constants.programChangesMap.get(paramater1);
			break;
		case CHANNELAFTERTOUCH:
			firstParameterName = "Aftertouch amount";
			firstParamaterValue = Integer.toString(paramater1);
			break;
		case PITCHBEND:
			firstParameterName = "Pitch bend:";
			int pitchBend = getPitchBend(paramater1, paramater2);
			firstParamaterValue = getPitchBend(paramater1, paramater2)+" "+getPitchBendRatio(pitchBend);
			break;
		default:
			break;
		}
		if (firstParameterName != null && firstParamaterValue != null)
			System.out.println(firstParameterName + " " + firstParamaterValue);
		if (secondParameterName != null&& secondParamaterValue != null)
			System.out.println(secondParameterName + " "
					+ secondParamaterValue);
	}

	public String getNoteName(int noteNumber) {
		String noteNames[] = Constants.noteNamesWithSharps;
		return noteNames[noteNumber % noteNames.length];
	}
	private int getPitchBend(int LSB, int MSB){
		int pitchBend = MSB;
		pitchBend <<=7;
		pitchBend |=LSB;
		return pitchBend;
	}
	private double getPitchBendRatio(int pitchBend){
		return (pitchBend-8192)/(4096*12.0);
	}
	public int size() {
		MidiEventType midiEventType = getMidiEventType();
		if(midiEventType==MidiEventType.PROGRAMCHANGE||midiEventType==MidiEventType.NOTEAFTERTOUCH)
			return 1;	//these events have only one parameters while others have two.
		return 2;
	}

	private MidiEventType getMidiEventType() {
		int midiEventType = this.eventType / 16;
		return MidiEventType.getFromNumber(midiEventType);
	}
}
