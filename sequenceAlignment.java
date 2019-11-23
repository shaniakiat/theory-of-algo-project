import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// Implement the dynamic programming solution for sequence alignment.  Clearly comment your code.  Make it easy to modify 
// the formula for edit distance by simply changing the parameters  and .  Load your program with a dictionary of 
// at least 1000 English words.  Prompt the user for input and then output the word in the dictionary that is closest 
// (according to the edit distance) to the user’s input.

// author: Shania Kiat

class sequenceAlignment {
    public static void main(String[] args) throws IOException {
        // sequence alignment
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter string x, gap penalty, and mismatch penalty: ");

        // getting inputs from the users
        String x = sc.next();
        String y = "";
        int gap = sc.nextInt();
        int mismatch = sc.nextInt();
        int min = Integer.MAX_VALUE;
        String res = "";

        // Open the file
        FileInputStream fstream = new FileInputStream("strings.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        // Read File Line By Line
        // source: https://www.codota.com/code/java/methods/java.io.BufferedReader/close
        while ((strLine = br.readLine()) != null) {
            // Print the content on the console
            // System.out.println(strLine);
            // for (int i = 0; i < 1000; i++) {
            y = strLine;
            // calling the seqAlign methods
            int temp = seqAlign(x, y, gap, mismatch);
            // if seqAlign return the lowest value, keep temp as the min and also the word
            // with that lowest value
            if (temp < min) {
                min = temp;
                res = y;
            }
            // }
        }
        // Close the input stream
        fstream.close();
        sc.close();
        // System.out.println(min);
        System.out.println("The closest string to \"" + x + "\": " + res);
        System.out.println(
                "Given the two strings \"" + x + "\" and \"" + res + "\", the minimum cost of the alignment is " + min);

        // From class: 6 6 abraca abbaab 1 2

    }

    // m is the rows
    // n is the columns
    // let y be the word in the y direction (y.length() = m)
    // let x be the word in the x direction (x.length() = n)
    public static int seqAlign(String x, String y, int gap, int mismatch) {
        int m = y.length();
        int n = x.length();
        // initialize 2d array
        int[][] M = new int[y.length() + 1][x.length() + 1];

        // the first row will get the gap penalty
        for (int i = 0; i <= n; i++) {
            M[0][i] = i * gap;
            // System.out.print(i + " ");
        }

        // the first column will get the gap penalty
        for (int j = 0; j <= m; j++) {
            M[j][0] = j * gap;
            // System.out.print(j + " ");
        }
        // System.out.println();
        int gap1 = 0, gap2 = 0, miss = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // finding the mismatch
                // if there is mismatch between the characters
                if (y.charAt(i - 1) != x.charAt(j - 1)) {
                    gap1 = gap + M[i - 1][j];
                    gap2 = gap + M[i][j - 1];
                    miss = mismatch + M[i - 1][j - 1];
                    M[i][j] = findMin(gap1, gap2, miss);
                    // System.out.print(M[i][j] + " ");
                }
                // no mismatch
                else {
                    gap1 = gap + M[i - 1][j];
                    gap2 = gap + M[i][j - 1];
                    miss = M[i - 1][j - 1];
                    M[i][j] = findMin(gap1, gap2, miss);
                    // System.out.print(M[i][j] + " ");
                }
            }
        }

        // PRINT OUT THE MATRIX
        // for (int i = 0; i <= m; i++) {
        // for (int j = 0; j <= n; j++) {
        // System.out.print(M[i][j] + " ");
        // }
        // System.out.println();
        // }
        // System.out.println();
        return M[m][n];
    }

    public static int findMin(int gap1, int gap2, int miss) {
        return Math.min(Math.min(gap1, gap2), miss);
    }

}