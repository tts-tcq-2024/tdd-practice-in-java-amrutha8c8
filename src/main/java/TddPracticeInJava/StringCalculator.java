package TddPracticeInJava;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    public int add(String input) throws Exception {
        if (input.isEmpty()) {
            return 0;
        }

        String[] numbers = splitNumbers(input);
        return calculateSum(numbers);
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

    private int calculateSum(String[] numbers) throws Exception {
        int sum = 0;
        List<Integer> negatives = new ArrayList<>();
        for (String number : numbers) {
            processNumber(number, negatives, sum);
        }
        if (!negatives.isEmpty()) {
            throw new Exception("Negatives not allowed: " + negatives);
        }
        return sum;
    }

    private void processNumber(String number, List<Integer> negatives, int sum) {
        if (!number.isEmpty()) {
            int num = Integer.parseInt(number);
            if (num < 0) {
                negatives.add(num);
            } else if (num <= 1000) {
                sum += num;
            }
        }
    }

    public static void main(String[] args) {
        StringCalculator calculator = new StringCalculator();
        try {
            System.out.println(calculator.add("1\n2,3")); 
            System.out.println(calculator.add("//;\n1;2")); 
            System.out.println(calculator.add("//[***]\n1***2***3")); 
            System.out.println(calculator.add("")); 
            System.out.println(calculator.add("2,1001"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

