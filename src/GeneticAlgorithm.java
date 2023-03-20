import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.util.*;

public class GeneticAlgorithm {

    private static GuitarTab[] population;
    private static ArrayList<Double> generational_fitness = new ArrayList<>();
    protected static MidiFileReader midiFileReader;
    //private static HashMap<int[], String> chordDictionary = new HashMap<>();
    public static HashSet<int[]> chordDictionary = new HashSet<>();

    /*private void populateChordDictionary(){
        chordDictionary.put(new int[]{0, 2, 2, 2, 0, 0},"G-Major");//gmajor
        chordDictionary.put(new int[]{0, 2, 2, 1, 0, 0},"G-Minor");//gminor
        chordDictionary.put(new int[]{0, 2, 0, 2, 3, 0},"G-Dom7");//gdom7
        chordDictionary.put(new int[]{0, 2, 2, 1, 3, 0},"G-Minor7");//gminor7
        chordDictionary.put(new int[]{0, 2, 1, 2, 0, 0},"G-Major7");//gmajor7
        chordDictionary.put(new int[]{0, 2, 2, 2, 2, 0},"G-Major6");//gmajor6

        chordDictionary.put(new int[]{0, 0, 2, 3, 2, 0},"C-Major");//cmajor
        chordDictionary.put(new int[]{0, 0, 2, 3, 1, 0},"C-Minor");//cminor
        chordDictionary.put(new int[]{0, 0, 2, 1, 2, 0},"C-Dom7");//cdom7
        chordDictionary.put(new int[]{0, 0, 2, 1, 1, 0},"C-Minor7");//cminor7
        chordDictionary.put(new int[]{0, 0, 2, 2, 2, 0},"C-Major7");//cmajor7
        chordDictionary.put(new int[]{0, 0, 2, 3, 2, 2},"C-Major6");//cmajor6

        chordDictionary.put(new int[]{-1, 0, 0, 3, 3, 2},"F-Major");//fmajor
        chordDictionary.put(new int[]{-1, 0, 0, 3, 3, 1},"F-Minor");//fminor
        chordDictionary.put(new int[]{-1, 0, 0, 3, 1, 2},"F-Dom7");//fdom7
        chordDictionary.put(new int[]{-1, 0, 0, 3, 1, 1},"F-Minor7");//fminor7
        chordDictionary.put(new int[]{-1, 0, 0, 3, 2, 2},"F-Major7");//fmajor7
        chordDictionary.put(new int[]{-1, 0, 0, 3, 0, 2},"F-Major6");//fmajor6

        chordDictionary.put(new int[]{3, 2, 0, 1, 0, 3},"A#-Major");
        chordDictionary.put(new int[]{3, 1, 0, 1, 3, 3},"A#-Minor");
        chordDictionary.put(new int[]{3, 2, 0, 1, 3, 1},"A#-Dom7");
        chordDictionary.put(new int[]{3, -1, 3, 4, 3, 3},"A#-Minor7");
        chordDictionary.put(new int[]{-1, 2, 0, 1, 0, 2},"A#-Major7");
        chordDictionary.put(new int[]{3, 2, 0, 1, 0, 0},"A#-Major6");

        chordDictionary.put(new int[]{0, 3, 2, 1, 1, 3},"D#-Major");//
        chordDictionary.put(new int[]{-1, 3, 1, 1, 1, 3},"D#-Minor");//
        chordDictionary.put(new int[]{-1, 3, 2, 4, 1, 0},"D#-Dom7");//
        chordDictionary.put(new int[]{-1, 3, -1, 4, 4, 3},"D#-Minor7");//
        chordDictionary.put(new int[]{-1, 3, 2, 1, 0, 0},"D#-Major7");//
        chordDictionary.put(new int[]{-1, 3, 2, 3, 1, 0},"D#-Major6");//

        chordDictionary.put(new int[]{-1, 3, 3, 3, 1, 1},"G#-Major");//
        chordDictionary.put(new int[]{-1, 3, 3, 2, 1, 1},"G#-Minor");//
        chordDictionary.put(new int[]{-1, 3, 1, 3, 1, 1},"G#-Dom7");//
        chordDictionary.put(new int[]{-1, 3, 1, 2, 1, 1},"G#-Minor7");//
        chordDictionary.put(new int[]{-1, 0, 3, 3, 1, 0},"G#-Major7");//
        chordDictionary.put(new int[]{-1, 0, 3, 3, 3, 1},"G#-Major6");//

        chordDictionary.put(new int[]{2, 2, 1, 0, 0, 2},"D-Major");//
        chordDictionary.put(new int[]{2, 2, 0, 0, 0, 2},"D-Minor");//
        chordDictionary.put(new int[]{2, 0, 1, 0, 0, 2},"D-Dom7");//
        chordDictionary.put(new int[]{2, 0, 0, 0, 0, 2},"D-Minor7");//
        chordDictionary.put(new int[]{-1, 2, 4, 4, 4, 2},"D-Major7");//
        chordDictionary.put(new int[]{2, 2, 1, 0, 0, 2},"D-Major6");//
    }*/

