package com.cy.rpc.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = -9156435027567469341L;

    private Integer id;

    private String name;


}
