import React from 'react';
import background from './pictures/image 3_vector.png'
import styles from './Top.module.css'
export const Top = () =>{
    return(
        <div className={styles.topContainer}>
            <img src ={background} alt =""></img>
            <h2>Chores List</h2>
        </div>
    )
}