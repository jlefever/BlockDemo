import React from "react";
import Block from "./Block"

class BlockList extends React.Component {
    render() {
        const blocks = this.props.blocks.map(block => <Block block={block} />);
        return (<div class="row">{blocks}</div>);
    }
}

export default BlockList