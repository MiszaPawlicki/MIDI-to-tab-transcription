import java.io.File;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class FileReader {
    public static final int NOTE_ON = 0x90; //hex value to represent a note being turned on
    public static final int NOTE_OFF = 0x80; //hex value to represent a note being turned off
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

    public static void main(String[] args) throws Exception {
        Sequence sequence = MidiSystem.getSequence(new File("C:\\Users\\misza\\Documents\\work\\year 3\\project\\project_test_midi.mid"));

        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) { //for loop that loops through each track
            trackNumber++; //increment track number
            System.out.println("Track " + trackNumber + ": size = " + track.size()); //print each track number and size
            System.out.println();
            for (int i=0; i < track.size(); i++) { //for loop that loops through each event in a given track
                MidiEvent event = track.get(i); //get the current midi event and store in MidiEvent track
                System.out.print("@" + event.getTick() + " "); //print the current tick
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    System.out.print("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON) { //print data for note on
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                    } else if (sm.getCommand() == NOTE_OFF) { //print data for note off
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                    } else {
                        System.out.println("Command:" + sm.getCommand());
                    }
                } else {
                    System.out.println("Other message: " + message.getClass());
                }
            }

            System.out.println();
        }

    }
}