    private void populateChordDictionary(){

        chordDictionary.add(new int[]{0, 2, 2, 2, 0, 0});//G-Major");//gmajor
        chordDictionary.add(new int[]{0, 2, 2, 1, 0, 0});//G-Minor");//gminor
        chordDictionary.add(new int[]{0, 2, 0, 2, 3, 0});//G-Dom7");//gdom7
        chordDictionary.add(new int[]{0, 2, 2, 1, 3, 0});//G-Minor7");//gminor7
        chordDictionary.add(new int[]{0, 2, 1, 2, 0, 0});//G-Major7");//gmajor7
        chordDictionary.add(new int[]{0, 2, 2, 2, 2, 0});//G-Major6");//gmajor6

        chordDictionary.add(new int[]{0, 0, 2, 3, 2, 0});//C-Major");//cmajor
        chordDictionary.add(new int[]{0, 0, 2, 3, 1, 0});//C-Minor");//cminor
        chordDictionary.add(new int[]{0, 0, 2, 1, 2, 0});//C-Dom7");//cdom7
        chordDictionary.add(new int[]{0, 0, 2, 1, 1, 0});//C-Minor7");//cminor7
        chordDictionary.add(new int[]{0, 0, 2, 2, 2, 0});//C-Major7");//cmajor7
        chordDictionary.add(new int[]{0, 0, 2, 3, 2, 2});//C-Major6");//cmajor6

        chordDictionary.add(new int[]{-1, 0, 0, 3, 3, 2});//F-Major");//fmajor
        chordDictionary.add(new int[]{-1, 0, 0, 3, 3, 1});//F-Minor");//fminor
        chordDictionary.add(new int[]{-1, 0, 0, 3, 1, 2});//F-Dom7");//fdom7
        chordDictionary.add(new int[]{-1, 0, 0, 3, 1, 1});//F-Minor7");//fminor7
        chordDictionary.add(new int[]{-1, 0, 0, 3, 2, 2});//F-Major7");//fmajor7
        chordDictionary.add(new int[]{-1, 0, 0, 3, 0, 2});//F-Major6");//fmajor6

        chordDictionary.add(new int[]{3, 2, 0, 1, 0, 3});//A#-Major");
        chordDictionary.add(new int[]{3, 1, 0, 1, 3, 3});//A#-Minor");
        chordDictionary.add(new int[]{3, 2, 0, 1, 3, 1});//A#-Dom7");
        chordDictionary.add(new int[]{3, -1, 3, 4, 3, 3});//A#-Minor7");
        chordDictionary.add(new int[]{-1, 2, 0, 1, 0, 2});//A#-Major7");
        chordDictionary.add(new int[]{3, 2, 0, 1, 0, 0});//A#-Major6");

        chordDictionary.add(new int[]{0, 3, 2, 1, 1, 3});//D#-Major");//
        chordDictionary.add(new int[]{-1, 3, 1, 1, 1, 3});//D#-Minor");//
        chordDictionary.add(new int[]{-1, 3, 2, 4, 1, 0});//D#-Dom7");//
        chordDictionary.add(new int[]{-1, 3, -1, 4, 4, 3});//D#-Minor7");//
        chordDictionary.add(new int[]{-1, 3, 2, 1, 0, 0});//D#-Major7");//
        chordDictionary.add(new int[]{-1, 3, 2, 3, 1, 0});//D#-Major6");//

        chordDictionary.add(new int[]{-1, 3, 3, 3, 1, 1});//G#-Major");//
        chordDictionary.add(new int[]{-1, 3, 3, 2, 1, 1});//G#-Minor");//
        chordDictionary.add(new int[]{-1, 3, 1, 3, 1, 1});//G#-Dom7");//
        chordDictionary.add(new int[]{-1, 3, 1, 2, 1, 1});//G#-Minor7");//
        chordDictionary.add(new int[]{-1, 0, 3, 3, 1, 0});//G#-Major7");//
        chordDictionary.add(new int[]{-1, 0, 3, 3, 3, 1});//G#-Major6");//

        chordDictionary.add(new int[]{2, 2, 1, 0, 0, 2});//D-Major");//
        chordDictionary.add(new int[]{2, 2, 0, 0, 0, 2});//D-Minor");//
        chordDictionary.add(new int[]{2, 0, 1, 0, 0, 2});//D-Dom7");//
        chordDictionary.add(new int[]{2, 0, 0, 0, 0, 2});//D-Minor7");//
        chordDictionary.add(new int[]{-1, 2, 4, 4, 4, 2});//D-Major7");//
        chordDictionary.add(new int[]{2, 2, 1, 0, 0, 2});//D-Major6");//
    }

