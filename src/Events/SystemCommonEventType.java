package Events;

public enum SystemCommonEventType {
	QUARTERFRAME(0xF1,"MIDI Time Code Quarter Frame."),
	SONGPOSITIONPOINTER(0XF2,"Song Position Pointer. "),
	SONGSELECT(0XF3,"Song Select");


	
	
	
	private SystemCommonEventType(int num,String uiName){
		this.num=num;
		this.uiName=uiName;
	}
	public static SystemCommonEventType getFromNumber(int number){
		for (SystemCommonEventType systemCommonEventType : values()) {
			if(systemCommonEventType.num==number)
				return systemCommonEventType;
		}
		return null;
	}
	public static String getUiNameFromNumber(int number){
		for (SystemCommonEventType systemCommonEventType : values()) {
			if(systemCommonEventType.num==number)
				return systemCommonEventType.uiName;
		}
		return null;
	}
	private int num;
	private String uiName;
}
