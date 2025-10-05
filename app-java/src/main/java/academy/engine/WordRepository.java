package academy.engine;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Word repository with 3 categories:
 * IT, ANIMALS, FRUITS
 * Each category has 30 words: 10 easy, 10 medium, 10 hard.
 * Each word also has a hint.
 */
public final class WordRepository {

    public enum Category { IT, ANIMALS, FRUITS }

    private static final Random RNG = new Random();

    private static final Map<Category, List<Map.Entry<String, String>>> EASY = Map.of(
        Category.IT, List.of(
            Map.entry("bug", "A small software error"),
            Map.entry("cpu", "The brain of the computer"),
            Map.entry("git", "A version control system"),
            Map.entry("code", "What programmers write"),
            Map.entry("java", "A popular programming language"),
            Map.entry("node", "A JavaScript runtime"),
            Map.entry("sql", "Language for databases"),
            Map.entry("app", "A short word for application"),
            Map.entry("api", "Connects software components"),
            Map.entry("ux", "User experience")
        ),
        Category.ANIMALS, List.of(
            Map.entry("cat", "It purrs and likes milk"),
            Map.entry("dog", "Human’s best friend"),
            Map.entry("lion", "King of the jungle"),
            Map.entry("tiger", "A big striped cat"),
            Map.entry("fox", "Known for being clever"),
            Map.entry("bear", "Loves honey"),
            Map.entry("wolf", "Lives in packs"),
            Map.entry("frog", "Jumps and croaks"),
            Map.entry("fish", "Lives in water"),
            Map.entry("bat", "The only flying mammal")
        ),
        Category.FRUITS, List.of(
            Map.entry("apple", "It fell on Newton’s head"),
            Map.entry("pear", "Green and shaped like a bulb"),
            Map.entry("plum", "Purple and sweet"),
            Map.entry("kiwi", "Brown outside, green inside"),
            Map.entry("lime", "Small green citrus"),
            Map.entry("fig", "Used in ancient sweets"),
            Map.entry("mango", "Tropical orange fruit"),
            Map.entry("melon", "Big and juicy summer fruit"),
            Map.entry("peach", "Soft skin and a stone inside"),
            Map.entry("grape", "Used to make wine")
        )
    );

    private static final Map<Category, List<Map.Entry<String, String>>> MEDIUM = Map.of(
        Category.IT, List.of(
            Map.entry("service", "Provides functionality to clients"),
            Map.entry("adapter", "Connects incompatible interfaces"),
            Map.entry("compile", "Turns code into machine instructions"),
            Map.entry("package", "Group of related classes"),
            Map.entry("runtime", "When a program is executed"),
            Map.entry("thread", "Lightweight process"),
            Map.entry("network", "Connects computers together"),
            Map.entry("storage", "Where data is kept"),
            Map.entry("session", "Temporary connection between client and server"),
            Map.entry("timeout", "When an operation takes too long")
        ),
        Category.ANIMALS, List.of(
            Map.entry("elephant", "Has a long trunk"),
            Map.entry("giraffe", "Tallest land animal"),
            Map.entry("crocodile", "Lives in water, has sharp teeth"),
            Map.entry("squirrel", "Loves nuts"),
            Map.entry("falcon", "Fast flying bird"),
            Map.entry("ostrich", "Cannot fly, but runs fast"),
            Map.entry("dolphin", "Intelligent sea mammal"),
            Map.entry("turtle", "Has a shell"),
            Map.entry("monkey", "Likes bananas"),
            Map.entry("beaver", "Builds dams")
        ),
        Category.FRUITS, List.of(
            Map.entry("orange", "Round citrus fruit"),
            Map.entry("banana", "Long yellow fruit"),
            Map.entry("papaya", "Tropical fruit with orange flesh"),
            Map.entry("cherry", "Small red fruit with a pit"),
            Map.entry("avocado", "Green fruit, often in salads"),
            Map.entry("apricot", "Small orange fruit"),
            Map.entry("coconut", "Hard shell, white inside"),
            Map.entry("lychee", "Asian fruit, sweet and white inside"),
            Map.entry("guava", "Pink inside, green outside"),
            Map.entry("persimmon", "Orange fruit, tastes like honey")
        )
    );

    private static final Map<Category, List<Map.Entry<String, String>>> HARD = Map.of(
        Category.IT, List.of(
            Map.entry("microservice", "Small independent service in an app"),
            Map.entry("virtualization", "Running multiple OS on one machine"),
            Map.entry("asynchronous", "Non-blocking operations"),
            Map.entry("serialization", "Turning objects into bytes"),
            Map.entry("concurrency", "Multiple tasks at once"),
            Map.entry("polymorphism", "Same interface, different forms"),
            Map.entry("dependency", "External library used in project"),
            Map.entry("refactoring", "Improving code without changing behavior"),
            Map.entry("cryptography", "Science of secure communication"),
            Map.entry("orchestration", "Managing microservices automatically")
        ),
        Category.ANIMALS, List.of(
            Map.entry("wombat", "Australian burrowing marsupial"),
            Map.entry("narwhal", "Arctic whale with a horn"),
            Map.entry("armadillo", "Has a hard shell"),
            Map.entry("chameleon", "Changes color"),
            Map.entry("pangolin", "Covered in scales"),
            Map.entry("albatross", "Large seabird with long wings"),
            Map.entry("lemur", "Lives in Madagascar"),
            Map.entry("porcupine", "Covered in quills"),
            Map.entry("meerkat", "Stands upright on hind legs"),
            Map.entry("flamingo", "Pink bird that stands on one leg")
        ),
        Category.FRUITS, List.of(
            Map.entry("pomegranate", "Red fruit full of juicy seeds"),
            Map.entry("cranberry", "Small red tart berry"),
            Map.entry("blackberry", "Dark purple berry"),
            Map.entry("blueberry", "Small blue berry"),
            Map.entry("watermelon", "Green outside, red inside"),
            Map.entry("strawberry", "Red fruit with tiny seeds on skin"),
            Map.entry("pineapple", "Spiky tropical fruit"),
            Map.entry("cantaloupe", "Orange melon variety"),
            Map.entry("nectarine", "Smooth-skinned peach"),
            Map.entry("tangerine", "Small orange citrus fruit")
        )
    );

    private WordRepository() {}

    public record Pick(String word, String hint, Difficulty difficulty, Category category) {}

    public static Pick pickRandom(Category category, Difficulty difficulty) {
        List<Map.Entry<String, String>> list = switch (difficulty) {
            case EASY -> EASY.get(category);
            case MEDIUM -> MEDIUM.get(category);
            case HARD -> HARD.get(category);
        };
        if (list == null || list.isEmpty()) throw new IllegalArgumentException("Unknown category/difficulty");
        Map.Entry<String, String> entry = list.get(RNG.nextInt(list.size()));
        return new Pick(entry.getKey(), entry.getValue(), difficulty, category);
    }

    public static Pick pickRandom(Difficulty difficulty) {
        Category[] cats = Category.values();
        Category cat = cats[RNG.nextInt(cats.length)];
        return pickRandom(cat, difficulty);
    }

    public static Pick pickRandomAny() {
        Difficulty[] diffs = Difficulty.values();
        Difficulty diff = diffs[RNG.nextInt(diffs.length)];
        return pickRandom(diff);
    }
}
