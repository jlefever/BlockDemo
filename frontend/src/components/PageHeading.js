import React from "react";
import logo from "../logo.jpg";

function PageHeading() {
  return (
    <div className="jumbotron">
      <div className="container">
        <h1 className="display-3">A Blockchain Demo</h1>
        <img src={logo} width="170" height="150" alt="logo" />
        <p>By Jason Lefever, Mitali Sanwal, Shreyansh Vyas, and Sirui Wang</p>
        <a href="#" class="btn btn-lg btn-primary">Broadcast</a>
        <a href="#" class="btn btn-lg btn-secondary">Refresh</a>
        <a href="#" class="btn btn-lg btn-secondary">Add Block</a>
      </div>
    </div>
  );
}

export default PageHeading;
