import React from "react";
import "./App.css";
import PageHeading from "./components/PageHeading";
import AddBlockButton from "./components/AddBlockButton";
import AddBroadcastButton from "./components/AddBroadcastButton";

class Block extends React.Component {
  render() {
    return (
      <div className="col-md-6 col-lg-4 col-xl-3 py-2">
        <div className="card h-100 ">
          <div className="card-block">
            <form>
              <div className="card-body">
                <div className="form-group">
                  <label>Index</label>
                  <input
                    type="text"
                    className="form-control form-control-sm"
                    value={this.props.block.index}
                    disabled
                  />
                </div>
                <div className="form-group">
                  <label>Timestamp</label>
                  <input
                    type="text"
                    className="form-control form-control-sm"
                    value={this.props.block.timestamp}
                    disabled
                  />
                </div>
                <div className="form-group">
                  <label>Parent</label>
                  <input
                    type="text"
                    className="form-control form-control-sm"
                    value={this.props.block.parent}
                    disabled
                  />
                </div>
                <div className="form-group">
                  <label>Nonce</label>
                  <input
                    type="text"
                    className="form-control form-control-sm"
                    value={this.props.block.nonce}
                    disabled
                  />
                </div>
                <div className="form-group">
                  <label>Data</label>
                  <textarea className="form-control corm-control-sm" />
                  {/* value = {this.props.block.data} */}
                </div>
                <div className="form-group">
                  <label>Hash</label>
                  <input
                    type="text"
                    className="form-control form-control-sm"
                    value={this.props.block.hash}
                    disabled
                  />
                </div>
              </div>
              <div className="card-footer text-right">
                <button type="submit" className="btn btn-secondary">
                  Mine
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    );
  }
}

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