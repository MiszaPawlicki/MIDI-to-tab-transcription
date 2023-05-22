import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class UnitTests {

    // testing readMidiFile
    @Test
    @DisplayName("Test that readMidiFile function reads a file and returns a list of notes")
    public void testReadMidiFile() throws Exception {
        ArrayList<Note> midiFileData = MidiFileReader.readMidiFile("Tabs/F_da_Milano/MIDI/001_FMRicercar01.mid");

        //check list is not null
        Assert.assertNotNull(midiFileData);

        //check the first note is correct
        Assert.assertEquals("G2", midiFileData.get(0).fullNoteName);

        //check the first note is at the correct tick
        Assert.assertEquals(0, midiFileData.get(0).tick);
    }

    // testing findFretLocations
    @Test
    @DisplayName("Test that findFretLocations returns the correct boolean array")
    public void testFindFretLocations(){
        //note that is present on one string
        String testNoteOne = "G2";

        //note that is present on two strings
        String testNoteTwo = "G#4";

        //note that is present on three strings
        String testNoteThree = "A3";

        //note that is present on zero strings
        String testNoteFour = "A9";

        boolean[] testOne = GuitarTab.findFretLocations(testNoteOne);
        boolean[] testTwo = GuitarTab.findFretLocations(testNoteTwo);
        boolean[] testThree = GuitarTab.findFretLocations(testNoteThree);
        boolean[] testFour = GuitarTab.findFretLocations(testNoteFour);

        //check the correct indexes of strings on the instrument are flagged as true
        Assert.assertArrayEquals(new boolean[]{true, false, false, false, false, false},testOne);
        Assert.assertArrayEquals(new boolean[]{false, false, false, false, true, true},testTwo);
        Assert.assertArrayEquals(new boolean[]{false, true, true, true, false, false},testThree);
        Assert.assertArrayEquals(new boolean[]{false, false, false, false, false, false},testFour);
    }

    // testing getSimultaneous notes
    @Test
    @DisplayName("Testing that getSimultaneousNotes returns the correct notes at a given tick")
    public void testGetSimultaneousNotes() throws Exception {
        MidiFileReader midiFileReader = new MidiFileReader("Tabs/F_da_Milano/MIDI/001_FMRicercar01.mid");
        GuitarTab guitarTab = new GuitarTab((int)midiFileReader.tickLength);

        // test for tick where there is a single note
        ArrayList<Note> testOne =  guitarTab.getSimultaneousNotes(960, midiFileReader.notes);

        // test for tick where there are multiple notes
        ArrayList<Note> testTwo =  guitarTab.getSimultaneousNotes(0, midiFileReader.notes);

        // test for tick where there are no notes
        ArrayList<Note> testThree =  guitarTab.getSimultaneousNotes(2, midiFileReader.notes);

        // check lists are of the correct length
        Assert.assertEquals(1,testOne.size());
        Assert.assertTrue(testTwo.size()>1);
        Assert.assertEquals( 0,testThree.size());
    }

    // testing randomlyPlaceNote
    @Test
    @DisplayName("Testing that randomlyPlaceNote correctly places an int in the correct place")
    public void testRandomlyPlaceNote() throws Exception {

        GuitarTab guitarTab = new GuitarTab(100);

        // test one - place a note with one possible fretting
        guitarTab.randomlyPlaceNote(new boolean[] {true, false,false,false,false,false},"G2", 0 );

        // test two - place note with two possible frettings
        guitarTab.randomlyPlaceNote(new boolean[]{false, false, false, false, true, true}, "G#4", 1);

        // test three - place note with three possible frettings
        guitarTab.randomlyPlaceNote(new boolean[]{false, true, true, true, false, false}, "A3", 2);

        // check index at 0 is not -1 (no note)
        boolean testOne = false;
        if(guitarTab.bottomE[0]!=-1){
            testOne = true;
        }

        // check index at 1 is not -1 (no note)
        boolean testTwo = false;
        if(guitarTab.bString[1]!=-1){
            testTwo = true;
        }else if(guitarTab.topE[1]!=-1){
            testTwo=  true;
        }

        // check index at 2 is not -1 (no note)
        boolean testThree = false;
        if(guitarTab.aString[2]!=-1){
            testThree = true;
        }else if(guitarTab.dString[2]!=-1){
            testThree = true;
        }else if(guitarTab.gString[2]!=-1){
            testThree = true;
        }

        Assert.assertTrue(testOne);
        Assert.assertTrue(testTwo);
        Assert.assertTrue(testThree);


    }

    // testing placeSingleNote
    @Test
    @DisplayName("Test that place single note works")
    public void testPlaceSingleNote() throws Exception {
        GuitarTab guitarTab = new GuitarTab(100);

        // place note with one possible fretting
        Note testNoteOne = new Note(0, "G",2);

        // place note with two possible frettings
        Note testNoteTwo = new Note(1, "G#",4);

        // place note with three possible frettings
        Note testNoteThree = new Note(2, "A",3);

        //place notes
        guitarTab.placeSingleNote(testNoteOne);
        guitarTab.placeSingleNote(testNoteTwo);
        guitarTab.placeSingleNote(testNoteThree);

        // check index at 0 is not -1 (no note)
        boolean testOne = false;
        if(guitarTab.bottomE[0]!=-1){
            testOne = true;
        }

        // check index at 1 is not -1 (no note)
        boolean testTwo = false;
        if(guitarTab.bString[1]!=-1){
            testTwo = true;
        }else if(guitarTab.topE[1]!=-1){
            testTwo=  true;
        }

        // check index at 2 is not -1 (no note)
        boolean testThree = false;
        if(guitarTab.aString[2]!=-1){
            testThree = true;
        }else if(guitarTab.dString[2]!=-1){
            testThree = true;
        }else if(guitarTab.gString[2]!=-1){
            testThree = true;
        }

        Assert.assertTrue(testOne);
        Assert.assertTrue(testTwo);
        Assert.assertTrue(testThree);
    }

    // testing checkMatrix
    @Test
    @DisplayName("Testing that matrix returns correct values when checking if there are any true values present")
    public void testCheckMatrix(){
        GuitarTab guitarTab = new GuitarTab(100);

        //matrix where all notes have a place to play
        boolean[][] testMatrixOne = new boolean[][]{{true, false, false, false, false, false,},
                {false, true, false, false, false, false},
                {false, false, false, false, false, true}};

        //matrix where some notes have more than one place to play a place to play
        boolean[][] testMatrixTwo = new boolean[][]{{true, false, false, false, false, false,},
                {false, false, true, true, false, false},
                {false, false, false, false, false, true}};

        //check function returns true and false correctly
        Assert.assertTrue(guitarTab.checkMatrix(testMatrixOne));
        Assert.assertFalse(guitarTab.checkMatrix(testMatrixTwo));
    }

    // testing lowestTrueCount
    @Test
    @DisplayName("Testing that lowestTrueCount returns the correct index of the note with the least number of frettings")
    public void testLowestTrueCount(){
        GuitarTab guitarTab = new GuitarTab(0);
        ArrayList<Integer> setChordList = new ArrayList<>();

        boolean[][] testMatrixOne = new boolean[][]{{true, false, false, false, false, false,},
                {false, true, true, false, false, false},
                {false, false, false, true, true, true}};
        boolean[][] testMatrixTwo = new boolean[][]{{true, true, false, false, false, false,},
                {false, false, false, true, true, true},
                {false, false, true, false, false, false}
                };
        boolean[][] testMatrixThree = new boolean[][]{{true, false, false, false, false, false,},
                {false, true, false, false, false, false},
                {false, false, true, false, false, false}
        };

        int testOne = guitarTab.lowestTrueCount(testMatrixOne,setChordList);
        int testTwo = guitarTab.lowestTrueCount(testMatrixTwo,setChordList);
        int testThree = guitarTab.lowestTrueCount(testMatrixThree,setChordList);

        //check index of lowest true count is correct
        Assert.assertEquals(0, testOne);
        Assert.assertEquals(2, testTwo);
        Assert.assertEquals(0, testThree); //should be 0 as first index to be lowest always returned
    }

    // testing pickRandomIndex
    @Test
    @DisplayName("Testing that pickRandomIndex can pick and return a random index of a string")
    public void testPickRandomIndex(){
        GuitarTab guitarTab = new GuitarTab(100);

        //where only one is true
        boolean[] testOneArray = new boolean[]{true,false,false,false,false,false};

        //where two are true
        boolean[] testTwoArray = new boolean[]{false,true,true,false,false,false};

        //where three are true
        boolean[] testThreeArray = new boolean[]{false,false,false,true,true,true};

        //getting random indexes
        int indexOne = guitarTab.pickRandomIndex(testOneArray);
        int indexTwo = guitarTab.pickRandomIndex(testTwoArray);
        int indexThree = guitarTab.pickRandomIndex(testThreeArray);

        Assert.assertTrue(indexOne==0);
        Assert.assertTrue(1 <= indexTwo && indexTwo <= 2);
        Assert.assertTrue(3 <= indexThree && indexThree <= 5);
    }

    // testing set
    @Test
    @DisplayName("Testing that set correctly marks strings as false when a note is placed on a string")
    public void testSet(){
        GuitarTab guitarTab = new GuitarTab(100);

        //test matrices
        boolean[][] noteMatrix = new boolean[][]{{true, true, true, false, false, false,},
                {false, true, true, true, false, false},
                {false, false, true, true, true, false}};

        boolean[][] goalMatrix = new boolean[][]{{true, true, false, false, false, false},
                {false, true, false, true, false, false},
                {false, false, true, false, false, false}};

        //setting the note
        boolean[][] testOne = guitarTab.set(noteMatrix, 2,2);

        //should set values to false for the locations in which other notes can play
        Assert.assertArrayEquals(goalMatrix, testOne);
    }

    // testing configureChord
    @Test
    @DisplayName("testing that configure chord can correctly find a valid fretting where all notes are placed")
    public void testConfigureChord(){
        GuitarTab guitarTab = new GuitarTab(100);
        ArrayList<Note> notes = new ArrayList<>();

        //notes to be placed
        Note noteOne = new Note(0,"G",2);
        Note noteTwo = new Note(0,"E",3);
        Note noteThree = new Note(0,"F",3);
        Note noteFour = new Note(0,"C#",4);
        Note noteFive = new Note(0,"D",4);
        Note noteSix = new Note(0,"C",5);

        notes.add(noteOne);
        notes.add(noteTwo);
        notes.add(noteThree);
        notes.add(noteFour);
        notes.add(noteFive);
        notes.add(noteSix);

        //configure the chord
        boolean[][] chord = guitarTab.configureChord(notes);

        //only possible fretting
        boolean[][] goalConfiguration = new boolean[][]{
                {true, false, false, false, false, false},
                {false, true, false, false, false, false},
                {false, false, true, false, false, false},
                {false, false, false, true, false, false},
                {false, false, false, false, true, false},
                {false, false, false, false, false, true}
        };

        //check correct
        Assert.assertArrayEquals(goalConfiguration, chord);
    }

    // testing generatePopulation
    @Test
    @DisplayName("Testing that the program can correctly generate a list of guitar tabs")
    public void testGeneratePopulation() throws Exception {
        //generate population
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        geneticAlgorithm.generatePopulation(100,"Tabs/F_da_Milano/MIDI/001_FMRicercar01.mid");

        //get population
        GuitarTab[] population = geneticAlgorithm.getPopulation();

        //check length
        Assert.assertTrue(population.length==100);

        //check all guitarTab objects
        for(GuitarTab gt : population){
            Assert.assertNotNull(gt);
        }
    }

    // testing euclideanDistance, serves as the fitness function
    @Test
    @DisplayName("Testing that euclidean distance calculates correctly")
    public void testEuclideanDistance(){
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        int[] testArrayOne = new int[]{0};
        int[] testArrayTwo = new int[]{0};
        int[] testArrayThree = new int[]{1,1};
        int[] testArrayFour = new int[]{2,2};


        double testOneTotal = geneticAlgorithm.euclideanDistance(testArrayOne,testArrayTwo);
        double testTwoTotal = geneticAlgorithm.euclideanDistance(testArrayThree,testArrayFour);

        //Not zero because its euclidean distance of string fret pain
        Assert.assertTrue(0.7071067811865476 == testOneTotal);

        //should be 2.8867213195971426
        Assert.assertTrue(2.8867213195971426 == testTwoTotal);
    }


    // testing tournamentSelection
    @Test
    @DisplayName("Testing tournament selection favours the correct tab")
    public void testTournamentSelection() throws Exception {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

        // create population randomly select 2
        geneticAlgorithm.generatePopulation(2,"Tabs/F_da_Milano/MIDI/001_FMRicercar01.mid");
        geneticAlgorithm.calculateEachMemberFitness();

        //tournament selection, should select the fittest, the fittest will be at index zero of the population
        ArrayList<Integer> indexList = geneticAlgorithm.tournamentSelection(1);
        int indexOfFittest = indexList.get(0);

        //check index is correct
        Assert.assertEquals(0,indexOfFittest);

    }

    // testing crossover
    @Test
    @DisplayName("Testing crossover correctly merges two tabs")
    public void testCrossover() throws Exception {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

        // create population randomly select 2
        geneticAlgorithm.generatePopulation(2,"Tabs/F_da_Milano/MIDI/001_FMRicercar01.mid");
        geneticAlgorithm.calculateEachMemberFitness();
        GuitarTab[] population = geneticAlgorithm.getPopulation();
        GeneticAlgorithm.crossover(population[0],population[1],0.5);
    }
    // testing mutation
    @Test
    @DisplayName("Testing mutation changes notes in the tab")
    public void testMutation() throws Exception {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

        // create population of 1
        geneticAlgorithm.generatePopulation(1,"Tabs/F_da_Milano/MIDI/001_FMRicercar01.mid");
        GuitarTab initialTab = geneticAlgorithm.getPopulation()[0];

        //create a copy for comparatative purposes
        GuitarTab initialTabCopy = new GuitarTab(geneticAlgorithm.getPopulation()[0].numTicks);

        initialTabCopy.bottomE = Arrays.copyOf(initialTab.bottomE,initialTab.bottomE.length);
        initialTabCopy.aString = Arrays.copyOf(initialTab.aString,initialTab.aString.length);
        initialTabCopy.dString = Arrays.copyOf(initialTab.dString,initialTab.dString.length);
        initialTabCopy.gString = Arrays.copyOf(initialTab.gString,initialTab.gString.length);
        initialTabCopy.bString = Arrays.copyOf(initialTab.bString,initialTab.bString.length);
        initialTabCopy.topE = Arrays.copyOf(initialTab.topE,initialTab.topE.length);

        //mutate the tab
        GuitarTab mutatedTab = geneticAlgorithm.mutate(initialTab, 1);

        //check at least one string array is different
        boolean tabsEqual = true;

        if(!Arrays.equals(initialTabCopy.bottomE,mutatedTab.bottomE)){
            tabsEqual = false;
        }else if(!Arrays.equals(initialTabCopy.aString,mutatedTab.aString)){
            tabsEqual = false;
        }else if(!Arrays.equals(initialTabCopy.dString,mutatedTab.dString)){
            tabsEqual = false;
        }else if(!Arrays.equals(initialTabCopy.gString,mutatedTab.gString)){
            tabsEqual = false;
        }else if(!Arrays.equals(initialTabCopy.bString,mutatedTab.bString)){
            tabsEqual = false;
        }else if(!Arrays.equals(initialTabCopy.topE,mutatedTab.topE)){
            tabsEqual = false;
        }

        //check at least one note has changed
        Assert.assertFalse(tabsEqual);

    }

    // testing randomlyChangeNote
    @Test
    @DisplayName("Testing randomly change note changes a note")
    public void testRandomlyChangeNote() throws Exception {
        GuitarTab guitarTab = new GuitarTab(100);

        //place a note that has two possible places to play
        Note noteOne = new Note(0,"C",3);
        ArrayList<Note> notes = new ArrayList<>();
        notes.add(noteOne);
        guitarTab.placeSingleNote(noteOne);

        //switch note to other possible location
        if(guitarTab.bottomE[0] != -1){
            guitarTab.randomlyChangeNote(0,0);

            //check original string now empty and new string has note placed
            Assert.assertTrue(guitarTab.bottomE[0]==-1);
            Assert.assertTrue(guitarTab.aString[0]!=-1);
        }else if(guitarTab.aString[0] != -1){
            guitarTab.randomlyChangeNote(0,0);

            //check original string not empty and new string has nothing placed
            Assert.assertTrue(guitarTab.bottomE[0]!=-1);
            Assert.assertTrue(guitarTab.aString[0]==-1);
        }


    }

}
