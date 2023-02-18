import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main {


    public static void main(String[] args) throws Exception {
        /*String path = "F_da_Milano/MIDI/004_Milano_4.mid";
        String tabPath = "F_da_Milano/TAB/004_Milano_4.tab";
        //initialise genetic algorithm object and run
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        GuitarTab tab = geneticAlgorithm.runGeneticAlgorithm(path,300);
        //System.out.println(TabReader.convertGeneratedTab(tab.allStrings));


        TabReader.compare(tabPath, TabReader.convertGeneratedTab(tab.allStrings));*/

        checkDatabase("F_da_Milano/MIDI","F_da_Milano/TAB");

    }

    public static boolean moveFile(String sourcePath, String targetPath) {
        File fileToMove = new File(sourcePath);
        return fileToMove.renameTo(new File(targetPath));
    }

    public static void checkDatabase(String midiFolderPath, String tabFolderPath) throws Exception{
        /*
            A function to check what midi files within a folder can be transcribed by the program depending on tuning

            path: path to the folder
        */
        File midiPaths = new File(midiFolderPath);
        File tabPaths = new File(tabFolderPath);

        File [] midiFiles = midiPaths.listFiles();
        File [] tabFiles = tabPaths.listFiles();
        for (int i = 0; i < midiFiles.length; i++){
            if (midiFiles[i].isFile()){ //this line weeds out other directories/folders
                try{
                    System.out.println(midiFiles[i]);
                    System.out.println(tabFiles[i]);
                    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
                    GuitarTab tab = geneticAlgorithm.runGeneticAlgorithm(midiFiles[i].toString(),500);
                    TabReader.compare(tabFiles[i].toString(), TabReader.convertGeneratedTab(tab.allStrings));
                    System.out.println("-------------------------------------------------------------");


                }catch(Exception e){
                    //purely for non-crashing purposes
                }
            }
        }
    }
}