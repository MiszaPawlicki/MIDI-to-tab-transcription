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

    public static final String[] BOTTOM_E_NOTE_NAMES = {"E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4","C5", "C#5", "D5", "D#5","E5", "F5", "F#5", "G5", "G#5", "A5", "A#5", "B5","C6", "C#6", "D6", "D#6","E6"};
    public static final String[] A_STRING_NOTE_NAMES = {"A4", "A#4", "B4","C5", "C#5", "D5", "D#5","E5", "F5", "F#5", "G5", "G#5", "A5", "A#5", "B5","C6", "C#6", "D6", "D#6","E6","F6","F#6","G6","G#6","A6"};
    public static final String[] D_STRING_NOTE_NAMES = {"D5", "D#5","E5", "F5", "F#5", "G5", "G#5", "A5", "A#5", "B5","C6", "C#6", "D6", "D#6","E6","F6","F#6","G6","G#6","A6","A#6","B6","C7","C#7","D7"};
    public static final String[] G_STRING_NOTE_NAMES = {"G5", "G#5", "A5", "A#5", "B5","C6", "C#6", "D6", "D#6","E6","F6","F#6","G6","G#6","A6","A#6","B6","C7","C#7","D7","D#7","E7","F7","F#7","G7"};
    public static final String[] B_STRING_NOTE_NAMES = {"B5","C6", "C#6", "D6", "D#6","E6","F6","F#6","G6","G#6","A6","A#6","B6","C7","C#7","D7","D#7","E7","F7","F#7","G7","G#7","A7","A#7","B7"};
    public static final String[] TOP_E_NOTE_NAMES = {"E6", "F6", "F#6", "G6", "G#6", "A6", "A#6", "B6","C7", "C#7", "D7", "D#7","E7", "F7", "F#7", "G7", "G#7", "A7", "A#7", "B7","C8", "C#8", "D8", "D#8","E8"};



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

    public void generateTab(ArrayList<Note> notes){

        int count = 0;
        Note previous_note = null;
        Note current_note = null;
        ArrayList<Note> simultanious_notes = new ArrayList<>();
        for(int i = 0; i<notes.size();i++) {
            //if single note call place note
            if(i==13){
                System.out.println();
            }
            current_note = notes.get(i);
            if(previous_note==null||previous_note.tick==current_note.tick){
                simultanious_notes.add(current_note);
            }
            else{
                if (simultanious_notes.size()==1){
                    placeSingleNote(previous_note);
                }else{
                    try {
                        boolean[][] note_matrix = configureNotes(simultanious_notes);
                        //place notes
                        placeMultipleNotes(note_matrix,simultanious_notes);
                        System.out.println();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    simultanious_notes.clear();
                    simultanious_notes.add(current_note);
                }
            }

            previous_note = current_note;
            if(i==notes.size()-1){
                try {
                    boolean[][] note_matrix = configureNotes(simultanious_notes);
                    //place notes
                    placeMultipleNotes(note_matrix,simultanious_notes);
                    System.out.println();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if(simultanious_notes.size()==1){
            placeSingleNote(current_note);
        }


        System.out.println();
    }

    public void placeMultipleNotes(boolean note_matrix[][], ArrayList<Note> notes){
        int counter = 0;
        for (Note note:notes) {
            placeNote(note_matrix[counter++],note.full_note_name,(int)note.tick);//this can be refactored
        }
    }

    public void placeSingleNote(Note note){
        boolean note_array[];//initialise array to show if a note can be played (from bottom E to top E)
        note_array = findFretLocations(note);//find possible fret locations
        //no need to check if string is empty as it is the only note being placed
        placeNote(note_array, note.full_note_name, (int)note.tick);//place the note according to which notes are free
    }

    public boolean[][] configureNotes(ArrayList<Note> notes) throws Exception {
        //check if each note is in range
        boolean[][] note_matrix = new boolean[6][];
        int counter = 0;
        for (Note note:notes) {
            note = checkIfInRange(note);
            note_matrix[counter++] = findFretLocations(note);
        }
        //check if there can be placement without clashes
        //resolve any clashes
        //place notes
        for (int i=0;i<notes.size();i++){//for each note
            int true_count = 0;
            for(int j=0; j<6;j++){//for each string
               if(note_matrix[i][j]==true){
                   true_count++;
               }
            }
            //if count ==1
            //set note
            if(true_count==1){
                int note_index = 0;
                while(true){
                    if(note_matrix[i][note_index]==true){
                        break;
                    }else if(note_index>5){
                        throw new Exception();
                    }
                    note_index++;
                }
                note_matrix = Set(note_matrix,i+1, notes.size(), note_index);
            }
            //if count == 0
            //shift
            if(true_count==0){
                while (true){
                    notes.get(i).setNote_octave(notes.get(i).note_octave+1);
                    //check to see if string has been used already
                    boolean[] temp_frets = findFretLocations(notes.get(i));
                    System.out.println();
                    for(int y = 0; y<i;y++){
                        for(int x = 0; x<6;x++){
                            if(note_matrix[y][x]==true){
                                temp_frets[x]=false;
                            }
                        }
                    }
                    int temp_true_count=0;
                    for (Boolean bool:temp_frets) {
                        if(bool==true){
                            temp_true_count++;
                        }
                    }
                    if(temp_true_count==1){
                        int note_index = 0;
                        while(true){
                            if(temp_frets[note_index]==true){
                                note_matrix[i] = temp_frets;
                                break;
                            }else if(note_index>5){
                                throw new Exception();
                            }
                            note_index++;
                        }
                        note_matrix = Set(note_matrix,i+1, notes.size(), note_index);
                        break;
                    } else if (temp_true_count>1) {
                        note_matrix[i] = temp_frets;
                        break;
                    }
                    //check true count
                        //set if 1, break if >1
                }
            }

        }
        return note_matrix;

    }

    public static boolean[][] Set(boolean note_matrix[][],int start_index, int end_index, int string_index){
        for(int i = start_index;i<end_index;i++){
            note_matrix[start_index++][string_index]=false;
        }
        return note_matrix;
    }

    public static Note shiftNote(Note note, int shift){
        note.setNote_octave(note.note_octave+shift);
        return note;
    }

    public static boolean[] findFretLocations(Note note){
        boolean bottom_e = false;
        boolean a_string = false;
        boolean d_string = false;
        boolean g_string = false;
        boolean b_string = false;
        boolean top_e = false;
        int count = 0;

        Note in_range = checkIfInRange(note);
        if(in_range!=null){
            note = in_range;
        }

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

    boolean[] checkIfEmpty(boolean[] note_array, int tick){
        int counter = 0;
        for (int[] string: all_strings) {
            if(note_array[counter]==true&&string[tick]!=-1){
                note_array[counter]=false;
            }
            counter++;
        }

        return note_array;
    }

    public void placeNote(boolean[] bool_notes, String note, int tick){
        Random rand = new Random();
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

                break;
            }
        }
    }

    public static Note checkIfInRange(Note note){

        if(note.note_octave==4){//check if note is in lowest possible octave
            if(Arrays.asList(BOTTOM_E_NOTE_NAMES).contains(note.full_note_name)){ //check if note is in the Bottom E array
                return note; //return string if present
            }

            note.setNote_octave(note.note_octave+1); //increment by an octave if not present
            return note; //return new note

        } else if (note.note_octave<4) {//if octave is less than 4 increment the octave and try the algorithm again recursivley
            note.setNote_octave(note.note_octave+1);
            return checkIfInRange(note);
        }

        if(note.note_octave==8){
            if(Arrays.asList(TOP_E_NOTE_NAMES).contains(note.full_note_name)){
                return note;
            }

            note.setNote_octave(note.note_octave-1);
            return note;
        } else if (note.note_octave>8) {
            note.setNote_octave(note.note_octave-1);
            return note;
        }
        return note;
    }

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

    public static void main(String[] args){
        //System.out.println(GuitarTab.checkIfInRange("G",19));

    }

}
