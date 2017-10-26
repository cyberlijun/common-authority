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
import org.lijun.common.authority.entity.Org
import org.lijun.common.authority.repository.OrgRepository
import org.lijun.common.authority.service.OrgService
import org.lijun.common.service.impl.BaseServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service - OrgServiceImpl
 *
 * @author lijun
 * @constructor
 */
@Service("orgService")
open class OrgServiceImpl(repository: OrgRepository) : BaseServiceImpl<Org, Long>(repository), OrgService {

    @Autowired
    private lateinit var orgRepository: OrgRepository

    @Transactional(readOnly = true)
    override fun findRoot(): Org {
        return this.orgRepository.findRoot()
    }

    @Transactional(readOnly = true)
    override fun findByCode(code: String): Org? {
        return this.orgRepository.findByCode(code)
    }

    @Transactional(readOnly = true)
    override fun checkCode(oldCode: String?, code: String): Boolean {
        return if (StringUtils.equalsIgnoreCase(oldCode, code)) {
            true
        } else {
            null == this.findByCode(code)
        }
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<Org> {
        val root: Org = this.findRoot()

        val orgs: List<Org> = this.orgRepository.findAll()

        return this.buildTree(orgs, root)
    }

    /**
     * 递归创建机构树形结构
     * @param orgs
     * @param parent
     * @return
     */
    open internal fun buildTree(orgs: List<Org>, parent: Org): List<Org> {
        val list: MutableList<Org> = mutableListOf()

        orgs.forEach {
            if (null == it.parent) {
                return@forEach
            }

            if (it.parent?.equals(parent)!!) {
                list.add(it)

                list.addAll(buildTree(orgs, it))
            }
        }

        return list
    }

}