package me.finnbon.graphqltest;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Finn Bon
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class SayingResource {
	private final String template;
	private final String defaultName;
	private final AtomicLong counter = new AtomicLong();

	public SayingResource(String template, String defaultName) {
		this.template = template;
		this.defaultName = defaultName;
	}

	@GET
	@Timed
	@Path("/hello-world")
	public Saying sayHello(@QueryParam("name") Optional<String> name) {
		final String value = String.format(template, name.orElse(defaultName));
		return new Saying(counter.incrementAndGet(), value);
	}
}
