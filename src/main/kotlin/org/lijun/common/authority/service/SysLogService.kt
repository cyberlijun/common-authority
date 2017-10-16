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

package org.lijun.common.authority.service

import org.lijun.common.authority.entity.SysLog
import org.lijun.common.authority.querycondition.SysLogQueryCondition
import org.lijun.common.service.BaseService
import org.lijun.common.vo.DataTable
import javax.servlet.http.HttpServletRequest

/**
 * Service - SysLogService
 *
 * @author lijun
 */
interface SysLogService : BaseService<SysLog, Long> {

    /**
     * 保存系统日志
     * @param request
     * @param e
     * @param auditable
     */
    fun save(request: HttpServletRequest, e: Exception?, auditable: Boolean = false)

    /**
     * 分页查询系统日志
     * @param condition
     * @return
     */
    fun findPage(condition: SysLogQueryCondition): DataTable<SysLog>

}