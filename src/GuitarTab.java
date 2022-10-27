import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GuitarTab {
    protected int[] bottomE;
    protected int[] aString;
    protected int[] dString;
    protected int[] gString;
    protected int[] bString;
    protected int[] topE;

    public final int[][] all_strings;// = {bottomE,aString,dString,gString,bString,topE};

    protected int numTicks;

    //notes

    public static final String[] BOTTOM_E_NOTE_NAMES = {"E3", "F3", "F#3", "G3", "G#3", "A3", "A#3", "B3","C4", "C#4", "D4", "D#4","E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4","C5", "C#5", "D5", "D#5","E5"};
    public static final String[] A_STRING_NOTE_NAMES = {"A3", "A#3", "B3","C4", "C#4", "D4", "D#4","E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4","C5", "C#5", "D5", "D#5","E5","F5","F#5","G5","G#5","A5"};
    public static final String[] D_STRING_NOTE_NAMES = {"D4", "D#4","E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4","C5", "C#5", "D5", "D#5","E5","F5","F#5","G5","G#5","A5","A#5","B5","C6","C#6","D6"};
    public static final String[] G_STRING_NOTE_NAMES = {"G4", "G#4", "A4", "A#4", "B4","C5", "C#5", "D5", "D#5","E5","F5","F#5","G5","G#5","A5","A#5","B5","C6","C#6","D6","D#6","E6","F6","F#6","G6"};
    public static final String[] B_STRING_NOTE_NAMES = {"B4","C5", "C#5", "D5", "D#5","E5","F5","F#5","G5","G#5","A5","A#5","B5","C6","C#6","D6","D#6","E6","F6","F#6","G6","G#6","A6","A#6","B6"};
    public static final String[] TOP_E_NOTE_NAMES = {"E5", "F5", "F#5", "G5", "G#5", "A5", "A#5", "B5","C6", "C#6", "D6", "D#6","E6", "F6", "F#6", "G6", "G#6", "A6", "A#6", "B6","C7", "C#7", "D7", "D#7","E7"};


    public GuitarTab(int numTicks) {
        this.bottomE = new int[numTicks];
        this.aString = new int[numTicks];
        this.dString = new int[numTicks];
        this.gString = new int[numTicks];
        this.bString = new int[numTicks];
        this.topE = new int[numTicks];

        Arrays.fill(this.bottomE, -1);
        Arrays.fill(this.aString, -1);
        Arrays.fill(this.dString, -1);
        Arrays.fill(this.gString, -1);
        Arrays.fill(this.bString, -1);
        Arrays.fill(this.topE, -1);

        this.numTicks = numTicks;
        all_strings = new int[][]{bottomE, aString, dString, gString, bString, topE};

    }
    ///////////////////

    public void generateTab(ArrayList<Note> notes){
        int last_tick = -1;
        ArrayList<Note> simultaneousNotes = new ArrayList<>();
        for (Note note:notes) {
            if(note.tick!=last_tick){
                simultaneousNotes = getSimultaneousNotes((int)note.tick,notes);
                if(simultaneousNotes.size()==1){
                    placeSingleNote(simultaneousNotes.get(0));
                }else{
                    configureChord(simultaneousNotes);
                }
            }

        }
    }

    public ArrayList<Note> getSimultaneousNotes(int tick,ArrayList<Note> notes){
        ArrayList<Note> simultaneousNotes = new ArrayList<>();
        for (Note note:notes) {
            if(note.tick==tick){
                simultaneousNotes.add(note);
            }
        }
        return simultaneousNotes;
    }

    public static void configureChord(ArrayList<Note> notes){//currently working on the assumption that all notes are in range and only up to six notes played at once

        boolean[][] note_matrix = new boolean[6][];//matrix of boolean values displaying whether it is possible to play the note or not on the string

        //assigning true or false values for whether a note can be played on a given string
        int counter = 0;
        for (Note note:notes) {
            note_matrix[counter++]=findFretLocations(note);
        }
        System.out.println();

        //working out the configuration of notes so they can all be played simultaneously

        //do until each array in note matrix only has 1 true value
            //select array with lowes true_count // if draw latest
                //if true_count==1
                    //set
                //else
                    //pick one at random
                    //set all others to false
                    //set
    }

    public boolean[][] set(boolean[][] note_matrix){
        return note_matrix;
    }
    public void placeSingleNote(Note note){
        boolean note_array[];//initialise array to show if a note can be played (from bottom E to top E)
        note_array = findFretLocations(note);//find possible fret locations
        //no need to check if string is empty as it is the only note being placed
        randomlyPlaceNote(note_array, note.full_note_name, (int)note.tick);//place the note according to which notes are free
    }

    public static boolean[] findFretLocations(Note note){
        boolean bottom_e = false;
        boolean a_string = false;
        boolean d_string = false;
        boolean g_string = false;
        boolean b_string = false;
        boolean top_e = false;
        int count = 0;



        if(Arrays.asList(BOTTOM_E_NOTE_NAMES).contains(note.full_note_name)){
            bottom_e=true;
            count++;
        }
        if(Arrays.asList(A_STRING_NOTE_NAMES).contains(note.full_note_name)){
            a_string=true;
            count++;
        }
        if(Arrays.asList(D_STRING_NOTE_NAMES).contains(note.full_note_name)){
            d_string=true;
            count++;
        }
        if(Arrays.asList(G_STRING_NOTE_NAMES).contains(note.full_note_name)){
            g_string=true;
            count++;
        }
        if(Arrays.asList(B_STRING_NOTE_NAMES).contains(note.full_note_name)){
            b_string=true;
            count++;
        }
        if(Arrays.asList(TOP_E_NOTE_NAMES).contains(note.full_note_name)){
            top_e=true;
            count++;
        }


        return new boolean[]{bottom_e, a_string, d_string, g_string, b_string, top_e};
    }

    public void randomlyPlaceNote(boolean[] bool_notes, String note, int tick){
        Random rand = new Random();

        checkIfPlayable(bool_notes);

        while(true){
            int i = rand.nextInt(0,6);
            if(bool_notes[i]){
                //need to check if string is empty or not
                switch (i) {
                    case (0) -> this.bottomE[tick] = Arrays.asList(BOTTOM_E_NOTE_NAMES).indexOf(note);
                    case (1) -> this.aString[tick] = Arrays.asList(A_STRING_NOTE_NAMES).indexOf(note);
                    case (2) -> this.dString[tick] = Arrays.asList(D_STRING_NOTE_NAMES).indexOf(note);
                    case (3) -> this.gString[tick] = Arrays.asList(G_STRING_NOTE_NAMES).indexOf(note);
                    case (4) -> this.bString[tick] = Arrays.asList(B_STRING_NOTE_NAMES).indexOf(note);
                    case (5) -> this.topE[tick] = Arrays.asList(TOP_E_NOTE_NAMES).indexOf(note);
                }
                //System.out.println(tick);
                break;
            }
        }
    }
    ///////////////////
    public void printTab(int resolution) {
        printGuitarString(this.topE,resolution);
        printGuitarString(this.bString,resolution);
        printGuitarString(this.gString,resolution);
        printGuitarString(this.dString,resolution);
        printGuitarString(this.aString,resolution);
        printGuitarString(this.bottomE, resolution);
    }

    public void printGuitarString(int[] guitarString, int resolution){
        int counter = 1;
        for(int note : guitarString){
            if(note==-1){
                counter++;
                if(counter == resolution/4){
                    System.out.print("-");
                    counter=0;
                }
            }else{
                System.out.print(note);
            }
        }
        System.out.println("");
    }

    public boolean checkIfPlayable(boolean[] note_array){//A function to check if there is a place for the note to be played
        if(note_array==new boolean[]{false,false,false,false,false,false}){
            try {
                throw new Exception();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    public static void main(String[] args){
        //System.out.println(GuitarTab.checkIfInRange("G",19));

    }

}
