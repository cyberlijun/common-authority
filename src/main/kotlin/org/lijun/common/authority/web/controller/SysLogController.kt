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

import org.lijun.common.authority.entity.SysLog
import org.lijun.common.authority.querycondition.SysLogQueryCondition
import org.lijun.common.authority.service.SysLogService
import org.lijun.common.vo.DataTable
import org.lijun.common.web.controller.BaseController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

/**
 * Controller - SysLogController
 *
 * @author lijun
 * @constructor
 */
@Controller
@RequestMapping("\${adminPath:admin}/log/sys")
open class SysLogController : BaseController() {

    @Autowired
    private lateinit var sysLogService: SysLogService

    /**
     * 转发到系统日志查询首页
     * @return
     */
    @GetMapping
    open fun index(): String = "admin/sys_log/list"

    /**
     * 分页查询系统日志
     * @param condition
     * @return
     */
    @PostMapping("list")
    @ResponseBody
    open fun list(condition: SysLogQueryCondition): DataTable<SysLog> = this.sysLogService.findPage(condition)

}