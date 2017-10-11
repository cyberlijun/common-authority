package org.lijun.common.authority

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = arrayOf("org.lijun.common"))
open class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)

}