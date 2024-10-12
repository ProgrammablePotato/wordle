import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class GameMain {
    private HashMap<Character, Integer> alphabet = new HashMap<>();
    private Random rnd;
    private int len;
    private Character[] word;
    private ArrayList<Character[]> words = new ArrayList<>();

    public GameMain(int len) {
        rnd = new Random();
        this.len = len;
        word = new Character[len];
        words = ReadFile(len);
        alphabet = GenerateHashMap(words);
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

    public String ReturnSolution() {
        String out = "";
        for (char ch : word) {
            out += ch;
        }
        return out;
    }

    private static HashMap<Character,Integer> GenerateHashMap(ArrayList<Character[]> in) {
        HashMap<Character,Integer> out = new HashMap<>();
        Character current = 'z';
        for (int x = 0; x < in.size(); x++) {
            if (Character.compare(in.get(x)[0],current) != 0) {
                current = in.get(x)[0];
                out.put(current, x);
            }
        }
        System.out.println(out);
        return out;
    }

    public boolean WordExists(Character[] inWord) {
        Character[] word;
        int exists = 0;
        int i = alphabet.get(inWord[0]);
        //System.out.println(i);
        //System.out.println((Character)(char)(((int)inWord[0])+1));

        //okay so this didn't work quite as intended cuz when I entered "wowow" as my guessed word while testing with the 5 letter word list, it would've wanted to get the int at 'x' from the HashMap. BUT X NEVER EXISTED. Solved by adding the word "xenon" to the word list
        int b = (Character.compare(inWord[0], 'z') != 0)?alphabet.get((Character)(char)(((int)inWord[0])+1)):words.size();
        for (int x = i; x < b; x++) {
            exists = 1;
            word = words.get(x);
            for (int y = 0; y < len; y++) {
                if (Character.compare(inWord[y],word[y]) != 0) {
                    exists = 0;
                    break;
                }
            }
            if (exists == 1) {
                return true;
            }
        }
        return false;
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