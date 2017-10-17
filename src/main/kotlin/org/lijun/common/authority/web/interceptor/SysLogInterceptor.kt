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

package org.lijun.common.authority.web.interceptor

import org.lijun.common.authority.service.SysLogService
import org.lijun.common.web.controller.BaseController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Interceptor - SysLogInterceptor
 *
 * @author lijun
 * @constructor
 */
open class SysLogInterceptor(private var auditable: Boolean) : HandlerInterceptorAdapter() {

    @Autowired
    private lateinit var sysLogService: SysLogService

    @Throws(Exception::class)
    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: Exception?) {
        if (handler is HandlerMethod && handler.bean is BaseController) {
            this.sysLogService.save(request!!, ex, auditable)
        }
    }

}