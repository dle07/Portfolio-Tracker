import axios from 'axios';
import cookie from 'react-cookies';

const ALPHA_VANTAGE_API_KEY = "081RFR9FBMSD3GBN";





export const getUserAssets = () =>{
  const jwt_token = cookie.load("jwt_token");

  axios({
    method:'get',
    url:'http://localhost:8080/assets/get_assets_summary',
    headers:{'Authorization':'Bearer ' + jwt_token},
  })
  .then( (HttpResponse) =>{
    console.log(HttpResponse.data);
    return HttpResponse.data;
  })
  .catch(e=>{
    
  })


}

export const transactionsOptions = () =>{
  return <>
    <option value = "Buy" selected>Buy</option>,
    <option value = "Sell">Sell</option>
  </>
  
}

