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

import com.google.common.net.HttpHeaders
import org.apache.commons.lang3.ArrayUtils
import org.apache.commons.lang3.StringUtils
import org.lijun.common.authority.entity.SysLog
import org.lijun.common.authority.entity.SysUser
import org.lijun.common.authority.querycondition.SysLogQueryCondition
import org.lijun.common.authority.repository.SysLogRepository
import org.lijun.common.authority.service.SysLogService
import org.lijun.common.authority.service.SysUserService
import org.lijun.common.service.impl.BaseServiceImpl
import org.lijun.common.util.DateUtils
import org.lijun.common.util.WebUtils
import org.lijun.common.vo.DataTable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.AntPathMatcher
import org.springframework.util.PathMatcher
import org.springframework.web.util.UrlPathHelper
import java.util.*
import javax.persistence.criteria.Join
import javax.persistence.criteria.JoinType
import javax.persistence.criteria.Predicate
import javax.servlet.http.HttpServletRequest

/**
 * Service - SysLogServiceImpl
 *
 * @author lijun
 * @constructor
 */
@Service("sysLogService")
open class SysLogServiceImpl(repository: SysLogRepository) : BaseServiceImpl<SysLog, Long>(repository), SysLogService {

    @Autowired
    private lateinit var sysUserService: SysUserService

    @Value("\${syslog.exclude-url:}")
    private lateinit var excludeUrls: Array<String>

    @Value("\${syslog.ignore-params:}")
    private lateinit var ignoreParams: Array<String>

    private val pathMatcher: PathMatcher = AntPathMatcher()

    private val urlPathHelper: UrlPathHelper = UrlPathHelper()

    @Transactional
    override fun save(request: HttpServletRequest, e: Exception?, auditable: Boolean) {
        var match: Boolean = false

        if (ArrayUtils.isNotEmpty(excludeUrls)) {
            excludeUrls.forEach {
                if (pathMatcher.match(it, urlPathHelper.getLookupPathForRequest(request))) {
                    match = true

                    return@forEach
                }
            }
        }

        if (match.not()) {
            val sysLog: SysLog = SysLog()

            with (sysLog) {
                if (null != e) {
                    type = SysLog.LogType.EXCEPTION_LOG
                    exception = e.toString()
                } else {
                    type = SysLog.LogType.ACCESS_LOG
                    exception = StringUtils.EMPTY
                }

                remoteAddr = WebUtils.getRemoteIp(request)
                userAgent = request.getHeader(HttpHeaders.USER_AGENT.toLowerCase())
                requestUri = request.requestURI
                method = request.method
                params = WebUtils.getRequestParams(*ignoreParams)

                if (auditable && null != sysUserService.getCurrent()) {
                    createUser = sysUserService.getCurrent()
                }

                super.save(sysLog)
            }
        }
    }

    @Transactional(readOnly = true)
    override fun findPage(condition: SysLogQueryCondition): DataTable<SysLog> {
        val specification: Specification<SysLog> = Specification { root, _, cb ->
            var restrictions: Predicate = cb!!.conjunction()

            if (StringUtils.isNotBlank(condition.username)) {
                val user = this.sysUserService.findByUsername(condition.username!!)

                if (null != user) {
                    val join: Join<SysLog, SysUser> = root.join("createUser", JoinType.INNER)

                    restrictions = cb.and(restrictions, cb.equal(join.get<Long>("id"), user.id))
                }
            }

            if (StringUtils.isNotBlank(condition.requestUri)) {
                restrictions = cb.and(restrictions, cb.equal(root.get<String>("requestUri"), condition.requestUri))
            }

            if (StringUtils.isNotBlank(condition.method)) {
                restrictions = cb.and(restrictions, cb.equal(root.get<String>("method"), condition.method?.toUpperCase()))
            }

            if (null != condition.type) {
                restrictions = cb.and(restrictions, cb.equal(root.get<SysLog.LogType>("type"), condition.type))
            }

            if (null != condition.startDate) {
                restrictions = cb.and(restrictions, cb.greaterThanOrEqualTo(root.get<Date>("createDate"), DateUtils.getStartDate(condition.startDate!!)))
            }

            if (null != condition.endDate) {
                restrictions = cb.and(restrictions, cb.lessThanOrEqualTo(root.get<Date>("createDate"), DateUtils.getEndDate(condition.endDate!!)))
            }

            restrictions
        }

        val sort: Sort = Sort(Sort.Direction.DESC, "createDate")

        return this.findPage(condition, specification, sort)
    }

}