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

package org.lijun.common.authority.querycondition

import org.lijun.common.authority.entity.SysLog
import org.lijun.common.querycondition.DataTableQueryCondition

/**
 * 系统日志查询条件
 *
 * @author lijun
 * @constructor
 */
class SysLogQueryCondition : DataTableQueryCondition() {

    /**
     * 用户名
     */
    var username: String? = null

    /**
     * 请求URI
     */
    var requestUri: String? = null

    /**
     * 请求方法
     */
    var method: String? = null

    /**
     * 日志类型
     */
    var type: SysLog.LogType? = null

}