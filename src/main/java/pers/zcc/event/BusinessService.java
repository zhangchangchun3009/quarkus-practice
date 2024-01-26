package pers.zcc.event;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

/**
 * BusinessService
 *
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
@ApplicationScoped
public class BusinessService {

    @Inject
    Event<TaskComplete> event;

    public void doSomething(){
        //...
        event.fire(new TaskComplete(System.currentTimeMillis()));
    }
}
