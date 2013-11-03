package com.github.jaapterwoerds.jfall;

import com.github.jaapterwoerds.jfall.chat.ChatServerModule;
import com.google.inject.Binder;
import com.google.inject.Module;

import static com.google.inject.name.Names.named;

public class ChatApplicationModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.install(new ChatServerModule());
        binder.bindConstant().annotatedWith(named("port")).to(8080);
    }
}
