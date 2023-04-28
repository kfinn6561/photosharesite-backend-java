package com.photosharesite.backend.resources;

import com.codahale.metrics.annotation.Timed;
import com.photosharesite.backend.api.Greeting;
import io.swagger.annotations.Api;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Api(value = "Hello World")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;
    private Jdbi jdbi;

    public HelloWorldResource(Jdbi jdbi, String template, String defaultName) {
        this.jdbi=jdbi;
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Greeting sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        jdbi.useHandle(handle -> {
            handle.execute("insert into Users (IPAddress) values ('54321');");
        });
        return new Greeting(counter.incrementAndGet(), value);
    }
}