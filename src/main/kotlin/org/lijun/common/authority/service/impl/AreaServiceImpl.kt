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
import org.lijun.common.authority.entity.Area
import org.lijun.common.authority.repository.AreaRepository
import org.lijun.common.authority.service.AreaService
import org.lijun.common.service.impl.BaseServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service - AreaServiceImpl
 *
 * @author lijun
 * @constructor
 */
@Service("areaService")
open class AreaServiceImpl(repository: AreaRepository) : BaseServiceImpl<Area, Long>(repository), AreaService {

    @Autowired
    private lateinit var areaRepository: AreaRepository

    @Transactional(readOnly = true)
    override fun findRoot(): Area {
        return this.areaRepository.findRoot()
    }

    @Transactional(readOnly = true)
    override fun findByCode(code: String): Area? {
        return this.areaRepository.findByCode(code)
    }

    @Transactional(readOnly = true)
    override fun findByName(name: String): Area? {
        return this.areaRepository.findByName(name)
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<Area> {
        val root: Area = this.findRoot()

        val areas: List<Area> = this.areaRepository.findAll()

        return this.buildTree(areas, root)
    }

    @Transactional(readOnly = true)
    override fun checkCode(oldCode: String?, code: String): Boolean {
        return if (StringUtils.equalsIgnoreCase(oldCode, code)) {
            true
        } else {
            null == this.findByCode(code)
        }
    }

    /**
     * 递归创建树形结构
     * @param areas
     * @param parent
     * @return
     */
    open internal fun buildTree(areas: List<Area>, parent: Area): List<Area> {
        val list: MutableList<Area> = mutableListOf()

        areas.forEach {
            if (null == it.parent) {
                return@forEach
            }

            if (it.parent?.equals(parent)!!) {
                list.add(it)

                list.addAll(buildTree(areas, it))
            }
        }

        return list
    }

}