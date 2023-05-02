import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class TabReader {

    public static String convertGeneratedTab(int[][] tab) throws Exception {
        int length = tab[0].length;
        String fullTab = "";
        for(int i = 0; i<length; i++){
            int[] currentNotes = new int[]{tab[0][i], tab[1][i], tab[2][i], tab[3][i], tab[4][i], tab[5][i]};

            //check if all notes are -1
            if (checkMinusOne(currentNotes)) {

                // initialise individual line
                String line = "";

                //loop through each note and convert fret number to fret letter
                for (int note : currentNotes){
                    if(note!=-1){
                        line+= tabNumToNum(note);
                    }else{
                        line+=" ";
                    }
                }

                //append fret letter to line
                //line = new StringBuffer(line).reverse().toString();
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

    private static String tabNumToNum(int fretNum) throws Exception {
        /*
            Convert int fret number to equivalent fret char
        */
        switch (fretNum){
            case 0:
                return "0";
            case 1:
                return "1";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "7";
            case 8:
                return "8";
            case 9:
                return "9";
            case 10:
                return "10";


        }
        throw new Exception();
    }

    public static int tabCharToNum(char fretChar) throws Exception {
        /*
            Convert int fret number to equivalent fret char
        */
        switch (fretChar){
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            case 'i':
                return 8;
            case 'k':
                return 9;
            case 'l':
                return 10;


        }
        return -1;
    }

    public static String parseTabFile(String fileContents,boolean nums){
        String[] lines = fileContents.split("\n");

        /*
            To do:
                - fix white space errors, strips spaces at beginnign of word
        */

        String cleanedTab = "";
        for(String line : lines){
            if(line.length()>0){
                if(line.charAt(0)!='%'&&line.charAt(0)!='{'&&line.charAt(0)!='$'){

                    // remove first char of line
                    line = line.substring(1);
                    if(line.length()>0){
                        // if line begins with %, skip
                        // remove all non numeric chars or spaces
                        if(nums){
                            line = line.replaceAll("[^0-9\s]+", "");
                        }
                        else{
                            line = line.replaceAll("[^a-l\s]+", "");
                        }

                        //line=line.stripTrailing();
                        //line = new StringBuffer(line).reverse().toString();
                        if(line.stripTrailing()!=""){
                            cleanedTab+=line+"\n";
                        }

                    }
                }

            }
        }

        return cleanedTab;
    }


    public static double compare(String path,String generatedTab) throws IOException {
        //NEED FILE PATH TO TAKE PARAMETER AND NOT STING LITERAL

        Path filePath = Path.of(path);
        String content = Files.readString(filePath);
        String grandTruth = parseTabFile(content, true);

        String[] grandTruthRows = grandTruth.trim().split("\n");
        String[] generatedTabRows = generatedTab.trim().split("\n");

        int counter = 0;

        if(grandTruthRows.length==generatedTabRows.length){
            for(int i=0; i<grandTruthRows.length; i++){
                if(generatedTabRows[i].stripTrailing().equals(grandTruthRows[i].stripTrailing())){
                    counter++;
                }else{
                    //System.out.println("gen: "+generatedTabRows[i].stripTrailing());
                    //System.out.println("GT: "+grandTruthRows[i].stripTrailing());
                }
            }

            double accuracy = (((double)counter)/((double)generatedTabRows.length))*100;
            System.out.println("acc: "+ accuracy+"%");
            return accuracy;
        }else{
            System.out.println("Tabs different length");
        }
        return 0;
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(tabNumToChar(2));

        Path filePath = Path.of("Dowland/6.tab");
        String content = Files.readString(filePath);


        System.out.println(parseTabFile(content,false));

    }
}
