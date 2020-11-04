package io.github.muzi00.gateway;


import io.github.muzi00.gateway.inbound.HttpInboundServer;

import java.util.ArrayList;
import java.util.List;

public class NettyServerApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";
    // 本地
    public final static String HTTP_LOCALHOST = "http://localhost:";

    public static void main(String[] args) {

        List<String> proxyServers = new ArrayList<>();
        proxyServers.add(HTTP_LOCALHOST + "8801");
        proxyServers.add(HTTP_LOCALHOST + "8801");
        proxyServers.add(HTTP_LOCALHOST + "8801");
        proxyServers.add(HTTP_LOCALHOST + "8801");

        String proxyServer = System.getProperty("proxyServer", "http://localhost:8088");
        String proxyPort = System.getProperty("proxyPort", "8888");

        //  http://localhost:8888/api/hello  ==> gateway API
        //  http://localhost:8088/api/hello  ==> backend service

        int port = Integer.parseInt(proxyPort);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " starting...");
        HttpInboundServer server = new HttpInboundServer(port, proxyServers);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION + " started at http://localhost:" + port + " for server:" + proxyServers);
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
