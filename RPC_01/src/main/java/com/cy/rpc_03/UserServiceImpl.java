package com.cy.rpc_03;

import com.cy.rpc.common.IUserService;
import com.cy.rpc.common.User;

/**
 * Description:RPC
 * Author: chengyu
 * Created: 2021-07-17 11:19
 */
public class UserServiceImpl implements IUserService {

    @Override
    public User findUserById(Integer id) {
        return new User(id, "Alice");
    }
}
