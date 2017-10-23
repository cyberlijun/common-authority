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

package org.lijun.common.authority.web.controller

import org.lijun.common.authority.entity.Org
import org.lijun.common.authority.entity.Role
import org.lijun.common.authority.entity.SysMenu
import org.lijun.common.authority.querycondition.RoleQueryCondition
import org.lijun.common.authority.service.OrgService
import org.lijun.common.authority.service.RoleService
import org.lijun.common.authority.service.SysMenuService
import org.lijun.common.vo.DataTable
import org.lijun.common.vo.JsonResult
import org.lijun.common.web.controller.BaseController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 * Controller - RoleController
 *
 * @author lijun
 * @constructor
 */
@Controller
@RequestMapping("\${adminPath:admin}/role")
open class RoleController : BaseController() {

    @Autowired
    private lateinit var roleService: RoleService

    @Autowired
    private lateinit var orgService: OrgService

    @Autowired
    private lateinit var sysMenuService: SysMenuService

    /**
     * 转发到角色管理首页
     * @return
     */
    @GetMapping
    open fun index(model: Model): String {
        loadOrgs(model)

        return "admin/role/list"
    }

    /**
     * 分页查询角色
     * @param condition
     * @return
     */
    @PostMapping("list")
    @ResponseBody
    open fun list(condition: RoleQueryCondition): DataTable<Role> = this.roleService.findPage(condition)

    /**
     * 转发到添加角色页面
     */
    @GetMapping("add")
    open fun add(model: Model): String {
        loadOrgs(model)

        loadMenus(model)

        return "admin/role/add"
    }

    /**
     * 添加角色
     * @param role
     * @param menuIds
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    open fun add(role: Role, @RequestParam("menuIds[]") menuIds: Array<Long>): JsonResult {
        var menus: Set<SysMenu> = setOf()

        menuIds.forEach {
            val menu: SysMenu = SysMenu()

            menu.id = it

            menus += menu
        }

        role.menus = menus

        this.roleService.save(role)

        return success()
    }

    /**
     * 校验角色名称是否存在
     * @param oldName
     * @param name
     * @param oldOrgId
     * @param orgId
     * @return
     */
    @PostMapping("check_name")
    @ResponseBody
    open fun checkName(@RequestParam(required = false) oldName: String?,
                       name: String,
                       @RequestParam(required = false) oldOrgId: Long?,
                       orgId: Long): Boolean {
        var oldOrg: Org? = null

        val org: Org = this.orgService.findById(orgId)

        if (null != oldOrgId) {
            oldOrg = this.orgService.findById(oldOrgId)
        }

        return this.roleService.checkName(oldName, name, oldOrg, org)
    }

    /**
     * 转发到编辑页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("edit")
    open fun edit(id: Long, model: Model): String {
        loadOrgs(model)

        loadMenus(model)

        val role: Role = this.roleService.findById(id)

        model.addAttribute("role", role)

        return "admin/role/edit"
    }

    /**
     * 加载机构列表
     * @param model
     */
    private fun loadOrgs(model: Model) {
        val orgs: List<Org> = this.orgService.findAll()

        model.addAttribute("orgs", orgs)
    }

    /**
     * 加载菜单列表
     * @param model
     */
    private fun loadMenus(model: Model) {
        val menus: List<SysMenu> = this.sysMenuService.findAll()

        model.addAttribute("menus", menus)
    }

}