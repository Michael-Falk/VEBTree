/**
 * Implementation of van Emde-Boas Tree for CS 1332 Final Project
 * 
 * @author Michael Falk and Dylan Webb
 */
public class VanEmdeBoasTree<V>{

	private Node min;
	private Node max;
	//size as in capacity of tree, not as in number of elements in tree
	//always of the form 2^k such that k is an Integer > 0
	private int size;
	private VanEmdeBoasTree<V> summary;
	private VanEmdeBoasTree<V>[] clusters;
	
	//null means it is not assigned to a value in the tree, essentially null;
	public VanEmdeBoasTree(int size) {
		if (size < 2) throw new IllegalArgumentException();
		int testSize = size;
		while (testSize % 2 == 0) {
			testSize /= 2;
		}
		if (testSize != 1) throw new IllegalArgumentException();
		this.size = size;
		min = null;
		max = null;
		if(size == 2) {
			summary = null;
			clusters = null;
		} else {
			summary = new VanEmdeBoasTree<V>(upRoot(size));
			clusters = new VanEmdeBoasTree[upRoot(size)];
			for(int i = 0; i < clusters.length; i++)
				clusters[i] = new VanEmdeBoasTree<V>(downRoot(size));
		}
	}
	
	//null if no min exists
	public Node getMin() {
		return min;
	}
	
	//null if no max exists
	public Node getMax() {
		return max;
	}
	
	public boolean contains(Integer key) {
		if(key < 0 || key >= size) return false;
		if(min == null) return false;
		if(min.key.equals(key) || max.key.equals(key))
			return true;
		else if(size == 2) return false;
		else return clusters[high(key)].contains(low(key));
	}
	
	//returns old value if key exists in tree, else null
	public V insert(Integer key, V v) {
		if(key >= size || key < 0) throw new IllegalArgumentException();
		V toExchange = null;
		if(min == null)
			emptyInsert(key, v);
		else {
			if(key == min.key) {
				toExchange = min.value;
				min.value = v;
			}
			if(key < min.key) {
				Node newX = min;
				min = new Node(key, v);
				key = newX.key;
				v = newX.value;
			}
			if(size > 2) {
				if(clusters[high(key)].min == null) {
					summary.insert(high(key), v);
					clusters[high(key)].emptyInsert(low(key), v);
				} else clusters[high(key)].insert(low(key), v);
			}
			if(key == max.key) {
				toExchange = max.value;
				max.value = v;
			}
			if(key > max.key) max = new Node(key, v);	
		}
		return toExchange;
	}
	
	private void emptyInsert(Integer key, V v) {
		min = new Node(key, v);
		max = new Node(key, v);
	}
	
	//returns deleted value, null if key is not a key in there
	public V delete(int key) {
		if(!contains(key)) return null;
		V toDelete = null;
		if(min.key == max.key) {
			toDelete = min.value;
			min = null;
			max = null;
			return toDelete;
		}
		if(size == 2) {
			if(key == min.key) {
				toDelete = min.value;
				if(min.key == max.key) {
					min = null;
					max = null;
				} else min = max;
				return toDelete;
			} else {
				toDelete = max.value;
				max = min;
				return toDelete;
			}
		}
		if(key == min.key) {
			toDelete = min.value;
			if(key == max.key) {
				min = null;
				max = null;
				return toDelete;
			}
			Node newMin = findSuccessor(min.key);
			clusters[high(newMin.key)].delete(low(newMin.key));
			min = newMin;
			return toDelete;
		}
		toDelete = clusters[high(key)].delete(low(key));
		if(clusters[high(key)].min == null) {
			summary.delete(high(key));
		}
		if(key == max.key) {
			toDelete = max.value;
			max = findPredecessor(max.key);
		}
		return toDelete;
	}
	
	// null indicates no successor
	public Node findSuccessor(int key) {
		if(key < 0 || key >= size) return null;
		if(size == 2) {
			if(key == 0 && max != null && max.key == 1) return max;
			else return null;
		}
		if(min.key != null && key < min.key)
			return min;
		else {
			Node maxPos = clusters[high(key)].max;
			if(maxPos != null && low(key) < maxPos.key) {
				Node low = clusters[high(key)].findSuccessor(low(key));
				return new Node(index(high(key), low.key), low.value);
			} else {
				Node successorCluster = summary.findSuccessor(high(key));
				if(successorCluster == null) {
					return null;
				} else {
					Node low = clusters[successorCluster.key].min;
					return new Node(index(successorCluster.key, low.key), low.value);
				}
			}
		}
	}
	
	//null indicates no predecessor
	public Node findPredecessor(int key) {
		if(key < 0 || key >= size) return null;
		if(size == 2) {
			if(key == 1 && min != null && min.key == 0) return min;
			else return null;
		}
		if(max != null && key > max.key)
			return max;
		else {
			Node minPos = clusters[high(key)].min;
			if(minPos != null && low(key) > minPos.key) {
				Node low = clusters[high(key)].findPredecessor(low(key));
				return new Node(index(high(key), low.key), low.value);
			} else {
				Node predecessorCluster = summary.findPredecessor(high(key));
				if(predecessorCluster == null) {
					if(min != null && key > min.key)
						return min;
					return null;
				} else {
					Node low = clusters[predecessorCluster.key].max;
					return new Node(index(predecessorCluster.key, low.key), low.value);
				}
			}
		}
	}
	
	// gives the cluster key is in
	private int high(int key) {
		return (int)Math.floor(1.0*key/downRoot(size));
	}
	
	// gives key's position in it's cluster
	private int low(int key) {
		return key % downRoot(size);
	}
	
	//returns index given cluster and position in cluster
	private int index(int high, int low) {
		return high*downRoot(size) + low;
	}
	
	private int downRoot(int u) {
		int exp =(int)Math.floor(Math.log(u)/(2*Math.log(2)));
		int result = 1;
		for(int i = 0; i < exp; i++)
			result *= 2;
		return result;
	}
	
	private int upRoot(int u) {
		int exp = (int)Math.ceil(Math.log(u)/(2*Math.log(2)));
		int result = 1;
		for(int i = 0; i < exp; i++)
			result *= 2;
		return result;
	}
	
	public class Node{
		protected Integer key;
		protected V value;
		
		public Node(Integer key, V value){
			this.key = key;
			this.value = value;
		}
		
		public V getValue(){
			return value;
		}
		
		public Integer getKey(){
			return key;
		}
	}
}