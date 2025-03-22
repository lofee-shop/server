package com.example.server.dto.response;

import java.util.List;

public record ProductSearchResponse(List<Suggestion> suggestions) {

	public record Suggestion(String type, Long id, String name) {
	}

}
