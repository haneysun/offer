package com.example.offer.configurer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

@Slf4j
public class WebSocket extends WebSocketClient {
    public WebSocket(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info("已连接");
    }

    @Override
    public void onMessage(String s) {
        String[] args = {"spot/candle60s:ETH-USDT"};
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("op","subscribe");
        jsonObject.put("args", args);
        send(jsonObject.toJSONString());
        log.info("发送消息");
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("结束连接");
    }

    @Override
    public void onError(Exception e) {
        log.info("出现异常");
    }
}
