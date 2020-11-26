package com.example.springbootv5.until;

import java.lang.annotation.*;

/**
 * @author fangtaozhu
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD,ElementType.PARAMETER,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelAnno {
    String head() default "";
}
