package tech.shann.util;

/**
 * Created by shann on 17/7/19.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
//使用该注解，为BaseMapper提供一个表名，
// 如果没有该注解BaseMapper会从xml或实体类信息中
// 猜测并获取表名
public @interface TableName {
    String value();
}
