package com.example

import java.math.BigInteger
import java.security.MessageDigest
import java.time.Instant
import java.util.LinkedList

data class Block(
    val index: Int,
    val prevHash: String,
    val timestamp: Long,
    val data: String
)

fun createNewChain(): LinkedList<Block> {
    val genBlock = Block(0, "", getTimestamp(), "")
    return LinkedList(listOf(genBlock))
}

fun createNewBlock(prevBlock: Block, data: String = ""): Block {
    val index = prevBlock.index + 1
    return Block(index, hash(prevBlock), getTimestamp(), data)
}

fun addBlockToChain(block: Block, chain: LinkedList<Block>): Boolean {
    if (!isValidNewBlock(block, chain.last())) return false
    chain.add(block)
    return true
}

fun isValidChain(chain: List<Block>): Boolean {
    when {
        chain.isEmpty() || chain.size == 1 -> return true
        else -> {
            for (i in 1 until chain.size) {
                if (!isValidNewBlock(chain[i], chain[i - 1])) return false
            }
            return true
        }
    }
}

fun isValidNewBlock(newBlock: Block, prevBlock: Block) =
    newBlock.index == prevBlock.index + 1
            && newBlock.prevHash == hash(prevBlock)

fun hash(block: Block): String {
    return hash(block.index, block.prevHash, block.timestamp, block.data)
}

fun hash(index: Int, previousHash: String, timestamp: Long, data: String): String {
    return "$index$previousHash$timestamp$data".hash()
}

fun String.hash(algorithm: String = "SHA-256"): String {
    val messageDigest = MessageDigest.getInstance(algorithm)
    messageDigest.update(this.toByteArray())
    return String.format("%064x", BigInteger(1, messageDigest.digest()))
}

fun getTimestamp() = Instant.now().toEpochMilli()