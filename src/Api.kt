package com.example

data class UpdateRequest(val index: Int, val data: String)

data class BlockResponse(
    val index: Int,
    val prevHash: String,
    val timestamp: Long,
    val data: String,
    val nonce: Int,
    val hash: String,
    val isMined: Boolean,
    val isValidNewBlock: Boolean
)

fun toBlockResponse(block: Block, chain: List<Block>): BlockResponse {
    val isValid = if (block.index == 0)
        true
    else
        isValidNewBlock(block, chain[block.index - 1])

    return BlockResponse(
        block.index,
        block.prevHash,
        block.timestamp,
        block.data,
        block.nonce,
        hash(block),
        isMined(block),
        isValid
    )
}

fun toChainResponse(chain: List<Block>) : List<BlockResponse> =
    chain.map { block -> toBlockResponse(block, chain) }
