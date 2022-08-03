/**
 * Copyright (c) 2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
 *
 * This software is available under the terms of the MIT license. Parts are licensed
 * under different terms if stated. The legal terms are attached to the LICENSE file
 * and are made available on:
 *
 *      https://opensource.org/licenses/MIT
 *
 * SPDX-License-Identifier: MIT
 *
 * Contributors:
 *      Kristoffer Paulsson - initial implementation
 */
package org.angproj.io.buf.data

import org.angproj.io.buf.NativeBuffer

/**
 * Immutable heap data-buffer represents an immutable data-buffer allocated outside the heap. Use this interface as types on
 * method parameters in order to allow third party implementations of buffers.
 *
 * @constructor Create implementation of the ImmutableNativeDataBuffer interface.
 */
interface ImmutableNativeDataBuffer : ImmutableDataBuffer, NativeBuffer