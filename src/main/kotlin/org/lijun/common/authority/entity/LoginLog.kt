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

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import java.util.*
import javax.persistence.*

/**
 * Entity - LoginLog
 *
 * @author lijun
 * @constructor
 */
@Entity
@Table(name = "tb_login_log")
@DynamicInsert
@DynamicUpdate
class LoginLog : UserAuditingEntity() {

    /**
     * 登录用户
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT(10) COMMENT '用户ID'")
    @NotFound(action = NotFoundAction.IGNORE)
    var user: SysUser? = null

    /**
     * 登录次数
     */
    @Column(columnDefinition = "BIGINT(10) COMMENT '登录次数'")
    var loginCount: Long = 0L

    /**
     * 登录用户IP
     */
    @Column(columnDefinition = "VARCHAR(200) COMMENT '登录用户IP'")
    var ip: String? = null

    /**
     * 最后登录时间
     */
    @Column(columnDefinition = "DATETIME COMMENT '最后登录时间'")
    @Temporal(TemporalType.TIMESTAMP)
    var lastLoginTime: Date? = null

    /**
     * 当前登录时间
     */
    @Column(columnDefinition = "DATETIME COMMENT '当前登录时间'")
    @Temporal(TemporalType.TIMESTAMP)
    var loginTime: Date? = null

}