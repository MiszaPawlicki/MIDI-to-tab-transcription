import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class TabReader {


    public static String convertGeneratedTab(int[][] tab) throws Exception {
        int length = tab[0].length;
        String fullTab = "";
        for(int i = 0; i<length; i++){
            int[] currentNotes = new int[]{tab[5][i], tab[4][i], tab[3][i], tab[2][i], tab[1][i], tab[0][i]};

            //check if all notes are -1
            if (checkMinusOne(currentNotes)) {

                // initialise individual line
                String line = "";

                //loop through each note and convert fret number to fret letter
                for (int note : currentNotes){
                    if(note!=-1){
                        line+=tabNumToChar(note);
                    }else{
                        line+=" ";
                    }
                }

                //append fret letter to line
                line+="\n";
                fullTab+=line;
            }
        }
        return fullTab;
    }

    private static boolean checkMinusOne(int[] tabStrings){
        /*
            function to check if all ints in an array are -1, returns true if not, false if so
        */
        for(int i : tabStrings){
            if(i!=-1){
                return true;
            }
        }
        return false;
    }

    private static String tabNumToChar(int fretNum) throws Exception {
        /*
            Convert int fret number to equivalent fret char
        */
        switch (fretNum){
            case 0:
                return "a";
            case 1:
                return "b";
            case 2:
                return "c";
            case 3:
                return "d";
            case 4:
                return "e";
            case 5:
                return "f";
            case 6:
                return "g";
            case 7:
                return "h";
            case 8:
                return "i";
            case 9:
                return "k";
            case 10:
                return "l";

        }
        throw new Exception();
    }

    public static String parseTabFile(String fileContents){

        String[] lines = fileContents.split("\n");

        String cleanedTab = "";

        for(String line : lines){
            if(checkLine(line)){
                cleanedTab+=filterLine(line);
            }
        }

        return cleanedTab;
    }

    private static boolean checkLine(String line){
        /*
            Function to check if line contains fretting information
        */
        if(line==""){
            return false;
        }
        if(line.charAt(0)=='{'){
            return false;
        }
        if(line.charAt(0)=='b'&&line.length()==1){
            return false;
        }
        if(line.charAt(0)=='\n'){
            return false;
        }
        if(line.charAt(0)=='%'){
            return false;
        }



        return true;
    }

    private static String filterLine(String line){
        /*
            Function that takes a line and returns only the fretting information
        */

        String cleanString = line;
        ArrayList<Character> letters = new ArrayList<Character>(Arrays.asList('x','X', 'Y', 'v','b'));
        ArrayList<Character> primarySymbols = new ArrayList<Character>(Arrays.asList('#'));
        ArrayList<Character> secondarySymbols = new ArrayList<Character>(Arrays.asList('-','.'));
        ArrayList<Character> numbers = new ArrayList<Character>(Arrays.asList('0','1','2','3','4','5','6','7','8','0'));

        line=filter(letters, line);
        line=filter(primarySymbols, line);
        line=filter(numbers, line);
        line=filter(secondarySymbols, line);

        if(line.length()!=0){
            if(Character.isAlphabetic(line.charAt(0))){
                if(Character.isUpperCase(line.charAt(0))){
                    line=line.substring(1);
                }
            }
        }

        if(line.length()==0){
            return "";
        }else{
            line+="\n";
        }

        return line;
    }

    public static String filter(ArrayList<Character> filters, String line){
        if(line.length()!=0){
            if(filters.contains(line.charAt(0))){
                line=line.substring(1);
            }
        }

        return line;
    }

    public static void compare(String path, String generatedTab) throws IOException {
        Path filePath = Path.of("Dowland/2.tab");
        String content = Files.readString(filePath);
        String grandTruth = parseTabFile(content);

        String[] grandTruthRows = grandTruth.split("\n");
        String[] generatedTabRows = generatedTab.split("\n");

        int counter = 0;

        if(grandTruthRows.length==generatedTabRows.length){
            for(int i=0; i<grandTruthRows.length; i++){
                if(  generatedTabRows[i].stripTrailing().equals(grandTruthRows[i])){
                    counter++;
                }
            }

            double accuracy = (((double)counter)/((double)generatedTabRows.length))*100;
            System.out.println("acc: "+ accuracy+"%");
        }else{
            System.out.println("Tabs different length");
        }
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(tabNumToChar(2));

        Path filePath = Path.of("Dowland/2.tab");
        String content = Files.readString(filePath);
        parseTabFile(content);

        System.out.println(checkLine("x    a"));

    }
}
