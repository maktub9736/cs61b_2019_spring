public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordToDeque = (Deque<Character>) new ArrayDeque();
        for (int i = 0; i < word.length(); i++) {
            wordToDeque.addLast(word.charAt(i));
        }
        return wordToDeque;
    }

    public boolean isPalindrome(String word) {
        /** Using the Deque structure and recursion. */
        Deque d = wordToDeque(word);
        return DequeHelper(d);

        /** Not using the Deque structure.
         int len = word.length();
         for (int i = 0; i < len/2; i++) {
             if (word.charAt(i) != word.charAt(len - i - 1)) {
             return false;
             }
         }
         return true;
         */
    }

    private boolean DequeHelper(Deque d) {
        if (d.size() < 2) {
            return true;
        }
        else {
            return d.removeFirst() == d.removeLast() && DequeHelper(d);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        return DequeHelper2(wordToDeque(word), cc);
    }


    private boolean DequeHelper2(Deque<Character> d, CharacterComparator cc) {
        if (d.size() < 2) {
            return true;
        }
        else {
            return cc.equalChars(d.removeFirst(), d.removeLast()) && DequeHelper2(d, cc);
        }
    }
}
