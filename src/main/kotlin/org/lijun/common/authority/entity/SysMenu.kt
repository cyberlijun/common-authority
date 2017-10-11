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

package org.lijun.common.authority.entity

import org.hibernate.annotations.*
import javax.persistence.*
import javax.persistence.Entity
import javax.persistence.Table

/**
 * Entity - SysMenu
 *
 * @author lijun
 * @constructor
 */
@Entity
@Table(name = "tb_sys_menu")
@DynamicInsert
@DynamicUpdate
class SysMenu : UserAuditingEntity() {

    /**
     * 菜单名称
     */
    @Column(columnDefinition = "VARCHAR(200) COMMENT '菜单名称'")
    var menuName: String? = null

    /**
     * 菜单URL
     */
    @Column(columnDefinition = "VARCHAR(200) COMMENT '菜单URL'")
    var menuUrl: String? = null

    /**
     * 上级菜单
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", columnDefinition = "BIGINT(10) COMMENT '上级菜单ID'")
    @NotFound(action = NotFoundAction.IGNORE)
    var parent: SysMenu? = null

    /**
     * 菜单节点图标
     */
    @Column(columnDefinition = "VARCHAR(50) COMMENT '菜单节点图标样式'")
    var iconCls: String? = null

    /**
     * 菜单权限标识
     */
    @Column(columnDefinition = "VARCHAR(100) COMMENT '菜单权限标识'")
    var permission: String? = null

    /**
     * 菜单显示顺序
     */
    @Column(columnDefinition = "BIGINT(10) COMMENT '显示顺序'")
    var sort: Long? = null

    /**
     * 子菜单
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    var childs: Set<SysMenu>? = null

}