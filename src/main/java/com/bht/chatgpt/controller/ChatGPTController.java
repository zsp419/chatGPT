package com.bht.chatgpt.controller;

import cn.hutool.json.JSONObject;
import com.bht.chatgpt.service.ChatGPTService;
import com.bht.chatgpt.service.ChatModeService;
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

    private final ChatModeService chatGPTService;
    private final Map<String, ChatGPTService> chatGPTServiceMap;

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
