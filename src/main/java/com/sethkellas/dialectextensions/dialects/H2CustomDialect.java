package com.sethkellas.dialectextensions.dialects;

import static org.hibernate.type.StandardBasicTypes.STRING;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;

public class H2CustomDialect extends H2Dialect {

    public H2CustomDialect() {
        super();
        registerFunction("LISTAGG", new SQLFunctionTemplate(STRING, 
            "LISTAGG(distinct ?1, ',') WITHIN GROUP(ORDER BY ?1)"));
    }
    
}
