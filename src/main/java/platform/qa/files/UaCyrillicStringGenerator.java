package platform.qa.files;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UaCyrillicStringGenerator {

    private static final String charset = "абвгдеєжзиіїйклмнопрстуфхцчшщьюя";
    private static final Random random = new Random();

    /**
     * Generates a random Ukrainian Cyrillic string of the specified length.
     *
     * @param length the length of the generated string.
     * @return the generated string.
     */
    public static String generate(int length) {
        return IntStream.range(0, length)
                .map(i -> random.nextInt(charset.length()))
                .mapToObj(index -> String.valueOf(charset.charAt(index)))
                .collect(Collectors.joining());
    }

}
