package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "My Demo Application",
                version = "${build.version}",
                license = @License(
                        name = "GPLv3",
                        url = "https://www.gnu.org/licenses/gpl-3.0.en.html"
                )
        ),
        servers = {
                @Server(url = "http://test.internal.net", description = "Test deployment"),
                @Server(url = "http://api.production.com", description = "Production deployment"),
                @Server(url = "http://localhost:8080", description = "Localhost"),
        }
)
public class OpenAPIConfig {
}
