package demo.rpc;

import demo.rpc.rpcServer.RpcProxyServer;
import demo.rpc.service.DemoServiceImpl;
import demo.rpc.service.IDemoService;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {

        System.out.println( "The project is RPC framework" );
        IDemoService demoService = new DemoServiceImpl();
        RpcProxyServer proxyServer = new RpcProxyServer();
        proxyServer.publisher(demoService,8081);//发布服务，即启动socket服务端
    }
}
