/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castile.samples;

import com.castile.entity.User;
import io.vertx.core.MultiMap;
import org.apache.servicecomb.core.CoreConst;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.swagger.invocation.Response;
import org.apache.servicecomb.swagger.invocation.context.ContextUtils;
import org.apache.servicecomb.swagger.invocation.context.InvocationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.CompletableFuture;

@RestSchema(schemaId = "ProviderController")
@RequestMapping(path = "/")
public class ProviderController {
  // a very simple service to echo the request parameter
  @GetMapping("/sayHello")
  public String sayHello(@RequestParam("name") String name) {
    return "Hello " + name;
  }

  @GetMapping("/test/health")
  public String health(){
    return "UP";
  }

  @RequestMapping(path = "/cseResponseCorrect", method = RequestMethod.GET)
  public Response cseResponseCorrect(InvocationContext c1) {
    Response response = Response.createSuccess(jakarta.ws.rs.core.Response.Status.ACCEPTED, new User());
    MultiMap headers = response.getHeaders();
    headers.add("h1", "h1v " + c1.getContext().get(CoreConst.SRC_MICROSERVICE));

    InvocationContext c2 = ContextUtils.getInvocationContext();
    headers.add("h2", "h2v " + c2.getContext().get(CoreConst.SRC_MICROSERVICE));
    return response;
  }



  @GetMapping("/sayHi")
  public CompletableFuture<String> sayHi(String name) {
    CompletableFuture<String> future = new CompletableFuture<>();
    future.complete(name);
    return future;
  }

//  @GetMapping("/test/invoke")
//  public String invoke(@RequestParam("url") String url){
//    RestTemplate restTemplate = RestTemplateBuilder.create();
//    return restTemplate.getForObject(url, String.class);
//  }
//  @GetMapping("/test/invokeTest")
//  public String invokeTest(String url){
//    RestTemplate restTemplate = RestTemplateBuilder.create();
//    return restTemplate.getForObject("cse://provider/test/health", String.class);
//  }
}
