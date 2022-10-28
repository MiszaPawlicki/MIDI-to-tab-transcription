import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InvalidMidiDataException, IOException {
        String path = "new.mid";

        MidiFileReader midiFileReader = new MidiFileReader(path);

        for (Note n: midiFileReader.notes) {
            System.out.println("NOTE: "+n.full_note_name+ " - TICK: "+n.tick);
        }

        GuitarTab guitarTab = new GuitarTab((int)midiFileReader.tick_length);
        guitarTab.generateTab(midiFileReader.notes);
        guitarTab.printTab(midiFileReader.resolution);
    }
}