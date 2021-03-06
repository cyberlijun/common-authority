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

import org.lijun.common.authority.entity.SysMenu
import org.lijun.common.repository.BaseRepository
import org.springframework.data.jpa.repository.Query

/**
 * Repository - SysMenuRepository
 *
 * @author lijun
 */
interface SysMenuRepository : BaseRepository<SysMenu, Long> {

    /**
     * 查询根节点
     * @return
     */
    @Query("select menu from SysMenu menu where menu.id = 1")
    fun findRoot(): SysMenu

    /**
     * 根据根节点查询菜单
     * @param parent
     * @return
     */
    fun findByParent(parent: SysMenu): List<SysMenu>

}