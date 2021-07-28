import React from 'react'
import NavBar from './NavBar'
import axios from 'axios';
import { transactionsOptions } from '../Component Functions/HomeFunctions.js';
import { useState, useEffect } from 'react';
import cookie from 'react-cookies';
import { RenderStocks } from './RenderStocks';

export const Home = () => {
  const ALPHA_VANTAGE_API_KEY = process.env.ALPHA_VANTAGE_API_KEY;
  const [assets, setAssets] = useState({ cash:0,
                                        all_stocks_and_valuations:[],
                                        total_portfolio_value:0,
                                        assets_value:0,
                                        });

  const [tickerOptions, setTickerOptions] = useState([]); 
  const [transactionType, setTransactionType] = useState("Buy");
  const [quantity, setQuantity] = useState(0);
  const [ticker, setTicker] = useState("");



  useEffect(() => {
    getUserAssets();
  }, [])

  useEffect(() => {

  }, [tickerOptions])

  const getOptions = (ticker) =>{
    const url = `https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=${ticker}&apikey=${ALPHA_VANTAGE_API_KEY}`
    //api, gets response, loops through the json data, creates a new array mapping only the information we need
    axios({
      method:'get',
      url:url,
    })
    .then( (res) =>{
      let result = res.data["bestMatches"].map( (i )=> ({ ticker: i["1. symbol"], name: i["2. name"], } ));
      setTickerOptions(result);

    })
    .catch( e => {
      window.alert(e);
    });
  }

  const getUserAssets = () =>{
    const jwt_token = cookie.load("jwt_token");
  
    axios({
      method:'get',
      url:'http://localhost:8080/assets/get_assets_summary',
      headers:{'Authorization':'Bearer ' + jwt_token},
    })
    .then( (HttpResponse) =>{
      let assets = HttpResponse.data;
      setAssets(assets);
    })
    .catch(e=>{
      
    })
  
  
  }

  const processTransaction = () =>{
    const jwt_token = cookie.load("jwt_token");
    let url = "";

    if( transactionType === "Buy"  ){
      url = "http://localhost:8080/assets/purchase_stock";
    }else{
      url = "http://localhost:8080/assets/sell_stock";
    }
    axios({
      method:'post',
      url:url,
      headers:{'Authorization':'Bearer ' + jwt_token},
      data:{
        ticker:ticker,
        quantity:quantity
      }
    })
    .then(response =>{
      setAssets(response.data);
    })
    .catch(error =>{
      console.log(error);
    })
  }

  return (
    <div>


    <NavBar />
      <h3>Cash: ${assets.cash}</h3>

      <form>


      <input type = "text" placeholder="Ticker" onChange = {e => setTicker(e.target.value)} value = {ticker}></input>

      <button onClick = { (e) => {e.preventDefault(); 
                                  getOptions(ticker);}}>Search</button>
      <br></br>

      
      <label>
        <select onChange = {e => setTicker(e.target.value)}>


        {
          tickerOptions.map((match, index) => <option key = {index} value={match.ticker}>{"$"+ match.ticker}{"       |       " +match.name}</option>)
        
      }
        

        </select>
      </label>  

        <br></br>
        <input type = "number" onChange = {(e)=>{setQuantity(e.target.value)}} placeholder="Quantity" min = "0" inputValue = {quantity}></input>
        <select onChange = {e => {setTransactionType(e.target.value); console.log( transactionType ); }}>
        { transactionsOptions() }</select>

        <br></br>
        <button onClick = { e => { e.preventDefault(); processTransaction();}}>Process Transaction</button>
      </form>

       {assets.all_stocks_and_valuations.length !== 0? <RenderStocks stocks = {assets.all_stocks_and_valuations} /> : <></> }

    



    </div>
  )
  


}




