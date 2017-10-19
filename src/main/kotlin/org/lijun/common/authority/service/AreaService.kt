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

import org.lijun.common.authority.entity.Area
import org.lijun.common.service.BaseService

/**
 * Service - AreaService
 *
 * @author lijun
 */
interface AreaService : BaseService<Area, Long> {

    /**
     * 查询根节点地区
     * @return
     */
    fun findRoot(): Area

    /**
     * 根据区域编码查询
     * @param code
     * @return
     */
    fun findByCode(code: String): Area?

    /**
     * 根据区域名称查询
     * @param name
     * @return
     */
    fun findByName(name: String): Area?

    /**
     * 校验区域编码是否存在
     * @param oldCode
     * @param code
     * @return
     */
    fun checkCode(oldCode: String?, code: String): Boolean

}