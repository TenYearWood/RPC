package com.cy.rpc_02;

import com.cy.rpc.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description:RPC
 * Author: chengyu
 * Created: 2021-07-17 12:50
 */
public class Stub {

    public User findUserById(Integer id) throws IOException {
        Socket s = new Socket("127.0.0.1", 8888);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);

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
}
