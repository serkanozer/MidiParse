package Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		System.out.println("Manufacturer : "+Constants.manifacturersMap.get(ByteUtils.bytesToInt(manufacturerId)));
		super.printInfo();
		if (manufacturerId.length == 1) {
			int id = manufacturerId[0] & 0xff;
			Map<Integer, String> exMap = new HashMap<Integer, String>();
			int sumid = 0;
			int subid1 = dataBytes.get(0) & 0xff;
			if (id == 0x7E) {
				System.out.println("Non real time");
				int subid2 = 255;
				sumid = subid1 * 256 + subid2;
				System.out.println(exMap.get(sumid));
				if (subid1 > 0x03 && subid1 <= 0x0C) {
					subid2 = dataBytes.get(1) & 0xff;
					sumid = subid1 * 256 + subid2;
					System.out.println(exMap.get(sumid));
				}
			} else if (id == 0x7F) {
				System.out.println("Real time");
				int subid2 = 255;
				sumid = subid1 * 256 + subid2;
				System.out.println(exMap.get(sumid));
				if (subid1 > 0x00 ) {
					subid2 = dataBytes.get(1) & 0xff;
					sumid = subid1 * 256 + subid2;
					System.out.println(exMap.get(sumid));
				}
			}

			System.out.println(exMap.get(sumid));
		}
	}

	List<Byte> dataBytes;
	byte[] manufacturerId;
	boolean isPredefined;
}
