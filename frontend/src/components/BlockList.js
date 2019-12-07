import React from "react";
import Block from "./Block"

class BlockList extends React.Component {
    render() {
        const blocks = this.props.blocks.map(block =>
            <Block
                key={block.index}
                block={block}
                handleMineClick={this.props.handleMineClick}
                handleUpdate={this.props.handleUpdate}
            />);
            
        return (<div className="row">{blocks}</div>);
    }
}

export default BlockList