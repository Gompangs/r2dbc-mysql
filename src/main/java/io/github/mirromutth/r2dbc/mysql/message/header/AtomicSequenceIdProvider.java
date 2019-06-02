/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.mirromutth.r2dbc.mysql.message.header;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * An implementation of {@link SequenceIdProvider} based on atomic.
 */
final class AtomicSequenceIdProvider implements SequenceIdProvider {

    private final AtomicInteger value = new AtomicInteger();

    @Override
    public byte next() {
        return (byte) (value.getAndIncrement() & 0xFF);
    }

    @Override
    public void last(int value) {
        setValue(value + 1);
    }

    @Override
    public void reset() {
        setValue(0);
    }

    private void setValue(int value) {
        // Can not use get() because of lazySet()
        this.value.lazySet(value);
    }
}
