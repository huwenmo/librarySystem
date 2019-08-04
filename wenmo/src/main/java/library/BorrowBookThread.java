package library;
/*
借书线程类
*/
public class BorrowBookThread implements Runnable{
    private int flag = 0;
    private Library library = Library.getInstance();
    public void run(){
        while(true){
            library.BorrowBook();
        }
    }
}
