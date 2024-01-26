package pers.zcc.orm.mybatis.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Country
 *
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
@RegisterForReflection
public class City {

    public String countryCode;

    public String name;

    public Integer population;

}
