package com.cy.rpc_02;

import java.io.IOException;

/**
 * rpc_01不好，为什么
 * 1.麻烦，user要增加个属性代码就要改了。
 * 2.user属性的增减，你的程序得跟着不断的动。
 */
public class Client {

    /**
     * 1.把中间网络传输的这部分省掉，水平不行，不了解IO，inputStream，outPutStream，能不能给个简单的接口？
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        /**
         * 代理，去网络上传输哪些，你替我干了。
         * Client端，把网络的那部分单独出来。屏蔽一些网络的细节
         */
        Stub stub = new Stub();

        System.out.println(stub.findUserById(123));
    }
}
