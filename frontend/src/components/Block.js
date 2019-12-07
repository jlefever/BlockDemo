import React from "react";

class Block extends React.Component {
  render() {
    return (
      <div className="col-md-6 col-lg-4 col-xl-3 py-2">
        <div className="card h-100 ">
          <div className="card-block">
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
                  value={this.props.block.prevHash}
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
                <textarea className="form-control corm-control-sm" value={this.props.data} />
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
              <div className="form-group">
                <label>Is Mined?</label>
                <input
                  type="text"
                  className="form-control form-control-sm"
                  value={this.props.block.mined ? "Yes" : "No"}
                  disabled
                />
              </div>
              <div className="form-group">
                <label>Is Valid Next Block?</label>
                <input
                  type="text"
                  className="form-control form-control-sm"
                  value={this.props.block.validNewBlock ? "Yes" : "No"}
                  disabled
                />
              </div>

            </div>
            <div className="card-footer text-right">
                <button
                  className="btn btn-secondary"
                  onClick={(e) => this.props.handleMineClick(this.props.block.index, e)}>
                  Mine
                  </button>
              </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Block