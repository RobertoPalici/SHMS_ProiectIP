import React, { useState, useEffect, useRef } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
import APIRequest from '../APIRequest';
import Chore, { ChoreProps, ChoresList } from './Chore';
import HistoryChore from './HistoryChore';

interface MidProps{
  onData:(updatedChore: string) => void;
  chores: ChoreProps;
  choresHistory: ChoreProps;
  addChore: (name: string, description: string, duration: string, addToHistoryCheck: number) => void;
  handleSubmit: (e : React.FormEvent<HTMLFormElement>) => void;
  handleClearHistory: () => void;
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

const ChoreList: React.FC<MidProps> = ({onData, chores, choresHistory, addChore, handleDelete, handleClearHistory, handleSubmit, handleSubmitUpdate, handleUpdate, setNewChore, setNewDesc, setNewDuration, setUpdatedChore, setUpdatedDesc, setUpdatedDuration}) => {

  const {choresList} = chores;
  const choresListHistory: ChoresList[] = choresHistory.choresList;
  const [history, setHistory] = useState(false)
  const handleHistory = () => {
    setHistory(!history);
  }

  return (
    <div className={styles.midsection}>
          <Chore
              onData={onData}
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
             <button className={styles.historyButton} onClick={handleHistory}>History</button>
            {history && 
              <div className={styles.historyContainer}>
                <h2>Chore History</h2>
                <button onClick={handleClearHistory}>Clear History</button>
                <div className={styles.history_chores_css}>
                  {choresListHistory?.map((chore, index) => (
                    <div className={styles.hChoreTile} key={index}>
                      <HistoryChore
                        onData={onData}
                        name = {chore.name}
                        imageSrc={placeholder}
                        description={chore.description}
                        personID={chore.personID}
                        duration={chore.duration}
                        addChore={addChore}
                        handleSubmit={handleSubmit}
                        handleSubmitUpdate={handleSubmitUpdate}
                        handleDelete={handleDelete}
                        handleUpdate={handleUpdate}
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
            }
        <div className={styles.chores_css}>
        {choresList?.map((chore, index) => (
          <div className={styles.choreTile} key={index}>
            <Chore
              onData={onData}
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