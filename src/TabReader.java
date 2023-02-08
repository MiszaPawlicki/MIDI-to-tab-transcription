import java.util.ArrayList;

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
        return "Not implemented yet";
    }

    public static void main(String[] args) throws Exception {
        System.out.println(tabNumToChar(2));

    }
}
