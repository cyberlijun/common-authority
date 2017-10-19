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
import org.lijun.common.service.BaseService

/**
 * Service - OrgService
 *
 * @author lijun
 */
interface OrgService : BaseService<Org, Long> {

    /**
     * 查询根节点机构
     * @return
     */
    fun findRoot(): Org

    /**
     * 根据机构编码查询机构
     * @param code
     * @return
     */
    fun findByCode(code: String): Org?

    /**
     * 校验机构编码是否存在
     * @param oldCode
     * @param code
     * @return
     */
    fun checkCode(oldCode: String?, code: String): Boolean

}