package com.example.server.service;

import java.math.BigInteger;

import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

public class EthereumSignatureService {

	public static String recoverAddressFromSignature(String message, String signature) {
		try {
			String prefix = "\u0019Ethereum Signed Message:\n" + message.length();
			String fullMsg = prefix + message;

			//서명 -> 바이트 배열
			byte[] signatureBytes = Numeric.hexStringToByteArray(signature);

			//서명 -> r,s,v 추출
			Sign.SignatureData signatureData = new Sign.SignatureData(
				signatureBytes[64], //v
				java.util.Arrays.copyOfRange(signatureBytes, 0, 32), //r
				java.util.Arrays.copyOfRange(signatureBytes, 32, 64) //s
			);

			//복원된 공개 키 계싼
			BigInteger publicKey = Sign.signedMessageToKey(fullMsg.getBytes(), signatureData);
			//공개 키 -> 주소
			return "0x" + Keys.getAddress(publicKey);

		} catch (Exception e) {
			throw new RuntimeException("서명으로부터 이더리움 주소를 복원하는데 실패하였습니다.", e);
		}

	}
}
