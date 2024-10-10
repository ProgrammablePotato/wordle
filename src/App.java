import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
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
                while (true) {
                    choice = sc.nextLine().strip();
                    if (choice.length() != l) {
                        System.out.printf("Please only enter %d letter long letters: ",l);
                        continue;
                    }
                    break;
                }
                char[] tmp_word_char = choice.toCharArray();
                for (int y = 0; y < l; y++) {
                    word_char[y] = (Character)tmp_word_char[y];
                }
                word_data = gm.ReturnWordData(word_char);
                for (int z = 0; z < l; z++) {
                    if (word_data[z] == 2) {
                        System.out.printf("%s%s%c",bgs[2],fgs[2],word_char[z]);
                    }
                    else {
                        win = 0;
                        System.out.printf("%s%s%c",bgs[word_data[z]],fgs[word_data[z]],word_char[z]);
                    }
                }
                System.out.printf("%s%s\n",bgs[0],fgs[0]);
                if (win == 1) {
                    System.out.printf("%s%sYou won!\n",bgs[0],fgs[0]);
                    break;
                }
            }
            if (win == 0) {
                System.out.printf("%s%sSorry, you lost! The word was %s.\n",bgs[0],fgs[0],gm.GimmeDaWord());
            }
            System.out.println("Would you like to play again? [Y/N] ");
            
            while (true) {
                choice = sc.nextLine();
                if (choice.strip().toLowerCase() == "" || choice.strip().toLowerCase() == "y") {
                    win = 1;
                    break;
                }
                else if (choice.strip().toLowerCase() == "n") {
                    win = 0;
                    break;
                }
                else {
                    System.out.println("Not recognized vlaue, select [Y]es or [N]o: ");
                    continue;
                }
            }
            if (win == 1) {
                gm.NewWord();
                continue;
            }
            else {
                break;
            }
        }
        sc.close();
    }
}
