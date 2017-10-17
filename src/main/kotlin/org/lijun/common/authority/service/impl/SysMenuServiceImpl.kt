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
    override fun findAll(): List<SysMenu> {
        val list: MutableList<SysMenu> = mutableListOf()

        val menus: List<SysMenu> = this.sysMenuRepository.findAll()

        val root: SysMenu = this.findRoot()

        return this.sort(list, menus, root)
    }

    @Transactional(readOnly = true)
    override fun buildMenuTree(user: SysUser?): List<SysMenu> {
        val menus: Set<SysMenu>? = user?.role?.menus

        if (CollectionUtils.isEmpty(menus)) {
            return emptyList()
        }

        val list: LinkedList<SysMenu> = LinkedList()

        val root: SysMenu = this.findRoot()

        var firstLevelMenus: List<SysMenu> = arrayListOf()

        menus?.forEach {
            if (null != it.parent && it.parent?.equals(root)!! && it.isShow()) {
                firstLevelMenus += it
            }
        }

        if (CollectionUtils.isNotEmpty(firstLevelMenus)) {
            firstLevelMenus.sortedBy { it.sort }.forEach {
                it.childs = getChilds(menus!!.toList(), it).toSet()
            }

            list.addAll(firstLevelMenus)
        }

        return list
    }

    /**
     * 对菜单进行排序
     * @param list
     * @param menus
     * @param parent
     * @return
     */
    open internal fun sort(list: MutableList<SysMenu>, menus: List<SysMenu>, parent: SysMenu): List<SysMenu> {
        menus.forEach { menu ->
            if (null != menu.parent && menu.parent?.equals(parent)!!) {
                list += menu

                menus.forEach { child ->
                    if (null != child.parent && child.parent?.equals(menu)!!) {
                        sort(list, menus, menu)
                    }
                }
            }
        }

        return list
    }

    /**
     * 获得子菜单列表
     * @param menus
     * @param parent
     * @return
     */
    open internal fun getChilds(menus: List<SysMenu>, parent: SysMenu): List<SysMenu> {
        var list: List<SysMenu> = listOf()

        menus.forEach {
            if (null != it.parent && it.parent?.equals(parent)!! && it.isShow()) {
                list += it
            }
        }

        list.forEach {
            if (StringUtils.isBlank(it.menuUrl) && it.isShow()) {
                it.childs = getChilds(menus, it).toSet()
            }
        }

        if (CollectionUtils.isEmpty(list)) {
            return emptyList()
        }

        return list.sortedBy { it.sort }
    }

}