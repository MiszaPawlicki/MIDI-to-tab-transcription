import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.midi.*;

public class MidiFileReader {

    public static final int NOTE_ON = 0x90; //value for note on
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"}; //all possible notes regardless of octave that can be played
    public static String path;
    public static long tickLength;
    public static int resolution;
    public static ArrayList<Note> notes;

    public MidiFileReader(String path) throws Exception {
        this.path = path;
        this.resolution = getResolution(path);
        this.tickLength = getTickLength(path);
        this.notes = readMidiFile(path);
    }

    //a function to read a midi file in to a list of notes
    public static ArrayList<Note> readMidiFile(String path) throws Exception {
        //read midi file in
        Sequence sequence = MidiSystem.getSequence(new File(path));
        ArrayList<Note> noteArrayList = new ArrayList<>();

        //loop through each track
        for(Track track : sequence.getTracks()){

            //loop through each track event
            for(int i=0; i<track.size();i++){
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();

                if(message instanceof ShortMessage){
                    //print info including the tick, event type and note information
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == NOTE_ON) { //print data for note on
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        if(octave<=1){
                            throw new Exception("error");
                        }
                        int note = key % 12;

                        String noteName = NOTE_NAMES[note];
                        Note tempNote = new Note(event.getTick(),noteName,octave);

                        noteArrayList.add(tempNote);
                    }
                }
            }

        }
        return noteArrayList;
    }

    //function that returns the number of ticks in the midi file
    public static long getTickLength(String path) throws InvalidMidiDataException, IOException {
        Sequence sequence = MidiSystem.getSequence(new File(path));
        return sequence.getTickLength();
    }

    //function to return resolution of a midi file
    public static int getResolution(String path) throws InvalidMidiDataException, IOException {
        Sequence sequence = MidiSystem.getSequence(new File(path));
        return sequence.getResolution();
    }

    public static void main(String[] args) throws Exception{

    }
}
