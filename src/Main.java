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
        //geneticAlgorithm.runGeneticAlgorithm("C:\\Users\\misza\\OneDrive\\Documents\\Work\\year 3\\TYP\\TYP\\Classical guitar tabs\\duarte_op003_prelude_in_c.mid");
        geneticAlgorithm.runGeneticAlgorithm("Classical guitar tabs/abreu_ticotico.mid");

        /*
        //Code to find all files that can be run
        File path = new File("Classical guitar tabs");

        File [] files = path.listFiles();
        ArrayList<String> workingFiles = new ArrayList<>();
        for (int i = 0; i < files.length; i++){
            if (files[i].isFile()){ //this line weeds out other directories/folders
                try{
                    String filePath = files[i].toPath().toString();

                    GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
                    //geneticAlgorithm.runGeneticAlgorithm("C:\\Users\\misza\\OneDrive\\Documents\\Work\\year 3\\TYP\\TYP\\Classical guitar tabs\\duarte_op003_prelude_in_c.mid");
                    geneticAlgorithm.runGeneticAlgorithm(filePath);
                    workingFiles.add(filePath);

                    moveFile(files[i].toPath().toString(), "working_tabs\\"+filePath);
                    String txtPath = filePath.replace(".mid",".txt");
                    moveFile(txtPath, "working_tabs\\"+txtPath);
                    System.out.println(1);

                }catch(Exception e){

                }
            }
        }*/

    }

    public static boolean moveFile(String sourcePath, String targetPath) {

        File fileToMove = new File(sourcePath);

        return fileToMove.renameTo(new File(targetPath));
    }
}