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
    const [edit, setEdit] = useState(true);

    const handleButton = () => {
        setTimeout(() => {
          if(inputRef.current) {inputRef.current.value = '';}
        }, 30);
      };
    const handleEdit = () => {
        setEdit(!edit);
        console.log(name);
        if(name != undefined && setNewChore){
            console.log(name);
            console.log(newChore);
            setNewChore(name);
            console.log(newChore);
        }
        console.log('EDITAM');
    }
    const handleSubmitEdit = () => {
        handleEdit();
    }

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
                <input
                autoFocus
                ref={inputRef2}
                type="text"
                placeholder="Deadline"
                value={2}
                onChange={(e) => {if(setNewDesc) setNewDesc(e.target.value)}}
                />
                <button type="submit" onClick={handleButton}>Add chore</button>
                
            </form>)}
            {imageSrc!== null && (
            <>
            <img src={placeholder} alt="" />
            {edit &&
            <div className={styles.bottom}>
                <div className={styles.textStyle}>

                    <h1>{name}</h1>
                    <div className={styles.details}>
                        <h2>Details</h2>
                        <p>{description}</p>
                    </div>
                    <div className ={styles.duration}>
                        <h2>Deadline: </h2>
                        <p>Marti la 10am</p>

                    </div>
                </div>
                <div className={styles.buttonContainer}>
                <button className={styles.buttonStyle} onClick={() => {if(name) handleDelete(name)}}>Remove</button>
                <button className={styles.button2Style} onClick={handleEdit}>Edit</button>
                <button className={styles.button3Style}>Done</button>
                </div>
                
            </div>}
            {!edit && 
            <div className={styles.bottom}>
            <div className={styles.textStyle}>
                <form className={styles.editStyles1}>
                <input
                type="text"
                placeholder="pulapizda"
                value={newChore}
                onChange={(e) => {if(setNewDesc) setNewDesc(e.target.value)}}
                />
                </form>
                <div className={styles.details}>
                    <h2>Details</h2>
                    <form className={styles.editStyles}>
                <input
                type="text"
                placeholder="cacapula"
                
                />
                </form>
                    
                </div>
                <div className ={styles.duration}>
                    <h2>Deadline: </h2>
                    <form className={styles.editStyles}>
                <input
                type="text"
                placeholder="caca"
                />
                </form>

                </div>
            </div>
            <div className={styles.buttonContainer}>
            <button className={styles.buttonStyle} onClick={() => {if(name) handleDelete(name)}}>Remove</button>
            <button className={styles.button2Style}>Return</button>
            <button className={styles.button4Style}>Confirm</button>
            </div>
            
        </div>
            }
            </>)}
        </>
    )
}

export default Chore;