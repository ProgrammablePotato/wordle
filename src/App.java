import java.util.Arrays;
import java.util.Scanner;

public class App {
    private static void PrintAlphabet(int[] letters_available) {
        System.out.println();
        for (int x = 0; x < letters_available.length; x++) {
            if (letters_available[x] == 0) {
                System.out.print("\033[42m\033[30m"+(char)(x+97));
            }
            else {
                System.out.print("\033[41m\033[30m"+(char)(x+97));
            }
        }
        System.out.printf("\033[40m\033[37m\n");
    }
    public static void main(String[] args) throws Exception {
        int[] letters_available = new int[26];
        Arrays.fill(letters_available, 0);
        String[] bgs = {"\033[40m","\033[43m","\033[42m"}; //black, yellow, green
        String[] fgs = {"\033[37m","\033[30m","\033[30m"}; //white, black, black
        GameMain gm;
        int l = 0;
        int win = 0;
        Scanner sc = new Scanner(System.in);
        String choice;
        Character[] word_char;
        int[] word_data;

        System.out.print("Please enter the length of words you want to play with (5-5): ");
        while (true) {
            choice = sc.nextLine().strip();
            try {
                l = Integer.parseInt(choice);
                //System.out.println(l);
            } catch (Exception e) {
                System.out.println("Please only enter number from 5 to 5");
                continue;
            }
            word_data = new int[l];
            word_char = new Character[l];
            Arrays.fill(word_char, '0');
            gm = new GameMain(l);
            word_data = gm.ReturnWordData(word_char);
            if (word_data[0] > 0) {
                System.out.println("Please only enter number from 5 to 5");
                continue;
            }
            break;
        }
        while (true) {
            System.out.println("Guess the word now:");
            for (int x = 0; x < 6; x++) {
                win = 1;
                System.out.print("Choice:  ");
                while (true) {
                    choice = sc.nextLine().strip().toLowerCase();
                    if (choice.length() != l) {
                        System.out.printf("Please only enter %d letter long letters: ",l);
                        continue;
                    }
                    char[] tmp_word_char = choice.toCharArray();
                    for (int y = 0; y < l; y++) {
                        word_char[y] = (Character)tmp_word_char[y];
                    }
                    if (!gm.WordExists(word_char)) {
                        System.out.print("This word doesn't exist!\nChoice:  ");
                        continue;
                    }
                    break;
                }
                System.out.print("Matches: ");
                
                word_data = gm.ReturnWordData(word_char);
                for (int z = 0; z < l; z++) {
                    if (word_data[z] == 2) {
                        System.out.printf("%s%s%c",bgs[2],fgs[2],word_char[z]);
                    }
                    else {
                        win = 0;
                        System.out.printf("%s%s%c",bgs[word_data[z]],fgs[word_data[z]],word_char[z]);
                        if (word_data[z] == 0) {
                            letters_available[(int)word_char[z]-97] = 1;
                        }
                    }
                }
                PrintAlphabet(letters_available);
                
                if (win == 1) {
                    System.out.printf("%s%sYou won!\n",bgs[0],fgs[0]);
                    break;
                }
            }
            if (win == 0) {
                System.out.printf("%s%sSorry, you lost! The word was %s.\n",bgs[0],fgs[0],gm.ReturnSolution());
            }
            System.out.print("Would you like to play again? [Y/N] ");
            
            while (true) {
                choice = sc.nextLine();
                choice = choice.strip().toLowerCase();
                //System.out.println(choice);
                if (choice.equals("") || choice.equals("y")) {
                    win = 1;
                    break;
                }
                else if (choice.equals("n")) {
                    win = 0;
                    break;
                } else {
                    System.out.print("Not recognized value, select [Y]es or [N]o: ");
                    continue;
                }
            }
            if (win == 1) {
                gm.NewWord();
                Arrays.fill(letters_available, 0);
                System.out.flush();
                continue;
            } else {break;}
        } sc.close();}
}
