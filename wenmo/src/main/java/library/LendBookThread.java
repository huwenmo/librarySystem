package library;

/*
还书线程类
*/
public class LendBookThread implements Runnable{
    private int flag = 0;
   private   Library library = Library.getInstance();
    public void run(){
        while(library.LendBook()||flag<5){
            //因为要满足每个线程只少运行5次，所有用一个flag来进行标记运行次数。
            flag=flag+1;
        }
    }
}
