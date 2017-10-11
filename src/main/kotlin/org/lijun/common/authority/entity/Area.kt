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
 * Entity - Area
 *
 * @author lijun
 * @constructor
 */
@Entity
@Table(name = "tb_area")
@DynamicInsert
@DynamicUpdate
class Area : UserAuditingEntity() {

    /**
     * 区域编码
     */
    @Column(columnDefinition = "VARCHAR(100) COMMENT '区域编码'")
    var code: String? = null

    /**
     * 名称
     */
    @Column(name = "[name]", columnDefinition = "VARCHAR(100) COMMENT '区域名称'")
    var name: String? = null

    /**
     * 上级区域
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", columnDefinition = "BIGINT(10) COMMENT '上级区域ID'")
    @NotFound(action = NotFoundAction.IGNORE)
    var parent: Area? = null

    /**
     * 下级区域
     */
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    var childs: Set<Area>? = null

}