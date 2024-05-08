import React, { useState, useEffect } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
export const Mid = () =>{
    const [title, setTitle] = useState('');
    const [details, setDetails] = useState('');
    const [choreTiles, setChoreTiles] = useState<{ title: string; details: string; }[]>([]);;
    
    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        let newChoreTile;
        if(title.trim() !==''){
            if(details.trim() ===''){
            newChoreTile = {title, details: "No details" };
            console.log(details);
            } else{
        newChoreTile = { title, details };}
        if (choreTiles.length > 0) {
            setChoreTiles([...choreTiles.slice(0, choreTiles.length - 1), newChoreTile]);
          } else {
            setChoreTiles([newChoreTile]);
          }
        setChoreTiles([...choreTiles, newChoreTile]);
        console.log('Title: ', title);
        console.log('Details: ', details);

        } else {
            alert('Please enter a title for the chore');
        }

    }
    const removeTile = (index: number) => {
        setChoreTiles(prev => [...prev.slice(0, index), ...prev.slice(index + 1)]);
    };
    return(
        <div className={styles.midsection}>
        <form className={styles.formStyle} onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <input
          type="text"
          placeholder="Details"
          value={details}
          onChange={(e) => setDetails(e.target.value)}
        />
        <button type="submit">Add chore</button>
      </form>
        <div className={styles.chores}>
        {choreTiles.map((chore, index) => (
          <div className={styles.choreTile} key={index}>
            <img src={placeholder} alt="" />
            <div className={styles.bottom}>
            <div className={styles.textStyle}>
              <h1>{chore.title}</h1>
              <div className={styles.details}>
              <h2>Details</h2>
              <p>{chore.details}</p>
              </div>
              </div>
              
              <button className={styles.buttonStyle} onClick={ () =>removeTile(index)}>Remove</button>
              </div>
            
          </div>
        ))}
      </div>
      
    </div>
  );
};
