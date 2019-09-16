package demo.rpc;

import demo.rpc.handler.ProcessorHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yellow
 * @date 2019/9/12 14:42
 * 温馨提醒:
 * 代码千万行，
 * 注释第一行。
 * 命名不规范，
 * 同事两行泪。
 */
public class RpcProxyServer {

    private final ExecutorService executorService = Executors.newCachedThreadPool();//可缓存线程池,创建的都是非核心线程,它只适用于生命周期短的任务

    /**
     *
     * @param service 需要发布的服务
     * @param port    端口号
     */
    public void publisher(Object service, int port) throws IOException{
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);//启动一个服务
            while (true){
                final Socket socket = serverSocket.accept();//获得一个远程链接
                executorService.execute(new ProcessorHandler(service,socket));

            }


        }catch (Exception ex){

        }finally {
            if (serverSocket!=null){
                serverSocket.close();
            }
        }

    }
}
