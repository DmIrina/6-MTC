package task4;

import java.util.Set;

public class Appearance {
    private final String file;
    private Set<String> words;

    public Appearance(String file, Set<String> words) {
        this.file = file;
        this.words = words;
    }

    public Set<String> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return String.format("%nФайл: %s%nСлова всього: %s%n", file, words);
    }
}
