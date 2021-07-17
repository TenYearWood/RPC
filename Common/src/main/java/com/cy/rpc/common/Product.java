package com.cy.rpc.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Description:RPC
 * Author: chengyu
 * Created: 2021-07-17 15:23
 */
@Data
@AllArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 1075043484710216017L;

    private Integer id;
    private String productName;
}
