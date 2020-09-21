package me.finnbon.graphqltest;

import com.smoketurner.dropwizard.graphql.GraphQLBundle;
import com.smoketurner.dropwizard.graphql.GraphQLFactory;
import graphql.schema.idl.RuntimeWiring;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * @author Finn Bon
 */
public class GraphQLTestApplication extends Application<GraphQLTestConfiguration> {

	public static void main(String[] args) throws Exception {
		new GraphQLTestApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<GraphQLTestConfiguration> bootstrap) {
		final GraphQLBundle<GraphQLTestConfiguration> bundle =
			new GraphQLBundle<GraphQLTestConfiguration>() {
				@Override
				public GraphQLFactory getGraphQLFactory(GraphQLTestConfiguration graphQLTestConfiguration) {
					GraphQLFactory factory = graphQLTestConfiguration.getGraphql();
					factory.setRuntimeWiring(buildWiring(graphQLTestConfiguration));
					return factory;
				}
			};
		bootstrap.addBundle(bundle);
	}

	public void run(GraphQLTestConfiguration graphQLTestConfiguration, Environment environment) throws Exception {
		// Enable CORS to allow GraphQL on a separate port to reach the API
		final FilterRegistration.Dynamic cors = environment.servlets().addFilter("cors", CrossOriginFilter.class);
		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

		final SayingResource resource = new SayingResource(graphQLTestConfiguration.getTemplate(), graphQLTestConfiguration.getDefaultName());
		environment.jersey().register(resource);
	}

	private static RuntimeWiring buildWiring(GraphQLTestConfiguration configuration) {

		final SayingDataFetcher fetcher =
			new SayingDataFetcher(configuration.getTemplate(), configuration.getDefaultName());

		final RuntimeWiring wiring =
			RuntimeWiring.newRuntimeWiring()
				.type("Query", typeWiring -> typeWiring.dataFetcher("saying", fetcher))
				.build();

		return wiring;
	}
}
