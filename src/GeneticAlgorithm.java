import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {

    private static GuitarTab[] population;
    private static ArrayList<Double> generational_fitness = new ArrayList<>();
    protected static MidiFileReader midiFileReader;

    private static void readMidiFile(String path) throws InvalidMidiDataException, IOException {
        midiFileReader = new MidiFileReader(path);
    }

    private static void generatePopulation(int population_size, String path) throws InvalidMidiDataException, IOException {

        readMidiFile(path);//read midi file
        population = new GuitarTab[population_size];

        for(int i = 0; i<population_size;i++){//generate a population of guitar tabs of size population_size
            GuitarTab guitarTab = new GuitarTab((int)midiFileReader.tick_length);
            guitarTab.generateTab(midiFileReader.notes);
            population[i] = guitarTab;
        }
    }

    private static void calculateEachMemberFitness(){
        long current_tick = -1;
        int[] current_simultanious_notes = null;
        int[] previous_simultanious_notes = null;
        double total_distance;
        double distance;


        for (GuitarTab guitarTab:population) {
            total_distance = 0;
            for (Note note : midiFileReader.notes) {
                if(note.tick!=current_tick){
                    if(current_simultanious_notes==null){
                        current_simultanious_notes = getSimultaniousNotes((int)note.tick, guitarTab);
                        current_tick = note.tick;
                    }else{
                        previous_simultanious_notes = current_simultanious_notes;
                        current_simultanious_notes = getSimultaniousNotes((int)note.tick, guitarTab);
                        distance = euclidianDistance(previous_simultanious_notes, current_simultanious_notes);
                        total_distance+=distance;
                    }
                }
            }
            guitarTab.setFitness(total_distance);
        }

        Arrays.sort(population);

    }

    private static int[] getSimultaniousNotes(int tick, GuitarTab guitarTab){
        int[] all_strings = {-1,-1,-1,-1,-1,-1};
        if(guitarTab.bottomE[tick]!=-1){
            all_strings[0] = guitarTab.bottomE[tick];
        }
        if(guitarTab.aString[tick]!=-1){
            all_strings[1] = guitarTab.aString[tick];
        }
        if(guitarTab.dString[tick]!=-1){
            all_strings[2] = guitarTab.dString[tick];
        }
        if(guitarTab.gString[tick]!=-1){
            all_strings[3] = guitarTab.gString[tick];
        }
        if(guitarTab.bString[tick]!=-1){
            all_strings[4] = guitarTab.bString[tick];
        }
        if(guitarTab.topE[tick]!=-1){
            all_strings[5] = guitarTab.topE[tick];
        }
        return all_strings;
    }

    private static double euclidianDistance(int[] x, int[] y){//for single notes

        int x_counter = 0;
        int y_counter = 0;

        double total = 0;

        for(int i=0; i<x.length; i++){
            if(x[i]!=-1){
                for(int j=0; j<y.length;j++){
                    if(y[j]!=-1){
                        int a = i+x[i];
                        int b = j+y[j];
                        double distance = Math.sqrt(Math.pow(a,2)+Math.pow(b,2));//x(string, fret), y(string, fret)///////i and y indicate string,
                        total+=distance;
                        y_counter++;
                    }
                }
                x_counter++;
            }
        }
        int number_of_connections = x_counter+y_counter;
        total = total/number_of_connections;
        //divide total distance by number of connections
        return total;
    }

    private static ArrayList<Integer> tournamentSelection(int num_selections){
        Random random = new Random();
        ArrayList<Integer> index_of_fittest = new ArrayList<>();

        for(int i = 0; i< num_selections; i++){
            int random_index_1;
            int random_index_2;

            while(true){
                random_index_1 = random.nextInt(num_selections);
                random_index_2 = random.nextInt(num_selections);
                if(!(index_of_fittest.contains(random_index_1))&&!(index_of_fittest.contains(random_index_2))){
                    break;
                }
            }

            if(population[random_index_1].getFitness()>population[random_index_2].getFitness()){
                index_of_fittest.add(random_index_1);
            }else{
                index_of_fittest.add(random_index_2);
            }

        }
        return index_of_fittest;
    }

    static void reproduce(ArrayList<Integer> index_of_fittest){//for each 2 tabs selected in the tournament, swap the middle third to create new child tabs
        int counter = 0;
        GuitarTab[] new_population = new GuitarTab[population.length];
        Random rand = new Random();

        for (int n = 1; n<index_of_fittest.size();n+=2){
            GuitarTab tab1 = population[index_of_fittest.get(n)];
            GuitarTab tab2 = population[index_of_fittest.get(n-1)];

            GuitarTab child_tab1 = tab1;
            GuitarTab child_tab2 = tab2;

            int third_length = tab1.bottomE.length/3;
            //crossover
            for(int i = third_length;i<third_length*2;i++){
                child_tab1.bottomE[i] = tab2.bottomE[i];
                child_tab1.aString[i] = tab2.aString[i];
                child_tab1.dString[i] = tab2.dString[i];
                child_tab1.gString[i] = tab2.gString[i];
                child_tab1.bString[i] = tab2.bString[i];
                child_tab1.topE[i] = tab2.topE[i];

                child_tab2.bottomE[i] = tab1.bottomE[i];
                child_tab2.aString[i] = tab1.aString[i];
                child_tab2.dString[i] = tab1.dString[i];
                child_tab2.gString[i] = tab1.gString[i];
                child_tab2.bString[i] = tab1.bString[i];
                child_tab2.topE[i] = tab1.topE[i];
            }

            if(rand.nextInt(2)==1){
                child_tab1 = mutate(child_tab1);
            }else{
                child_tab2 = mutate(child_tab2);
            }

            new_population[counter++] = tab1;
            new_population[counter++] = tab2;
            new_population[counter++] = child_tab1;
            new_population[counter++] = child_tab2;




        }
        population = new_population;

    }

    public static GuitarTab mutate(GuitarTab child_tab){
        Random random = new Random();
        ArrayList<Integer> index_list = new ArrayList<>();
        int[] string_array = getStringArray(random.nextInt(6),child_tab);//select a string at random
        for(int i = 0; i<string_array.length;i++){
            if(string_array[i]!=-1){//get index of all notes played on that string
                index_list.add(i);
            }
        }
        int random_string_index = random.nextInt(index_list.size());
        int random_note_index = index_list.get(random_string_index);//select a note index at random


        child_tab.randomlyChangeNote(random_string_index, random_note_index);

        return child_tab;
    }

    private static void calculateGenerationFitness(){
        double total = 0;

        for (GuitarTab guitarTab:population) {
            total+=guitarTab.fitness;
        }
        total=total/population.length;
        generational_fitness.add(total);
    }

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

    public static void main(String[] args) throws InvalidMidiDataException, IOException {
        generatePopulation(100,"new.mid");

        for(int i = 0; i<10;i++){
            calculateEachMemberFitness();
            ArrayList<Integer> indexes_of_fittest = tournamentSelection(50);
            calculateGenerationFitness();
            System.out.println(generational_fitness.get(i));
            reproduce(indexes_of_fittest);
        }


        /*int res = midiFileReader.resolution;
        for (GuitarTab gt:population) {  ///PRINTING CODE
            gt.printTab(res);
            System.out.println();
        }*/
    }
}
