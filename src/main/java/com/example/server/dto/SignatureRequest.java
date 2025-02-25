package com.example.server.dto;

import lombok.Getter;

@Getter
public class SignatureRequest {
    private String walletAddress;
    private String nonce;
    private String signature;
}