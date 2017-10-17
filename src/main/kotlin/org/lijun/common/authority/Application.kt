package org.lijun.common.authority

import com.fasterxml.jackson.databind.ObjectMapper
import freemarker.ext.beans.BeansWrapper
import freemarker.ext.beans.BeansWrapperBuilder
import org.lijun.common.freemarker.method.PropertyMethod
import org.lijun.common.freemarker.shiro.ShiroTags
import org.lijun.common.support.CustomObjectMapper
import org.lijun.common.web.interceptor.LoggingInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
import freemarker.template.Configuration as FreemarkerConfiguration
import javax.annotation.PostConstruct

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = arrayOf("org.lijun.common"))
open class Application : WebMvcConfigurerAdapter() {

    @Value("#{servletContext.contextPath}")
    private lateinit var ctx: String

    @Value("\${adminPath:admin}")
    private lateinit var adminPath: String

    @Autowired
    private lateinit var freeMarkerConfigurer: FreeMarkerConfigurer

    @Autowired
    private lateinit var propertyMethod: PropertyMethod

    @Bean
    @Primary
    open fun objectMapper(): ObjectMapper = CustomObjectMapper()

    @Bean
    open fun loggingInterceptor(): HandlerInterceptorAdapter = LoggingInterceptor()

    override fun addInterceptors(registry: InterceptorRegistry?) {
        registry?.addInterceptor(loggingInterceptor())
                ?.addPathPatterns("/**")
    }

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer?) {
        val map: Map<String, MediaType> = mapOf(
                "xml" to MediaType.APPLICATION_XML,
                "json" to MediaType.APPLICATION_JSON_UTF8
        )

        configurer?.mediaTypes(map)
        configurer?.ignoreAcceptHeader(true)
        configurer?.favorPathExtension(true)
        configurer?.defaultContentType(MediaType.TEXT_HTML)
    }

    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer?) {
        configurer?.enable()
    }

    @PostConstruct
    open fun init() {
        val wrapper: BeansWrapper = BeansWrapperBuilder(FreemarkerConfiguration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).build()

        val variables: Map<String, Any> = mapOf(
                "ctx" to ctx,
                "adminPath" to "$ctx$adminPath",
                "property" to propertyMethod,
                "shiro" to ShiroTags(wrapper)
        )

        val configuration: FreemarkerConfiguration = this.freeMarkerConfigurer.configuration

        configuration.setSharedVaribles(variables)
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}