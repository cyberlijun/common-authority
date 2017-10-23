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

package org.lijun.common.authority.aop

import org.apache.commons.lang3.ArrayUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.Signature
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.lijun.common.authority.service.SysLogService
import org.lijun.common.querycondition.DataTableQueryCondition
import org.lijun.common.util.Constants
import org.lijun.common.util.SystemUtils
import org.lijun.common.util.WebUtils
import org.lijun.common.vo.DataTable
import org.lijun.common.vo.JsonResult
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

/**
 * Aspect - AuthorityAspect
 *
 * @author lijun
 * @constructor
 */
@Aspect
@Component
open class AuthorityAspect {

    @Autowired
    private lateinit var sysLogService: SysLogService

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    /**
     * 拦截Controller中的方法，当抛出异常时记录错误日志
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(* org.lijun.common.authority.web.controller..*.*(..))")
    @Throws(Throwable::class)
    open fun around(pjp: ProceedingJoinPoint): Any? {
        var returnValue: Any? = null

        try {
            returnValue = pjp.proceed()
        } catch (e: Exception) {
            this.saveSysLog(pjp, e)

            val signature: Signature = pjp.signature

            if (signature is MethodSignature) {
                val returnType: Class<Any> = signature.returnType

                when (returnType) {
                    JsonResult::class.java -> returnValue = JsonResult(Constants.STATUS_ERROR, "发生系统错误，请联系管理员！")
                    DataTable::class.java -> {
                        var condition: DataTableQueryCondition? = null

                        pjp.args.forEach {
                            if (it is DataTableQueryCondition) {
                                condition = it

                                return@forEach
                            }
                        }

                        return DataTable.createErrorTable<Any>(condition?.draw!!, "查询时发生错误！")
                    }
                }
            }
        }

        return returnValue
    }

    /**
     * 保存异常到系统日志表
     * @param pjp
     * @param e
     */
    private fun saveSysLog(pjp: ProceedingJoinPoint, e: Exception) {
        val signature: Signature = pjp.signature

        val className: String = SystemUtils.extractClassName(pjp.target.javaClass)

        logger.error("拦截到{}类的{}方法抛出异常，方法参数：{}", className, signature.name, ArrayUtils.toString(pjp.args))

        logger.error(e.message, e)

        val request: HttpServletRequest = WebUtils.getRequest()

        this.sysLogService.save(request, e)
    }

}