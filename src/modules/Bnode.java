package modules;


public class Bnode {
	int[] keys;
	int index ;
	Bnode[] child;
	int n ;
	boolean leaf;
	public Bnode(int t1 ,boolean leaf1) {
		index= t1;
		leaf = leaf1;
		keys = new int[2*index-1];
		child= new Bnode[2*index];
		n=0;
	}
	

	
	
	public Bnode search(int k){
		int i =0;
		Btree.counter++;
		while(i<n && k >keys[i])
			i++;
		if(keys[i]==k) 
			return this;
		if(leaf==false)
			return null;
		return child[i].search(k);
	}
	
	public void insertNoNFull(int k ) {
		// Initialize index as index of rightmost element 
		int i = n-1; 

		// If this is a leaf node 
		if (leaf == true) 
		{ 
			while (i >= 0 && keys[i] > k) 
			{ 
				keys[i+1] = keys[i]; 
				i--; 
			}
			// Insert the new key at found location 
			keys[i+1] = k; 
			n = n+1; 
		} 
		else // If this node is not leaf 
		{ 
			// Find the child which is going to have the new key 
			while (i >= 0 && keys[i] > k) 
				i--; 
			// See if the found child is full 
			if (child[i+1].n == 2*index-1) 
			{ 
				
				splitChild(i+1, child[i+1]); 

				if (keys[i+1] < k) 
					i++; 
			} 
			child[i+1].insertNoNFull(k); 
		}
	}

	public void splitChild(int i, Bnode y) {
		// Create a new node which is going to store (t-1) keys 
		// of y 
		Bnode z = new Bnode(y.index, y.leaf); 
		z.n = index - 1; 

		// Copy the last (t-1) keys of y to z 
		for (int j = 0; j < index-1; j++) 
			z.keys[j] = y.keys[j+index]; 

		// Copy the last t children of y to z 
		if (y.leaf == false) 
		{ 
			for (int j = 0; j < index; j++) 
				z.child[j] = y.child[j+index]; 
		} 

		// Reduce the number of keys in y 
		y.n = index - 1; 

		// Since this node is going to have a new child, 
		// create space of new child 
		for (int j = n; j >= i+1; j--) 
			child[j+1] = child[j]; 

		// Link the new child to this node 
		child[i+1] = z; 

		for (int j = n-1; j >= i; j--) 
			keys[j+1] = keys[j]; 

		// Copy the middle key of y to this node 
		keys[i] = y.keys[index-1]; 

		// Increment count of keys in this node 
		n = n + 1; 
		
	}
}

