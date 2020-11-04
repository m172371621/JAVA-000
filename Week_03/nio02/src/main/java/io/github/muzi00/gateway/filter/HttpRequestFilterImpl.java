package io.github.muzi00.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * HttpRequestFilter 实现类
 */
public class HttpRequestFilterImpl implements  HttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().add("nio", "muzi 00");
    }
}
