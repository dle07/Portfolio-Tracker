import React from 'react'

export const DisplayStock = (props) => {
  
  return (
    <div className = "DisplayStock">
      {props.stock.ticker+"  -  " + props.stock.quantity + "              $" + props.stock.total_valuation }
    </div>
  )
}
