package com.text.modular.console.controller;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by FH on 2018/8/28.
 */
public class text {


    public static void main(String[] args){

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://112.74.47.13:3306/demo_fh";
        String username = "root";
        String password = "1314fan.";
        Connection conn = null;

        String s = "线程池的创建public ThreadPoolExecutor(int corePoolSize,\n" +
                "                              int maximumPoolSize,\n" +
                "                              long keepAliveTime,\n" +
                "                              TimeUnit unit,\n" +
                "                              BlockingQueue<Runnable> workQueue,\n" +
                "                              RejectedExecutionHandler handler)\n" +
                "corePoolSize：线程池核心线程数量\n" +
                "maximumPoolSize:线程池最大线程数量\n" +
                "keepAliverTime：当活跃线程数大于核心线程数时，空闲的多余线程最大存活时间\n" +
                "unit：存活时间的单位\n" +
                "workQueue：存放任务的队列\n" +
                "handler：超出线程范围和队列容量的任务的处理程序\n" +
                "\n" +
                "初始化4种类型的线程池：\n" +
                "newFixedThreadPool()\n" +
                "说明：初始化一个指定线程数的线程池，其中corePoolSize == maxiPoolSize，使用LinkedBlockingQuene作为阻塞队列\n" +
                "特点：即使当线程池没有可执行任务时，也不会释放线程。\n" +
                "newCachedThreadPool()\n" +
                "说明：初始化一个可以缓存线程的线程池，默认缓存60s，线程池的线程数可达到Integer.MAX_VALUE，即2147483647，内部使用SynchronousQueue作为阻塞队列；\n" +
                "特点：在没有任务执行时，当线程的空闲时间超过keepAliveTime，会自动释放线程资源；当提交新任务时，如果没有空闲线程，则创建新线程执行任务，会导致一定的系统开销；\n" +
                "因此，使用时要注意控制并发的任务数，防止因创建大量的线程导致而降低性能。\n" +
                "newSingleThreadExecutor()\n" +
                "说明：初始化只有一个线程的线程池，内部使用LinkedBlockingQueue作为阻塞队列。\n" +
                "特点：如果该线程异常结束，会重新创建一个新的线程继续执行任务，唯一的线程可以保证所提交任务的顺序执行\n" +
                "newScheduledThreadPool()\n" +
                "特定：初始化的线程池可以在指定的时间内周期性的执行所提交的任务，在实际的业务场景中可以使用该线程池定期的同步数据。\n" +
                "总结：除了newScheduledThreadPool的内部实现特殊一点之外，其它线程池内部都是基于ThreadPoolExecutor类（Executor的子类）实现的";

        String CONTENT = "";
        try {
            byte[] bytes =String.valueOf(s).getBytes("UTF-8");

            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
            String sql = "INSERT into t_content_post CONTENT="+bytes;

            PreparedStatement pstmt = conn.prepareStatement(sql) ;
            int i=pstmt.executeUpdate(sql);
            System.out.println("i="+i);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
