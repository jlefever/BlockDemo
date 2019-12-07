import React from "react";
import logo from "../logo.jpg";

function PageHeading(props) {
  return (
    <div className="jumbotron">
      <div className="container">
        <h1 className="display-3">A Blockchain Demo</h1>
        <img src={logo} width="170" height="150" alt="logo" />
        <p>By Jason Lefever, Mitali Sanwal, Shreyansh Vyas, and Sirui Wang</p>
        <button onClick={props.handleBroadcastClick} className="btn btn-lg btn-primary">Broadcast</button>
        <button onClick={props.handleRefreshClick} className="btn btn-lg btn-secondary">Refresh</button>
        <button onClick={props.handleAddBlockClick} className="btn btn-lg btn-secondary">Add Block</button>
      </div>
    </div>
  );
}

export default PageHeading;
