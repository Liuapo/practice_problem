

/* lintcode 623, google, airbnb, hard
 * 
 * Given a set of strings which just has lower case letters and a target string, 
 * output all the strings for each the edit distance with the target no greater than k.
 * 
 * You have the following 3 operations permitted on a word:
 * Insert a character
 * Delete a character
 * Replace a character
 * 
 * Given words = ["abc", "abd", "abcd", "adc"] and target = "ac", k = 1
 * Return ["abc", "adc"]
 * 
 * */
import java.util.*;

// trie + dp  
public class kEditDistance {
	class Node {
        Node[] child;
        String word;
        public Node() {
            this.child = new Node[26];
        }
	}
	class Trie {
		Node root;
    	public Trie() {
    		this.root = new Node();
    	}
    
    	public void add(String s) {
    		Node curr = root;
    		for (int i = 0; i < s.length(); i++) {
    			char ch = s.charAt(i);
    			if (curr.child[ch - 'a'] == null) {
    				curr.child[ch - 'a'] = new Node();
    			}
    			curr = curr.child[ch - 'a'];
    		}
    		curr.word = s;
    	}
	}
	
	public List<String> kDistance(String[] words, String target, int k) {
        if (words == null || words.length == 0) return new ArrayList<>();
        List<String> result = new ArrayList<>();
        Trie dict = new Trie();
        for (String word: words) {
            dict.add(word);
        }
        int[] dp = new int[target.length() + 1];
        for (int i = 0; i <= target.length(); i++) {
            dp[i] = i;
        }
        find(dict.root, result, target, dp, k, 0);
        return result;
    }
    
    private void find(Node curr, List<String> result, String target, int[] dp, int k, int idx) {
        if (curr.word != null && dp[target.length()] <= k) {
            result.add(curr.word);
        }
        int[] next = new int[target.length() + 1];
        for (int i = 0; i < 26; i++) {
            if (curr.child[i] != null) {
                next[0] = idx + 1;
                for (int j = 1; j <= target.length(); j++) {
                    if (target.charAt(j-1) - 'a' == i) {
                        next[j] = dp[j-1];
                    } else {
                        next[j] = Math.min(next[j-1], Math.min(dp[j], dp[j-1])) + 1;
                    }
                }
                // if (curr.child[i].word != null && next[target.length()] <= k) {
                //     result.add(curr.child[i].word);
                // } // 判定条件不能写在这里，这样会漏掉 s = "", 的情况
                find(curr.child[i], result, target, next, k, idx+1);
            }
        }
    }
    
    public static void main(String[] args) {
    	
    	
    }
    
}
