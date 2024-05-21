import React, { useState, useEffect, useRef } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
import APIRequest from '../APIRequest';
import Chore, { ChoreProps } from './Chore';

interface MidProps{
  chores: ChoreProps;
  handleSubmit: (e : React.FormEvent<HTMLFormElement>) => void;
  handleSubmitUpdate: (e : React.FormEvent<HTMLFormElement>, oldName: string) => void;
  handleDelete: (name : string) => void;
  handleUpdate: (oldName: string, updatedName: string, description: string, duration: string) => void;
  setNewChore: React.Dispatch<React.SetStateAction<string>>;
  setNewDesc: React.Dispatch<React.SetStateAction<string>>;
  setNewDuration: React.Dispatch<React.SetStateAction<string>>;
  setUpdatedChore: React.Dispatch<React.SetStateAction<string>>;
  setUpdatedDesc: React.Dispatch<React.SetStateAction<string>>;
  setUpdatedDuration: React.Dispatch<React.SetStateAction<string>>;
}

const ChoreList: React.FC<MidProps> = ({chores, handleDelete, handleSubmit, handleSubmitUpdate, handleUpdate, setNewChore, setNewDesc, setNewDuration, setUpdatedChore, setUpdatedDesc, setUpdatedDuration}) => {

  const {choresList} = chores;

  return (
    <div className={styles.midsection}>
          <Chore
              name = {null}
              imageSrc={null}
              description={"No details"}
              personID={-1}
              duration={"No deadline"}
              handleSubmit={handleSubmit}
              handleSubmitUpdate={handleSubmitUpdate}
              handleDelete={handleDelete}
              handleUpdate={handleUpdate}
              setNewChore={setNewChore}
              setNewDesc={setNewDesc}
              setNewDuration={setNewDuration}
              setUpdatedChore={setUpdatedChore}
              setUpdatedDuration={setUpdatedDuration}
              setUpdatedDesc={setUpdatedDesc}
            />
            <button className={styles.historyButton}>History</button>
        <div className={styles.chores_css}>
        {choresList?.map((chore, index) => (
          <div className={styles.choreTile} key={index}>
            <Chore
              name = {chore.name}
              imageSrc={placeholder}
              description={chore.description}
              personID={chore.personID}
              duration={chore.duration}
              handleSubmit={handleSubmit}
              handleSubmitUpdate={handleSubmitUpdate}
              handleDelete={handleDelete}
              handleUpdate={handleUpdate}
              setNewChore={setNewChore}
              setNewDesc={setNewDesc}
              setNewDuration={setNewDuration}
              updatedChore={chore.name ?? ''}
              updatedDesc={chore.description}
              updatedDuration={chore.duration}
              setUpdatedChore={setUpdatedChore}
              setUpdatedDuration={setUpdatedDuration}
              setUpdatedDesc={setUpdatedDesc}
            />
          </div>
        ))}
      </div>
    </div>
  )

}
export default ChoreList;