import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.util.ArrayList;

public class GeneticAlgorithm {

    private static GuitarTab[] population;
    private static ArrayList<Float> generational_fitness;

    private static void generatePopulation(int population_size, String path) throws InvalidMidiDataException, IOException {

        MidiFileReader midiFileReader = new MidiFileReader(path);//read midi file
        population = new GuitarTab[population_size];

        for(int i = 0; i<population_size;i++){//generate a population of guitar tabs of size population_size
            GuitarTab guitarTab = new GuitarTab((int)midiFileReader.tick_length);
            guitarTab.generateTab(midiFileReader.notes);
            population[i] = guitarTab;
        }
        int res = midiFileReader.resolution;
        /*for (GuitarTab gt:population) {  ///PRINTING CODE
            gt.printTab(res);
            System.out.println();
        }*/
    }

    private static double fitness(){
        //get each list of simultanious notes
            //calculate eucdid,
                //if counts of simultanious notes
                    //for each note in currentlist
                        //calculate distance to each note in the next list
                    //calculate average eucdid


        return 0;
    }

    private static double euclidianDistance(double x1, double y1, double x2, double y2){//for single notes
        double a = x1+y1;
        double b = x2+y2;
        return Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
    }
    void tournamentSelection(){}

    void reproduce(){}

    void mutate(){}

    public static void main(String[] args) throws InvalidMidiDataException, IOException {
        generatePopulation(100,"new.mid");
    }
}
