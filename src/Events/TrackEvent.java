package Events;

import utils.Constants;

public class TrackEvent {
	
	long occurenceTime;
	long deltaTime;
	int eventType;
	public TrackEvent(long deltaTime,int eventType){
		this.deltaTime=deltaTime;
		this.eventType=eventType;
	}
	public long getDeltaTime() {
		return deltaTime;
	}
	public void setDeltaTime(long deltaTime) {
		this.deltaTime = deltaTime;
	}
	public void setOccurenceTime(long occurenceTime){
		this.occurenceTime = occurenceTime;
	}
	public long getOccurenceTime(){
		return occurenceTime;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public void printInfo(){

		System.out.println("Occured at " +occurenceTime+ " deltaTime "+ " "+deltaTime+" eventType "+ " "+Constants.eventMap.get(eventType));
		
	}
	public int size(){
		return 0;
	}
	public TrackEvent(){
		
	}
}
