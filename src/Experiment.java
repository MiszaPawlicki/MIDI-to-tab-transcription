import java.io.File;
import java.util.ArrayList;

public class Experiment {

    public static ArrayList<Double> accs = new ArrayList<Double>();

    public static void checkDatabase(String midiFolderPath, String tabFolderPath, int iterations, int populationSize, double crossover, double mutationRate, int interval, int runLength, int range) throws Exception{
        /*
            A function to check what midi files within a folder can be transcribed by the program depending on tuning

            path: path to the folder
        */
        File midiPaths = new File(midiFolderPath);
        File tabPaths = new File(tabFolderPath);

        File [] midiFiles = midiPaths.listFiles();
        File [] tabFiles = tabPaths.listFiles();

        double totalAccuracy = 0;
        for (int i = 0; i < midiFiles.length; i++){
            if (midiFiles[i].isFile()){ //this line weeds out other directories/folders
                try{
                    System.out.println(midiFiles[i]);
                    System.out.println(tabFiles[i]);
                    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
                    GuitarTab tab = geneticAlgorithm.runGeneticAlgorithm(midiFiles[i].toString(),iterations, populationSize, crossover, mutationRate, interval, runLength, range);
                    double accForTab = TabReader.compare(tabFiles[i].toString(), TabReader.convertGeneratedTab(tab.allStrings));
                    System.out.println("\n");
                    accs.add(accForTab);
                    totalAccuracy+=accForTab;
                    //System.out.println("-------------------------------------------------------------");


                }catch(Exception e){
                    //purely for non-crashing purposes
                }
            }
        }
        //System.out.println("_____________");
        //System.out.println("Average accuracy: "+(totalAccuracy/ midiFiles.length));
    }

    //parameter tuning
    public static void experimentOne() throws Exception {
        int[] populationValues = {60, 100, 140, 180, 220};
        double[] crossoverValues = {0.1, 0.2, 0.3, 0.4, 0.5};
        double[] mutationValues = {0.2, 0.4, 0.6, 0.8, 1};

        int iterations = 5000;
        int interval = 200; // number of iterations before there is a check of whether fitness is flattening out //200
        int runLength = 100; // number of fitness scores checked to see if flattening out //100
        int range = 10; // max range between highest and lowest fitness scores when checking if fitness is flattening //10

        for(int p : populationValues){
            for(double c : crossoverValues){
                for(double m : mutationValues){
                    System.out.println("-----------------------------------------------------");
                    checkDatabase("Tabs/A_Mudarra/MIDI","Tabs/A_Mudarra/TABS",iterations, p, c, m,  interval, runLength, range);
                    checkDatabase("Tabs/F_da_Milano/MIDI","Tabs/F_da_Milano/TABS",iterations, p, c, m,  interval, runLength, range);
                    checkDatabase("Tabs/Galilei/MIDI","Tabs/Galilei/TABS",iterations, p, c, m,  interval, runLength, range);
                    checkDatabase("Tabs/Misc_Italian/MIDI","Tabs/Misc_Italian/TABS",iterations, p, c, m,  interval, runLength, range);
                    checkDatabase("Tabs/Segovia/MIDI","Tabs/Segovia/TABS",iterations, p, c, m,  interval, runLength, range);
                    checkDatabase("Tabs/Skene/MIDI","Tabs/Skene/TABS",iterations, p, c, m,  interval, runLength, range);
                    checkDatabase("Tabs/Spinacino/MIDI","Tabs/Spinacino/TABS",iterations, p, c, m,  interval, runLength, range);
                    checkDatabase("Tabs/Valderrabano/MIDI","Tabs/Valderrabano/TABS",iterations, p, c, m,  interval, runLength, range);

                    double sum = 0;
                    for (double i :accs){
                        sum+=i;
                    }
                    Double averageAcc = sum/accs.size();
                    System.out.println("-----------------------------------------------------");
                    System.out.println("{ population: " + p + ", crossover: " + c + ", mutation rate: " + m + "}");
                    System.out.println("Total Average Accs: "+ averageAcc);
                    System.out.println("-----------------------------------------------------");

                    accs.clear();

                }
            }
        }
    }
}
