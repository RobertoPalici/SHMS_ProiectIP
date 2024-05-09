import React, { useState, useEffect, useRef } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
import APIRequest from '../APIRequest';

export type ChoresList = {
  name : string | null;
  description: string;
  personID: number;
  duration: number;
  imageSrc: string | null;
}

export type ChoreProps = {
  choresList : ChoresList[];
}

interface ChoreDeclareProps {
    handleDelete: (name : string) => void;
    handleSubmit?: (e: React.FormEvent<HTMLFormElement>) => void ;
    newChore ?: string;
    setNewChore ?: React.Dispatch<React.SetStateAction<string>>;
    newDesc ?: number;
    setNewDesc?: React.Dispatch<React.SetStateAction<string>>;
}

const Chore: React.FC<ChoresList & ChoreDeclareProps> = ({name, description, duration, personID, imageSrc, handleSubmit, handleDelete, newChore, setNewChore, newDesc, setNewDesc}) => {

    const inputRef = useRef<HTMLInputElement>(null);
    const inputRef2 = useRef<HTMLInputElement>(null);

    const handleButton = () => {
        setTimeout(() => {
          if(inputRef.current) {inputRef.current.value = '';}
        }, 30);
      };

    return (
        <>
            {imageSrc === null && (
            <form className={styles.formStyle} onSubmit={handleSubmit}>
                <input
                autoFocus
                ref={inputRef}
                type="text"
                placeholder="Title"
                required
                value={newChore}
                onChange={(e) => {if(setNewChore) setNewChore(e.target.value)}}
                />
                <input
                autoFocus
                ref={inputRef2}
                type="text"
                placeholder="Details"
                value={newDesc}
                onChange={(e) => {if(setNewDesc) setNewDesc(e.target.value)}}
                />
                <button type="submit" onClick={handleButton}>Add chore</button>
            </form>)}

            {imageSrc!== null && (
            <>
            <img src={placeholder} alt="" />
            <div className={styles.bottom}>
                <div className={styles.textStyle}>
                    <h1>{name}</h1>
                    <div className={styles.details}>
                        <h2>Details</h2>
                        <p>{description}</p>
                    </div>
                </div>
                <button className={styles.buttonStyle} onClick={() => {if(name) handleDelete(name)}}>Remove</button>
            </div>
            </>)}
        </>
    )
}

export default Chore;