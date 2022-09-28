import java.util.Arrays;

public class GuitarTab {
    protected int[] bottomE;
    protected int[] aString;
    protected int[] dString;
    protected int[] gString;
    protected int[] bString;
    protected int[] topE;
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
    }

    public void addNoteToTab(String note, int octave, int tick){


        String fullNote = checkIfInRange(note,octave); //checking if the note is in range and correcting it to the closest note if not.


        //check if note is within certain range else recursivley call

        
        if(Arrays.asList(BOTTOM_E_NOTE_NAMES).contains(fullNote)){
            this.bottomE[tick] = (char) Arrays.asList(BOTTOM_E_NOTE_NAMES).indexOf(fullNote);
        }else if(Arrays.asList(A_STRING_NOTE_NAMES).contains(fullNote)) {
            this.aString[tick] = (char) Arrays.asList(A_STRING_NOTE_NAMES).indexOf(fullNote);
            System.out.println("A: "+ this.aString[tick]);
        }else if(Arrays.asList(D_STRING_NOTE_NAMES).contains(fullNote)) {
            this.dString[tick] = (char) Arrays.asList(D_STRING_NOTE_NAMES).indexOf(fullNote);
            System.out.println("D: "+ this.dString[tick]);
        }else if(Arrays.asList(G_STRING_NOTE_NAMES).contains(fullNote)) {
            this.gString[tick] = (char) Arrays.asList(G_STRING_NOTE_NAMES).indexOf(fullNote);
            System.out.println("G: "+ this.gString[tick]);
        }else if(Arrays.asList(B_STRING_NOTE_NAMES).contains(fullNote)) {
            this.bString[tick] = (char) Arrays.asList(B_STRING_NOTE_NAMES).indexOf(fullNote);
            System.out.println("B: "+ this.bString[tick]);
        }else if(Arrays.asList(TOP_E_NOTE_NAMES).contains(fullNote)) {
            this.topE[tick] = (char) Arrays.asList(TOP_E_NOTE_NAMES).indexOf(fullNote);
            System.out.println("Top E: "+ this.topE[tick]);
        }



    }

    /*
    Need to check if note can exist, for example j# is not a note. wont likely comeup but want to reduce possible errors.
     */


    public static String checkIfInRange(String note, int octave){
        if(octave==4){//check if note is in lowest possible octave
            if(Arrays.asList(BOTTOM_E_NOTE_NAMES).contains(note+Integer.toString(octave))){ //check if note is in the Bottom E array
                return note+Integer.toString(octave); //return string if present
            }

            octave+=1; //increment by an octave if not present
            return note+Integer.toString(octave); //return new note

        } else if (octave<4) {//if octave is less than 4 increment the octave and try the algorithm again recursivley
            octave+=1;
            return checkIfInRange(note, octave);
        }

        if(octave==8){
            if(Arrays.asList(TOP_E_NOTE_NAMES).contains(note+Integer.toString(octave))){
                return note+Integer.toString(octave);
            }

            octave-=1;
            return note+Integer.toString(octave);
        } else if (octave>8) {
            octave-=1;
            return checkIfInRange(note,octave);
        }
        return null;
    }


    public void printTab() {
        printGuitarString(this.bottomE, 96);
        printGuitarString(this.aString,96);
        printGuitarString(this.dString,96);
        printGuitarString(this.gString,96);
        printGuitarString(this.bString,96);
        printGuitarString(this.topE,96);
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
        System.out.println(GuitarTab.checkIfInRange("G",19));

    }

}
