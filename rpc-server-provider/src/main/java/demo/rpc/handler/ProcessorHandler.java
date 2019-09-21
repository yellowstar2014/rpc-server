package demo.rpc.handler;

import demo.rpc.request.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author yellow
 * @date 2019/9/22
 * 温馨提醒:
 * 代码千万行，
 * 注释第一行。
 * 命名不规范，
 * 同事两行泪。
 */
public class ProcessorHandler implements Runnable{
    Socket socket;
    Object service;

    public ProcessorHandler(Object service,Socket socket) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        //当有IO数据过来，会执行这里
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            //inputStream里面存在的信息有：请求的目标类、请求方法名称、请求方法参数、请求的参数类型
            RpcRequest request = (RpcRequest) inputStream.readObject();
            System.out.println( "服务端收到了请求连接，需调用的服务接口信息是:" +request.toString());
            Object result = invoke(request);//调用方法，获得结果

            //把方法调用的结果输出给client
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);//序列化，写入到通信管道里
            outputStream.flush();
            System.out.println( "服务端调用服务接口后返回的信息是:" +result.toString());

        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            if (inputStream!=null){
                try{
                    inputStream.close();
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
            if (outputStream!=null){
                try{
                    outputStream.close();
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }


    }

    private Object invoke(RpcRequest req) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object[] args = req.getParams();//方法的参数
        Class<?>[] types = new Class[args.length];//存放方法的参数类型
        for (int i=0;i<args.length;i++){
            types[i]=args[i].getClass();//得到方法的参数类型
        }
        //反射加载对应class
        Class clazz = Class.forName(req.getClassName());
        //通过反射找到对应class中的方法
        Method method = clazz.getMethod(req.getMethodName(),types);

        Object result = method.invoke(service,args);//调用方法

        return result;
    }
}
