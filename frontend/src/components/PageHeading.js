import React from "react";
import logo from "../logo.jpg";

function PageHeading() {
  return (
    <div className="App">
      <meta charSet="utf-8" />
      <meta
        name="viewport"
        content="width=device-width, initial-scale=1, shrink-to-fit=no"
      />
      <title>Block Demo</title>
      {/* Bootstrap core CSS */}
      <link
        href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
        rel="stylesheet"
      />
      <nav className="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a className="navbar-brand" href="null">
          Blockchain Demo
        </a>
      </nav>
      <main role="main">
        {/* Main jumbotron for a primary marketing message or call to action */}
        <div className="jumbotron">
          <div className="container">
            <h1 className="display-3">A Blockchain Demo</h1>
            <img src={logo} width="170" height="150" alt="logo" />

            <p>
              By Jason Lefever, Mitali Sanwal, Shreyansh Vyas, and Sirui Wang
            </p>
          </div>
        </div>
      </main>
    </div>
  );
}

export default PageHeading;
