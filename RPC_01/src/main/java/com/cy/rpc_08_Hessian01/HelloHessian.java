package com.cy.rpc_08_Hessian01;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.cy.rpc.common.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 上几个版本，采用java自带的Serializable序列化。效率低，序列化后的长度特长，只支持java语言等等缺点。
 *
 * 这里介绍Hessian
 * hessian2序列化
 */
public class HelloHessian {

    public static void main(String[] args) throws Exception {
        User user = new User(1, "zhangsan");
        byte[] bytes = serialize(user);
        System.out.println(bytes.length);
        User deserializeUser = (User) deserialize(bytes);
        System.out.println(deserializeUser);
    }

    public static byte[] serialize(Object o) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(baos);
        output.writeObject(o);
        output.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();
        output.close();
        return bytes;
    }

    public static Object deserialize(byte[] bytes) throws Exception{
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Hessian2Input input = new Hessian2Input(bais);
        Object o = input.readObject();
        input.close();
        bais.close();
        return o;
    }


}
