import React from 'react';
import styles from './Footer.module.css'
import placeholder from './pictures/footerimage.jpg'

export const Footer = () => {
    return(
        <div className={styles.containerStyle}>
            <img src={placeholder} alt=""></img>
        <div className={styles.textStyle}> 
        <h1>Smart<br></br>Household<br></br>Management</h1>
        <div className={styles.contact}>
            <h2>Contact</h2>
            <p>info@household.com</p>
            <h2>+373 699 999 999</h2>
        </div>
        </div>
       
        </div>
    )
}