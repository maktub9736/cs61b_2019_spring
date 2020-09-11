public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordToDeque = (Deque<Character>) new ArrayDeque();
        for (int i = 0; i < word.length(); i++) {
            wordToDeque.addLast(word.charAt(i));
        }
        return wordToDeque;
    }

}
