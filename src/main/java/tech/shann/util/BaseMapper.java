package tech.shann.util;

import org.apache.ibatis.annotations.*;

public interface BaseMapper<T, K> {

    @InsertProvider(type = SqlSourceBuilder.class, method = "build")
    public Long insert(T model);

    @UpdateProvider(type = SqlSourceBuilder.class, method = "build")
    public Long updateById(T model);

    @DeleteProvider(type = SqlSourceBuilder.class, method = "build")
    public Long deleteById(@Param("id") K id);

    @SelectProvider(type = SqlSourceBuilder.class, method = "build")
    public T getById(@Param("id") K id);

    @SelectProvider(type = SqlSourceBuilder.class, method = "build")
    public Boolean existById(@Param("id") K id);

}