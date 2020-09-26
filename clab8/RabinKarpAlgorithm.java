public class RabinKarpAlgorithm {

    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        int len = pattern.length();
        RollingString patternRS = new RollingString(pattern, len);
        RollingString inputRS = new RollingString(input.substring(0, len), len);

        int i = 0;
        while (i < input.length() - len) {
            if (inputRS.equals(patternRS)) {
                return i;
            } else {
                inputRS.addChar(input.charAt(i + len));
            }
            i += 1;
        }
        return -1;
    }

}
