package ticket.service.system.booking.web.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.AliasFor;
import ticket.service.system.booking.config.SecurityConfig;

import java.lang.annotation.*;

@WebMvcTest
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigureMockMvc(addFilters = false)
public @interface WebMvcTestWithNoSecurity {

    @AliasFor(annotation = WebMvcTest.class, attribute = "properties")
    String[] properties() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "value")
    Class<?>[] value() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers")
    Class<?>[] controllers() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "useDefaultFilters")
    boolean useDefaultFilters() default true;

    @AliasFor(annotation = WebMvcTest.class, attribute = "includeFilters")
    ComponentScan.Filter[] includeFilters() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "excludeFilters")
    ComponentScan.Filter[] excludeFilters() default @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                                                          classes = SecurityConfig.class);

    @AliasFor(annotation = WebMvcTest.class, attribute = "excludeAutoConfiguration")
    Class<?>[] excludeAutoConfiguration() default {};

}
