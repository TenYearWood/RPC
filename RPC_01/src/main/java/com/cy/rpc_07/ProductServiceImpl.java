package com.cy.rpc_07;

import com.cy.rpc.common.IProductService;
import com.cy.rpc.common.Product;

/**
 * Description:RPC
 * Author: chengyu
 * Created: 2021-07-17 15:22
 */
public class ProductServiceImpl implements IProductService {

    @Override
    public Product findProductById(Integer id) {
        return new Product(id, "productName.no_" + id);
    }
}
