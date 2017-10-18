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

package org.lijun.common.authority.audit

import org.lijun.common.authority.entity.SysUser
import org.lijun.common.authority.service.SysUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component

/**
 * AuditorAware - SysUserAuditorAware
 *
 * @author lijun
 * @constructor
 */
@Component
open class SysUserAuditorAware : AuditorAware<SysUser>, ApplicationListener<ContextRefreshedEvent> {

    // 以下代码是在没有集成Shiro时的临时解决方案，打包前去掉相关代码

    private var initializeComplete: Boolean = false

    private var sysUser: SysUser? = null

    override fun onApplicationEvent(event: ContextRefreshedEvent?) {
        if (initializeComplete.not()) {
            this.sysUser = this.sysUserService.findByUsername("admin")

            initializeComplete = true
        }
    }

    @Autowired
    private lateinit var sysUserService: SysUserService

    override fun getCurrentAuditor(): SysUser? {
        return this.sysUser
    }

}