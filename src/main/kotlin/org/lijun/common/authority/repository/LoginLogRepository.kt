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

package org.lijun.common.authority.repository

import org.lijun.common.authority.entity.LoginLog
import org.lijun.common.authority.entity.SysUser
import org.lijun.common.repository.BaseRepository

/**
 * Repository - LoginLogRepository
 *
 * @author lijun
 */
interface LoginLogRepository : BaseRepository<LoginLog, Long> {

    /**
     * 根据用户查询登录信息
     * @param user
     * @return
     */
    fun findByUser(user: SysUser): LoginLog?

}