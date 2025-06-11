package Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WordList {
    private static String[] words = {
        "angsty", "apostrophe", "amazing", "binary", "botanic", "boats", "charisma", "conscious", "cocoon", "cone", "dart", "drift", "demolition", "extreme", "eggs", "economy", "flame", "fear", "fifteen", "giraffe", "game", "growth", "hours", "indices", "intentional", "important", "justice", "java", "jacket", "king", "kangaroo", "keyboard", "llama", "love", "latex", "ministry", "magic", "magnitude", "notification", "nail", "natural", "oblivion", "objective", "overload", "patience", "python", "palace", "queen", "queue", "quest", "rational", "racecar", "reveal", "sand", "solace", "solitude", "treasure", "technology", "triceratops", "underneath", "umbrella", "ultimate", "victory", "vanish", "veil", "waterfall", "wish", "waiting", "xylophone", "xray", "youth", "yarn", "yesterday", "zoom", "zigzag", "zone"
    };

    public static String getRandomWord() {
        return RandomUtilities.choice(words);
    }

    public static String getRandomWordLength(int length) {
        String word;
        do {
            word = getRandomWord();
        } while (word.length() != length);

        return word;
    }

    public static void parseWordsFromFile(String filePath) {
        WordList.parseWordsFromFile(new File(filePath));
    }

    public static void parseWordsFromFile(File file) {
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<String> wordList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            wordList.add(line.trim());
        }
    }

    public static boolean isWord(String word) {
        for (String s : words) {
            if (s.equals(word)) {
                return true;
            }
        }

        return false;
    }
}
