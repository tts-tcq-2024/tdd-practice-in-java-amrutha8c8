package TddPracticeInJava;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    public int add(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        String[] numbers = splitNumbers(input);
        List<Integer> negativeNumbers = new ArrayList<>();
        int sum = 0;

        for (String number : numbers) {
            int num = parseNumber(number, negativeNumbers);
            if (num <= 1000) {
                sum += num;
            }
        }

        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negativeNumbers);
        }

        return sum;
    }

    private String[] splitNumbers(String input) {
        String delimiter = ",|\n";
        if (input.startsWith("//")) {
            String[] parts = input.split("\n", 2);
            delimiter = extractDelimiters(parts[0]);
            input = parts[1];
        }
        return input.split(delimiter);
    }

    private String extractDelimiters(String header) {
        if (header.startsWith("//[")) {
            List<String> delimiters = new ArrayList<>();
            Matcher matcher = Pattern.compile("\\[(.*?)]").matcher(header);
            while (matcher.find()) {
                delimiters.add(Pattern.quote(matcher.group(1)));
            }
            return String.join("|", delimiters);
        } else {
            return Pattern.quote(header.substring(2, 3));
        }
    }

    private int parseNumber(String number, List<Integer> negativeNumbers) {
        if (number.isEmpty()) {
            return 0;
        }

        int num = Integer.parseInt(number);
        if (num < 0) {
            negativeNumbers.add(num);
        }
        return num;
    }
}
