package midiread;

public enum MidiFormat {
	SINGLE_TRACK("single track file format"),
	MULTIPLE_TRACK("multiple track file format"),
	MULTIPLE_SONG("multiple song file format");
	

	String Description;
	MidiFormat(String Description){


		this.Description=Description;
	}
	public String getDescription() {
		return Description;
	}
}
