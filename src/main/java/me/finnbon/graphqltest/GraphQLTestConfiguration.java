package me.finnbon.graphqltest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smoketurner.dropwizard.graphql.GraphQLFactory;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Finn Bon
 */
public class GraphQLTestConfiguration extends Configuration {
	@NotEmpty
	private String template;

	@NotEmpty
	private String defaultName = "Stranger";

	@NotNull
	@Valid
	private final GraphQLFactory graphql = new GraphQLFactory();

	public GraphQLFactory getGraphql() {
		return graphql;
	}

	@JsonProperty
	public String getTemplate() {
		return template;
	}

	@JsonProperty
	public void setTemplate(String template) {
		this.template = template;
	}

	@JsonProperty
	public String getDefaultName() {
		return defaultName;
	}

	@JsonProperty
	public void setDefaultName(String name) {
		this.defaultName = name;
	}
}
