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
 * Entity - Role
 *
 * @author lijun
 * @constructor
 */
@Entity
@Table(name = "tb_sys_role")
@DynamicInsert
@DynamicUpdate
class Role : UserAuditingEntity() {

    @Column(name = "[name]", columnDefinition = "VARCHAR(100) COMMENT '角色名称'")
    var name: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", columnDefinition = "BIGINT(10) COMMENT '所属机构ID'")
    @NotFound(action = NotFoundAction.IGNORE)
    var org: Org? = null

    /**
     * 菜单列表
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_menu",
                joinColumns = arrayOf(JoinColumn(name = "role_id", columnDefinition = "BIGINT(10) COMMENT '角色ID'")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "menu_id", columnDefinition = "BIGINT(10) COMMENT '菜单ID'")))
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    var menus: Set<SysMenu>? = null

    /**
     * 用户列表
     */
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    var users: Set<SysUser>? = null

}