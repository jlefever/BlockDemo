import React from "react";
import Block from "./Block";
import PageHeading from "./PageHeading";
import AddBlockButton from "./AddBlockButton";
import AddBroadcastButton from "./AddBroadcastButton";

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      blockChain: [
        {
          index: 0,
          timestamp: 1234567,
          parent: 0,
          nonce: 0,
          data: 0,
          hash: 0
        }
      ]
    };
  }

  triggerAddBlockState = () => {
    this.setState({
      ...this.state,
      blockChain: [
        ...this.state.blockChain,
        { index: 1, timestamp: 2323, parent: 1, nonce: 1, data: 1, hash: 1 }
      ]
    });
  };

  render() {
    const blocks = this.state.blockChain.map(block => <Block block={block} />);
    return (
      <div>
        <>
          <PageHeading />
          <AddBlockButton addBlock={this.triggerAddBlockState} />
          <AddBroadcastButton addBroadcast={this.triggerAddBroadcastState} />
          <div className="container">
            <div className="row">{blocks}</div>
          </div>
        </>
      </div>
    );
  }
}

export default App;