    //reading the midi file
    private static void readMidiFile(String path) throws Exception {
        midiFileReader = new MidiFileReader(path);
    }

    //a method to generate the population of guitar tabs
    private static void generatePopulation(int populationSize, String path) throws Exception {
        readMidiFile(path);//read midi file
        population = new GuitarTab[populationSize];

        for(int i = 0; i<populationSize;i++){//generate a population of guitar tabs of size population_size
            GuitarTab guitarTab = new GuitarTab((int)midiFileReader.tickLength);
            guitarTab.generateTab(midiFileReader.notes);
            population[i] = guitarTab;
        }
    }

    //a method to calculate the fitness of each individual member of the population
    private static void calculateEachMemberFitness(){
        long currentTick = -1;
        int[] currentSimultaneousNotes = null;
        int[] previousSimultaneousNotes;

        double totalDistance; // stores the total euclidean distance of a tab
        double distance;//stores the distance of one set of notes to another

        //looping through all notes calculating the distance from one set to another
        for (GuitarTab guitarTab:population) {
            totalDistance = 0;
            for (Note note : midiFileReader.notes) {
                if(note.tick!=currentTick){
                    if(currentSimultaneousNotes==null){
                        currentSimultaneousNotes = getSimultaneousNotes((int)note.tick, guitarTab);
                        //totalDistance+= chordDifficulty(currentSimultaneousNotes);
                        currentTick = note.tick;
                    }else{
                        previousSimultaneousNotes = currentSimultaneousNotes;
                        currentSimultaneousNotes = getSimultaneousNotes((int)note.tick, guitarTab);
                        distance = euclideanDistance(previousSimultaneousNotes, currentSimultaneousNotes);
                        //totalDistance+= chordDifficulty(currentSimultaneousNotes);
                        totalDistance+=distance;
                    }
                }
            }
            guitarTab.setFitness(totalDistance);
        }

        //sort the population in order of fitness
        Arrays.sort(population);

    }

    public static int chordDifficulty(int[] currentNotes){
        /*
            A function to penalise fitness if notes are further than 4 frets apart whilst being played simultaneously
        */

        if(!((int) Arrays.stream(currentNotes).filter(i -> i == -1).count()>=5)){
            //penalty based on euclidean distance
            ArrayList<Integer> notes = new ArrayList<Integer>();

            double sum = 0;
            int count = 0;


            for (int i = 0; i < currentNotes.length; i++) {
                if (currentNotes[i] != -1) {
                    if (currentNotes[i] != 0){
                        sum += Math.pow(currentNotes[i] - i, 2);
                        count++;
                    }else{
                        sum += Math.pow(currentNotes[i] - i, 2)/2;
                        count++;

                    }

                }
            }

            return (int)Math.sqrt(sum / count);
        }
        return 0;


    }

