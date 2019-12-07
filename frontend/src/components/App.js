import React from "react";
import BlockList from "./BlockList";
import PageHeading from "./PageHeading";

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoaded: false,
      blocks: null
    };
  }

  componentDidMount() {
    this.refreshBlockchain();
  }

  refreshBlockchain = () => {
    console.log("Refreshing...")
    fetch("/ui/blockchain")
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            blocks: result
          });
        });
  }

  addBlock = () => {
    console.log("Calling mine...")
    postData("/ui/add");
    this.refreshBlockchain();
  }

  mineBlock = (index) => {
    console.log("Calling mine with index " + index + "...")
    console.log(index)
    postData("/ui/mine/" + index).then(res => this.refreshBlockchain());
  }

  render() {
    const { isLoaded, blocks } = this.state;
    console.log(this.state)
    if (!isLoaded) {
      return (<p>Loading...</p>)
    } else {
      return (
        <>
          <nav className="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
            <a className="navbar-brand" href="null">Blockchain Demo</a>
          </nav>
          <main>
            <PageHeading
              handleBroadcastClick={this.broadcast}
              handleRefreshClick={this.refreshBlockchain}
              handleAddBlockClick={this.addBlock}
            />
            <div className="container">
              <BlockList blocks={blocks} handleMineClick={this.mineBlock} />
            </div>
          </main>
        </>
      );
    }
  }
}

// From MDN
// https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
async function postData(url, data = {}) {
  // Default options are marked with *
  const response = await fetch(url, {
    method: 'POST', // *GET, POST, PUT, DELETE, etc.
    mode: 'cors', // no-cors, *cors, same-origin
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    credentials: 'same-origin', // include, *same-origin, omit
    headers: {
      'Content-Type': 'application/json'
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: 'follow', // manual, *follow, error
    referrer: 'no-referrer', // no-referrer, *client
    body: JSON.stringify(data) // body data type must match "Content-Type" header
  });
  return await response.json(); // parses JSON response into native JavaScript objects
}

export default App;