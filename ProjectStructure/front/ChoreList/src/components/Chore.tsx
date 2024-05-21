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
    handleSubmitUpdate?: (e: React.FormEvent<HTMLFormElement>, oldName: string) => void ;
    handleUpdate: (oldName: string, updatedName: string, description: string, duration: number) => void;
    newChore?: string;
    setNewChore?: React.Dispatch<React.SetStateAction<string>>;
    newDesc?: string;
    setNewDesc?: React.Dispatch<React.SetStateAction<string>>;
    newDuration?: number;
    setNewDuration?: React.Dispatch<React.SetStateAction<number>>;
    updatedChore?: string;
    setUpdatedChore: React.Dispatch<React.SetStateAction<string>>;
    updatedDesc?: string;
    setUpdatedDesc: React.Dispatch<React.SetStateAction<string>>;
    updatedDuration?: number;
    setUpdatedDuration: React.Dispatch<React.SetStateAction<number>>;
}

const Chore: React.FC<ChoresList & ChoreDeclareProps> = ({name, description, duration, personID, imageSrc, handleSubmit, handleSubmitUpdate, handleDelete, handleUpdate, newChore, setNewChore, newDesc, setNewDesc, newDuration, setNewDuration, updatedChore, setUpdatedChore, updatedDesc, setUpdatedDesc, updatedDuration, setUpdatedDuration}) => {

    const inputRef = useRef<HTMLInputElement>(null);
    const inputRef2 = useRef<HTMLInputElement>(null);
    const inputRef3 = useRef<HTMLInputElement>(null);
    const [edit, setEdit] = useState(true);
    const [inputValue, setInputValue] = useState(name || '');
    const [inputValue2, setInputValue2] = useState(description || '');
    const [inputValue3, setInputValue3] = useState(duration || -1);

    const handleButton = () => {
        setTimeout(() => {
          if(inputRef.current) {inputRef.current.value = '';}
        }, 30);
    };

    const handleButton2 = () => {
        setTimeout(() => {
            if(inputRef.current) {inputRef.current.value = '';}
        }, 30);
    }

    const handleEdit = () => {
        setEdit(!edit);
        if(name !== null && setNewChore){
            setNewChore(name);
        }
    }
    const handleSubmitEdit = (e : React.FormEvent<HTMLFormElement>, oldName: string) => {
        e.preventDefault();
        handleEdit();
        if(handleSubmitUpdate){
            console.log('AM INTTRAT');
            handleSubmitUpdate(e, oldName);}
        console.log(updatedChore);
    }
    useEffect(() => {
        setUpdatedChore(name || '');
        setUpdatedDesc(description || '');
        setUpdatedDuration(duration || 0);
    }, [name, description, duration]);

    const handleInputChange = (e : React.ChangeEvent<HTMLInputElement>, index: number) => {
        switch(index){
            case 0:
                setUpdatedChore(e.target.value);
                break;
            case 1:
                setUpdatedDesc(e.target.value);
                break;
            case 2:
                setUpdatedDuration(parseInt(e.target.value));
                break;
        }
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
                ref={inputRef3}
                type="text"
                placeholder="Deadline"
                value={newDuration}
                onChange={(e) => {if(setNewDuration) setNewDuration(parseInt(e.target.value))}}
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
                        <h2>Details: </h2>
                        <p>{description}</p>
                    </div>
                    <div className ={styles.duration}>
                        <h2>Deadline: </h2>
                        <p>{duration}</p>

                    </div>
                </div>
                <div className={styles.buttonContainer}>
                <button className={styles.buttonStyle} onClick={() => {if(name) handleDelete(name)}}>Remove</button>
                <button className={styles.button2Style} onClick={handleEdit}>Edit</button>
                <button className={styles.button3Style}>Done</button>
                </div>
                
            </div>}
            {!edit && <>
            <form className={styles.editStyles} onSubmit={(e) => {if(name) handleSubmitEdit(e,name)}}>
            <div className={styles.bottom}>
                <div className={styles.textStyle}>
                    <input
                    type="text"
                    placeholder="Name"
                    value={updatedChore}
                    ref={inputRef}
                    onChange={(e) => {if(setUpdatedChore) setUpdatedChore(e.target.value)}}
                    />
                    <div className={styles.details}>
                        <h2>Details: </h2>
                            <input
                            type="text"
                            placeholder="Details"
                            value={updatedDesc}
                            ref={inputRef2}
                            onChange={(e) => {if(setUpdatedDesc) setUpdatedDesc(e.target.value)}}
                            />
                    </div>
                    <div className ={styles.duration}>
                        <h2>Deadline: </h2>
                        <input
                        type="text"
                        placeholder="Deadline"
                        value={updatedDuration}
                        ref={inputRef3}
                        onChange={(e) => {if(setUpdatedDuration) setUpdatedDuration(parseInt(e.target.value))}}
                        />
                    </div>
                </div>
                <div className={styles.buttonContainer}>
                    <button className={styles.buttonStyle} onClick={() => {if(name) handleDelete(name)}}>Remove</button>
                    <button className={styles.button2Style} onClick={handleEdit}>Return</button>
                    <button className={styles.button4Style} type="submit" onClick={handleButton2}>Confirm</button>
                </div>
            </div>
            </form>
            </>}
            </>)}
        </>
    )
}

export default Chore;