    // a function to get all notes played simultaneously
    private static int[] getSimultaneousNotes(int tick, GuitarTab guitarTab){
        int[] allStrings = {-1,-1,-1,-1,-1,-1}; // boolean array representing each note and whether the string is in use

        //check if any note is played on each string at a given tick
        if(guitarTab.bottomE[tick]!=-1){
            allStrings[0] = guitarTab.bottomE[tick];
        }
        if(guitarTab.aString[tick]!=-1){
            allStrings[1] = guitarTab.aString[tick];
        }
        if(guitarTab.dString[tick]!=-1){
            allStrings[2] = guitarTab.dString[tick];
        }
        if(guitarTab.gString[tick]!=-1){
            allStrings[3] = guitarTab.gString[tick];
        }
        if(guitarTab.bString[tick]!=-1){
            allStrings[4] = guitarTab.bString[tick];
        }
        if(guitarTab.topE[tick]!=-1){
            allStrings[5] = guitarTab.topE[tick];
        }
        return allStrings;
    }

    // a function to calculate the euclidean distance between two arrays
    private static double euclideanDistance(int[] x, int[] y){//for single notes

        int xCounter = 0;
        int yCounter = 0;

        double total = 0;

        for(int i=0; i<x.length; i++){
            if(x[i]!=-1){
                for(int j=0; j<y.length;j++){
                    if(y[j]!=-1){
                        int a = i+x[i];
                        int b = j+y[j];
                        double distance = Math.sqrt(Math.pow(a,2)+Math.pow(b,2));//x(string, fret), y(string, fret)///////i and y indicate string,
                        total+=distance;
                        yCounter++;
                    }
                }
                xCounter++;
            }
        }
        int numberOfConnections = xCounter+yCounter;
        total = total/numberOfConnections;
        //divide total distance by number of connections
        return total;
    }

    //randomly select two tabs and return the index of the tabs that one their individual comparisons
    private static ArrayList<Integer> tournamentSelection(int numSelections){
        Random random = new Random();
        ArrayList<Integer> indexOfFittest = new ArrayList<>();

        //run the selection process numSelection times
        for(int i = 0; i< numSelections; i++){
            int randomIndex1;
            int randomIndex2;

            while(true){
                //getting the random tabs
                randomIndex1 = random.nextInt(numSelections);
                randomIndex2 = random.nextInt(numSelections);
                //checking the tab hasn't already been selected
                if(!(indexOfFittest.contains(randomIndex1))&&!(indexOfFittest.contains(randomIndex2))){
                    break;
                }
            }

            //comparing both tabs and adding the index of the fittest to indexOfFittest list
            if(population[randomIndex1].getFitness()>population[randomIndex2].getFitness()){
                indexOfFittest.add(randomIndex1);
            }else{
                indexOfFittest.add(randomIndex2);
            }

        }
        return indexOfFittest;
    }

    //method to create 2 child tabs from 2 parent tabs
    static void reproduce(ArrayList<Integer> indexOfFittest, double crossoverValue){//for each 2 tabs selected in the tournament, swap the middle third to create new child tabs
        int counter = 0;
        GuitarTab[] newPopulation = new GuitarTab[population.length];
        Random rand = new Random();

        //loop through the index of the fittest, two tabs at a time
        //already random as the index of fittest list is randomly selected
        for (int n = 1; n<indexOfFittest.size();n+=2){
            int idx1 = n;
            int idx2 = n-1;

            // set the two parent tabs
            GuitarTab tab1 = population[indexOfFittest.get(idx1)];
            GuitarTab tab2 = population[indexOfFittest.get(idx2)];

            //crossover the two parent tabs to create the child tabs
            GuitarTab childTab1 = crossover(tab1,tab2,crossoverValue);
            GuitarTab childTab2 = crossover(tab2,tab1,crossoverValue);

            //randomly choose one child tab to mutate
            if(rand.nextInt(2)==1){
                childTab1 = mutate(childTab1);
            }else{
                childTab2 = mutate(childTab2);
            }

            //add new tabs to the population
            newPopulation[counter++] = tab1;
            newPopulation[counter++] = tab2;
            newPopulation[counter++] = childTab1;
            newPopulation[counter++] = childTab2;
        }
        population = newPopulation;

    }
    //a function to crossover to parent tabs to create a child tab
    static GuitarTab crossover(GuitarTab parent1,GuitarTab parent2, double crossover){
        GuitarTab childTab = new GuitarTab((int)midiFileReader.tickLength);


        for(int i = 0; i<midiFileReader.tickLength; i++){
            if(Math.random()<crossover){
                childTab.bottomE[i]=parent1.bottomE[i];
                childTab.aString[i]=parent1.aString[i];
                childTab.dString[i]=parent1.dString[i];
                childTab.gString[i]=parent1.gString[i];
                childTab.bString[i]=parent1.bString[i];
                childTab.topE[i]=parent1.topE[i];
            }else{
                childTab.bottomE[i]=parent2.bottomE[i];
                childTab.aString[i]=parent2.aString[i];
                childTab.dString[i]=parent2.dString[i];
                childTab.gString[i]=parent2.gString[i];
                childTab.bString[i]=parent2.bString[i];
                childTab.topE[i]=parent2.topE[i];
            }
        }
        return childTab;
    }

