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

        int n = Math.max(secret.length(), attempt.length());
        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < secret.length(); i++) {
            char s = secret.charAt(i);
            char a = (i < attempt.length()) ? attempt.charAt(i) : 0;
            if (a != 0 && Character.toLowerCase(a) == Character.toLowerCase(s)) {
                masked.append(s);
            } else {
                masked.append('*');
            }
        }
        boolean fullMatch = secret.length() == attempt.length() &&
            secret.equalsIgnoreCase(attempt);
        String result = fullMatch ? "POS" : "NEG";
        return masked.toString() + ";" + result;
    }
}
