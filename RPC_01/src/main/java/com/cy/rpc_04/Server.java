package com.cy.rpc_04;

import com.cy.rpc.common.IUserService;
import com.cy.rpc.common.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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
        ObjectInputStream oos = new ObjectInputStream(in);
        DataOutputStream dos = new DataOutputStream(out);

        /**
         * 将方法名读进来
         * 将方法的参数类型、参数都读进来
         */
        String methodName = oos.readUTF();
        Class<?>[] parameterTypes = (Class<?>[]) oos.readObject();
        Object[] args = (Object[]) oos.readObject();

        IUserService service = new UserServiceImpl();
        Method method = service.getClass().getMethod(methodName, parameterTypes);
        User user = (User) method.invoke(service, args);

        dos.writeInt(user.getId());
        dos.writeUTF(user.getName());
        dos.flush();
    }
}
