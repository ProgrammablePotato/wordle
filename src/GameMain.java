import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class GameMain {
    private Random rnd;
    private int len;
    private Character[] word;
    private ArrayList<Character[]> words = new ArrayList<>();

    public GameMain(int len) {
        rnd = new Random();
        this.len = len;
        word = new Character[len];
        words = ReadFile(len);
        //System.out.println(words.size());
        if (words == null) {
            word[0] = '0';
        }
        else {
            //System.out.println(words.size());
            int c = rnd.nextInt(words.size());
            //System.out.println(c);
            word = words.get(c);
            //for (Character character : word) {
            //    System.out.print(character);
            //}
        }
    }

    public void NewWord() {
        int c = rnd.nextInt(words.size());
        word = words.get(c);
    }

    public int[] ReturnWordData(Character[] inWord) {
        /*
         * Return values:
         * 0 - Not match
         * 1 - In word
         * 2 - Match
         */
        int[] matches = new int[len];
        Arrays.fill(matches, 0);
        for (int x = 0; x < len; x++) {
            if (Character.compare(inWord[x],word[x]) == 0) {
                matches[x] = 2;
            }
            else {
                for (int y = 0; y < len; y++) {
                    if (Character.compare(inWord[x],word[y]) == 0) {
                        matches[x] = 1;
                        break;
                    }
                }
            }
        }
        return matches;
    }

    public String GimmeDaWord() {
        String out = "";
        for (char ch : word) {
            out += ch;
        }
        return out;
    }

    private static ArrayList<Character[]> ReadFile(int len) {
        ArrayList<Character[]> out = new ArrayList<>();
        String data;
        try {
            File f = new File("src\\resources\\"+String.valueOf(len)+"letter.txt");
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                Character[] word = new Character[len];
                char[] word_char = new char[len];
                data = sc.nextLine();
                word_char = data.toCharArray();
                for (int x = 0; x < len; x++) {
                    word[x] = (Character)word_char[x];
                }
                out.add(word);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.printf("%d letter words are not available!\n",len);
            return null;
        }
        //for (Character[] ch : out) {
        //    for (Character c : ch) {
        //        System.out.print(c);
        //    }
        //    System.out.println();
        //}
        return out;
    }
}