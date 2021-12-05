package org.hack.domain.bean;

import lombok.Data;

@Data
public class Message {
    private String id;
    private String command;
    private String payload;
}
