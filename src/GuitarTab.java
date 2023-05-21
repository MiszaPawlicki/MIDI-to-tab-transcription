import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GuitarTab implements Comparable<GuitarTab> {
    protected int[] bottomE;
    protected int[] aString;
    protected int[] dString;
    protected int[] gString;
    protected int[] bString;
    protected int[] topE;

    public final int[][] allStrings;// = {bottomE,aString,dString,gString,bString,topE};

    protected int numTicks;
    protected double fitness;


    //all notes on a guitar in standard tuning
    /*public static final String[] BOTTOM_E_NOTE_NAMES = {"E3", "F3", "F#3", "G3", "G#3", "A3", "A#3", "B3","C4", "C#4", "D4", "D#4","E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4","C5", "C#5", "D5", "D#5","E5"};
    public static final String[] A_STRING_NOTE_NAMES = {"A3", "A#3", "B3","C4", "C#4", "D4", "D#4","E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4","C5", "C#5", "D5", "D#5","E5","F5","F#5","G5","G#5","A5"};
    public static final String[] D_STRING_NOTE_NAMES = {"D4", "D#4","E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4","C5", "C#5", "D5", "D#5","E5","F5","F#5","G5","G#5","A5","A#5","B5","C6","C#6","D6"};
    public static final String[] G_STRING_NOTE_NAMES = {"G4", "G#4", "A4", "A#4", "B4","C5", "C#5", "D5", "D#5","E5","F5","F#5","G5","G#5","A5","A#5","B5","C6","C#6","D6","D#6","E6","F6","F#6","G6"};
    public static final String[] B_STRING_NOTE_NAMES = {"B4","C5", "C#5", "D5", "D#5","E5","F5","F#5","G5","G#5","A5","A#5","B5","C6","C#6","D6","D#6","E6","F6","F#6","G6","G#6","A6","A#6","B6"};
    public static final String[] TOP_E_NOTE_NAMES = {"E5", "F5", "F#5", "G5", "G#5", "A5", "A#5", "B5","C6", "C#6", "D6", "D#6","E6", "F6", "F#6", "G6", "G#6", "A6", "A#6", "B6","C7", "C#7", "D7", "D#7","E7"};*/

    //classical guitar tabs
    /*public static final String[] BOTTOM_E_NOTE_NAMES = {"E2", "F2", "F#2", "G2", "G#2", "A2", "A#2", "B2","C3", "C#3", "D3", "D#3","E3", "F3", "F#3", "G3", "G#3", "A3", "A#3", "B3","C4", "C#4", "D4", "D#4","E4"};
    public static final String[] A_STRING_NOTE_NAMES = {"A2", "A#2", "B2","C3", "C#3", "D3", "D#3","E3", "F3", "F#3", "G3", "G#3", "A3", "A#3", "B3","C4", "C#4", "D4", "D#4","E4","F4","F#4","G4","G#4","A4"};
    public static final String[] D_STRING_NOTE_NAMES = {"D3", "D#3","E3", "F3", "F#3", "G3", "G#3", "A3", "A#3", "B3","C4", "C#4", "D4", "D#4","E4","F4","F#4","G4","G#4","A4","A#4","B4","C5","C#5","D5"};
    public static final String[] G_STRING_NOTE_NAMES = {"G3", "G#3", "A3", "A#3", "B3","C4", "C#4", "D4", "D#4","E4","F4","F#4","G4","G#4","A4","A#4","B4","C5","C#5","D5","D#5","E5","F5","F#5","G5"};
    public static final String[] B_STRING_NOTE_NAMES = {"B3","C4", "C#4", "D4", "D#4","E4","F4","F#4","G4","G#4","A4","A#4","B4","C5","C#5","D5","D#5","E5","F5","F#5","G5","G#5","A5","A#5","B5"};
    public static final String[] TOP_E_NOTE_NAMES = {"E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4","C5", "C#5", "D5", "D#5","E5", "F5", "F#5", "G5", "G#5", "A5", "A#5", "B5","C6", "C#6", "D6", "D#6","E6"};
*/
   //lute tabs
    public static final String[] BOTTOM_E_NOTE_NAMES = {"G2", "G#2", "A2", "A#2", "B2","C3", "C#3", "D3", "D#3","E3", "F3"};
    public static final String[] A_STRING_NOTE_NAMES = {"C3", "C#3", "D3", "D#3","E3", "F3", "F#3", "G3", "G#3", "A3", "A#3"};
    public static final String[] D_STRING_NOTE_NAMES = {"F3", "F#3", "G3", "G#3", "A3", "A#3", "B3","C4", "C#4", "D4", "D#4"};
    public static final String[] G_STRING_NOTE_NAMES = {"A3", "A#3", "B3","C4", "C#4", "D4", "D#4","E4","F4","F#4","G4"};
    public static final String[] B_STRING_NOTE_NAMES = {"D4", "D#4","E4","F4","F#4","G4","G#4","A4","A#4","B4","C5"};
    public static final String[] TOP_E_NOTE_NAMES = {"G4", "G#4", "A4", "A#4", "B4","C5", "C#5", "D5", "D#5","E5", "F5"};


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
        allStrings = new int[][]{bottomE, aString, dString, gString, bString, topE};

        fitness = 0;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    //randomly generate a guitar tab given a list of notes
    public void generateTab(ArrayList<Note> notes) throws Exception {

        int last_tick = -1;
        ArrayList<Note> simultaneousNotes;

        for (Note note:notes) {//loop through each note in the midi file
            if(note.tick!=last_tick){//check you are not at the last tick in the file
                //get all notes that are played at a given tick
                simultaneousNotes = getSimultaneousNotes((int)note.tick,notes);

                if(simultaneousNotes.size()==1){//if there is only one note played at a given tick
                    placeSingleNote(simultaneousNotes.get(0));
                }else{//if there is more than one note being played at a given tick

                    boolean[][] note_matrix = configureChord(simultaneousNotes);//find a valid permutation of strings the notes can be played on

                    for(int i = 0; i<note_matrix.length;i++){
                        if(Arrays.equals(note_matrix[i],new boolean[]{false, false, false, false, false, false})){
                            throw new Exception();
                        }
                        randomlyPlaceNote(note_matrix[i],simultaneousNotes.get(i).fullNoteName,(int)simultaneousNotes.get(i).tick);
                    }
                }
                last_tick = (int)note.tick;
            }

        }
    }

    //a function to return an array list of all notes played at a given tick
    public static ArrayList<Note> getSimultaneousNotes(int tick, ArrayList<Note> notes){
        ArrayList<Note> simultaneousNotes = new ArrayList<>();
        boolean done = false;
        //for each note in the note list. if the tick is equal to the tick parameter, add note to the simultaneous notes list
        for (Note note:notes) {
            if(note.tick==tick){
                done = true;
                simultaneousNotes.add(note);
            }else if (done == true){
                break;
            }
        }
        return simultaneousNotes;
    }

    public static boolean[][] configureChord(ArrayList<Note> notes){//currently working on the assumption that all notes are in range and only up to six notes played at once
        //create boolean array of size [number of notes][6]
        boolean[][] noteMatrix = new boolean[notes.size()][6];
        for(int i = 0; i<noteMatrix.length;i++){
            noteMatrix[i] = findFretLocations(notes.get(i).fullNoteName);
        }
        ArrayList<Integer> setChordList = new ArrayList<>();
        while(!checkMatrix(noteMatrix)){//do until each array in note matrix only has 1 true value
            //select array with lowes true_count // if draw latest
            int lowestCountIndex = lowestTrueCount(noteMatrix, setChordList);

            int randomIndex = pickRandomIndex(noteMatrix[lowestCountIndex]);
            //set all others to false
            set(noteMatrix,randomIndex,lowestCountIndex);
            //add index of set note to list
            setChordList.add(lowestCountIndex);

        }
        return noteMatrix;
    }


    static boolean checkMatrix(boolean[][] noteMatrix){//function to see if each array in the matrix has a true count of one true_count is 1 for each note, returns true
        for (boolean[] stringArray:noteMatrix) {
            int trueCount = 0;
            for (boolean string:stringArray) {
                if (string) {
                    trueCount++;
                }
            }
            if(trueCount<1){
                return false;
            }
        }
        return true;
    }

    //a function to return note with the least number of string available to play on
    static int lowestTrueCount(boolean[][] noteMatrix, ArrayList<Integer> setChordList){//loop through the matrix and return the note with the lowest true count
        int lowestCountIndex = 0;
        int lowestCount = 7;
        int trueCount;
        int indexCounter = 0;

        //loops through each array in the note matrix and counts the total number of true bool vars
        for (boolean[] stringArray:noteMatrix) {
            if((!setChordList.contains(indexCounter))){
                trueCount = 0;
                for(int i=0; i<=5;i++){
                    if(stringArray[i]){
                        trueCount++;
                    }
                }
                if(trueCount<lowestCount){
                    lowestCount=trueCount;
                    lowestCountIndex=indexCounter;
                }
            }

            indexCounter++;
        }

        return lowestCountIndex;
    }

    //select random index from playable notes
    static int pickRandomIndex(boolean[] stringArray){
        int randomIndex;
        ArrayList<Integer> trueIndexList = new ArrayList<>();

        for(int i = 0; i<=5;i++){
            if(stringArray[i]){
                trueIndexList.add(i);
            }
        }

        Random random = new Random();

        randomIndex = random.nextInt(trueIndexList.size());

        return trueIndexList.get(randomIndex);
    }


    public static void set(boolean[][] noteMatrix, int stringIndex, int noteIndex){//string index - index of the string being set, note index - index of the note being set
        /////FUNCTION TO SET ALL NOTES BUT ONE TO TRUE OF A CERTAIN STRING VALUE // need to alter to set the string itself.
        for(int i=0; i<noteMatrix.length;i++){
            if(i!=noteIndex){
                for(int j=0; j<=5;j++){
                    if(j==stringIndex){
                        noteMatrix[i][j]=false;
                    }
                }
            }else{
                for(int j=0; j<=5; j++){
                    if(j!=stringIndex){
                        noteMatrix[i][j]=false;
                    }
                }
            }

        }
    }
    public void placeSingleNote(Note note) throws Exception {
        boolean[] noteArray;//initialise array to show if a note can be played (from bottom E to top E)
        noteArray = findFretLocations(note.fullNoteName);//find possible fret locations
        //no need to check if string is empty as it is the only note being placed
        if(Arrays.equals(noteArray, new boolean[]{false, false, false, false, false, false})){
            throw new Exception();
        }
        randomlyPlaceNote(noteArray, note.fullNoteName, (int)note.tick);//place the note according to which notes are free
    }

    //a function to return a boolean array representing whether a note can be played on each string
    public static boolean[] findFretLocations(String fullNoteName){
        boolean bottomE = false;
        boolean aString = false;
        boolean dString = false;
        boolean gString = false;
        boolean bString = false;
        boolean topE = false;


        if(Arrays.asList(BOTTOM_E_NOTE_NAMES).contains(fullNoteName)){
            bottomE=true;
        }
        if(Arrays.asList(A_STRING_NOTE_NAMES).contains(fullNoteName)){
            aString=true;
        }
        if(Arrays.asList(D_STRING_NOTE_NAMES).contains(fullNoteName)){
            dString=true;
        }
        if(Arrays.asList(G_STRING_NOTE_NAMES).contains(fullNoteName)){
            gString=true;
        }
        if(Arrays.asList(B_STRING_NOTE_NAMES).contains(fullNoteName)){
            bString=true;
        }
        if(Arrays.asList(TOP_E_NOTE_NAMES).contains(fullNoteName)){
            topE=true;
        }


        return new boolean[]{bottomE, aString, dString, gString, bString, topE};
    }

    //given a note, the note is randomly placed on a string where it can be played
    public void randomlyPlaceNote(boolean[] boolNotes, String note, int tick){
        Random rand = new Random();

        checkIfPlayable(boolNotes);

        while(true){
            int i = rand.nextInt(0,6);
            if(boolNotes[i]){
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



    public void printTab(int resolution){
        boolean doubleDash;

        String e = "";
        String a = "";
        String d = "";
        String g = "";
        String b = "";
        String E = "";

        for(int i = 0; i<numTicks; i++){
            //print each string, if one fret has a note above the 9th octave, print 2 dashes instead of one
            doubleDash = false;

            if(i%(resolution*3)==0){
                e+="|-";
                a+="|-";
                d+="|-";
                g+="|-";
                b+="|-";
                E+="|-";
            }

            if(bottomE[i]==-1&&aString[i]==-1&&dString[i]==-1&&gString[i]==-1&&bString[i]==-1&&topE[i]==-1){
                if(i%numTicks/4==0){
                    e+="-";
                    a+="-";
                    d+="-";
                    g+="-";
                    b+="-";
                    E+="-";
                }
            }else{
                if(bottomE[i]>=10||aString[i]>=10||dString[i]>=10||gString[i]>=10||bString[i]>=10||topE[i]>=10){
                    doubleDash=true;
                }
                e+= noteToString(bottomE[i],doubleDash);
                a+= noteToString(aString[i],doubleDash);
                d+= noteToString(dString[i],doubleDash);
                g+= noteToString(gString[i],doubleDash);
                b+= noteToString(bString[i],doubleDash);
                E+= noteToString(topE[i],doubleDash);
            }
        }

        System.out.println(E);
        System.out.println(b);
        System.out.println(g);
        System.out.println(d);
        System.out.println(a);
        System.out.println(e);
    }

    String noteToString(int fret, boolean doubleDash){
        if(fret==-1){
            if(doubleDash){
                return "----";
            }else{
                return "--";
            }
        }else{
            if(doubleDash){
                if(fret<10){
                    return fret+"---";
                }else{
                    return fret+"--";
                }
            }
        }
        return fret+"-";
    }

    //a function to check if any values in a noteArray are true
    public static void checkIfPlayable(boolean[] noteArray){
        if(Arrays.equals(noteArray, new boolean[]{false, false, false, false, false, false})){
            try {
                throw new Exception();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    //A function to randomly change a note position to another valid place to play the same note
    public void randomlyChangeNote(int stringIndex, int noteIndex){
        //get note name
        int fretNumber;
        String noteName = "";
        switch (stringIndex) {
            case 0 -> {
                fretNumber = this.bottomE[noteIndex];
                noteName = BOTTOM_E_NOTE_NAMES[fretNumber];
            }
            case 1 -> {
                fretNumber = this.aString[noteIndex];
                noteName = A_STRING_NOTE_NAMES[fretNumber];
            }
            case 2 -> {
                fretNumber = this.dString[noteIndex];
                noteName = D_STRING_NOTE_NAMES[fretNumber];
            }
            case 3 -> {
                fretNumber = this.gString[noteIndex];
                noteName = G_STRING_NOTE_NAMES[fretNumber];
            }
            case 4 -> {
                fretNumber = this.bString[noteIndex];
                noteName = B_STRING_NOTE_NAMES[fretNumber];
            }
            case 5 -> {
                fretNumber = this.topE[noteIndex];
                noteName = TOP_E_NOTE_NAMES[fretNumber];
            }
        }

        //check if playable elsewhere
        boolean[] stringBoolean = findFretLocations(noteName);
        int trueCount = 0;
        for(boolean bool : stringBoolean){
            if(bool){
                trueCount++;
            }
        }
        if(trueCount>1){
            int[][] stringArray = {bottomE,aString,dString,gString,bString,topE};
            int counter = 0;


            for (int[] string: stringArray) {//for each string
                if(string[noteIndex]!=-1){//checks to see if note played at given tick
                    stringBoolean[counter]=false;
                }
                counter++;
            }
            trueCount = 0;
            for(boolean bool : stringBoolean){
                if(bool){
                    trueCount++;
                }
            }
            if(trueCount>0){
                randomlyPlaceNote(stringBoolean,noteName,noteIndex);
                switch (stringIndex) {
                    case 0 -> this.bottomE[noteIndex] = -1;
                    case 1 -> this.aString[noteIndex] = -1;
                    case 2 -> this.dString[noteIndex] = -1;
                    case 3 -> this.gString[noteIndex] = -1;
                    case 4 -> this.bString[noteIndex] = -1;
                    case 5 -> this.topE[noteIndex] = -1;
                }
            }
        }
    }


    public static void main(String[] args){

    }


    @Override
    public int compareTo(GuitarTab o) {
        return Double.compare(fitness,o.getFitness());
    }
}
