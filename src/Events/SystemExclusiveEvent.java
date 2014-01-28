package Events;

import java.util.ArrayList;
import java.util.List;

import utils.ByteUtils;
import utils.Constants;

public class SystemExclusiveEvent extends TrackEvent {

	public SystemExclusiveEvent(long deltaTime, int eventType) {
		super(deltaTime, eventType);
		// TODO Auto-generated constructor stub
	}

	public SystemExclusiveEvent() {

	}

	public SystemExclusiveEvent(byte[] manufacturerId, ArrayList<Byte> dataBytes) {
		this.dataBytes = dataBytes;
		this.manufacturerId = manufacturerId;
	}

	public int size() {
		return manufacturerId.length + dataBytes.size();
	}

	public void printInfo() {
		System.out.println("System exclusive event");

		String manifacturer = Constants.manifacturersMap.get(ByteUtils.bytesToInt(manufacturerId));
		if(manifacturer==null)manifacturer="No manifacturer with given id is found";
		System.out.println("Manufacturer : "+manifacturer);
		super.printInfo();
		if (manufacturerId.length == 1) {
			int id = manufacturerId[0] & 0xff;
			int subid1 = dataBytes.get(0) & 0xff;
			if (id == 0x7E) {
				System.out.println("Non real time");
				System.out.println(getSysExString(id, subid1, 255));

				if (subid1 > 0x03 && subid1 <= 0x0C) {	//only this range has second sub id
					int subid2 = dataBytes.get(1) & 0xff;
					System.out.println(getSysExString(id, subid1, subid2));
				}
			} else if (id == 0x7F) { //only this range has second sub id
				System.out.println("Real time");
				System.out.println(getSysExString(id, subid1, 255));

				if (subid1 > 0x00 ) {
					int subid2 = dataBytes.get(1) & 0xff;
					System.out.println(getSysExString(id, subid1, subid2));
				}
			}

		}
	}
	private String getSysExString(int id,int subid1, int subid2){
		if (id == 0x7E) {

			return Constants.nonRealSysExMap.get(subid1 * 256 + subid2);
		} else if (id == 0x7F) {
			System.out.println("Real time");
			return Constants.realSysExMap.get(subid1 * 256 + subid2);

		}
		return Constants.realSysExMap.get(subid1 * 256 + subid2);
	}
	List<Byte> dataBytes;
	byte[] manufacturerId;
}
