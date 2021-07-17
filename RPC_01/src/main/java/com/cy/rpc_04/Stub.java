package com.cy.rpc_04;

import com.cy.rpc.common.IUserService;
import com.cy.rpc.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 但是这里仅仅实现了findUserById的方法代理，如果要实现其他方法的代理该怎么做呢？
 * 这里就要从协议层做出改进
 *
 * 服务器端也要做出对应处理
 */
public class Stub {

    public static IUserService getStub(){
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket s = new Socket("127.0.0.1", 8888);
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                /**
                 * 将方法传到服务器那边，方法是动态产生的。传进来的任何方法都可以。
                 */
                String methodName = method.getName();                   //方法名
                Class<?>[] parameterTypes = method.getParameterTypes(); //参数类型
                oos.writeUTF(methodName);
                oos.writeObject(parameterTypes);
                oos.writeObject(args);
                oos.flush();

                DataInputStream dis = new DataInputStream(s.getInputStream());
                int id = dis.readInt();
                String name = dis.readUTF();
                User user = new User(id, name);

                oos.close();
                s.close();

                return user;
            }
        };

        Object o = Proxy.newProxyInstance(IUserService.class.getClassLoader(), new Class[]{IUserService.class}, h);
        System.out.println(o.getClass().getName());
        System.out.println(o.getClass().getInterfaces()[0]);
        return (IUserService) o;
    }
}
