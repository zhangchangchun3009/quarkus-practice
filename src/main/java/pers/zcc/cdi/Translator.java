package pers.zcc.cdi;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;

/**
 * Translator
 *
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
/** qualifier，用来区分同类型注入时注入类型的标签，默认default可以省略。如果有多个同类型实现，直接注入会报错
 ，此时需要自定义注解，使用@Qualifier注解自定义注解 */
//@Default

/**声明bean。
 * 有Singleton（类似ApplicationScoped，但不是懒加载，而且bean不会被生成代理，性能更好）
 * ApplicationScoped（通常使用这个。bean会被代理，为了解决循环引用、提供懒加载、支持已窄化的域注入较宽的类型。并且能避免在人工注销bean后导致其它依赖bean出现不一致状态）
  RequestScoped(生命周期与一次http请求相关)
  Dependent(每次注入生成新实例)
  SessionScoped(quarkus-undertow扩展开启时才有用。httpsession对象)
 */
@ApplicationScoped
public class Translator {

    String sentence;

    public String getSentence(){return sentence;}

    //框架会自动生成无参构造,直接在别的地方按无参方式注入也不会报错
    //构造方法注入时只有一个构造方法的话不必添加inject.
    //构造方法注入优先于set注入。注释掉构造方法即使用set
    @Inject
    public Translator(String _sentence){
        this.sentence = _sentence;
    }

    //set方法注入非常灵活，只要加了inject就行，方法名不必遵循bean规范，参数个数也没有限制
    @Inject
    public void setAB(int a, String b){
        System.out.println(a);
        this.sentence = b;
    }

    @PostConstruct //生命周期回调方法 bean构造后被调用
    void init() {
        // ...
    }

    @PreDestroy // bean注销前调用
    void destroy() {
        // ...
    }
}
