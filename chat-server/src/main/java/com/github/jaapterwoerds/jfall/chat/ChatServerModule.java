package com.github.jaapterwoerds.jfall.chat;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class ChatServerModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(new TypeLiteral<ChannelInitializer<SocketChannel>>() {
        }).to(SocketChannelChannelInitializer.class);
    }
}
