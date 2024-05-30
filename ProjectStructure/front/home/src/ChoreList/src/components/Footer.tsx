import React from 'react';
import './Footer.css'
import placeholder from './pictures/footerimage.jpg'

export const Footer = () => {
  return(
    <div className="containerStyle">
      <img src={placeholder} alt=""></img>
      <div className="textStyleFooterChores"> 
        <h1>Smart<br></br>Household<br></br>Management</h1>
        <div className="contact">
          <h2>Contact</h2>
          <p>info@household.com</p>
          <h2>+373 699 999 999</h2>
        </div>
      </div>
    </div>
  )
}