package org.cizquierdo.trifork.books;

import org.cizquierdo.trifork.books.util.Constants;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configuration class to use SpringFox dependency and generates OpenAPI specification.
 * @author Carlos Izquierdo
 * @author izqunited@gmail.com
 */
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(Constants.CONTROLLERS_BASE_PACKAGE))
                .paths(PathSelectors.ant("/api/books/*")).build();
    }
}
