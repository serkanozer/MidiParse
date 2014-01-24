package midiread;

public enum MidiFormat {
	SINGLE_TRACK(0,"single track file format"),
	MULTIPLE_TRACK(1,"multiple track file format"),
	MULTIPLE_SONG(2," multiple song file format");
	
	int id;
	String Description;
	MidiFormat(int id, String Description){

		this.id=id;
		this.Description=Description;
	}
	public String getDescription() {
		return Description;
	}
}
