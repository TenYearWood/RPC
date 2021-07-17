package com.cy.rpc_10;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianInput;
import com.cy.rpc_07.ProductServiceImpl;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description:RPC
 * Author: chengyu
 * Created: 2021-07-17 11:27
 */
public class Server {

    private static boolean running = true;

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8888);
        while (running){
            Socket s = ss.accept();
            process(s);
            s.close();
        }
        ss.close();
    }

    private static void process(Socket s) throws Exception {
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
        Hessian2Input input = new Hessian2Input(in);

        String clazzName = (String) input.readObject();
        String methodName = (String) input.readObject();
        Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
        Object[] args = (Object[]) input.readObject();

        /**
         * 从服务注册表找到具体的类
         */
        Class clazz = null;
        clazz = UserServiceImpl.class;


        Method method = clazz.getMethod(methodName, parameterTypes);
        Object o = method.invoke(clazz.newInstance(), args);

        /**
         * 直接使用writeObject将对象写出去；
         */
        Hessian2Output output = new Hessian2Output(out);
        output.writeObject(o);
        output.flush();
    }
}
