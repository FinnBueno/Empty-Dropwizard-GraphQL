package me.finnbon.graphqltest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Finn Bon
 */
public class Saying {
	private long id;

	private String content;

	public Saying() {
		// Jackson deserialization
	}

	public Saying(long id, String content) {
		this.id = id;
		this.content = content;
	}

	@JsonProperty
	public long getId() {
		return id;
	}

	@JsonProperty
	public String getContent() {
		return content;
	}
}
