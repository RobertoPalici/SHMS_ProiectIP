import React, { useState, useEffect, useRef } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
import APIRequest from '../APIRequest';
import Chore, { ChoreProps, ChoresList } from './Chore';
import HistoryChore from './HistoryChore';

interface MidProps{
  chores: ChoreProps;
  choresHistory: ChoreProps;
  addChore: (name: string, description: string, duration: string, addToHistoryCheck: number) => void;
  handleSubmit: (e : React.FormEvent<HTMLFormElement>) => void;
  handleClearHistory: () => void;
  handleClearHistoryChore: (name: string) => void;
  handleSubmitUpdate: (e : React.FormEvent<HTMLFormElement>, oldName: string) => void;
  handleDelete: (name : string) => void;
  handleDone: (name : string) => void;
  handleUpdate: (oldName: string, updatedName: string, description: string, duration: string) => void;
  setNewChore: React.Dispatch<React.SetStateAction<string>>;
  setNewDesc: React.Dispatch<React.SetStateAction<string>>;
  setNewDuration: React.Dispatch<React.SetStateAction<string>>;
  setUpdatedChore: React.Dispatch<React.SetStateAction<string>>;
  setUpdatedDesc: React.Dispatch<React.SetStateAction<string>>;
  setUpdatedDuration: React.Dispatch<React.SetStateAction<string>>;
}

const ChoreList: React.FC<MidProps> = ({ 
  chores, 
  choresHistory, 
  addChore, 
  handleDone, 
  handleDelete, 
  handleClearHistory, 
  handleClearHistoryChore, 
  handleSubmit, 
  handleSubmitUpdate, 
  handleUpdate, 
  setNewChore, 
  setNewDesc, 
  setNewDuration, 
  setUpdatedChore, 
  setUpdatedDesc, 
  setUpdatedDuration
}) => {

  const {choresList} = chores;
  const choresListHistory: ChoresList[] = choresHistory.choresList;
  const [history, setHistory] = useState(false)
  const [confirmHistory, setConfirmHistory] = useState(false)

  const handleConfirmHistory = () => {
    setConfirmHistory(!confirmHistory);
  }

  const handleHistory = () => {
    setHistory(!history);
  }

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
        handleDone={handleDone}
        setNewChore={setNewChore}
        setNewDesc={setNewDesc}
        setNewDuration={setNewDuration}
        setUpdatedChore={setUpdatedChore}
        setUpdatedDuration={setUpdatedDuration}
        setUpdatedDesc={setUpdatedDesc}
      />
      <button className={styles.historyButton} onClick={handleHistory}>History</button>
      {history && 
        <div className={styles.historyContainer}>
          <h2>Chore History</h2>
          {confirmHistory && 
            <button className={styles.clearHistory} onClick={handleConfirmHistory}>Clear History</button>
          }
          {!confirmHistory && 
            <div className={styles.confirmContainer}>
              <h3>Are you sure you want to clear your whole history?</h3>
              <div className={styles.clearHistoryButtons}>
                <button onClick={() => {handleClearHistory(); handleConfirmHistory();}}>Yes</button>
                <button onClick={handleConfirmHistory}>No</button>
              </div>
            </div>
          }
          <div className={styles.history_chores_css}>
            {choresListHistory?.map((chore, index) => (
              <div className={styles.hChoreTile} key={index}>
                <HistoryChore
                  name = {chore.name}
                  imageSrc={placeholder}
                  description={chore.description}
                  duration={chore.duration}
                  addChore={addChore}
                  handleClearHistoryChore={handleClearHistoryChore}
                  /*updatedChore={chore.name ?? ''} 
                  updatedDesc={chore.description}
                  updatedDuration={chore.duration}*/
                />
              </div>
            ))}
          </div>
        </div>
      }
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
              handleDone={handleDone}
              setNewChore={setNewChore}
              setNewDesc={setNewDesc}
              setNewDuration={setNewDuration}
              /*updatedChore={chore.name ?? ''} 
              updatedDesc={chore.description}
              updatedDuration={chore.duration}*/
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