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

import org.lijun.common.authority.entity.SysUser
import org.lijun.common.authority.querycondition.SysUserQueryCondition
import org.lijun.common.service.BaseService
import org.lijun.common.vo.DataTable

/**
 * Service - SysUserService
 *
 * @author lijun
 */
interface SysUserService : BaseService<SysUser, Long> {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    fun findByUsername(username: String): SysUser?

    /**
     * 根据邮箱查询用户信息
     * @param email
     * @return
     */
    fun findByEmail(email: String): SysUser?

    /**
     * 获取当前登录用户信息
     * @return
     */
    fun getCurrent(): SysUser?

    /**
     * 分页查询用户信息
     * @param condition
     * @return
     */
    fun findPage(condition: SysUserQueryCondition): DataTable<SysUser>

    /**
     * 校验用户名是否存在
     * @param oldUsername
     * @param username
     */
    fun checkUsername(oldUsername: String?, username: String): Boolean

    /**
     * 校验邮箱是否存在
     * @param oldEmail
     * @param email
     * @return
     */
    fun checkEmail(oldEmail: String?, email: String): Boolean

}