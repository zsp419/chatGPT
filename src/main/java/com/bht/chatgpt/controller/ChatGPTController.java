package com.bht.chatgpt.controller;

import cn.hutool.json.JSONObject;
import com.bht.chatgpt.service.ChatGPTStrategyService;
import com.bht.chatgpt.service.ChatModeStrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatGPT")
public class ChatGPTController {

    private final ChatModeStrategyService chatGPTService;
    private final Map<String, ChatGPTStrategyService> chatGPTServiceMap;

    @RequestMapping("getAnswer")
    public Map<String, String> getAnswer(String question){
        return chatGPTService.getResultMap(question);
    }

    @PostMapping("getResult")
    public Map<String, String> getResult(@RequestBody JSONObject jsonObject){
        String question = jsonObject.getStr("question");
        String mode = jsonObject.getStr("mode");
        return chatGPTServiceMap.get(mode).getResultMap(question);
    }
}
