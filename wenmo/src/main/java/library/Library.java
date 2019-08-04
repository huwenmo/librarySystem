package library;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
运用单例模式创建Library类，让还书和借书操作的是一个类
*/
public class Library {
    private Lock lock = new ReentrantLock();//创建一个锁对象
    private Condition cdLend = lock.newCondition();//控制还书线程的锁
    private Condition cdBorrow = lock.newCondition();//控制借书线程的锁
    private int bookNum = 1;//图书馆图书的数量。
    private final static int MAX_BOOK = 3;//图书馆最大容量
    private static Library library  = new Library();

    private Library(){}

    static Library getInstance(){
        return library;
    }

    boolean LendBook(){//还书方法
        boolean flag = true;
        lock.lock();
        try{
            if(bookNum<MAX_BOOK){
                bookNum = bookNum + 1;
                System.out.println("还书一本，现在现在图书馆有："+bookNum+"本书。");
                cdBorrow.signal();//还了一本书就可以开启借书线程
            } else{
                System.out.println("图书馆的书已经满了，还不了书了！！");
                cdLend.await();//图书馆书满了，就关闭还书线程
                flag = false;//图书馆的书满了，就个还书线程一个关闭线程的标记
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return flag;
    }
    void BorrowBook(){//借书方法
        lock.lock();
        try{
            if(bookNum>0){//判断书的数量是否大于0;
                bookNum = bookNum - 1;
                System.out.println("借书一本，现在现在图书馆有："+bookNum+"本书。");
                cdLend.signal();//借了一本书了，就可以唤醒还书线程
            } else{
                System.out.println("现在图书馆已经没有书了，借不了书了！！");
                cdBorrow.await();//图书馆没有书了就关闭借书线程。
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LendBookThread lb = new LendBookThread();
        BorrowBookThread bb = new BorrowBookThread();

        Thread t1 = new Thread(lb);
        Thread t2 = new Thread(bb);

        t1.start();//开启还书线程
        t2.start();//开启借书线程
    }
}

