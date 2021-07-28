import { BrowserRouter, Route } from "react-router-dom";
import { Home } from "./Components/Home";
import { Login } from "./Components/Login";
import { Register } from "./Components/Register";
import { Transactions } from './Components/Transactions';


function App() {
    return (
        
        <div className="App">
            {generateRoutes()}
            
        </div>
        
    );
}

const generateRoutes = () =>{
    return (
        <>
        <Route path = "/"               exact = {true}           component = {Login}></Route>
        <Route path = "/user/register/" exact = {true}           component = {Register}></Route>
        <Route path = "/home"           exact = {true}           component = {Home}></Route>
        <Route path = "/transactions"   exact = {true}           component = {Transactions}></Route>
        </>
        
    )
}


export default App;
