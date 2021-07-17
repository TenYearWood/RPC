package com.cy.rpc_01;

import com.cy.rpc.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Description:RPC
 * Author: chengyu
 * Created: 2021-07-17 11:18
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 8888);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();   //转成二进制了，所有的传输归根结底要转化为010101二进制传输
        DataOutputStream dos = new DataOutputStream(baos);      //专门用来写各种基础数据类型的
        dos.writeInt(123);

        s.getOutputStream().write(baos.toByteArray());
        s.getOutputStream().flush();

        DataInputStream dis = new DataInputStream(s.getInputStream());
        int id = dis.readInt();         //在这里阻塞着，等着读
        String name = dis.readUTF();
        User user = new User(id, name);

        System.out.println(user);

        dos.close();
        s.close();
    }
}