    // a function to randomly change one note within a guitar tab
    public static GuitarTab mutate(GuitarTab guitarTab){
        Random random = new Random();
        ArrayList<Integer> indexList = new ArrayList<>();
        //pick random string
        int randomStringIndex=random.nextInt(6);
        int[] string_array = getStringArray(randomStringIndex,guitarTab);//select a string at random

        //for each tick in the string add the tick number if the value is not -1
        for(int i = 0; i<string_array.length;i++){
            if(string_array[i]!=-1){//get index of all notes played on that string
                indexList.add(i);
            }
        }
        if(indexList.size()==0){
            return guitarTab;
        }

        int randomElement = random.nextInt(indexList.size());
        int randomTick = indexList.get(randomElement);//select a note index at random

        guitarTab.randomlyChangeNote(randomStringIndex, randomTick);

        return guitarTab;
    }

    //calculate the average fitness of a generation
    private static void calculateGenerationFitness(){
        double total = 0;

        for (GuitarTab guitarTab:population) {
            total+=guitarTab.fitness;
        }
        total=total/population.length;
        generational_fitness.add(total);

    }

    //a function to return the string array containing the notes of a given string. takes the string index and guitar tab as parameters
    private static int[] getStringArray(int index, GuitarTab tab){

        if(index==0){
            return tab.bottomE;
        }else if(index == 1){
            return tab.aString;
        }else if(index == 2){
            return tab.dString;
        }else if(index == 3){
            return tab.gString;
        }else if(index == 4){
            return tab.bString;
        }else if(index == 5){
            return tab.topE;
        }
        return null;
    }

    //method to run all steps in the genetic algorithm
    public GuitarTab runGeneticAlgorithm(String path, int iterations, int populationSize, int numberOfSelections, double crossover, int interval, int runLength, int range) throws Exception {
        populateChordDictionary();
        generatePopulation(populationSize,path);

        for(int i = 0; i<iterations;i++){
            calculateEachMemberFitness();
            ArrayList<Integer> indexesOfFittest = tournamentSelection(numberOfSelections);
            calculateGenerationFitness();
            //System.out.println("i: "+i+" "+generational_fitness.get(i));
            reproduce(indexesOfFittest,crossover);
            calculateEachMemberFitness();
            Arrays.sort(population);

            if(checkFitnessMonotony(i, interval, runLength,range)){
                break;
            }
        }
        System.out.println("i: " + (generational_fitness.size()-1)+" size: "+population[0].numTicks);
        population[0].printTab(midiFileReader.resolution);
        return population[0];
    }

    public boolean checkFitnessMonotony(int i, int interval, int runLength, int range){
        /*
            Function to check if there is little to no change in generational fitness. Returns true if no change else
            false

            i: iteration number,
            interval: number of iterations between each check
            runLength: number of consecutive generational fitness scores to be checked
            range: the allowable difference between the highest and lowest fitness values of a run
        */
        if(i%interval == 0 && i>0){//this number would be scaled to the size of the track
            double totalFitness = 0;
            double averageFitness;
            double highestFitness = 0;
            double lowestFitness = generational_fitness.get(generational_fitness.size()-1);
            double currentFitness = 0;

            for(int j = i; j>(i-runLength); j--){
                currentFitness = generational_fitness.get(j);
                if(currentFitness<lowestFitness){
                    lowestFitness = currentFitness;
                }
                if(currentFitness>highestFitness){
                    highestFitness = currentFitness;
                }
                totalFitness+=currentFitness;
            }

            averageFitness = totalFitness/runLength;

            //want to check that the average fitness is within 5 of the lowest and highest

            if((lowestFitness+range)>=highestFitness){
                if(averageFitness>=lowestFitness&&averageFitness<=highestFitness){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static void main(String[] args) throws InvalidMidiDataException, IOException {


    }
}

