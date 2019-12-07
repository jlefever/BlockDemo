import React from "react";
import Block from "./Block";
import BlockList from "./BlockList";
import PageHeading from "./PageHeading";
import AddBlockButton from "./AddBlockButton";
import AddBroadcastButton from "./AddBroadcastButton";

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      blocks: [
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
      blocks: [
        ...this.state.blocks,
        { index: 1, timestamp: 2323, parent: 1, nonce: 1, data: 1, hash: 1 }
      ]
    });
  };

  render() {
    return (
      <>
        <nav className="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
          <a className="navbar-brand" href="null">Blockchain Demo</a>
        </nav>
        <main>
          <PageHeading />
          <div className="container">
            <BlockList blocks={this.state.blocks} />
          </div>
        </main>
      </>
    );
  }
}

export default App;