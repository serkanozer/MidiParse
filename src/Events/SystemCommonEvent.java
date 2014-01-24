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
			int type = paramater1 & 70;
			type >>= 4;
			type &= 0x08;
			int values = paramater1 & 0x0F;
			firstParamaterValue = type + "," + values;
			break;
		case SONGPOSITIONPOINTER:
			firstParameterName = "LSB";
			firstParamaterValue = Integer.toString(paramater1);
			secondParameterName = "MSB";
			secondParamaterValue = Integer.toString(paramater2);
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
			System.out
					.println(secondParameterName + " " + secondParamaterValue);
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
}
