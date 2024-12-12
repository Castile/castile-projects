/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.castile.cas.demos.web;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author castile
 */
@RestController
@RequestMapping("/basic")
public class BasicController {

    // http://127.0.0.1:8080/hello?name=lisi
    @RequestMapping("/hello")
    @ResponseBody
    public Mono<String> hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return Mono.justOrEmpty("Hello " + name);
    }

    // http://127.0.0.1:8080/user
    @RequestMapping("/user")
    @ResponseBody
    public Mono<User> user() {
        User user = new User();
        user.setName("theonefx");
        user.setAge(666);
        return Mono.create(userMonoSink -> userMonoSink.success(user));
    }

    // http://127.0.0.1:8080/save_user?name=newName&age=11
    @RequestMapping("/save_user")
    @ResponseBody
    public Mono<String> saveUser(User u) {
        return Mono.create(stringMonoSink -> stringMonoSink.success("user will save: name=" + u.getName() + ", age=" + u.getAge()));
    }

    @ModelAttribute
    public void parseUser(@RequestParam(name = "name", defaultValue = "unknown user") String name
            , @RequestParam(name = "age", defaultValue = "12") Integer age, User user) {
        user.setName("zhangsan");
        user.setAge(18);
    }
}
