package com.example

import java.math.BigInteger
import java.security.MessageDigest
import java.time.Instant
import java.util.LinkedList

data class Block(
    val index: Int,
    var prevHash: String,
    val timestamp: Long,
    var data: String,
    var nonce: Int
)

data class Blockchain(var chain: LinkedList<Block>)

fun createNewChain(): LinkedList<Block> {
    val genBlock = Block(0, "", getTimestamp(), "", 0)
    return LinkedList(listOf(genBlock))
}

fun createNewBlock(prevBlock: Block, data: String = ""): Block {
    val index = prevBlock.index + 1
    return Block(index, hash(prevBlock), getTimestamp(), data, 0)
}

fun mineBlock(block: Block, chain: List<Block>): Boolean {
    if (isMined(block)) {
        return true;
    }

    for (i in 0..Int.MAX_VALUE) {
        block.nonce = i
        if (isMined(block)) {
            recalcuatePrevHashs(chain, block.index + 1)
            return true
        }
    }
    return false
    // Optionally, we could update the prevHash of all succeeding blocks.


}

fun recalcuatePrevHashs(chain: List<Block>, index: Int) {
    for(i in index..chain.last().index) {
        if (i == 0) {
            // Special case for genesis block
            chain[0].prevHash = ""
        } else {
            chain[i].prevHash = hash(chain[i - 1])
        }
    }
}

fun addBlockToChain(block: Block, chain: LinkedList<Block>): Boolean {
    if (!isValidChain(chain)) return false
    chain.add(block)
    return true
}

fun isValidChain(chain: LinkedList<Block>): Boolean {
    when {
        chain.isEmpty() -> return true
        chain.size == 1 -> return isMined(chain[0])
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
            && isMined(newBlock)

// We hardcode a difficulty of 3
fun isMined(block: Block) = hash(block).startsWith("0".repeat(3))

fun isInRange(chain: LinkedList<Block>, index: Int) = index >= 0 && index <= chain.last().index

fun hash(block: Block): String {
    return hash(block.index, block.prevHash, block.timestamp, block.data, block.nonce)
}

fun hash(index: Int, previousHash: String, timestamp: Long, data: String, nonce: Int): String {
    return "$index$previousHash$timestamp$data$nonce".hash()
}

fun String.hash(algorithm: String = "SHA-256"): String {
    val messageDigest = MessageDigest.getInstance(algorithm)
    messageDigest.update(this.toByteArray())
    return String.format("%064x", BigInteger(1, messageDigest.digest()))
}

fun getTimestamp() = Instant.now().toEpochMilli()