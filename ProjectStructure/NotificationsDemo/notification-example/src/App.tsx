import React from 'react';
import logo from './logo.svg';
import './App.css';

import { Bounce, ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const notifySuccess = () => {
  toast.success('Login Successful!', {
    position: "bottom-right",
    autoClose: 5000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: "light",
    transition: Bounce
    });
}

const notifyError = () => {
  toast.error('Incorrect Email or Password!', {
    position: "bottom-right",
    autoClose: 6000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: "light",
    transition: Bounce,
    });
}
const notifyInfo = () => {
  toast.info('E-mail Sent!', {
    position: "bottom-right",
    autoClose: 4000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: "light",
    transition: Bounce,
    });
}

const notifyCustom= () => {
  toast('Welcome!', {
    position: "bottom-right",
    autoClose: 4000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: "dark",
    transition: Bounce,
    });
}

function App() {
  return (
    <div className="App">
      <div>
        <button className="button" onClick={notifySuccess}>Notify me! (succes)</button>
        <button className="button" onClick={notifyError}>Notify me! (error)</button>
        <button className="button" onClick={notifyInfo}>Notify me! (info)</button>
        <button className="button" onClick={notifyCustom}>Notify me! (custom)</button>
      
        <ToastContainer
        position="bottom-right"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
        transition={Bounce}
        />

      </div>
    </div>
  );
}

export default App;
