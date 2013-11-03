package com.github.jaapterwoerds.jfall;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * A simple generic netty server that takes care of initializing resources, configuring the server behaviour, starting
 * and stopping.
 *
 * @author Jaap ter Woerds
 */
public class NettyServer {
    public static final int BACKLOG = 100;
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private final ServerBootstrap b = new ServerBootstrap();

    private final int port;
    /**
     * The channel initializer creates the pipeline of {@link io.netty.channel.ChannelHandler}s through which the IO events are propagated.
     */
    private final ChannelInitializer<SocketChannel> channelInitializer;

    @Inject
    public NettyServer(@Named("port") int port, ChannelInitializer<SocketChannel> channelInitializer) {
        this.port = port;
        this.channelInitializer = channelInitializer;
    }

    public void start() throws Exception {
        try {
            configure();

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            cleanup();
        }
    }

    private void configure() {
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, BACKLOG)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(channelInitializer);
    }

    public void cleanup() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
