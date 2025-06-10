package Utility;

public class WordList {
    private static String[] words = {
        "angsty", "apostrophe", "amazing", "binary", "botanic", "boats", "charisma", "conscious", "cocoon", "cone", "dart", "drift", "demolition", "extreme", "eggs", "economy", "flame", "fear", "fifteen", "giraffe", "game", "growth", "hours", "indices", "intentional", "important", "justice", "java", "jacket", "king", "kangaroo", "keyboard", "llama", "love", "latex", "ministry", "magic", "magnitude", "notification", "nail", "natural", "oblivion", "objective", "overload", "patience", "python", "palace", "queen", "queue", "quest", "rational", "racecar", "reveal", "sand", "solace", "solitude", "treasure", "technology", "triceratops", "underneath", "umbrella", "ultimate", "victory", "vanish", "veil", "waterfall", "wish", "waiting", "xylophone", "xray", "youth", "yarn", "yesterday", "zoom", "zigzag", "zone"
    };

    public static String getRandomWord() {
        return RandomUtilities.choice(words);
    }
}
