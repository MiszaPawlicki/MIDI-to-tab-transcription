import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

public class UnitTests {
    public static int adder(int a, int b){
        return a+b;
    }

    @Test
    @DisplayName("Test that adder correctly adds 5+4")
    public void testAdder(){
        int a = 5;
        int b = 4;
        int result = adder(a,b);
        Assert.assertEquals(result, 9);
    }

    //Test midiFileReader

    @Test
    @DisplayName("Test that readMidiFile function reads a file and returns a list of notes")
    public void testReadMidiFile() throws Exception {
        ArrayList<Note> midiFileData = MidiFileReader.readMidiFile("Tabs/F_da_Milano/MIDI/001_FMRicercar01.mid");

        //check list is not null
        Assert.assertNotNull(midiFileData);

        //check the first note is correct
        Assert.assertEquals(midiFileData.get(0).fullNoteName, "G2");

        //check the first note is at the correct tick
        Assert.assertEquals(midiFileData.get(0).tick, 0);
    }

    //
}
