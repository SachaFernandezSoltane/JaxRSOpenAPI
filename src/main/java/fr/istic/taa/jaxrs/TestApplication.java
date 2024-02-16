/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.istic.taa.jaxrs;

import java.util.HashSet;
import java.util.Set;

import fr.istic.taa.jaxrs.domain.Status;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.rest.PetResource;
import fr.istic.taa.jaxrs.rest.SwaggerResource;
import fr.istic.taa.jaxrs.rest.UserResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/")
public class TestApplication extends Application {
	

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();

        // SWAGGER endpoints
        resources.add(OpenApiResource.class);
        //NEW LINE TO ADD
        resources.add(SwaggerResource.class);
        resources.add(PetResource.class);
        resources.add(UserResource.class);

        return resources;
    }

}
