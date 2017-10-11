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

import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction
import org.lijun.common.domain.ManageableEntity
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

/**
 * Entity - UserAuditingEntity
 *
 * @author lijun
 * @constructor
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class UserAuditingEntity : ManageableEntity() {

    /**
     * 创建用户
     */
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user", columnDefinition = "BIGINT(10) COMMENT '创建用户ID'")
    @NotFound(action = NotFoundAction.IGNORE)
    var createUser: SysUser? = null

    /**
     * 最后修改用户
     */
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user", columnDefinition = "BIGINT(10) COMMENT '最后修改用户ID'")
    @NotFound(action = NotFoundAction.IGNORE)
    var updateUser: SysUser? = null

}