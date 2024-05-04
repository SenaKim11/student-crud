import React, {Component} from 'react';
import {Navbar, NavbarBrand} from 'reactstrap';
import {Link} from 'react-router-dom';

export default class AppNavbar extends Component {
    constructor(props) {
        super(props);
        this.state = {isOpen: false};
        this.toggle = this.toggle.bind(this);
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    render() {
        return <div style={{position: 'relative'}}>
                    <Navbar color="dark" dark expand="md">
                        <NavbarBrand tag={Link} to="/" style={{marginLeft: 10}}>Home</NavbarBrand>
                        <p style={{position: 'absolute', color: 'white', right: 30, top: 16}}>Kim Julianne B. Sena - IT3A</p>
                    </Navbar>
                </div>
    }
}