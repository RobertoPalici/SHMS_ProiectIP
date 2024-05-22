import React, { useState, useEffect, useRef } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
import APIRequest from '../APIRequest';

export type ChoresList = {
  name : string | null;
  description: string;
  personID: number;
  duration: string;
  imageSrc: string | null;
}

export type ChoreProps = {
  choresList : ChoresList[];
}

interface ChoreDeclareProps {
    onData:(updatedChore: string) => void;
    addChore: (name: string, description: string, duration: string, addToHistoryCheck: number) => void;
    handleDelete: (name : string) => void;
    handleSubmit?: (e: React.FormEvent<HTMLFormElement>) => void ;
    handleSubmitUpdate?: (e: React.FormEvent<HTMLFormElement>, oldName: string) => void ;
    handleUpdate: (oldName: string, updatedName: string, description: string, duration: string) => void;
    newChore?: string;
    setNewChore?: React.Dispatch<React.SetStateAction<string>>;
    newDesc?: string;
    setNewDesc?: React.Dispatch<React.SetStateAction<string>>;
    newDuration?: string;
    setNewDuration?: React.Dispatch<React.SetStateAction<string>>;
    updatedChore?: string;
    setUpdatedChore: React.Dispatch<React.SetStateAction<string>>;
    updatedDesc?: string;
    setUpdatedDesc: React.Dispatch<React.SetStateAction<string>>;
    updatedDuration?: string;
    setUpdatedDuration: React.Dispatch<React.SetStateAction<string>>;
}

const HistoryChore: React.FC<ChoresList & ChoreDeclareProps> = ({onData, name, description, duration, personID, imageSrc, addChore, handleSubmit, handleSubmitUpdate, handleDelete, handleUpdate, newChore, setNewChore, newDesc, setNewDesc, newDuration, setNewDuration, updatedChore, setUpdatedChore, updatedDesc, setUpdatedDesc, updatedDuration, setUpdatedDuration}) => {

    const handleAddToHistory = (name: string, desc: string, duration: string) => {
        addChore(name, desc, duration, 0);
    }
    return (
        <>
            {imageSrc!== null && (
            <>
            <img src={placeholder} alt="" />
            <div className={styles.hbottom}>
                <div className={styles.htextStyle}>

                    <h3>{name}</h3>
                    <div className={styles.hdetails}>
                        <h3>Details: </h3>
                        <p>{description}</p>
                    </div>
                    <div className ={styles.hduration}>
                        <h3>Deadline: </h3>
                        <p>{duration}</p>

                    </div>
                </div>
                <div className={styles.buttonContainer}>
                <button className={styles.addFromHistory} onClick={() => {if(name) handleAddToHistory(name, description, duration)}}>Add Chore</button>
                <button className={styles.removeFromHistory}>Clear Chore</button>
                </div>
            </div>
            </>)}
        </>
    )
}

export default HistoryChore;