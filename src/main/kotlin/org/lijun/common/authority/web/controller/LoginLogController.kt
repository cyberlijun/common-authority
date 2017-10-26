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

package org.lijun.common.authority.web.controller

import org.lijun.common.authority.entity.LoginLog
import org.lijun.common.authority.querycondition.LoginLogQueryCondition
import org.lijun.common.authority.service.LoginLogService
import org.lijun.common.vo.DataTable
import org.lijun.common.web.controller.BaseController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Controller - LoginLogController
 *
 * @author lijun
 * @constructor
 */
@Controller
@RequestMapping("\${adminPath}/log/login")
open class LoginLogController : BaseController() {

    @Autowired
    private lateinit var loginLogService: LoginLogService

    @GetMapping
    open fun index(): String = "admin/login_log/list"

    /**
     * 分页查询登录日志
     * @param condition
     * @return
     */
    @PostMapping("list")
    @ResponseBody
    open fun list(condition: LoginLogQueryCondition): DataTable<LoginLog> = this.loginLogService.findPage(condition)

}