package modules;

import java.io.*;


public class LinearHashing extends Trees {

	private HashBucket[] hashBuckets;	// pointer to the hash buckets

	private float maxThreshold;		// max load factor threshold


	private int bucketSize;		// max number of keys in each bucket
	private int keysNum;			// number of keys currently stored in the table
	private int keySpace;			// total space the hash table has for keys
	private int pointerBucket;				// pointer to the next bucket to be split
	private int currentNumber;				// current number of buckets
	private int forHashing;				// the n used for the hash function
	
	public static int counter =0;

	public boolean secondLimit=false;


	public LinearHashing(int itsBucketSize, int initPages) { 	// Constructor.

		

		bucketSize = itsBucketSize;
		keysNum = 0;
		pointerBucket = 0;
		currentNumber = initPages;
		forHashing = initPages;
		
		keySpace = currentNumber*bucketSize;
		maxThreshold = (float)0.8;
		

		if ((bucketSize == 0) || (currentNumber == 0)) {
		  System.out.println("error: space for the table cannot be 0");
		  System.exit(1);
		}
		hashBuckets = new HashBucket[currentNumber];
		init();
	
}
	void init() {
		for (int i = 0; i < currentNumber; i++) {
			   hashBuckets[i] = new HashBucket(bucketSize,0);
		}
	}

	private float loadFactor() {		// Returns the current load factor of the hash table.

		return ((float)this.keysNum)/((float)this.keySpace);
	}
	public int getBucketSize() {return bucketSize;}
	public int getKeysNum() {return keysNum;}
	public int getKeySpace() {return keySpace;}
	public void setBucketSize(int size) {bucketSize = size;}
	public void setKeysNum(int num) {keysNum = num;}
	public void setKeySpace(int space) {keySpace = space;}
	
	private int hashFunction(int key){	// Returns a hash based on the key

		int retval;

		retval = Math.abs(key%this.forHashing);
		
		if (retval >= pointerBucket){
		  
		  return retval;
		}
		else {
			 retval = Math.abs(key%(2*this.forHashing));
		         return retval;
		}
	}
	public void insert(int key) {	// Insert a new key.

		//System.out.println( "hashBuckets[" + this.hashFunction(key) + "] =  " + key);
		this.hashBuckets[this.hashFunction(key)].insertKey(key, this);
		if (this.loadFactor() > maxThreshold){
		  //System.out.println("loadFactor = " + this.loadFactor() );
		  this.bucketSplit();
		  //System.out.println("BucketSplit++++++");
		}
		
		if(extra() && secondLimit) {
			bucketSplit();
		}

	}

	private void bucketSplit() {		// Splits the bucket pointed by p.

		int i;
		HashBucket[] newHashBuckets;

		newHashBuckets= new HashBucket[currentNumber+1];
		for (i = 0; i < this.currentNumber; i++){
		   newHashBuckets[i] = this.hashBuckets[i];
		}

		hashBuckets = newHashBuckets;
		hashBuckets[this.currentNumber] = new HashBucket(this.bucketSize,0);
		this.keySpace += this.bucketSize;
		this.hashBuckets[this.pointerBucket].splitBucket(this, 2*this.forHashing, this.pointerBucket, hashBuckets[this.currentNumber]);
		this.currentNumber++;
		if (this.currentNumber == 2*this.forHashing) {
		  this.forHashing = 2*this.forHashing;
		  this.pointerBucket = 0;
		}
		else {
		    this.pointerBucket++;
		}
	}
	public void search(int  key) {
		this.counter =0;
		searchKey(key);
	}

	public boolean searchKey(int key) {		// Search for a key.

		return this.hashBuckets[this.hashFunction(key)].searchKey(key, this);
	}
	boolean extra() {
		for (int i =0; i< hashBuckets.length ; i++) {
			if(numberOfOverflow(hashBuckets[i])>2)
				return true;
		}
		return false;
	}
	int numberOfOverflow(HashBucket hash) {
		if(hash.overflowBucket != null)
			return numberOfOverflow(hash.overflowBucket);
		return hash.getLevel();
	}
	
	
	@Override
	public int get() {
		// TODO Auto-generated method stub
		return this.counter;
	}

}