package numberrangesummarizer;

import java.util.*;

/** 
 * @author Yael Lerato Kallmann
 * 
 * Implement this Interface to produce a comma delimited list of numbers, grouping the numbers into a range when they are sequential.
 * 
 * Assumptions:
 * - null or blank input returns an empty collection
 * - whitespace around numbers is trimmed
 * - duplicates are removed
 * - result is sorted ascending
 * - single consecutive pair (e.g. 1,2) is still a range: "1-2"
*/

public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {

    @Override
    public Collection<Integer> collect(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> numbers = new ArrayList<>();

        for (String part : input.split(",")) {
            numbers.add(Integer.parseInt(part.trim()));
        }

        Collections.sort(numbers);

        return numbers;
    }

    @Override
    public String summarizeCollection(Collection<Integer> input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        List<Integer> numbers = new ArrayList<>(input);
        StringBuilder result = new StringBuilder();

        int rangeStart = numbers.get(0);
        int rangeEnd   = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            int current = numbers.get(i);

            if (current == rangeEnd + 1) {
                // Current number continues the range
                rangeEnd = current;
            } else {
                // Gap found - write the completed range, then start a new one
                appendRange(result, rangeStart, rangeEnd);
                result.append(", ");
                rangeStart = current;
                rangeEnd   = current;
            }
        }

         // Write the final range
        appendRange(result, rangeStart, rangeEnd);

        return result.toString();
    }

    private void appendRange(StringBuilder result, int start, int end) {
        if (start == end) {
            result.append(start);
        } else {
            result.append(start).append("-").append(end);
        }
    }

}