import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.util.ArrayList;

public class GeneticAlgorithm {

    private static GuitarTab[] population;
    private static ArrayList<Float> generational_fitness;

    private static void generatePopulation(int population_size, String path) throws InvalidMidiDataException, IOException {

        MidiFileReader midiFileReader = new MidiFileReader(path);
        population = new GuitarTab[population_size];

        for(int i = 0; i<population_size;i++){
            GuitarTab guitarTab = new GuitarTab((int)midiFileReader.tick_length);
            guitarTab.generateTab(midiFileReader.notes);
            population[i] = guitarTab;
        }
    }


    void tournamentSelection(){}

    void fitness(){}

    void reproduce(){}

    void mutate(){}

    public static void main(String[] args) throws InvalidMidiDataException, IOException {
        generatePopulation(100,"new.mid");
    }
}
