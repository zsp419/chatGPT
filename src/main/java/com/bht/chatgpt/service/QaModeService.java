package com.bht.chatgpt.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bht.chatgpt.enums.ModeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("qa")
public class QaModeService implements ChatGPTService{
    @Value("${token}")
    private String token;

    public Map<String, String> getResultMap(String question) {
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json;charset=UTF-8");
        JSONObject json = new JSONObject();
        json.set("model","text-curie-001");
        json.set("prompt", question);
        json.set("temperature",0.5);
        json.set("max_tokens",260);
        json.set("top_p",1.0);
        json.set("frequency_penalty",0.5);
        json.set("presence_penalty",0.0);
        json.set("stop","stop");

        HttpResponse response = HttpRequest.post("https://api.openai.com/v1/completions")
                .headerMap(headers, false)
                .bearerAuth(token)
                .body(String.valueOf(json))
                .timeout(5 * 60 * 1000)
                .execute();
        String body = response.body();
        JSONObject entries = JSONUtil.parseObj(body);
        Object choices = entries.get("choices");
        Object o = JSONUtil.parseArray(choices).get(0);
        JSONObject entries1 = JSONUtil.parseObj(o);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("answer", entries1.get("text").toString());
        return resultMap;
    }
}
