package com.github.jaapterwoerds.jfall.chat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jaap ter Woerds
 */
@ChannelHandler.Sharable
public class ModeratorHandler extends ChannelInboundHandlerAdapter {
    private final Set<String> dictionary = new HashSet<String>();

    {
        dictionary.add("ruby");
        dictionary.add("c++");
        dictionary.add("cobol");
        dictionary.add("fortan");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
        if (hasFoulLanguage(message)) {
            super.channelRead(ctx, "Java\n");
        } else {
            super.channelRead(ctx, msg);
        }
    }

    private boolean hasFoulLanguage(String message) {
        boolean foundFoulLanguage = false;
        String[] words = StringUtil.split(message, ' ');
        for (String word : words) {
            if (dictionary.contains(word)) {
                foundFoulLanguage = true;
                break;
            }
        }
        return foundFoulLanguage;
    }
}
