package com.cy.rpc_10;

import com.cy.rpc.common.IUserService;
import com.cy.rpc.common.User;


public class Client {

    public static void main(String[] args) {
        IUserService service = (IUserService) Stub.getStub(IUserService.class);

        User user = service.findUserById(111);
        System.out.println(user);
    }
}
