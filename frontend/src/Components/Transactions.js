import React from 'react'
import NavBar from './NavBar';
import  cookie  from 'react-cookies';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { RenderPastTransactions } from './RenderPastTransactions';

export const Transactions = () => {
  

    const [pastTransactions, setPastTransactions] = useState([]);


    useEffect(() => {

      const jwt_token = cookie.load("jwt_token");

      axios({
        method:'get',
        url : "http://localhost:8080/transactions/all",
        headers:{'Authorization':`Bearer ${jwt_token}`}
      })
      .then(httpResponse =>{
        console.log(httpResponse);
        setPastTransactions([...httpResponse.data]);
        console.log (pastTransactions);
      })
      .catch(e=>{
        console.log(e);
      })

    }, [])
  
  
  
    return (
      <div>

        <NavBar />
        <br></br>
        <RenderPastTransactions transactions = {pastTransactions}></RenderPastTransactions>



      </div>
    )
  
  
}
