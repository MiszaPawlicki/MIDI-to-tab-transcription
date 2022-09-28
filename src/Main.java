import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InvalidMidiDataException, IOException {
        GuitarTab guitarTab = MidiFileReader.addToTab("project_test_midi.mid");
        guitarTab.printTab();
        //aaaa
    }
}