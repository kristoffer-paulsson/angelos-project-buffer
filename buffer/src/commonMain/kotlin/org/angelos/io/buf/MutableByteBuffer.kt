/**
 * Copyright (c) 2021-2022 by Kristoffer Paulsson <kristoffer.paulsson@talenten.se>.
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
package org.angelos.io.buf

/**
 * Mutable byte buffer implemented on the heap, as mutable.
 *
 * @constructor
 *
 * @param array
 * @param size
 * @param limit
 * @param position
 * @param endianness
 */
@Suppress("OVERRIDE_BY_INLINE")
@OptIn(ExperimentalUnsignedTypes::class)
class MutableByteBuffer internal constructor(
    array: ByteArray,
    size: Int,
    limit: Int,
    position: Int,
    endianness: Endianness,
) : AbstractMutableBuffer(size, limit, position, endianness), MutableHeapBuffer {
    private val _array = array
    private val _view = _array.asUByteArray()

    override inline fun load(index: Int): UByte {
        return _view[index]
    }

    override fun loadByte(index: Int): Byte = load(index).toByte()

    override inline fun loadLong(index: Int): Long = loadReadLong(this, position)

    override inline fun readByte(): Byte = load(position + 0).toByte()

    override inline fun readUByte(): UByte = load(position + 0)

    override inline fun readChar(): Char = when (reverse) {
        true -> loadReadReverseShort(this, position)
        false -> loadReadShort(this, position)
    }.toChar()

    override inline fun readShort(): Short = when (reverse) {
        true -> loadReadReverseShort(this, position)
        false -> loadReadShort(this, position)
    }.toShort()

    override inline fun readUShort(): UShort = when (reverse) {
        true -> loadReadReverseShort(this, position)
        false -> loadReadShort(this, position)
    }.toUShort()

    override inline fun readInt(): Int = when (reverse) {
        true -> loadReadReverseInt(this, position)
        false -> loadReadInt(this, position)
    }

    override inline fun readUInt(): UInt = when (reverse) {
        true -> loadReadReverseUInt(this, position)
        false -> loadReadUInt(this, position)
    }

    override inline fun readLong(): Long = when (reverse) {
        true -> loadReadReverseLong(this, position)
        false -> loadReadLong(this, position)
    }

    override inline fun readULong(): ULong = when (reverse) {
        true -> loadReadReverseULong(this, position)
        false -> loadReadULong(this, position)
    }

    override inline fun readFloat(): Int = when (reverse) {
        true -> loadReadReverseInt(this, position)
        false -> loadReadInt(this, position)
    }

    override inline fun readDouble(): Long = when (reverse) {
        true -> loadReadReverseLong(this, position)
        false -> loadReadLong(this, position)
    }

    override inline fun save(value: UByte, index: Int) {
        _view[index] = value
    }

    override fun saveByte(index: Int, value: Byte) {
        TODO("Not yet implemented")
    }

    override fun saveLong(index: Int, value: Long) {
        TODO("Not yet implemented")
    }

    override fun getArray(): ByteArray = _array

    internal fun writeByte(value: Byte) =
        save((value.toInt() and 0xFF).toUByte(), position + 0)

    internal fun writeUByte(value: UByte) = save(value, position + 0)

    internal fun writeChar(value: Char) = when (reverse) {
        true -> {
            save((value.code and 0xFF).toUByte(), position + 0)
            save(((value.code ushr 8) and 0xFF).toUByte(), position + 1)
        }
        false -> {
            save((value.code and 0xFF).toUByte(), position + 1)
            save(((value.code ushr 8) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeShort(value: Short) = when (reverse) {
        true -> {
            save((value.toInt() and 0xFF).toUByte(), position + 0)
            save(((value.toInt() ushr 8) and 0xFF).toUByte(), position + 1)
        }
        false -> {
            save((value.toInt() and 0xFF).toUByte(), position + 1)
            save(((value.toInt() ushr 8) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeUShort(value: UShort) = when (reverse) {
        true -> {
            save((value.toInt() and 0xFF).toUByte(), position + 0)
            save(((value.toInt() ushr 8) and 0xFF).toUByte(), position + 1)
        }
        false -> {
            save((value.toInt() and 0xFF).toUByte(), position + 1)
            save(((value.toInt() ushr 8) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeInt(value: Int) = when (reverse) {
        true -> {
            save((value and 0xFF).toUByte(), position + 0)
            save(((value ushr 8) and 0xFF).toUByte(), position + 1)
            save(((value ushr 16) and 0xFF).toUByte(), position + 2)
            save(((value ushr 24) and 0xFF).toUByte(), position + 3)
        }
        false -> {
            save((value and 0xFF).toUByte(), position + 3)
            save(((value ushr 8) and 0xFF).toUByte(), position + 2)
            save(((value ushr 16) and 0xFF).toUByte(), position + 1)
            save(((value ushr 24) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeUInt(value: UInt) = when (reverse) {
        true -> {
            save((value.toInt() and 0xFF).toUByte(), position + 0)
            save(((value.toInt() ushr 8) and 0xFF).toUByte(), position + 1)
            save(((value.toInt() ushr 16) and 0xFF).toUByte(), position + 2)
            save(((value.toInt() ushr 24) and 0xFF).toUByte(), position + 3)
        }
        false -> {
            save((value.toInt() and 0xFF).toUByte(), position + 3)
            save(((value.toInt() ushr 8) and 0xFF).toUByte(), position + 2)
            save(((value.toInt() ushr 16) and 0xFF).toUByte(), position + 1)
            save(((value.toInt() ushr 24) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeLong(value: Long) = when (reverse) {
        true -> saveWriteReverseLong(this, position, value)
        false -> {
            save((value and 0xFF).toUByte(), position + 7)
            save(((value ushr 8) and 0xFF).toUByte(), position + 6)
            save(((value ushr 16) and 0xFF).toUByte(), position + 5)
            save(((value ushr 24) and 0xFF).toUByte(), position + 4)
            save(((value ushr 32) and 0xFF).toUByte(), position + 3)
            save(((value ushr 40) and 0xFF).toUByte(), position + 2)
            save(((value ushr 48) and 0xFF).toUByte(), position + 1)
            save(((value ushr 56) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeULong(value: ULong) = when (reverse) {
        true -> {
            save((value.toLong() and 0xFF).toUByte(), position + 0)
            save(((value.toLong() ushr 8) and 0xFF).toUByte(), position + 1)
            save(((value.toLong() ushr 16) and 0xFF).toUByte(), position + 2)
            save(((value.toLong() ushr 24) and 0xFF).toUByte(), position + 3)
            save(((value.toLong() ushr 32) and 0xFF).toUByte(), position + 4)
            save(((value.toLong() ushr 40) and 0xFF).toUByte(), position + 5)
            save(((value.toLong() ushr 48) and 0xFF).toUByte(), position + 6)
            save(((value.toLong() ushr 56) and 0xFF).toUByte(), position + 7)
        }
        false -> {
            save((value.toLong() and 0xFF).toUByte(), position + 7)
            save(((value.toLong() ushr 8) and 0xFF).toUByte(), position + 6)
            save(((value.toLong() ushr 16) and 0xFF).toUByte(), position + 5)
            save(((value.toLong() ushr 24) and 0xFF).toUByte(), position + 4)
            save(((value.toLong() ushr 32) and 0xFF).toUByte(), position + 3)
            save(((value.toLong() ushr 40) and 0xFF).toUByte(), position + 2)
            save(((value.toLong() ushr 48) and 0xFF).toUByte(), position + 1)
            save(((value.toLong() ushr 56) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeFloat(value: Int) = when (reverse) {
        true -> {
            save((value and 0xFF).toUByte(), position + 0)
            save(((value ushr 8) and 0xFF).toUByte(), position + 1)
            save(((value ushr 16) and 0xFF).toUByte(), position + 2)
            save(((value ushr 24) and 0xFF).toUByte(), position + 3)
        }
        false -> {
            save((value and 0xFF).toUByte(), position + 3)
            save(((value ushr 8) and 0xFF).toUByte(), position + 2)
            save(((value ushr 16) and 0xFF).toUByte(), position + 1)
            save(((value ushr 24) and 0xFF).toUByte(), position + 0)
        }
    }

    internal fun writeDouble(value: Long) = when (reverse) {
        true -> saveWriteReverseLong(this, position, value)
        false -> {
            save((value and 0xFF).toUByte(), position + 7)
            save(((value ushr 8) and 0xFF).toUByte(), position + 6)
            save(((value ushr 16) and 0xFF).toUByte(), position + 5)
            save(((value ushr 24) and 0xFF).toUByte(), position + 4)
            save(((value ushr 32) and 0xFF).toUByte(), position + 3)
            save(((value ushr 40) and 0xFF).toUByte(), position + 2)
            save(((value ushr 48) and 0xFF).toUByte(), position + 1)
            save(((value ushr 56) and 0xFF).toUByte(), position + 0)
        }
    }

    companion object {
        internal inline fun saveWriteReverseShort(buf: AbstractBuffer, pos: Int): Int = buf.load(pos + 0).toInt() or
                (buf.load(pos + 1).toInt() shl 8)

        internal inline fun saveWriteShort(buf: AbstractBuffer, pos: Int): Int = (buf.load(pos + 1).toInt() or
                (buf.load(pos + 0).toInt() shl 8))

        internal inline fun saveWriteReverseInt(buf: AbstractBuffer, pos: Int): Int = buf.load(pos + 0).toInt() or
                (buf.load(pos + 1).toInt() shl 8) or
                (buf.load(pos + 2).toInt() shl 16) or
                (buf.load(pos + 3).toInt() shl 24)

        internal inline fun saveWriteInt(buf: AbstractBuffer, pos: Int): Int = buf.load(pos + 3).toInt() or
                (buf.load(pos + 2).toInt() shl 8) or
                (buf.load(pos + 1).toInt() shl 16) or
                (buf.load(pos + 0).toInt() shl 24)

        internal inline fun saveWriteReverseUInt(buf: AbstractBuffer, pos: Int): UInt = buf.load(pos + 0).toUInt() or
                (buf.load(pos + 1).toUInt() shl 8) or
                (buf.load(pos + 2).toUInt() shl 16) or
                (buf.load(pos + 3).toUInt() shl 24)

        internal inline fun saveWriteUInt(buf: AbstractBuffer, pos: Int): UInt = buf.load(pos + 3).toUInt() or
                (buf.load(pos + 2).toUInt() shl 8) or
                (buf.load(pos + 1).toUInt() shl 16) or
                (buf.load(pos + 0).toUInt() shl 24)

        internal inline fun saveWriteReverseLong(buf: AbstractMutableBuffer, pos: Int, value: Long) {
            buf.save((value and 0xFF).toUByte(), pos + 0)
            buf.save(((value ushr 8) and 0xFF).toUByte(), pos + 1)
            buf.save(((value ushr 16) and 0xFF).toUByte(), pos + 2)
            buf.save(((value ushr 24) and 0xFF).toUByte(), pos + 3)
            buf.save(((value ushr 32) and 0xFF).toUByte(), pos + 4)
            buf.save(((value ushr 40) and 0xFF).toUByte(), pos + 5)
            buf.save(((value ushr 48) and 0xFF).toUByte(), pos + 6)
            buf.save(((value ushr 56) and 0xFF).toUByte(), pos + 7)
        }

        internal inline fun saveWriteLong(buf: AbstractBuffer, pos: Int): Long = buf.load(pos + 7).toLong() or
                (buf.load(pos + 6).toLong() shl 8) or
                (buf.load(pos + 5).toLong() shl 16) or
                (buf.load(pos + 4).toLong() shl 24) or
                (buf.load(pos + 3).toLong() shl 32) or
                (buf.load(pos + 2).toLong() shl 40) or
                (buf.load(pos + 1).toLong() shl 48) or
                (buf.load(pos + 0).toLong() shl 56)

        internal inline fun saveWriteReverseULong(buf: AbstractBuffer, pos: Int): ULong = buf.load(pos + 0).toULong() or
                (buf.load(pos + 1).toULong() shl 8) or
                (buf.load(pos + 2).toULong() shl 16) or
                (buf.load(pos + 3).toULong() shl 24) or
                (buf.load(pos + 4).toULong() shl 32) or
                (buf.load(pos + 5).toULong() shl 40) or
                (buf.load(pos + 6).toULong() shl 48) or
                (buf.load(pos + 7).toULong() shl 56)

        internal inline fun saveWriteULong(buf: AbstractBuffer, pos: Int): ULong = buf.load(pos + 7).toULong() or
                (buf.load(pos + 6).toULong() shl 8) or
                (buf.load(pos + 5).toULong() shl 16) or
                (buf.load(pos + 4).toULong() shl 24) or
                (buf.load(pos + 3).toULong() shl 32) or
                (buf.load(pos + 2).toULong() shl 40) or
                (buf.load(pos + 1).toULong() shl 48) or
                (buf.load(pos + 0).toULong() shl 56)
    }
}