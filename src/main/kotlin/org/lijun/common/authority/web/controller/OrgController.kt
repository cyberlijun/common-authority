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

import org.lijun.common.authority.entity.Area
import org.lijun.common.authority.entity.Org
import org.lijun.common.authority.service.AreaService
import org.lijun.common.authority.service.OrgService
import org.lijun.common.vo.JsonResult
import org.lijun.common.util.ObjectUtils
import org.lijun.common.web.controller.BaseController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 * Controller - OrgController
 *
 * @author lijun
 * @constructor
 */
@Controller
@RequestMapping("\${adminPath}/org")
open class OrgController : BaseController() {

    @Autowired
    private lateinit var orgService: OrgService

    @Autowired
    private lateinit var areaService: AreaService

    /**
     * 转发到机构管理首页
     * @param model
     * @return
     */
    @GetMapping
    open fun index(model: Model): String {
        val orgs: List<Org> = this.orgService.findAll()

        model.addAttribute("orgs", orgs)

        return "admin/org/list"
    }

    /**
     * 转发到添加机构页面
     * @param parentId
     * @param model
     * @return
     */
    @GetMapping("add")
    open fun add(@RequestParam(required = false) parentId: Long?, model: Model): String {
        var parent: Org = if (null == parentId) {
            this.orgService.findRoot()
        } else {
            this.orgService.findById(parentId)
        }

        loadAreas(model)

        model.addAttribute("parent", parent)

        return "admin/org/add"
    }

    /**
     * 添加机构
     * @param org
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    open fun add(org: Org): JsonResult {
        this.orgService.save(org)

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
        val org: Org = this.orgService.findById(id)

        loadAreas(model)

        model.addAttribute("org", org)

        return "admin/org/edit"
    }

    /**
     * 修改机构信息
     * @param org
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    open fun update(org: Org): JsonResult {
        val target: Org = this.orgService.findById(org.id!!)

        ObjectUtils.copyProperties(target, org)

        this.orgService.save(target)

        return success()
    }

    /**
     * 删除机构
     * @param id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    open fun delete(id: Long): JsonResult {
        this.orgService.delete(id)

        return success()
    }

    /**
     * 校验机构编码是否存在
     * @param oldCode
     * @param code
     * @return
     */
    @PostMapping("check_code")
    @ResponseBody
    open fun checkCode(@RequestParam(required = false) oldCode: String?, code: String): Boolean {
        return this.orgService.checkCode(oldCode, code)
    }

    /**
     * 加载区域信息
     * @param model
     * @return
     */
    open internal fun loadAreas(model: Model) {
        val areas: List<Area> = this.areaService.findAll()

        model.addAttribute("areas", areas)
    }

}