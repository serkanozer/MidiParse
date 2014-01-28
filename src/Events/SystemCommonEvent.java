package Events;

import utils.ByteUtils;


public class SystemCommonEvent extends TrackEvent {

	public SystemCommonEvent() {

	}

	public SystemCommonEvent(byte parameter1, byte parameter2) {
		this.paramater1 = ByteUtils.byteToInt(parameter1);
		this.paramater2 = ByteUtils.byteToInt(parameter2);
	}

	public SystemCommonEvent(byte parameter1) {
		this.paramater1 = ByteUtils.byteToInt(parameter1);

	}

	int paramater1;

	int paramater2;

	@SuppressWarnings("unused")
	public void printInfo() {
		System.out.println("Midi event");
		super.printInfo();
		SystemCommonEventType systemCommonEventType = getSystemCommonEventType();
		String firstParameterName = null;
		String secondParameterName = null;
		String firstParamaterValue = null;
		String secondParamaterValue = null;
		switch (systemCommonEventType) {
		case QUARTERFRAME:
			firstParameterName = "Message type , values";
			firstParamaterValue = getQuarterFrame(paramater1);
			break;
		case SONGPOSITIONPOINTER:
			firstParameterName = "Song Position Pointer";
			firstParamaterValue = getSongPositionPointer(paramater1, paramater2);
			break;
		case SONGSELECT:
			firstParameterName = "Selected Song";
			firstParamaterValue = Integer.toString(paramater1);
			break;
		default:
			break;
		}
		if (firstParameterName != null && firstParamaterValue != null)
			System.out.println(firstParameterName + " " + firstParamaterValue);
		if (secondParameterName != null && secondParamaterValue != null)
			System.out.println(secondParameterName + " " + secondParamaterValue);
	}
	public int size() {
		SystemCommonEventType systemCommonEventType = getSystemCommonEventType();
		if(systemCommonEventType==SystemCommonEventType.SONGPOSITIONPOINTER)
			return 2;
		return 1;
	}
	private SystemCommonEventType getSystemCommonEventType() {
		return SystemCommonEventType.getFromNumber(this.eventType);
	}
	private String getSongPositionPointer(int LSB, int MSB){
		int songPositionPointer = MSB;
		songPositionPointer <<=7;
		songPositionPointer |=LSB;
		return Integer.toString(songPositionPointer);
	}
	private String getQuarterFrame(int input){
		int type = input & 70;
		type >>= 4;
		type &= 0x08;
		int values = input & 0x0F;
		return type + "," + values;
	}
}
