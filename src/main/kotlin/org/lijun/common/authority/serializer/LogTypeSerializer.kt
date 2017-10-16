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

package org.lijun.common.authority.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.apache.commons.lang3.StringUtils
import org.lijun.common.authority.entity.SysLog

/**
 * JsonSerializer - LogTypeSerializer
 *
 * @author lijun
 * @constructor
 */
/**
 * JsonSerializer - LogTypeSerializer
 *
 * @author lijun
 * @constructor
 */
class LogTypeSerializer : JsonSerializer<SysLog.LogType>() {

    override fun serialize(value: SysLog.LogType?, jgen: JsonGenerator?, provider: SerializerProvider?) {
        var text: String = StringUtils.EMPTY

        if (null != value) {
            text = when (value) {
                SysLog.LogType.ACCESS_LOG -> "<span style='color: #0000ff'>访问日志</span>"
                SysLog.LogType.EXCEPTION_LOG -> "<span style='color: #ff0000'>异常日志</span>"
            }
        }

        jgen?.writeString(text)
    }

}