package Events;

import utils.ByteUtils;
import utils.Constants;

public class MetaEvent extends TrackEvent {

	public MetaEvent(long deltaTime, int eventType, byte[] b, int current) {
		super(deltaTime, eventType);
		type = b[current];
		current++;

		byte target[] = ByteUtils.byteCopy(b, current,
				Math.min(b.length - current, 4));
		eventLength = (int) ByteUtils.ReadVarLen(target);
		current += ByteUtils.longLength(eventLength);
		if (eventLength > 0) {
			eventData = new byte[(int) eventLength];
			ByteUtils.byteCopy(b, eventData, current, (int) eventLength);
		}
		// TODO Auto-generated constructor stub
	}

	byte type;
	int eventLength;
	byte eventData[];

	public MetaEvent(byte type, int eventLength, byte eventData[]) {
		this.type = type;
		this.eventLength = eventLength;
		this.eventData = eventData;
	}

	public int size() {
		return Constants.blMetaEventType + ByteUtils.longLength(eventLength)
				+ eventLength;
	}

	public MetaEvent() {

	}

	public void printInfo() {
		System.out.println("Meta event");
		super.printInfo();
		System.out.println("Meta event type "
				+ MetaEventType.getUiNameFromNumber(ByteUtils.byteToInt(type)));
		MetaEventType metaEventType = MetaEventType.getFromNumber(ByteUtils
				.byteToInt(type));
		String dataString = null;
		switch (metaEventType) {
		case COPYRIGHT:
		case CUEPOINT:
		case SEQUENCENUMBER:
		case INSTRUMENTNAME:
		case LYRICS:
		case MARKER:
		case TEXTEVENT:

		case SEQUENCERSPECIFIC:
		case SEQUENCETRACKNAME:
			dataString = new String(eventData);
			break;

		case KEYSIGNATURE:
			int key = eventData[0] & 0xFF;
			boolean scale = eventData[1]>0 ;
			dataString = getKeyName(key,scale );
			break;

		case MIDICHANNELPREFIX:
			dataString = Integer.toString(ByteUtils.bytesToInt(eventData));
			break;

		case SETTEMPO:
			int mpqn = eventData[0] & 0xFF;
			dataString = "Tempo set: "+Constants.MICROSECONDS_PER_MINUTE/mpqn;
			break;
		case SMPTEOFFSET:
			int hour = eventData[0] & 0xFF;
			int min = eventData[1] & 0xFF;
			int sec = eventData[2] & 0xFF;
			int fr = eventData[3] & 0xFF;
			int subfr = eventData[4] & 0xFF;
			dataString ="hour: "+hour+" min: "+min+" sec: "+sec+" frame: "+fr+" subframe: "+subfr;
			break;
		case ENDOFTRACK:
			break;
		case TIMESIGNATURE:
			int numerator = eventData[0] & 0xFF;
			int denominator = (int) Math.pow(eventData[1] & 0xFF, 2);
			int metronomePulse = eventData[2] & 0xFF;
			int numOf32nds = eventData[3] & 0xFF;
			dataString = numerator + "/" + denominator + " metronome click "
					+ metronomePulse / Constants.clockSignalsPerClick
					+ " numOf32nds in a quarter note: " + numOf32nds;
			break;
		default:
			break;
		}
		if (eventLength > 0)
			System.out.println(dataString);
	}
	private String getKeyName(int numOfAccidentals, boolean isMinor){

		short c1=-5;

		String majMin = isMinor?"minor":"major";
		for(int i=0; i<12; i++){
			int currentAccidentals=(c1*i)%12;
			if(!(Math.abs(currentAccidentals)<6))
				currentAccidentals+=12;
			String tonicName ="";
			if(currentAccidentals==numOfAccidentals){
				if(numOfAccidentals<0)
					tonicName=Constants.noteNamesWithFlats[i];
				else{
					tonicName=Constants.noteNamesWithSharps[i];
				}
				return tonicName +" "+majMin;
			}
			
		}
		
		return null;
		
	}
}
