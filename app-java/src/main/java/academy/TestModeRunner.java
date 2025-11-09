package academy;

public final class TestModeRunner {

    private TestModeRunner() {}

    /**
     * Правило: символ совпадает (без учёта регистра) -> оставляется,
     * а все остальное превращается в "*". POS, если все символы угаданы (positive result),
     * иначе NEG (negative result).
     */
    public static String run(String secret, String attempt) {
        if (secret == null || attempt == null || secret.equals(" ") || attempt.equals(" ")) throw new IllegalArgumentException("Both args required");
        if (secret.isEmpty()) throw new IllegalArgumentException("Secret word must not be empty");

        String s = secret.trim();
        String a = attempt.trim();

        if (s.length() < 2 || s.length() > 30) throw new IllegalArgumentException("Secret word length must be < 31 and > 2");

        String sLow = s.toLowerCase(java.util.Locale.ROOT);
        String aLow = a.toLowerCase(java.util.Locale.ROOT);

        // Маска: раскрываем все буквы секрета, которые встречаются в попытке (без учёта позиции)
        java.util.HashSet<Character> attemptSet = new java.util.HashSet<>();
        for (char c : aLow.toCharArray()) attemptSet.add(c);

        StringBuilder masked = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char orig = s.charAt(i);
            char low  = sLow.charAt(i);
            masked.append(attemptSet.contains(low) ? orig : '*');
        }

        // POS если слова совпали НЕ целиком или целиком (без учёта регистра и пробелов по краям)
        String result = masked.indexOf("*") < 0 ? "POS" : "NEG";
        return masked + ";" + result;
    }
}
