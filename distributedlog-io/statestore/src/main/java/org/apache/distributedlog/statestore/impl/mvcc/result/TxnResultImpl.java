/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.distributedlog.statestore.impl.mvcc.result;

import io.netty.util.Recycler.Handle;
import java.util.Collections;
import java.util.List;
import lombok.Setter;
import org.apache.distributedlog.statestore.api.mvcc.op.OpType;
import org.apache.distributedlog.statestore.api.mvcc.result.Result;
import org.apache.distributedlog.statestore.api.mvcc.result.TxnResult;

@Setter
public class TxnResultImpl<K, V> extends ResultImpl<K, V, TxnResultImpl<K, V>> implements TxnResult<K, V> {

    private boolean isSuccess = false;
    private List<Result<K, V>> results = Collections.emptyList();

    TxnResultImpl(Handle<TxnResultImpl<K, V>> handle) {
        super(OpType.TXN, handle);
    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public List<Result<K, V>> results() {
        return results;
    }

    @Override
    TxnResultImpl<K, V> asResult() {
        return this;
    }

    @Override
    protected void reset() {
        this.results.forEach(Result::recycle);
        this.results.clear();
        this.results = Collections.emptyList();

        super.reset();
    }
}
