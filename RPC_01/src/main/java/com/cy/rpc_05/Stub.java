package com.cy.rpc_05;

import com.cy.rpc.common.IUserService;
import com.cy.rpc.common.User;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 这个版本，发往服务器的方法没问题了。但是接收还是有问题，还是将user属性拆解了进行接收，不好。
 *
 *
 */
public class Stub {

    public static IUserService getStub(){
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket s = new Socket("127.0.0.1", 8888);
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();
                oos.writeUTF(methodName);
                oos.writeObject(parameterTypes);
                oos.writeObject(args);
                oos.flush();

                //从服务器返回来的一定是个对象
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                User user = (User) ois.readObject();

                oos.close();
                s.close();

                return user;
            }
        };

        Object o = Proxy.newProxyInstance(IUserService.class.getClassLoader(), new Class[]{IUserService.class}, h);
        System.out.println(o.getClass().getName());
        System.out.println("rpc_05----" + o.getClass().getInterfaces()[0]);
        return (IUserService) o;
    }
}
