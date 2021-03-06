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
import org.lijun.common.domain.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * Entity - SystemConfig
 *
 * @author lijun
 * @constructor
 */
@Entity
@Table(name = "tb_system_config")
@DynamicInsert
@DynamicUpdate
class SystemConfig : BaseEntity() {

    /**
     * 配置项编码
     */
    @Column(name = "[code]", columnDefinition = "VARCHAR(100) COMMENT '配置项编码'")
    var code: String? = null

    /**
     * 配置项值
     */
    @Column(name = "[value]", columnDefinition = "VARCHAR(100) COMMENT '配置项值'")
    var value: String? = null

    /**
     * 配置项说明
     */
    @Column(columnDefinition = "VARCHAR(200) COMMENT '配置项说明'")
    var description: String? = null

}