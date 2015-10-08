```java
package MyPackage;

class Product{
	public static boolean count = false;
	public static boolean onSale = true;
	
	public Product(){
		count = false;
		onSale = true;
	}
	
}

class Producer implements Runnable{
	
	public synchronized void put(){
		if(Product.count==false){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Produced a product.");
			Product.count = true;
		}
		else{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Stock is full.");
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(Product.onSale){
			put();
		}
	}
}

class Consumer implements Runnable{
	
	public synchronized void get(){
		if(Product.count==true){
			try{
				Thread.sleep(50);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			System.out.println("Sale a product.");
			Product.count = false;
		}
		else{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Out of stock.");
		}
	}
	
	public void run(){
		while(Product.onSale){
			get();
		}
	}
}

class CountClass implements Runnable{
	private int count = 0;
	synchronized void add(){
		while(count<10){
			count++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("The count is " + count);
		}
		Product.onSale = false;
		System.out.println("The product is salestop.");
	}
	public void run(){
		add();
	}
}

public class MyClass{
	public static void main(String[] args){
		Producer pd = new Producer();
		Consumer cus = new Consumer();
		CountClass count = new CountClass();
		Thread t1 = new Thread(pd);
		Thread t2 = new Thread(cus);
		Thread t3 = new Thread(cus);
		Thread t4 = new Thread(cus);
		Thread ct = new Thread(count);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		ct.start();
		
	}
};
```