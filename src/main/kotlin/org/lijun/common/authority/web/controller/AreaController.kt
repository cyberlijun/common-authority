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
import org.lijun.common.authority.service.AreaService
import org.lijun.common.util.ObjectUtils
import org.lijun.common.vo.JsonResult
import org.lijun.common.web.controller.BaseController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 * Controller - AreaController
 *
 * @author lijun
 * @constructor
 */
@Controller
@RequestMapping("\${adminPath:admin}/area")
open class AreaController : BaseController() {

    @Autowired
    private lateinit var areaService: AreaService

    /**
     * 转发到菜单管理首页
     * @param model
     */
    @GetMapping
    open fun index(model: Model): String {
        val areas: List<Area> = this.areaService.findAll()

        model.addAttribute("areas", areas)

        return "admin/area/list"
    }

    /**
     * 转发到添加区域页面
     * @param parentId
     * @param model
     * @return
     */
    @GetMapping("add")
    open fun add(@RequestParam(required = false) parentId: Long?, model: Model): String {
        val parent: Area = if (null == parentId) {
            this.areaService.findRoot()
        } else {
            this.areaService.findById(parentId)
        }

        model.addAttribute("parent", parent)

        return "admin/area/add"
    }

    /**
     * 添加区域
     * @param area
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    open fun add(area: Area): JsonResult {
        this.areaService.save(area)

        return success()
    }

    /**
     * 校验区域编码是否存在
     * @param oldCode
     * @param code
     * @return
     */
    @PostMapping("check_code")
    @ResponseBody
    open fun checkCode(@RequestParam(required = false) oldCode: String?, code: String): Boolean {
        return this.areaService.checkCode(oldCode, code)
    }

    /**
     * 转发到编辑区域页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("edit")
    open fun edit(id: Long, model: Model): String {
        val area: Area = this.areaService.findById(id)

        model.addAttribute("area", area)

        return "admin/area/edit"
    }

    /**
     * 修改区域
     * @param area
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    open fun update(area: Area): JsonResult {
        val target: Area = this.areaService.findById(area.id!!)

        ObjectUtils.copyProperties(target, area)

        this.areaService.save(target)

        return success()
    }

    /**
     * 删除区域
     * @param id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    open fun delete(id: Long): JsonResult {
        this.areaService.delete(id)

        return success()
    }

}