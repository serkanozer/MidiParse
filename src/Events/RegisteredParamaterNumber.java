package Events;

public enum RegisteredParamaterNumber {
	PITCHBENDSENSITIVITY(0,"Pitch Bend Sensitivity"),
	CHANNELFINETUNING(1,"Channel Fine Tuning "),
	CHANNELCOARSETUNING(2,"Channel Coarse Tuning "),
	TUNINGPROGRAMCHANGE(3,"Tuning Program Change"),
	TUNINGBANKSELECT(4,"Tuning Bank Select"),
	MODULATIONDEPTHRANGE(5,"Modulation Depth Range "),
	AZIMUTHANGLE(15616,"AZIMUTH ANGLE"),
	ELEVATIONANGLE(15617,"ELEVATION ANGLE"),
	GAIN(15618,"GAIN"),
	DISTANCERATIO(15619,"DISTANCE RATIO"),
	MAXIMUMDISTANCE(15620,"MAXIMUM DISTANCE"),
	GAINATMAXIMUMDISTANCE(15621,"GAIN AT MAXIMUM DISTANCE"),
	REFERENCEDISTANCERATIO(15622,"REFERENCE DISTANCE RATIO"),
	PANSPREADANGLE(15623,"PAN SPREAD ANGLE"),
	ROLLANGLE(15624,"ROLL ANGLE"),
	NULLFUNCTIONNUMBER(32639,"Null Function Number for RPN/NRPN");
	private RegisteredParamaterNumber(int num,String uiName){
		this.num=num;
		this.uiName=uiName;
	}
	public static RegisteredParamaterNumber getFromNumber(int number){
		for (RegisteredParamaterNumber meta : values()) {
			if(meta.num==number)
				return meta;
		}
		return null;
	}
	public static String getUiNameFromNumber(int number){
		for (RegisteredParamaterNumber meta : values()) {
			if(meta.num==number)
				return meta.uiName;
		}
		return null;
	}
	private int num;
	private String uiName;
}
