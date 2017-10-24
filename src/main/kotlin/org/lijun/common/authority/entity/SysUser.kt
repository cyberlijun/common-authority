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

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import javax.persistence.*
import org.apache.commons.codec.digest.DigestUtils
import org.lijun.common.authority.serializer.SysUserStatusSerializer

/**
 * Entity - SysUser
 *
 * @author lijun
 * @constructor
 */
@Entity
@Table(name = "tb_sys_user")
@DynamicInsert
@DynamicUpdate
class SysUser : UserAuditingEntity() {

    /**
     * Enum - Status
     */
    enum class Status {

        /**
         * 正常
         */
        NORMAL,

        /**
         * 停用
         */
        DISABLED

    }

    /**
     * 所在机构
     */
    @JsonProperty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", columnDefinition = "BIGINT(10) COMMENT '所在机构ID'")
    @NotFound(action = NotFoundAction.IGNORE)
    var org: Org? = null

    /**
     * 角色
     */
    @JsonProperty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", columnDefinition = "BIGINT(10) COMMENT '角色ID'")
    @NotFound(action = NotFoundAction.IGNORE)
    var role: Role? = null

    /**
     * 登录用户名
     */
    @JsonProperty
    @Column(columnDefinition = "VARCHAR(50) COMMENT '登录用户名'")
    var username: String? = null

    /**
     * 登录密码
     */
    @Column(name = "[password]", columnDefinition = "VARCHAR(100) COMMENT '登录密码'")
    var password: String? = null

    /**
     * 邮箱
     */
    @JsonProperty
    @Column(columnDefinition = "VARCHAR(100) COMMENT '邮箱'")
    var email: String? = null

    /**
     * 是否是超级管理员
     */
    @JsonProperty
    @Column(columnDefinition = "TINYINT(1) COMMENT '是否是超级管理员'")
    var superAdmin: Boolean? = null

    /**
     * 状态
     */
    @JsonProperty
    @JsonSerialize(using = SysUserStatusSerializer::class)
    @Column(name = "[status]", columnDefinition = "INT(1) COMMENT '状态'")
    var status: SysUser.Status = SysUser.Status.NORMAL

    @PrePersist
    fun prePersist() {
        this.password = DigestUtils.md5Hex(this.password)
        this.status = SysUser.Status.NORMAL
        this.superAdmin = false
    }

}