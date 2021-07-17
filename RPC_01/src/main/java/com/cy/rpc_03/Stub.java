package com.cy.rpc_03;

import com.cy.rpc.common.IUserService;
import com.cy.rpc.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 而且Client的调用显的不是很合理(Stub里只有findById的代码)，如果有个findByName的新方法,那么就又得重新改进。
 * 下面这种写法解决了方法增加的问题
 */
public class Stub {

    public static IUserService getStub(){
        InvocationHandler h = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket s = new Socket("127.0.0.1", 8888);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                dos.writeInt(123);

                s.getOutputStream().write(baos.toByteArray());
                s.getOutputStream().flush();

                DataInputStream dis = new DataInputStream(s.getInputStream());
                int receiveId = dis.readInt();
                String name = dis.readUTF();
                User user = new User(receiveId, name);

                dos.close();
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
