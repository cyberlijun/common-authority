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

import org.apache.commons.lang3.StringUtils
import org.lijun.common.authority.entity.Org
import org.lijun.common.authority.entity.Role
import org.lijun.common.authority.entity.SysUser
import org.lijun.common.authority.querycondition.SysUserQueryCondition
import org.lijun.common.authority.service.OrgService
import org.lijun.common.authority.service.RoleService
import org.lijun.common.authority.service.SysUserService
import org.lijun.common.util.ObjectUtils
import org.lijun.common.vo.DataTable
import org.lijun.common.vo.JsonResult
import org.lijun.common.web.controller.BaseController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 * Controller - SysUserController
 *
 * @author lijun
 * @constructor
 */
@Controller
@RequestMapping("\${adminPath:admin}/user")
open class SysUserController : BaseController() {

    @Autowired
    private lateinit var sysUserService: SysUserService

    @Autowired
    private lateinit var orgService: OrgService

    @Autowired
    private lateinit var roleService: RoleService

    /**
     * 转发到用户管理首页
     * @param model
     * @return
     */
    @GetMapping
    open fun index(model: Model): String {
        loadOrgs(model)

        return "admin/sys_user/list"
    }

    /**
     * 分页查询系统用户
     * @param condition
     * @return
     */
    @PostMapping("list")
    @ResponseBody
    open fun list(condition: SysUserQueryCondition): DataTable<SysUser> = this.sysUserService.findPage(condition)

    /**
     * 转发到添加用户页面
     * @param model
     * @return
     */
    @GetMapping("add")
    open fun add(model: Model): String {
        loadOrgs(model)

        return "admin/sys_user/add"
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    open fun add(user: SysUser): JsonResult {
        this.sysUserService.save(user)

        return success()
    }

    /**
     * 转发到编辑页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("edit")
    open fun edit(id: Long, model: Model): String {
        val user: SysUser = this.sysUserService.findById(id)

        loadOrgs(model)

        if (null != user.role) {
            val roles: List<Role> = this.roleService.findByOrg(user.org!!)

            model.addAttribute("roles", roles)
        }

        model.addAttribute("user", user)

        return "admin/sys_user/edit"
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    open fun update(user: SysUser): JsonResult {
        val target: SysUser = this.sysUserService.findById(user.id!!)

        val orignalPassword: String? = target.password

        ObjectUtils.copyProperties(target, user)

        if (StringUtils.isBlank(user.password)) {
            target.password = orignalPassword
        }

        this.sysUserService.save(target)

        return success()
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    open fun delete(id: Long): JsonResult {
        this.sysUserService.delete(id)

        return success()
    }

    /**
     * 校验用户名是否存在
     * @param oldUsername
     * @param username
     * @return
     */
    @PostMapping("check_username")
    @ResponseBody
    open fun checkUsername(@RequestParam(required = false) oldUsername: String?, username: String): Boolean {
        return this.sysUserService.checkUsername(oldUsername, username)
    }

    /**
     * 校验邮箱是否存在
     * @param oldEmail
     * @param email
     * @return
     */
    @PostMapping("check_email")
    @ResponseBody
    open fun checkEmail(@RequestParam(required = false) oldEmail: String?, email: String): Boolean {
        return this.sysUserService.checkEmail(oldEmail, email)
    }

    /**
     * 修改用户状态
     * @param id
     * @param status
     * @return
     */
    @PostMapping("change_status")
    @ResponseBody
    open fun changeStatus(id: Long, status: SysUser.Status): JsonResult {
        val user: SysUser = this.sysUserService.findById(id)

        user.status = status

        this.sysUserService.save(user)

        return success()
    }

    /**
     * 加载机构列表
     * @param model
     */
    open internal fun loadOrgs(model: Model) {
        val orgs: List<Org> = this.orgService.findAll()

        model.addAttribute("orgs", orgs)
    }

}