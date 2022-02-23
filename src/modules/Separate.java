package modules;

import java.util.ArrayList;


public class Separate extends Trees {
		ArrayList<Integer> table[];
		int size =1000;
		public int ac =0;
		@SuppressWarnings("unchecked")
		public Separate() {
			// TODO Auto-generated constructor stub
			table = new ArrayList[size];
			for(int i=0; i <size ; i++) {
				table[i]= new  ArrayList<>();
			
			}
		}
		
		public void search(int k) {
			int h = hashFunc(k);
			ac=0;
			for(int i=0 ; i<table[h].size();i++ ) {
				ac++;
				if(table[h].get(i)==k)
					return ;
			}
			return ;
		}
		public void insert(int k) {
			int h = hashFunc(k);
			table[h].add(k);
		}
		public int hashFunc(int k) {
			return Math.abs( k%size);
		}
		
		
		@Override
		public int get() {
			// TODO Auto-generated method stub
			return ac;
		}
}
