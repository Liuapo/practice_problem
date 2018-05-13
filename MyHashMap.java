// implementation of the chaining hashmap
import java.util.Arrays;
import java.lang.IllegalArgumentException;

public class MyHashMap<K, V> {
	// The entry for the hashmap.
	private static class Node<K, V> {
		private final K key;
		private V value;
		Node<K, V> next;
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		} 
		public K getKey() {
			return key;
		}
		public V getValue() {
			return value;
		}
		public void setValue(V val) {
			value = val;
		}
	}
	private static final int DEFAULT_CAP = 16;
	private static final double LOAD_FACTOR = 0.7;
	
	private Node<K, V>[] array;
	private int size;
	
	public MyHashMap() {
		array = (Node<K, V>[]) new Node[DEFAULT_CAP];
		size = 0;
	}
	
	public V get(K key) {
		int idx = idxOf(key);
		Node<K, V> head = array[idx];
		while (head != null) {
			if (equalsKey(key, head.getKey())) {
				return head.getValue();
			}
		}
		return null;
	}
	
	public boolean containsKey(K key) {
		int idx = idxOf(key);
		Node<K, V> head = array[idx];
		while (head != null) {
			if (equalsKey(key, head.getKey())) {
				return true;
			}
			head = head.next;
		}
		return false;
	}
	
	// return the previous value of key, update the value of the key.
	public V put(K key, V value) {
		if (needReHashing()) {
			reHashing();
		}
		int idx = idxOf(key);
		Node<K, V> node = new Node<>(key, value);
		Node<K, V> head = array[idx];
		while (head != null) {
			if (equalsKey(key, head.getKey())) {
				V oldValue = head.getValue();
				head.setValue(value);
				return oldValue;
			}
			head = head.next;
		}
		node.next = array[idx];
		array[idx] = node;
		size++;
		return null;
	}
	
	public V remove(K key) {
		int idx = idxOf(key);
		Node<K, V> head = array[idx];
		if (head == null) {
			throw new IllegalArgumentException();
		}
		Node<K, V> dummy = new Node<>(null, null);
		dummy.next = head;
		V ans = null;
		Node<K, V> prev = dummy;
		while (head != null) {
			if (equalsKey(key, head.getKey())) {
				ans = head.getValue();
				prev.next = head.next;
				break;
			}
		}
		size--;
		array[idx] = dummy.next;
		return ans;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void clear() {
		Arrays.fill(array, null);
		size = 0;
	}
	
	private int hash(K key) {
		if (key == null) return 0;
		return key.hashCode() & 0x7fffffff;
	}
	
	private int idxOf(K key) {
		return hash(key) % array.length;
	}
	
	private boolean needReHashing() {
		return size >= LOAD_FACTOR * array.length;
	}
	
	private boolean equalsKey(K a, K b) {
		return a == b || a != null && a.equals(b);
	}
	
	private void reHashing() {
		Node<K, V>[] old = array;
		array = (Node<K, V>[]) new Node[array.length * 2];
		for (Node<K, V> node : old) {
			while (node != null) {
				Node<K, V> next = node.next;
				int idx = idxOf(node.getKey());
				node.next = array[idx];
				array[idx] = node;
				node = next;
			}
		}
	}
	
	public static void main(String[] args) {
		MyHashMap<Integer, Integer> map = new MyHashMap<>();
		map.put(0, 1);
		System.out.println(map.get(0));
		map.put(0, 2);
		System.out.println(map.containsKey(1));
		System.out.println(map.get(0));
		map.remove(0);
		System.out.println(map.containsKey(0));
		for (int i = 10; i < 40; i++) {
			map.put(i, 2 * i);
		}
		for (int i = 10; i < 40; i++) {
			System.out.println(map.get(i));
		}
	}
}
