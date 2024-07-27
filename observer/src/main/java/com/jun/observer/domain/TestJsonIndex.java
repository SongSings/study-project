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
@IndexName(value= "test-json-index")
public class TestJsonIndex {
    /**
     * es中的唯一id
     */
    private String id;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档内容
     */
    @IndexField(fieldType = FieldType.NESTED, nestedClass = JsonField.class)
    private JsonField jsonField;

    @Data
    public static class JsonField {
        private String subField1;
        private String subField2;
        private Integer subField3;
    }
}


