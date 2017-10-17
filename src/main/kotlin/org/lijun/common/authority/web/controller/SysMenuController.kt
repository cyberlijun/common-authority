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

import org.lijun.common.authority.entity.SysMenu
import org.lijun.common.authority.service.SysMenuService
import org.lijun.common.web.controller.BaseController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Controller - SysMenuController
 *
 * @author lijun
 * @constructor
 */
@Controller
@RequestMapping("\${adminPath:admin}/menu")
open class SysMenuController : BaseController() {

    @Autowired
    private lateinit var sysMenuService: SysMenuService

    /**
     * 转发到菜单管理首页
     * @param model
     * @return
     */
    @GetMapping
    open fun index(model: Model): String {
        val menus: List<SysMenu> = this.sysMenuService.findAll()

        model.addAttribute("menus", menus)

        return "admin/sys_menu/list"
    }

}