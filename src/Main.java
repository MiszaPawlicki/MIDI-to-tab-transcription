import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Main {


    public static void main(String[] args) throws Exception {

        //PARAMETERS
        int iterations = 5000; // maximum number of times the genetic algorithm will run (max number of generations)
        int populationSize = 100; // Size of each population of guitar tab for each generation
        int numOfSelections = 50; // number of tabs to select through tournament selection
        double crossover = 0.3; // rate at which 2 tabs are crossed over
        int interval = 200; // number of iterations before there is a check of whether fitness is flattening out
        int runLength = 100; // number of fitness scores checked to see if flattening out
        int range = 10; // max range between highest and lowest fitness scores when checking if fitness is flattening

        //PATHS
        String path = "Tabs/Valderrabano/MIDI/Valderrabano_Soneto_5.mid";
        String tabPath = "Tabs/Valderrabano/TAB/Valderrabano_Soneto_5.tab";

        //RUNNING THE GA
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        GuitarTab tab = geneticAlgorithm.runGeneticAlgorithm(path,iterations, populationSize, numOfSelections, crossover, interval, runLength, range);
        //System.out.println(TabReader.convertGeneratedTab(tab.allStrings));

        //COMPARING GA VS HUMAN WRITTEN TAB
        TabReader.compare(tabPath, TabReader.convertGeneratedTab(tab.allStrings));

        //convertTabFileToNumberFormat("Tabs/Valderrabano/TAB/Valderrabano_Soneto_5.tab");
    }

    public static boolean moveFile(String sourcePath, String targetPath) {
        File fileToMove = new File(sourcePath);
        return fileToMove.renameTo(new File(targetPath));
    }

    public static void checkDatabase(String midiFolderPath, String tabFolderPath, int iterations, int populationSize, int numberOfSelections, double crossover, int interval, int runLength, int range) throws Exception{
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
                    GuitarTab tab = geneticAlgorithm.runGeneticAlgorithm(midiFiles[i].toString(),iterations, populationSize, numberOfSelections, crossover, interval, runLength, range);
                    totalAccuracy+=TabReader.compare(tabFiles[i].toString(), TabReader.convertGeneratedTab(tab.allStrings));
                    System.out.println("-------------------------------------------------------------");


                }catch(Exception e){
                    //purely for non-crashing purposes
                }
            }
        }
        System.out.println("_____________");
        System.out.println("Average accuracy: "+(totalAccuracy/ midiFiles.length));
    }

    public static String convertTabFileToNumberFormat(String path) throws Exception {
        /*
            Function to convert char based tab to num based tab

        */
        String content = Files.readString(Path.of(path));
        String[] lines = content.split("\n");

        String cleanedTab = "";
        String cleanLine;
        int count;
        for(String line : lines){
            count = 0;
            cleanLine = "";
            line = line.replaceAll("[^a-z\s]+", "");
            if(line.length()>1){
                if(line.charAt(0)!='%'&&line.charAt(0)!='{'&&line.charAt(0)!='$'){

                    boolean firstChar = true;
                    for(char c : line.toCharArray()){
                        if(c!='x'){
                            count++;
                        }


                        if(c  == ' '){
                            cleanLine+=" ";
                        }else{
                            int temp = TabReader.tabCharToNum(c);
                            if(temp!=-1){
                                cleanLine+=temp;
                            }
                        }



                    }

                }
            }
            if(cleanLine!=""){
                String spaces = "";
                for(int i = 6-count; i>0; i--){
                    spaces+=" ";
                }
                cleanLine+=spaces;
                cleanLine = new StringBuilder(cleanLine).reverse().toString();
                cleanedTab+="x"+cleanLine+"\n";
            }
        }
        return cleanedTab;
    }




}