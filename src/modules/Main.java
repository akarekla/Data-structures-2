package modules;

import java.util.Random;
import java.util.Scanner;



public class Main {
		
	
	public static void main(String[] args) {
		
		
		int range [] = {1000,10000,30000,50000,70000,100000};
		Main m  = new Main();
		int option =1;
		
		while(option>=1 && option <5) {
		System.out.println("1 - > binary");
		System.out.println("2 - > separate");
		System.out.println("3 - > linears");
		System.out.println("4 - > b tree");
		
		Scanner s = new Scanner(System.in);
		option=s.nextInt();
		
		switch (option) {
		case 1:
			System.out.println("Binary");
			for(int i =0 ; i <range.length ; i++)
			{
				System.out.println("For range: "+range[i]);
				m.createAndSearch(range[i], new AVLTree());
			}
			break;
		case 2 :
			System.out.println("Separate Chaining");
			for(int i =0; i<range.length ; i++) {
				System.out.println("For range: "+range[i]);
				m.createAndSearch(range[i], new Separate());
			}
			break ;
		case 3 :
			System.out.println("Linear hashing1 ");
			for(int i =0; i<range.length ; i++) {
				System.out.println("For range: "+range[i]);
				
				m.createAndSearch(range[i], new LinearHashing(5, 100));
			}
			System.out.println("Linear hashing2 ");
			for(int i =0; i<range.length ; i++) {
				System.out.println("For range: "+range[i]);
				LinearHashing l = new LinearHashing(5,100);
				l.secondLimit=true;
				m.createAndSearch(range[i], l);
			}
			break ;
		case 4 :
			System.out.println("B Tree ");
			for(int i =0; i<range.length ; i++) {
				System.out.println("For range: "+range[i]);
				
				m.createAndSearch(range[i], new Btree(100));
			}
			break ;
		default:
			break;
		}
		}
		
		

		
	}
	
	public void createAndSearch(int size , Trees l) {
		int[] exist = new int[30];
		Random  r = new Random();
		for(int i=0; i <size ; i++) {
			int x =Math.abs(r.nextInt());
			if(i>size-30)
				exist [i-size+30]=x; 
			l.insert(x);
		}
		int counter =0;
		
		for(int i =0 ; i<30; i++) {
			l.search(exist[i]);
			counter += l .get();
		}
		int av = counter;
		System.out.println("The average is: "+(float)(av/30));
	}
	
}
