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

package org.lijun.common.authority.service.impl

import org.apache.commons.lang3.StringUtils
import org.lijun.common.authority.entity.LoginLog
import org.lijun.common.authority.entity.SysUser
import org.lijun.common.authority.querycondition.LoginLogQueryCondition
import org.lijun.common.authority.repository.LoginLogRepository
import org.lijun.common.authority.service.LoginLogService
import org.lijun.common.service.impl.BaseServiceImpl
import org.lijun.common.util.DateUtils
import org.lijun.common.vo.DataTable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.criteria.Join
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Predicate

/**
 * Service - LoginLogServiceImpl
 *
 * @author lijun
 * @constructor
 */
@Service("loginLogService")
open class LoginLogServiceImpl(repository: LoginLogRepository) : BaseServiceImpl<LoginLog, Long>(repository), LoginLogService {

    @Autowired
    private lateinit var loginLogRepository: LoginLogRepository

    @Transactional(readOnly = true)
    override fun findByUser(user: SysUser): LoginLog? {
        return this.loginLogRepository.findByUser(user)
    }

    @Transactional(readOnly = true)
    override fun findPage(condition: LoginLogQueryCondition): DataTable<LoginLog> {
        val specification: Specification<LoginLog> = Specification { root, _, cb ->
            var restrictions: Predicate = cb!!.conjunction()

            if (StringUtils.isNotBlank(condition.username)) {
                val join: Join<LoginLog, SysUser> = root.join("user", JoinType.INNER)

                restrictions = cb.and(restrictions, cb.equal(join.get<String>("username"), condition.username))
            }

            if (StringUtils.isNotBlank(condition.ip)) {
                restrictions = cb.and(restrictions, cb.equal(root.get<String>("ip"), condition.ip))
            }

            if (null != condition.startDate) {
                restrictions = cb.and(restrictions, cb.greaterThanOrEqualTo(root.get<Date>("lastLoginTime"), DateUtils.getStartDate(condition.startDate!!)))
            }

            if (null != condition.endDate) {
                restrictions = cb.and(restrictions, cb.lessThanOrEqualTo(root.get<Date>("lastLoginTime"), DateUtils.getEndDate(condition.endDate!!)))
            }

            restrictions
        }

        val sort: Sort = Sort("id")

        return this.findPage(condition, specification, sort)
    }

}