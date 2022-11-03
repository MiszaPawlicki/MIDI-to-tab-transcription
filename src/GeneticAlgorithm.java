import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm {

    private static GuitarTab[] population;
    private static ArrayList<Float> generational_fitness;
    protected static MidiFileReader midiFileReader;
    private static void generatePopulation(int population_size, String path) throws InvalidMidiDataException, IOException {

        midiFileReader = new MidiFileReader(path);//read midi file
        population = new GuitarTab[population_size];

        for(int i = 0; i<population_size;i++){//generate a population of guitar tabs of size population_size
            GuitarTab guitarTab = new GuitarTab((int)midiFileReader.tick_length);
            guitarTab.generateTab(midiFileReader.notes);
            population[i] = guitarTab;
        }
        int res = midiFileReader.resolution;
        for (GuitarTab gt:population) {  ///PRINTING CODE
            gt.printTab(res);
            System.out.println();
        }
    }

    private static void calculateGenerationFitness(){
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

    private static void tournamentSelection(int num_selections){
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

    }

    void reproduce(){}

    void mutate(){}

    public static void main(String[] args) throws InvalidMidiDataException, IOException {
        generatePopulation(100,"new.mid");
        calculateGenerationFitness();
        tournamentSelection(50);

    }
}
