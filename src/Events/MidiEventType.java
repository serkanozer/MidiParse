package Events;

public enum MidiEventType {
	NOTEOFF(8,"Note Off"),
	NOTEON(9,"Note On"),
	NOTEAFTERTOUCH(10,"Note Aftertouch"),
	CONTROLLER(11,"Controller"),
	PROGRAMCHANGE(12,"Program Change"),
	CHANNELAFTERTOUCH(13,"Channel Aftertouch"),
	PITCHBEND(14,"Pitch Bend");

	
	
	
	private MidiEventType(int num,String uiName){
		this.num=num;
		this.uiName=uiName;
	}
	public static MidiEventType getFromNumber(int number){
		for (MidiEventType midiEventType : values()) {
			if(midiEventType.num==number)
				return midiEventType;
		}
		return null;
	}
	public static String getUiNameFromNumber(int number){
		for (MidiEventType midiEventType : values()) {
			if(midiEventType.num==number)
				return midiEventType.uiName;
		}
		return null;
	}
	private int num;
	private String uiName;
}
