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
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.lijun.common.authority.entity.Org
import org.lijun.common.authority.entity.SysUser
import org.lijun.common.authority.querycondition.SysUserQueryCondition
import org.lijun.common.authority.repository.SysUserRepository
import org.lijun.common.authority.service.SysUserService
import org.lijun.common.service.impl.BaseServiceImpl
import org.lijun.common.vo.DataTable
import org.lijun.common.vo.Principal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.criteria.*

/**
 * Service - SysUserServiceImpl
 *
 * @author lijun
 * @constructor
 */
@Service("sysUserService")
open class SysUserServiceImpl(repository: SysUserRepository) : BaseServiceImpl<SysUser, Long>(repository), SysUserService {

    @Autowired
    private lateinit var sysUserRepository: SysUserRepository

    @Transactional(readOnly = true)
    override fun findByUsername(username: String): SysUser? {
        return this.sysUserRepository.findByUsername(username)
    }

    @Transactional(readOnly = true)
    override fun findByEmail(email: String): SysUser? {
        return this.sysUserRepository.findByEmail(email)
    }

    @Transactional(readOnly = true)
    override fun getCurrent(): SysUser? {
        val subject: Subject? = SecurityUtils.getSubject()

        if (null != subject) {
            val principal: Any? = subject.principal

            if (null != principal) {
                return this.findById((principal as Principal).id)
            }

        }

        return null
    }

    @Transactional(readOnly = true)
    override fun findPage(condition: SysUserQueryCondition): DataTable<SysUser> {
        val specification: Specification<SysUser> = Specification<SysUser> { root, _, cb ->
            var restrictions: Predicate = cb!!.conjunction()

            if (null != condition.org) {
                val join: Join<SysUser, Org> = root.join("org", JoinType.INNER)

                restrictions = cb.and(restrictions, cb.equal(join.get<Long>("id"), condition.org?.id))
            }

            if (StringUtils.isNotBlank(condition.username)) {
                restrictions = cb.and(restrictions, cb.equal(root.get<String>("username"), condition.username))
            }

            if (StringUtils.isNotBlank(condition.email)) {
                restrictions = cb.and(restrictions, cb.equal(root.get<String>("email"), condition.email))
            }

            if (null != condition.status) {
                restrictions = cb.and(restrictions, cb.equal(root.get<SysUser.Status>("status"), condition.status))
            }

            restrictions
        }

        val sort: Sort = Sort(Sort.Direction.ASC, "id")

        return this.findPage(condition, specification, sort)
    }

    @Transactional(readOnly = true)
    override fun checkUsername(oldUsername: String?, username: String): Boolean {
        return if (StringUtils.equalsIgnoreCase(oldUsername, username)) {
            true
        } else {
            null == this.sysUserRepository.findByUsername(username)
        }
    }

    @Transactional(readOnly = true)
    override fun checkEmail(oldEmail: String?, email: String): Boolean {
        return if (StringUtils.equalsIgnoreCase(oldEmail, email)) {
            true
        } else {
            null == this.sysUserRepository.findByEmail(email)
        }
    }

}