package com.castile.samples.api;

import com.castile.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.swagger.invocation.context.ContextUtils;
import org.hibernate.validator.constraints.Length;


/**
 * @author castile
 * @date 2025-02-14 22:32
 */
@RestSchema(schemaId = "validator")
@Path("/validator")
@Produces(MediaType.APPLICATION_JSON)
public class Validator {

    @Path("/add")
    @POST
    public int add(@FormParam("a") int a, @Min(20) @FormParam("b") int b) {
        return a + b;
    }

    @Path("/sayhi/{name}")
    @PUT
    public String sayHi(@Length(min = 3) @PathParam("name") String name) {
        ContextUtils.getInvocationContext().setStatus(202);
        return name + " sayhi";
    }

    @Path("/sayhello")
    @POST
    public User sayHello(@Valid User user) {
        user.setName("hello " + user.getName());
        user.setAge(user.getAge());
        return user;
    }
}
