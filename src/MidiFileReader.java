import org.w3c.dom.events.Event;

import java.io.File;
import java.io.IOException;
import javax.sound.midi.*;

public class MidiFileReader {

    public static final int NOTE_ON = 0x90; //value for note on
    public static final int NOTE_OFF = 0x80;//value for note off
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"}; //all possible notes regardless of octave that can be played



    public static GuitarTab addToTab(String path) throws InvalidMidiDataException, IOException {
        //read midi file in
        Sequence sequence = MidiSystem.getSequence(new File(path));

        //create tab object
        GuitarTab guitarTab = new GuitarTab((int) sequence.getTickLength());

        //loop through each track
        for(Track track : sequence.getTracks()){


            //loop through each track event
            for(int i=0; i<track.size();i++){
                MidiEvent event = track.get(i);

                /*
                strings from e6 to e4

                full range e4 to d8

                algo for string decision:

                shift entire track up if falling below, down if ahead. and then if there are isolated incidences of notes out of range,
                bring back to original note



                 */

                MidiMessage message = event.getMessage();
                if(message instanceof ShortMessage){
                    //print info including the tick, event type and note information
                    //System.out.print("Tick: " + event.getTick()+" - ");
                    ShortMessage sm = (ShortMessage) message;
                    //System.out.print("Channel: " + sm.getChannel() + " - ");
                    if (sm.getCommand() == NOTE_ON) { //print data for note on
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];

                        guitarTab.addNoteToTab(noteName,octave,(int)event.getTick());

                        //System.out.println("Note added, " + noteName + octave + " key=" + key);
                    }
                }


            }

        }
        return guitarTab;
    }

    public static void main(String[] args) throws Exception{
        /*
        //read midi file in
        Sequence sequence = MidiSystem.getSequence(new File("C:\\Users\\misza\\Documents\\work\\year 3\\project\\project_test_midi.mid"));

        //print ticks per quarter note
        System.out.println("\nTicks per quarter note (resolution) "+sequence.getResolution()+"\n");


        //total number of ticks
        System.out.println("Total ticks: "+sequence.getTickLength()+"\n");
        //loop through each track
        for(Track track : sequence.getTracks()){


            //loop through each track event
            for(int i=0; i<track.size();i++){
                MidiEvent event = track.get(i);


                MidiMessage message = event.getMessage();
                if(message instanceof ShortMessage){
                    //print info including the tick, event type and note information
                    System.out.print("Tick: " + event.getTick()+" - ");
                    ShortMessage sm = (ShortMessage) message;
                    System.out.print("Channel: " + sm.getChannel() + " - ");
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
                    }
                }


            }

        }

         */
        GuitarTab guitarTab = addToTab("C:\\Users\\misza\\Documents\\work\\year 3\\project\\project_test_midi.mid");
        guitarTab.printTab();
    }

}
