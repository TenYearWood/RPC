package com.cy.rpc_06;

import com.cy.rpc.common.IUserService;
import com.cy.rpc.common.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 将getStub改为可以返回任意接口Service
 *
 * 1.传入任何类型的class，对代码进行优化。
 *
 */
public class Stub {

    public static Object getStub(Class clazz){
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket s = new Socket("127.0.0.1", 8888);
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                String clazzName = clazz.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();

                oos.writeUTF(clazzName);
                oos.writeUTF(methodName);
                oos.writeObject(parameterTypes);
                oos.writeObject(args);
                oos.flush();

                //从服务器返回来的一定是个对象
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Object o = ois.readObject();

                oos.close();
                s.close();

                return o;
            }
        };

        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, h);
        System.out.println(o.getClass().getName());
        System.out.println("rpc_05----" + o.getClass().getInterfaces()[0]);
        return o;
    }
}
