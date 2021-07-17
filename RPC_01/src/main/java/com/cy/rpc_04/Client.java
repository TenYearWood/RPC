package com.cy.rpc_04;

import com.cy.rpc.common.IUserService;


public class Client {

    public static void main(String[] args) {
        IUserService service = Stub.getStub();
        System.out.println(service.findUserById(123));
    }
}
