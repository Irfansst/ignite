/*
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

package org.apache.ignite.internal.util;

/**
 *
 */
public class CacheStatistics {
    /** */
    private static final ThreadLocal<PutStatistic> putStat = new ThreadLocal<>();

    private final InternalStatistics stats;

    public CacheStatistics(InternalStatistics stats) {
        this.stats = stats;
    }

    public void putStart() {
        PutStatistic stat = putStat.get();

        if (stat == null)
            putStat.set(stat = new PutStatistic());

        stat.start();
    }

    public void putEnd() {
        PutStatistic stat = putStat.get();

        if (stat != null) {
            stat.end();

            stats.add(stat);
        }
    }

    public static final void opStart(Enum op) {
        PutStatistic stat = putStat.get();

        if (stat != null)
            stat.startOp(op.ordinal());
    }

    public static final void opEnd(Enum op) {
        PutStatistic stat = putStat.get();

        if (stat != null)
            stat.endOp(op.ordinal());
    }
}
