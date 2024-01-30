package pers.zcc.orm.mybatis.mapper;

import io.quarkiverse.mybatis.runtime.meta.MapperDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.zcc.orm.mybatis.entity.City;

/**
 * CityMapper
 *
 * @author zhangchangchun
 * @date 2024/1/16
 * @since 1.0
 */
@Mapper
@MapperDataSource("world") // 配置多个数据源时指定数据源
public interface CityMapper {
    City findByName(@Param("name") String name);
}
