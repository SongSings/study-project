package com.jun.observer.domain;

import lombok.Data;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.FieldType;

/**
 * @author songjun
 * @description TODO
 * @since 2024/7/18
 */
@Data
@IndexName(value= "api-test-index")
public class ApiTestIndex {
    /**
     * es中的唯一id
     */
    private String id;

    private String name;

    private String param;


}


