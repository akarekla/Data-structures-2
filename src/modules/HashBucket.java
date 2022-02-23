package modules;

import java.io.*;

public class HashBucket {

	private int keysNumber;
	private int[] keys;
	public HashBucket overflowBucket;
	private int level;
	public HashBucket(int bucketSize,int level) {		// Constructor: initialize variables
		this.level=level;
		keysNumber = 0;
		keys = new int[bucketSize];
		overflowBucket = null;
	}
	
	
	public int getLevel() {return this.level;}

	public int numKeys(){return keysNumber;}

	public void insertKey(int key, LinearHashing lh) { // inserts a key to the node


		int i;
		int bucketSize = lh.getBucketSize();
		int keysNum = lh.getKeysNum();
		int keySpace = lh.getKeySpace();

		for (i = 0; (i < this.keysNumber) && (i < bucketSize); i++){
		   if (this.keys[i] == key){	//key already here. Ignore the new one
		     return;
		   }
		}
		if (i < bucketSize){				// bucket not full write the new key
		  keys[i] = key;
		  this.keysNumber++;
		  keysNum++;
		  lh.setKeysNum(keysNum); 			// update linear hashing class.
		  //System.out.println("HashBucket.insertKey: KeysNum = " + keysNum );
		}
		else {
		    //System.out.println("Overflow.............");
		    if (this.overflowBucket != null){	// pass key to the overflow
		      this.overflowBucket.insertKey(key, lh);
		    }
		    else {						// create a new overflow and write the new key
			this.overflowBucket = new HashBucket(bucketSize,this.level++);
			keySpace += bucketSize;
			lh.setKeySpace(keySpace);		// update linear hashing class.
			this.overflowBucket.insertKey(key, lh);
		    }
		}
	}






	public boolean searchKey(int key, LinearHashing lh) {

		int i;
		int bucketSize = lh.getBucketSize();

		for (i = 0; (i < this.keysNumber) && (i < bucketSize); i++) {
			LinearHashing.counter++;
		   if (this.keys[i] == key) {	//key found
		     return true;
		   }
		}
		if (this.overflowBucket != null) {				//look at the overflow for the key if one exists
		  return this.overflowBucket.searchKey(key,lh);
	      }
	      else {
		  return false;
	      }
	}

	public void splitBucket(LinearHashing lh, int n, int bucketPos, HashBucket newBucket) {	//splits the current bucket

		int i;
		int bucketSize = lh.getBucketSize();
		int keySpace = lh.getKeySpace();
		int keysNum = lh.getKeysNum();

		for (i = 0; (i < this.keysNumber) && (i < bucketSize);) {
		   if ((this.keys[i]%n) != bucketPos){	//key goes to new bucket
		     newBucket.insertKey(this.keys[i], lh);
		     this.keysNumber--;
		     keysNum = lh.getKeysNum();
		     keysNum--;
		     lh.setKeysNum(keysNum);		// update linear hashing class.
		     //System.out.println("HashBucket.splitBucket.insertKey: KeysNum = " + keysNum );
		     this.keys[i] = this.keys[this.keysNumber];
		   }
		   else {				// key stays here
		     i++;
		   }
		}

		if (this.overflowBucket != null) {	// split the overflow too if one exists
		  this.overflowBucket.splitBucket(lh, n, bucketPos, newBucket);
		}
		while (this.keysNumber != bucketSize) {
		     if (this.overflowBucket == null) {
			 return;
		     }
		     if (this.overflowBucket.numKeys() != 0) {
		       this.keys[this.keysNumber] = this.overflowBucket.removeLastKey(lh);
		       if (this.overflowBucket.numKeys() == 0) {	// overflow empty free it
			 this.overflowBucket = null;
			 keySpace -= bucketSize;
			 lh.setKeySpace(keySpace);      // update linear hashing class.
		       }
		       this.keysNumber++;
		     }
		     else {				// overflow empty free it
			 this.overflowBucket = null;
			 keySpace -= bucketSize;
		         lh.setKeySpace(keySpace);	// update linear hashing class.
		     }
	 	}
	}

	int removeLastKey(LinearHashing lh) {	// remove bucket last key

		int retval;
		int bucketSize = lh.getBucketSize();
		int keySpace = lh.getKeySpace();

		if (this.overflowBucket == null) {
		  if (this.keysNumber != 0){
		    this.keysNumber--;
		    return this.keys[this.keysNumber];
		  }
		  return 0;
		}
		else {
		  retval = this.overflowBucket.removeLastKey(lh);
		  if (this.overflowBucket.numKeys() == 0) {	// overflow empty free it
		    this.overflowBucket = null;
		    keySpace -= bucketSize;
		    lh.setKeySpace(keySpace);			// update linear hashing class.
		  }
		  return retval;
		}
	}
	
	
     

	
} 