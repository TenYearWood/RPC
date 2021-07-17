package com.cy.rpc_06;

import com.cy.rpc.common.IUserService;


/**
 * Client端只能拿这么一个service，IUserService，getStub只能生成这么一个IUserService。
 *
 * 想通过getStub能够拿到任意的对外提供的公开的接口。
 */
public class Client {

    public static void main(String[] args) {
        IUserService service = (IUserService) Stub.getStub(IUserService.class);

        System.out.println(service.findUserById(123));
    }
}
