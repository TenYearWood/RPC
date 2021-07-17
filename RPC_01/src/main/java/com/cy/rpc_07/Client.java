package com.cy.rpc_07;

import com.cy.rpc.common.IProductService;
import com.cy.rpc.common.IUserService;


public class Client {

    public static void main(String[] args) {
        IProductService productService = (IProductService) Stub.getStub(IProductService.class);

        try {
            System.out.println(productService.findProductById(321));
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}
