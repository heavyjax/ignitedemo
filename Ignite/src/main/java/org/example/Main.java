package org.example;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class Main {

    @PostConstruct
    private void startup(){
        try {
            IgniteInitializer initializer = new IgniteInitializer();
            initializer.init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
