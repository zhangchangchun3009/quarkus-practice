package pers.zcc.event;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

/**
 * Observer
 *
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
@ApplicationScoped
public class Observer {

    void logCompleteEvent(@Observes TaskComplete event){
        System.out.println("receive complete event at " + event.time);
    }
}
