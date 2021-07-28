import React from 'react'

export const RenderPastTransactions = (props) => {

  return (
    <div className = "transaction_box_wrapper">

      {props.transactions.map( (transaction,index) =>{
        let transaction_string = transaction.transaction_type  +" "+ transaction.ticker +"----"+ transaction.quantity +"    Shares @$" + transaction.value_at_transaction + "--------"+transaction.transaction_date.substring(0,10);
        return(

          <div className = "transaction_box" key = {index}>
          {transaction_string}
          </div>
        );
        
      })}
    </div>
  )
}
