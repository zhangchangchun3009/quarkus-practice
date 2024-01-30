package pers.zcc.orm.mybatis.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Country
 *
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
@RegisterForReflection // 使用注解的方式声明native镜像使用的反射对象
public class City {

    public String countryCode;

    public String name;

    public Integer population;

}
