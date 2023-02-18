package com.bht.chatgpt.service;

import java.util.Map;

public interface ChatGPTStrategyService {
    Map<String, String> getResultMap(String question);
}
