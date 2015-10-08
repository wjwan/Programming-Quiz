package MyPackage;
```java
class MyThread implements Runnable{
    String message;
    public MyThread(){
        message = new String("Default Message");
    }
    public MyThread(String str){
        message = new String(str);
    }
    
    public void run(){
        while(MyGuess.flag==false){
            System.out.println(message);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("An interrupt occured.");
            }
        }
    }
}

class MyGuess extends Thread{
    public static boolean flag;
    int num;
    public MyGuess(int n){
        flag = false;
        num = n;
        System.out.println("Guess thread runs.");
    }
    public void run(){
        try{
            int tmp = (int) (Math.random()*100+1);
            while(tmp!=num){
                Thread.sleep(20);
                tmp = (int) (Math.random()*100+1);
                System.out.println("Guess " + tmp);
            }
            System.out.println("Correct!");
            flag = true;
        }catch (InterruptedException e){
            System.out.println("An interrupt occured.");
        }
    }
    
}

public class MyClass {

    public static void main(String[] args) {
        MyThread messageThread = new MyThread("Hellow");
        MyThread messageThread2 = new MyThread("Bye");
        Thread thread1 = new Thread(messageThread);
        System.out.println("A message sent.");
        thread1.start();
        Thread thread2 = new Thread(messageThread2);
        System.out.println("A message sent.");
        thread2.start();
        Thread thread3 = new MyGuess(23);
        thread3.start();
    }

}
```
