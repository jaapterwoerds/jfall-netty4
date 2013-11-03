package com.github.jaapterwoerds.jfall;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Jaap ter Woerds
 */
public class Application {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new ChatApplicationModule());
        injector.getInstance(NettyServer.class).start();
    }

}
