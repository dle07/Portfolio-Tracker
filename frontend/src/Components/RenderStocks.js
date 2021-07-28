import React from 'react'
import { DisplayStock } from './DisplayStock';

export const RenderStocks = (props) => {
  
  return (
    <>
      {props.stocks.map( (stock, index ) => <DisplayStock stock = {stock} key = {index}/>)}
    </>
  )
}
