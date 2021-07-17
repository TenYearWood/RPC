package com.cy.rpc_03;

import com.cy.rpc.common.IUserService;

/**
 * Description:RPC
 * Author: chengyu
 * Created: 2021-07-17 13:09
 */
public class Client {

    public static void main(String[] args) {
        IUserService service = Stub.getStub();
        System.out.println(service.findUserById(123));
    }
}
