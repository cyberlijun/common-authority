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

import com.google.common.collect.Lists
import org.apache.commons.collections.CollectionUtils
import org.apache.commons.lang3.StringUtils
import org.lijun.common.authority.entity.SysMenu
import org.lijun.common.authority.entity.SysUser
import org.lijun.common.authority.repository.SysMenuRepository
import org.lijun.common.authority.service.SysMenuService
import org.lijun.common.service.impl.BaseServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * Service - SysMenuServiceImpl
 *
 * @author lijun
 * @constructor
 */
@Service("sysMenuService")
open class SysMenuServiceImpl(repository: SysMenuRepository) : BaseServiceImpl<SysMenu, Long>(repository), SysMenuService {

    @Autowired
    private lateinit var sysMenuRepository: SysMenuRepository

    @Transactional(readOnly = true)
    override fun findRoot(): SysMenu {
        return this.sysMenuRepository.findRoot()
    }

    @Transactional(readOnly = true)
    override fun findByParent(parent: SysMenu): List<SysMenu> {
        return this.sysMenuRepository.findByParent(parent)
    }

    @Transactional(readOnly = true)
    override fun buildMenuTree(user: SysUser?): List<SysMenu> {
        if (null == user?.role) {
            return emptyList()
        }

        var list: LinkedList<SysMenu> = Lists.newLinkedList()

        val root: SysMenu = this.findRoot()

        val menus: Set<SysMenu> = user.role?.menus!!

        var firstLevelMenus: List<SysMenu> = Lists.newArrayList()

        menus.filter { null != it.parent && it.parent?.equals(root)!! && it.isShow() }.forEach {
            firstLevelMenus += it
        }

        if (CollectionUtils.isNotEmpty(firstLevelMenus)) {
            firstLevelMenus.sortedBy { it.sort }.forEach {
                it.childs = this.getChilds(menus, it).toSet()
            }

            list.addAll(firstLevelMenus)
        }

        return list
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<SysMenu> {
        val list: LinkedList<SysMenu> = Lists.newLinkedList()

        val root: SysMenu = this.findRoot()

        val menus: List<SysMenu> = this.sort(this.findByParent(root))

        menus.forEach {
            list.add(it)

            if (CollectionUtils.isNotEmpty(it.childs)) {
                list.addAll(it.childs!!)
            }
        }

        return list
    }

    /**
     * 递归获得子菜单
     * @param menus
     * @param parent
     * @return
     */
    open internal fun getChilds(menus: Set<SysMenu>, parent: SysMenu): List<SysMenu> {
        var list: LinkedList<SysMenu> = Lists.newLinkedList()

        menus.filter { it.parent?.equals(parent)!! && it.isShow() }.forEach {
            list.add(it)
        }

        if (CollectionUtils.isNotEmpty(list)) {
            list.filter { StringUtils.isBlank(it.menuUrl) && it.isShow() }.forEach {
                it.childs = getChilds(menus, it).toSet()
            }
        }

        if (CollectionUtils.isEmpty(list)) {
            return emptyList()
        }

        list.sortBy { it.sort }

        return list
    }

    /**
     * 对菜单进行排序
     * @param menus
     * @return
     */
    open internal fun sort(menus: List<SysMenu>): List<SysMenu> {
        var list: LinkedList<SysMenu> = Lists.newLinkedList()

        menus.sortedBy { it.sort }.forEach {
            if (CollectionUtils.isNotEmpty(it.childs)) {
                it.childs?.sortedBy { it.sort }

                list.add(it)
            } else {
                list.add(it)
            }
        }

        return list
    }

}