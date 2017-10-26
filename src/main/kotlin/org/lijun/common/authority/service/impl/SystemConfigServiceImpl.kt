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

package org.lijun.common.authority.service.impl

import org.lijun.common.authority.entity.SystemConfig
import org.lijun.common.authority.repository.SystemConfigRepository
import org.lijun.common.authority.service.SystemConfigService
import org.lijun.common.service.impl.BaseServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service - SystemConfigServiceImpl
 *
 * @author lijun
 * @constructor
 */
@Service("systemConfigService")
open class SystemConfigServiceImpl(repository: SystemConfigRepository) :
        BaseServiceImpl<SystemConfig, Long>(repository), SystemConfigService {

    @Autowired
    private lateinit var systemConfigRepository: SystemConfigRepository

    @Transactional(readOnly = true)
    override fun findByCode(code: String): SystemConfig? {
        return this.systemConfigRepository.findByCode(code)
    }

}