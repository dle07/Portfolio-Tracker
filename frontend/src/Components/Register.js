import React, { useState } from 'react'
import '../CSS/register.css';
import axios from 'axios';
import cookie from 'react-cookies';
import { saveJwtToCookie } from '../Utility Functions/JwtUtility';
import { useHistory } from 'react-router-dom';

const isValidRegistrationInput = ( username, password, confirmPassword) =>{
  if(username !== "" && password !== "" && confirmPassword !== ""  && password === confirmPassword ) return true;
  return false;
}


export const Register = () => {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  let history = useHistory();

  const usernameHandler = (e) =>{
    setUsername(e.target.value);
  }
  const passwordHandler = (e) =>{
    setPassword(e.target.value);
  }
  const confirmPasswordHandler = (e) =>{
    setConfirmPassword(e.target.value);
  }

  
  const submissionHandler = (e) =>
  { 
    e.preventDefault();
    if( isValidRegistrationInput(username,password,confirmPassword)){  // If all registration fields aren't empty and passwords match we can make the call
      axios({
        method:'post',
        url:'http://localhost:8080/user/register',
        data:{
          username:username,
          password:password
        },
      })
      .then((HttpResponse) => {
        saveJwtToCookie(HttpResponse);
        console.log(cookie.load("jwt_token"));
        history.push('/home');
        
      })
      .catch( (error) =>{
        window.alert(error);
      })
    }
    else if( username ==="" || password ==="" || confirmPassword === ""){
        window.alert("Registration Fields Are Empty");
    }else{
        window.alert("Passwords Don't match")
    }
  }

  

  return (
    <div>
      <form id = "form_box">

      <label for = "username_box">Username:</label> <br/>
      <input type id = "username_box" onChange = { (e) => usernameHandler(e)}></input>{conditonalAsterix(username)}
      <br />

      <label for = "password_box">Password:</label><br/>
      <input type = "password_box" onChange = {(e) => passwordHandler(e)}></input>{conditonalAsterix(password)}
      <br />

      <label for = "confirm_password_box" >Comfirm Password:</label>  <br/>
      <input type = "confirm_password_box" onChange = {(e) => confirmPasswordHandler(e)}></input>{conditonalAsterix(confirmPassword)}
      <br />

      <button onClick = {(e) => submissionHandler(e)}>Register</button>
      </form>
    </div>
  )
}


const conditonalAsterix = ( field ) =>{

  return <text className= "asterix">{field === "" ? "*" : ""}</text>
}