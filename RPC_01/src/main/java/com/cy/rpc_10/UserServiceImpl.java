package com.cy.rpc_10;

import com.cy.rpc.common.IUserService;
import com.cy.rpc.common.User;

public class UserServiceImpl implements IUserService {

    @Override
    public User findUserById(Integer id) {
        return new User(id, "Alice_rpc_10");
    }
}
