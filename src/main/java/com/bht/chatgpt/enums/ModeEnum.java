package com.bht.chatgpt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModeEnum {
    qa("qa", "问答模式"),
    chat("chat", "聊天模式");
    private final String key;
    private final String value;
}
