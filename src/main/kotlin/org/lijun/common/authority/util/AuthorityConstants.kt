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

package org.lijun.common.authority.util

/**
 * 权限模块常量类
 *
 * @author lijun
 * @constructor
 */
object AuthorityConstants {

    /**
     * 权限模块是否初始化配置项
     */
    const val AUTHORITY_INIT_COMPLETED_KEY: String = "authority_init_completed"

    /**
     * 权限系统初始化SQL文件
     */
    const val AUTHORITY_INIT_SQL: String = "authority_init.sql"

}