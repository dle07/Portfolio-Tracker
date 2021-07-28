import React, { useState, useEffect } from "react";
import { Switch, Route, Link, useHistory} from "react-router-dom";
import { Register } from "./Register";
import '../CSS/login.css';
import axios from 'axios';
import cookie from 'react-cookies';
import { saveJwtToCookie } from "../Utility Functions/JwtUtility";

const validCredentialInput = (username, password) => {
    return (username !=="" && password !== "")? true : false;
}

export const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [hidePassword, setHidePassword] = useState(true);
    
    let history = useHistory();
    useEffect(() => {                                   // Same as component did mount, only occurs once, upon first render
        document.title = "Portfolio Tracker"
     }, []);
    

    
    const hideShowHandler = () =>{
        setHidePassword((hidePassword) => !hidePassword);
    }
    const usernameHandler = (e) =>{
        setUsername(e.target.value);
        console.log(username);
    }

    const submitHandler = (e) =>{   //Call the authenticate endpoint 
        e.preventDefault();
        if(validCredentialInput(username, password) === false ){
            window.alert("Not all credential fields are filled");
            return;
        }

        axios({
            method:'post',
            url:'http://localhost:8080/user/authenticate',
            data:{
                username: username,
                password : password
            },
        })
        .then((HttpResponse)=>{
            saveJwtToCookie(HttpResponse);   //Utility Function
            console.log(cookie.loadAll());
            console.log('Printing jwtToken' +  cookie.load("jwt_token"));
            history.push('/home');
        })
        .catch((error) => {
            console.log(error);
        });
        
    }


    return (
        <div >

            <form className="LoginBox">
                <label for="username_box">Username:</label><br/>
                <input 
                    type="text" 
                    id="username_box" 
                    onChange = { (e) => usernameHandler(e)}/>
                <br></br>
        

                <label for="password_box">Password:</label><br/>
                <input 
                    onChange = { (e)=>{setPassword(e.target.value)} } 
                    type={hidePassword === true ? "password" : "text"} 
                    id="password_box"
                />
                 
                <input type="checkbox"  id = "hide_box" onClick = { () => {hideShowHandler()}} />
                <label for = "hide_box" >hide/show</label>
                <br></br>
                <button type = "submit" onClick = { (e) => submitHandler(e) }>Login</button>
                <Link to="/user/register" className="btn btn-primary">Sign up</Link>
            </form>
            
            
            <RouteInformation />
        </div>
    );
};

const RouteInformation = () => {
    return (
        <Switch>
            <Route path="/user/register">
                <Register />
            </Route>
        </Switch>
    );
};