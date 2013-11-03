package com.github.jaapterwoerds.jfall.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import javax.inject.Inject;

/**
 * @author Jaap ter Woerds
 */
public class SocketChannelChannelInitializer extends ChannelInitializer<SocketChannel> {
    private static final int MAX_LENGTH = 80;
    private static final StringDecoder STRING_DECODER = new StringDecoder(CharsetUtil.UTF_8);
    private static final StringEncoder STRING_ENCODER = new StringEncoder(CharsetUtil.UTF_8);

    private final ModeratorHandler moderatorHandler;
    private final ChatServerHandler chatServerHandler;

    @Inject
    public SocketChannelChannelInitializer(ModeratorHandler moderatorHandler,
                                           ChatServerHandler chatServerHandler) {
        this.moderatorHandler = moderatorHandler;
        this.chatServerHandler = chatServerHandler;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // Decoders
        pipeline.addLast("frameDecoder", new LineBasedFrameDecoder(MAX_LENGTH));
        pipeline.addLast("stringDecoder", STRING_DECODER);
        pipeline.addLast("moderatorHandler", moderatorHandler);
        pipeline.addLast("chatServerHandler", chatServerHandler);
        // Encoder
        pipeline.addLast("stringEncoder", STRING_ENCODER);
    }
}
