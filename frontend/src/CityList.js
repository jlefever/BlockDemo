import React from 'react';

class CityList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            error: null,
            isLoaded: false,
            cities: []
        };
    }

    componentDidMount() {
        fetch("/api/cities")
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        cities: result
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )
    }

    render() {
        const { error, isLoaded, cities } = this.state;
        if (error) {
            return <div>Sorry! We could not load cities at this time.</div>;
        } else if (!isLoaded) {
            return <div>Loading...</div>;
        } else {
            return (
                <ul>
                    {cities.map(city => (<li key={city.name}>{city.name}, {city.state}</li>))}
                </ul>
            );
        }
    }
}

export default CityList;
