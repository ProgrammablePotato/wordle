import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class GameMain {
    private int len;
    private Random rnd = new Random();
    private Character[] word = new Character[len];
    private List<Character[]> words = new ArrayList<>();

    public GameMain(int len) {
        this.len = len;
        this.words = ReadFile(len);
        if (this.words == null) {
            this.word[0] = '0';
        }
        else {
            int c = rnd.nextInt(words.size());
            this.word = words.get(c);
        }
    }

    public void NewWord() {
        int c = rnd.nextInt(words.size());
        this.word = words.get(c);
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
            if (inWord[x].equals(word[x])) {
                matches[x] = 2;
            }
            else {
                for (int y = 0; y < len; y++) {
                    if (inWord[y].equals(word[x])) {
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

    private static List<Character[]> ReadFile(int len) {
        List<Character[]> out = new ArrayList<>();
        Character[] word = new Character[len];
        String data;
        try {
            File f = new File("src\\resources\\"+String.valueOf(len)+"letter.txt");
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                data = sc.nextLine();
                char[] word_char = data.toCharArray();
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
        return out;
    }
}