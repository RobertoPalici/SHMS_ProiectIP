import React, { useState, useEffect, useRef } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
import APIRequest from '../APIRequest';

export type ChoreList = {
  name : string;
  description: string;
  personID: number;
  duration: number;
  imageSrc: string | null;
}

export type ChoreProps = {
  choreList : ChoreList[]
}

interface MidProps{
  chores: ChoreProps;
  newChore?: string;
  newDesc?: string;
  handleSubmit: (e : React.FormEvent<HTMLFormElement>) => void;
  handleDelete: (name : string) => void;
  setNewChore: React.Dispatch<React.SetStateAction<string>>;
  setNewDesc: React.Dispatch<React.SetStateAction<string>>;
}

const ChoreList: React.FC<MidProps> = ({chores, handleDelete, handleSubmit, newChore, setNewChore, newDesc, setNewDesc}) => {

  const inputRef = useRef<HTMLInputElement>(null);
  const inputRef2 = useRef<HTMLInputElement>(null);

  const {choreList} = chores;

  const handleButton = () => {
    console.log(chores);
    setTimeout(() => {
      if(inputRef.current) {inputRef.current.value = '';}
    }, 30);
  };

  return (
    <div className={styles.midsection}>
          <form className={styles.formStyle} onSubmit={handleSubmit}>
            
            <input
              autoFocus
              ref={inputRef}
              type="text"
              placeholder="Title"
              required
              value={newChore}
              onChange={(e) => setNewChore(e.target.value)}
            />
            <input
              autoFocus
              ref={inputRef2}
              type="text"
              placeholder="Details"
              
              value={newDesc}
              onChange={(e) => setNewDesc(e.target.value)}
            />
            <button type="submit" onClick={handleButton}>Add chore</button>
          </form>
        <div className={styles.chores_css}>
        {choreList?.map((chore, index) => (
          <div className={styles.choreTile} key={index}>
            <img src={placeholder} alt="" />
            <div className={styles.bottom}>
              <div className={styles.textStyle}>
                <h1>{chore.name}</h1>
                <div className={styles.details}>
                  <h2>Details</h2>
                  <p>{chore.description}</p>
                </div>
              </div>
              <button className={styles.buttonStyle} onClick={() => handleDelete(chore.name)}>Remove</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  )

}
export default ChoreList;