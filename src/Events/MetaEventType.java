package Events;

public enum MetaEventType {
	SEQUENCENUMBER(0,"Sequence Number"),
	TEXTEVENT(1,"Text Event"),
	COPYRIGHT(2,"Copyright"),
	SEQUENCETRACKNAME(3,"Sequence/Track name"),
	INSTRUMENTNAME(4,"Instrument Name"),
	LYRICS(5,"Lyrics"),
	MARKER(6,"Marker"),
	CUEPOINT(7,"Cue Point"),
	MIDICHANNELPREFIX(32,"Midi Channel Prefix"),
	ENDOFTRACK(47,"End of track"),
	SETTEMPO(81,"Set tempo"),
	SMPTEOFFSET(84,"Smpte offset"),
	TIMESIGNATURE(88,"Time Signature"),
	KEYSIGNATURE(89,"Key Signature"),
	SEQUENCERSPECIFIC(127,"Sequencer Specific");
	
	
	
	private MetaEventType(int num,String uiName){
		this.num=num;
		this.uiName=uiName;
	}
	public static MetaEventType getFromNumber(int number){
		for (MetaEventType meta : values()) {
			if(meta.num==number)
				return meta;
		}
		return null;
	}
	public static String getUiNameFromNumber(int number){
		for (MetaEventType meta : values()) {
			if(meta.num==number)
				return meta.uiName;
		}
		return null;
	}
	private int num;
	private String uiName;
}
