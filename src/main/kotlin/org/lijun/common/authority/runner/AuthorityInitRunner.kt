/*
 * Copyright 2006-2017 the original author or authors.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lijun.common.authority.runner

import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.apache.ibatis.jdbc.ScriptRunner
import org.lijun.common.authority.entity.SystemConfig
import org.lijun.common.authority.service.SystemConfigService
import org.lijun.common.authority.util.AuthorityConstants
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.InputStream
import java.io.StringReader
import java.sql.Connection
import javax.sql.DataSource

/**
 * CommandLineRunner - AuthorityInitRunner
 *
 * @author lijun
 * @constructor
 */
@Component
open class AuthorityInitRunner : CommandLineRunner {

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var systemConfigService: SystemConfigService

    override fun run(vararg args: String?) {
        val systemConfig: SystemConfig? = this.systemConfigService.findByCode(AuthorityConstants.AUTHORITY_INIT_COMPLETED_KEY)

        if (null == systemConfig || systemConfig.value?.toBoolean()?.not()!!) {
            logger.info("权限模块未初始化，开始初始化...")

            init(dataSource)

            logger.info("权限模块初始化完成...")
        }
    }

    /**
     * 执行初始化
     * @param dataSource
     */
    open internal fun init(dataSource: DataSource) {
        var input: InputStream? = null

        try {
            val connection: Connection = dataSource.connection

            val database: String = connection.catalog

            val resource: Resource = ClassPathResource(AuthorityConstants.AUTHORITY_INIT_SQL)

            if (resource.exists() && resource.isReadable) {
                input = resource.inputStream

                val sql: String = StringUtils.replace(IOUtils.toString(input), "\${database}", database)

                val scriptRunner: ScriptRunner = ScriptRunner(connection)

                scriptRunner.runScript(StringReader(sql))

                val systemConfig: SystemConfig? = this.systemConfigService.findByCode(AuthorityConstants.AUTHORITY_INIT_COMPLETED_KEY)

                if (null != systemConfig && systemConfig.value?.toBoolean()?.not()!!) {
                    systemConfig.value = "true"

                    this.systemConfigService.save(systemConfig)
                }
            }
        } finally {
            IOUtils.closeQuietly(input)
        }
    }

}