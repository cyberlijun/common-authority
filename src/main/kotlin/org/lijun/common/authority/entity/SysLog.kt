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
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * Entity - SysLog
 *
 * @author lijun
 * @constructor
 */
@Entity
@Table(name = "tb_sys_log")
@DynamicInsert
@DynamicUpdate
class SysLog : UserAuditingEntity() {

    /**
     * Enum - LogType
     */
    enum class LogType {

        /**
         * 访问日志
         */
        ACCESS_LOG,

        /**
         * 异常日志
         */
        EXCEPTION_LOG

    }

    /**
     * 日志类型
     */
    @Column(name = "[type]", columnDefinition = "INT(1) COMMENT '日志类型'")
    var type: LogType? = null

    /**
     * 用户ip地址
     */
    @Column(columnDefinition = "VARCHAR(200) COMMENT '用户ip地址'")
    var remoteAddr: String? = null

    /**
     * 用户浏览器agent
     */
    @Column(columnDefinition = "TEXT COMMENT '用户浏览器agent'")
    var userAgent: String? = null

    /**
     * 请求URI
     */
    @Column(columnDefinition = "TEXT COMMENT '请求URI'")
    var requestUri: String? = null

    /**
     * 请求方法
     */
    @Column(columnDefinition = "VARCHAR(5) COMMENT '请求方法'")
    var method: String? = null

    /**
     * 请求参数
     */
    @Column(columnDefinition = "LONGTEXT COMMENT '请求参数'")
    var params: String? = null

    /**
     * 异常信息
     */
    @Column(columnDefinition = "LONGTEXT COMMENT '异常信息'")
    var exception: String? = null

}