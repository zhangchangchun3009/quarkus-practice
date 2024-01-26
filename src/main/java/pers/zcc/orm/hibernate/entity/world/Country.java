package pers.zcc.orm.hibernate.entity.world;

import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.QueryHint;
import jakarta.persistence.Table;

import java.util.List;

/**
 * Country
 *
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
@Entity
@RegisterForReflection
@Table(name = "country")
@Cacheable //开启hibernate二级缓存
//@NamedQuery(name = "Country.findByPrimaryCode",
//        query = "select code, name, continent from country where code=?0",
//        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") ) //定义一个静态查询，可以使用entityManager调用
public class Country extends PanacheEntity {

    public String code;
    public String name;

    public String continent;

    //如果存在getter、setter，直接调用country.name会被框架改写为调用getName()或setName(String name)
    public String getName(){
        return name.toUpperCase();
    }

    public void setName(String name){
        this.name = name.toLowerCase();
    }

    public static  Country findByPrimaryCode(String code){
        return find("code",code).firstResult();
    }

    public static List<Country> findByContinent(String continent) {
        return list("continent",continent);
    }

    public static List<Country> findByContinent2(String continent) {
        return list("select code, name, continent from country where continent = ?0", continent );
    }

    public static void deletePrimaryCode(String code){
        delete("code", code);
    }

    public static void update(String code,String headOfState){
        update("HeadOfState=?0 where code=?1",headOfState,code);
    }

}
