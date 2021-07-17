package com.cy.rpc_10;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 在7版本的基础之上进行优化。马老师的本意应该是引用Hessian的序列化进来。
 * 但是我这个改写的应该不对。
 */
public class Stub {

    public static Object getStub(Class clazz){
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket s = new Socket("127.0.0.1", 8888);
                Hessian2Output output = new Hessian2Output(s.getOutputStream());

                String clazzName = clazz.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();

                output.writeObject(clazzName);
                output.writeObject(methodName);
                output.writeObject(parameterTypes);
                output.writeObject(args);
                output.flush();

                //从服务器返回来的一定是个对象
                Hessian2Input input = new Hessian2Input(s.getInputStream());
                Object o = input.readObject();
                input.close();
                s.close();

                return o;
            }
        };

        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, h);
        System.out.println(o.getClass().getName());
        System.out.println("rpc_10----" + o.getClass().getInterfaces()[0]);
        return o;
    }
}
