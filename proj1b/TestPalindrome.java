import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("cattta"));
        assertTrue(palindrome.isPalindrome("noon"));
    }

    @Test
    public void testIsPalindrome2() {
        assertFalse(palindrome.isPalindrome("cattta", new OffByOne()));
        assertTrue(palindrome.isPalindrome("flake", new OffByOne()));
    }

    @Test
    public void testIsPalindromeN() {
        OffByN OffBy5 = new OffByN(5);
        assertFalse(palindrome.isPalindrome("cattta", OffBy5));
        assertTrue(palindrome.isPalindrome("tinny", OffBy5));
        assertTrue(palindrome.isPalindrome("mouth", OffBy5));
        assertTrue(palindrome.isPalindrome("pink", OffBy5));
    }
}

// Uncomment this class once you've created your Palindrome class.