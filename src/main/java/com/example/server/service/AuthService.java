package com.example.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	NonceService nonceService;

	public boolean verifySignature(String walletAddress, String nonce, String signature) {
		//Redis에 저장된 nonce 가져오기
		String storedNonce = nonceService.getNonce(walletAddress);
		//Redis에 저장된 nonce와 다르면 인증 x
		if (storedNonce == null || !storedNonce.equals(nonce)) {
			return false;
		}

		//서명 검증 및 지갑 주소 복원
		String recoveredAddress = EthereumSignatureService.recoverAddressFromSignature(nonce, signature);

		//복원된 주소와 입력된 주소 비교
		if (!walletAddress.equalsIgnoreCase(recoveredAddress)) {
			return false;
		}

		//nonce 삭제 (일회성 사용)
		nonceService.deleteNonce(walletAddress);

		return true;
	}
}
