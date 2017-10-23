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

import org.lijun.common.authority.entity.Org
import org.lijun.common.authority.entity.Role
import org.lijun.common.authority.querycondition.RoleQueryCondition
import org.lijun.common.service.BaseService
import org.lijun.common.vo.DataTable

/**
 * Service - RoleService
 *
 * @author lijun
 */
interface RoleService : BaseService<Role, Long> {

    /**
     * 校验角色名称是否存在
     * @param oldName
     * @param name
     * @param oldOrg
     * @param org
     * @return
     */
    fun checkName(oldName: String?, name: String, oldOrg: Org?, org: Org): Boolean

    /**
     * 分页查询角色
     * @param condition
     * @return
     */
    fun findPage(condition: RoleQueryCondition): DataTable<Role>

}