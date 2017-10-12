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
 * Entity - Org
 *
 * @author lijun
 * @constructor
 */
@Entity
@Table(name = "tb_org")
@DynamicInsert
@DynamicUpdate
class Org : UserAuditingEntity() {

    /**
     * 所属区域
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", columnDefinition = "BIGINT(10) COMMENT '所属区域ID'")
    @NotFound(action = NotFoundAction.IGNORE)
    var area: Area? = null

    /**
     * 机构编码
     */
    @Column(name = "[code]", columnDefinition = "VARCHAR(100) COMMENT '机构编码'")
    var code: String? = null

    /**
     * 机构名称
     */
    @Column(name = "[name]", columnDefinition = "VARCHAR(100) COMMENT '机构名称'")
    var name: String? = null

    /**
     * 联系地址
     */
    @Column(columnDefinition = "VARCHAR(255) COMMENT '联系地址'")
    var address: String? = null

    /**
     * 邮编
     */
    @Column(columnDefinition = "VARCHAR(100) COMMENT '邮编'")
    var zipCode: String? = null

    /**
     * 负责人
     */
    @Column(columnDefinition = "VARCHAR(100) COMMENT '负责人'")
    var master: String? = null

    /**
     * 联系电话
     */
    @Column(columnDefinition = "VARCHAR(30) COMMENT '联系电话'")
    var phone: String? = null

    /**
     * 传真
     */
    @Column(columnDefinition = "VARCHAR(30) COMMENT '传真'")
    var fax: String? = null

    /**
     * 邮箱
     */
    @Column(columnDefinition = "VARCHAR(200) COMMENT '邮箱'")
    var email: String? = null

    /**
     * 上级机构
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", columnDefinition = "BIGINT(10) COMMENT '上级机构ID'")
    @NotFound(action = NotFoundAction.IGNORE)
    var parent: Org? = null

    /**
     * 下级机构
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    var childs: Set<Org>? = null

    /**
     * 角色列表
     */
    @OneToMany(mappedBy = "org", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    var roles: Set<Role>? = null

    /**
     * 用户列表
     */
    @OneToMany(mappedBy = "org", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    var users: Set<SysUser>? = null

}