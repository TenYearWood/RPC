package com.cy.rpc_09_Hession02;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.cy.rpc.common.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * HelloHessian中，用Hessian2Output有什么优势呢？相比于ObjectOutputStream？
 *
 * 序列化的框架，hessian对序列化之后的长度，字节压缩的很厉害，变的很小。
 * 而且，序列化和反序列化的时间也要比jdk快得多。
 *
 * 这是RPC序列化的一面。还有一面：
 * 我们之前的版本中，server的传输数据都是TCP/IP,最基础的。但是传输数据的协议，还可以使用http。这是RPC的网络协议。
 * 什么协议无所谓，无非是通过这种协议，将序列化好的数据发出去；
 */
public class HessianVsJDK {

    public static void main(String[] args) throws Exception {
        User user = new User(1, "zhangsan");
        System.out.println("hessian: " + hessianSerialize(user).length);
        System.out.println("jdk: " + jdkSerialize(user).length);

        /**
         * console：
         * hessian: 44
         * jdk: 186
         */
    }

    public static byte[] hessianSerialize(Object o) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(baos);
        output.writeObject(o);
        output.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();
        output.close();
        return bytes;
    }

    public static Object hessianDeserialize(byte[] bytes) throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Hessian2Input input = new Hessian2Input(bais);
        Object o = input.readObject();
        input.close();
        bais.close();
        return o;
    }

    public static byte[] jdkSerialize(Object o) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(baos);
        output.writeObject(o);
        output.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();
        output.close();
        return bytes;
    }

    public static Object JdkDeserialize(byte[] bytes) throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream input = new ObjectInputStream(bais);
        Object o = input.readObject();
        input.close();
        bais.close();
        return o;
    }
}
