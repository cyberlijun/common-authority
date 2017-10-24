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

import org.lijun.common.authority.entity.Org
import org.lijun.common.authority.entity.Role
import org.lijun.common.authority.querycondition.RoleQueryCondition
import org.lijun.common.authority.repository.RoleRepository
import org.lijun.common.authority.service.RoleService
import org.lijun.common.service.impl.BaseServiceImpl
import org.lijun.common.vo.DataTable
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.criteria.Join
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Predicate

/**
 * Service - RoleServiceImpl
 *
 * @author lijun
 * @constructor
 */
@Service("roleService")
open class RoleServiceImpl(repository: RoleRepository) : BaseServiceImpl<Role, Long>(repository), RoleService {

    @Autowired
    private lateinit var roleRepository: RoleRepository

    @Transactional(readOnly = true)
    override fun checkName(oldName: String?, name: String, oldOrg: Org?, org: Org): Boolean {
        return if (null != oldOrg && oldOrg == org) {
            StringUtils.equals(oldName, name)
        } else {
            null == this.roleRepository.findByNameAndOrg(name, org)
        }
    }

    @Transactional(readOnly = true)
    override fun findPage(condition: RoleQueryCondition): DataTable<Role> {
        val specification: Specification<Role> = Specification { root, _, cb ->
            var restrictions: Predicate = cb!!.conjunction()

            if (StringUtils.isNotBlank(condition.name)) {
                restrictions = cb.and(restrictions, cb.equal(root.get<String>("name"), condition.name))
            }

            if (null != condition.org?.id) {
                val join: Join<Role, Org> = root.join("org", JoinType.INNER)

                restrictions = cb.and(restrictions, cb.equal(join.get<Long>("id"), condition.org?.id))
            }

            restrictions
        }

        return this.findPage(condition, specification)
    }

    @Transactional(readOnly = true)
    override fun findByOrg(org: Org): List<Role> {
        return this.roleRepository.findByOrg(org)
    }

}