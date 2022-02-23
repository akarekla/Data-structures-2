package modules;



public class Btree extends Trees{
	Bnode root;
	int index;
	
	public static int counter=0;
	public Btree(int t) {
		
		this.index= t;
		root=null;
		
	}
	
	public void search(int key) {
		counter =0;
		search (key);
	}

	public Bnode search (int k) {
		
		return root == null ? null :root.search(k);
	}
	public void insert(int k) {
		if(root==null) {
			root= new Bnode(index, true);
			root.keys[0]=k;
			root.n=1;
		}else {
			if (root.n == 2*index-1) 
			{ 	Bnode s = new Bnode(index, false); 
				s.child[0] = root; 
				s.splitChild(0, root); 
				int i = 0; 
				if (s.keys[0] < k) 
					i++; 
				s.child[i].insertNoNFull(k); 
				root = s; 
			} 
			else // If root is not full, call insertNonFull for root 
				root.insertNoNFull(k); 
		}
	}
	
	

	@Override
	public int get() {
		// TODO Auto-generated method stub
		return counter;
	}
}
