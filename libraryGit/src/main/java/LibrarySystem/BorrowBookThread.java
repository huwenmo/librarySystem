package LibrarySystem;
//借书线程类
class BorrowBookThread implements Runnable{
    Library library = Library.getInstance();
    public void run(){
        while(true){
            library.BorrowBook();
        }
    }
}
