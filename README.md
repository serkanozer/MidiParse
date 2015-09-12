# MidiParse
A Java Midi Parser.

#Example
See src/midiread/Engine.java for how to read midi files and create midi Track objects.

#Command Line Interface
usage: MidiParse midifile options--
 
 -h                  Prints header content of midi file
 -help               Prints this help message
 -meta               Print meta events
 -midi               Print midi events
 -note               Print note on and note off events
 -sys                Print system exclusive events
 -t <track number>   Print the required track


