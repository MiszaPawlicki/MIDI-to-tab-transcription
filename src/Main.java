import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main {


    public static void main(String[] args) throws Exception {
        //initialise genetic algorithm object and run
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        geneticAlgorithm.runGeneticAlgorithm("Classical guitar tabs/abreu_ticotico.mid");

    }

    public static boolean moveFile(String sourcePath, String targetPath) {
        File fileToMove = new File(sourcePath);
        return fileToMove.renameTo(new File(targetPath));
    }

    public static void checkDatabase(String filePath) throws Exception{
        /*
            A function to check what midi files within a folder can be transcribed by the program depending on tuning

            path: path to the folder
        */
        File path = new File(filePath);

        File [] files = path.listFiles();
        ArrayList<String> workingFiles = new ArrayList<>();
        for (int i = 0; i < files.length; i++){
            if (files[i].isFile()){ //this line weeds out other directories/folders
                try{
                    System.out.println(files[i]);
                    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
                    geneticAlgorithm.runGeneticAlgorithm(files[i].toString());
                    //workingFiles.add(filePath);

                    //moveFile(files[i].toPath().toString(), "working_tabs\\"+filePath);
                    //String txtPath = filePath.replace(".mid",".txt");
                    //moveFile(txtPath, "working_tabs\\"+txtPath);
                    //System.out.println(1);

                }catch(Exception e){
                    //purely for non-crashing purposes
                }
            }
        }
    }
}